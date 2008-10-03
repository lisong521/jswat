/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is JSwat. The Initial Developer of the Original
 * Software is Nathan L. Fiedler. Portions created by Nathan L. Fiedler
 * are Copyright (C) 2004-2006. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: LaunchDebuggeeAction.java 6 2007-05-16 07:14:24Z nfiedler $
 */

package com.bluemarsh.jswat.ui.actions;

import com.bluemarsh.jswat.core.connect.JvmConnection;
import com.bluemarsh.jswat.core.session.Session;
import com.bluemarsh.jswat.core.session.SessionFactory;
import com.bluemarsh.jswat.core.session.SessionManager;
import com.bluemarsh.jswat.core.session.SessionProvider;
import com.bluemarsh.jswat.core.util.Processes;
import com.bluemarsh.jswat.ui.ActionEnabler;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import com.bluemarsh.jswat.ui.components.LaunchDebuggeePanel;
import com.sun.jdi.connect.VMStartException;

/**
 * Provides a means for launching a debuggee from within the debugger.
 *
 * @author Nathan Fiedler
 */
public class LaunchDebuggeeAction extends CallableSystemAction {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of LaunchDebuggeeAction.
     */
    public LaunchDebuggeeAction() {
        ActionEnabler ae = ActionEnabler.getDefault();
        ae.registerInactiveAction(this);
    }

    /**
     * Indicates if this action can be invoked on any thread.
     *
     * @return  true if asynchronous, false otherwise.
     */
    protected boolean asynchronous() {
        // performAction() should run in event thread
        return false;
    }

    /**
     * Returns the help context for this action.
     *
     * @return  help context.
     */
    public HelpCtx getHelpCtx() {
        return new HelpCtx("jswat-launch-debuggee");
    }

    /**
     * Returns the name of this action.
     *
     * @return  name of action.
     */
    public String getName() {
        return NbBundle.getMessage(getClass(), "LBL_LaunchDebuggeeAction");
    }

    /**
     * Specify the proper resource name for the action's icon.
     *
     * @return  the resource name for the icon.
     */
    protected String iconResource() {
        return NbBundle.getMessage(getClass(), "IMG_LaunchDebuggeeAction");
    }

    /**
     * Performs the action.
     */
    public void performAction() {
        LaunchDebuggeePanel ldp = new LaunchDebuggeePanel();
        SessionManager sm = SessionProvider.getSessionManager();
        Session session = sm.getCurrent();
        ldp.loadParameters(session);
        // Display dialog and get the user response.
        if (ldp.display()) {
            if (session.isConnected()) {
                // See if the user wants to open a new session.
                String msg = NbBundle.getMessage(getClass(), "MSG_ActivateNewSession");
                NotifyDescriptor desc = new NotifyDescriptor.Confirmation(msg);
                Object ans = DialogDisplayer.getDefault().notify(desc);
                if (ans == NotifyDescriptor.YES_OPTION) {
                    SessionFactory sf = SessionProvider.getSessionFactory();
                    String id = sm.generateIdentifier();
                    session = sf.createSession(id);
                    sm.add(session);
                    sm.setCurrent(session);
                } else if (ans == NotifyDescriptor.CANCEL_OPTION) {
                    return;
                } else {
                    session.disconnect(true);
                }
            }

            // Regardless of whether or not an error occurs below, save the
            // settings the user provided. Must do this before building the
            ldp.saveParameters(session);
            try {
                JvmConnection connection = ldp.buildConnection(session);
                connection.connect();
                session.connect(connection);
                if (ldp.shouldResume()) {
                    session.resumeVM();
                }
            } catch (VMStartException vmse) {
                // This can happen when the user enters an unknown JVM option
                // and the debuggee returns immediately with an error.
                // We must read the output from the JVM and display whatever
                // error messages it provided, along with the exception.
                Process proc = vmse.process();
                String output = Processes.waitFor(proc);
                String msg = NbBundle.getMessage(
                        getClass(), "ERR_LaunchFailed", vmse.toString() +
                        '\n' + output);
                NotifyDescriptor desc = new NotifyDescriptor.Message(
                    msg, NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notify(desc);
            } catch (Exception e) {
                String msg = NbBundle.getMessage(
                        getClass(), "ERR_LaunchFailed", e.toString());
                NotifyDescriptor desc = new NotifyDescriptor.Message(
                    msg, NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notify(desc);
            }
        }
    }
}
