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
 * are Copyright (C) 2004-2007. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: SetCurrentAction.java 6 2007-05-16 07:14:24Z nfiedler $
 */

package com.bluemarsh.jswat.nodes.sessions;

import com.bluemarsh.jswat.core.session.Session;
import com.bluemarsh.jswat.core.session.SessionManager;
import com.bluemarsh.jswat.core.session.SessionProvider;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

/**
 * Implements the action of changing the current session.
 *
 * @author  Nathan Fiedler
 */
public class SetCurrentAction extends NodeAction {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    @Override
    protected boolean asynchronous() {
        return false;
    }

    protected boolean enable(Node[] activatedNodes) {
        if (activatedNodes != null && activatedNodes.length == 1) {
            // Make sure selected sessions are not marked as current.
            SessionManager sm = SessionProvider.getSessionManager();
            Session current = sm.getCurrent();
            Node n = activatedNodes[0];
            if (n instanceof SessionNode) {
                SessionNode sessionNode = (SessionNode) n;
                Session session = sessionNode.getSession();
                return session != current;
            }
        }
        return false;
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    public String getName() {
        return NbBundle.getMessage(SetCurrentAction.class,
                "LBL_SetCurrentSessionAction");
    }

    protected void performAction(Node[] activatedNodes) {
        SessionManager sm = SessionProvider.getSessionManager();
        Node n = activatedNodes[0];
        if (n instanceof SessionNode) {
            SessionNode sn = (SessionNode) n;
            Session session = sn.getSession();
            sm.setCurrent(session);
        }
    }
}
