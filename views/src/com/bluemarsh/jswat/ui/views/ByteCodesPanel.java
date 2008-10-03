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
 * are Copyright (C) 2006. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: ByteCodesPanel.java 6 2007-05-16 07:14:24Z nfiedler $
 */

package com.bluemarsh.jswat.ui.views;

import com.bluemarsh.jswat.core.util.Strings;
import com.sun.jdi.Location;
import org.apache.bcel.classfile.Code;
import java.util.Arrays;
import java.util.List;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * Displays the byte codes of the current method.
 *
 * @author  nfiedler
 */
public class ByteCodesPanel extends javax.swing.JPanel {
    /** silence compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form ByteCodesPanel.
     */
    public ByteCodesPanel() {
        initComponents();
    }

    /**
     * Clears the display area and file field, showing the error message
     * in the text field.
     *
     * @param  msg  the error message.
     */
    public void displayError(String msg) {
        hideLocation();
        fileTextField.setText(msg);
    }

    /**
     * Clear the display to hide any location information.
     */
    public void hideLocation() {
        differLabel.setEnabled(false);
        fileTextField.setText("");
        signatureTextField.setText("");
    }

    /**
     * Show the location information for the byte codes. Use the file to
     * show where the byte codes came from. Likewise, use the location to
     * determine if the codes match with the method in the debuggee, and
     * give an indication if not.
     *
     * @param  file      file from which byte codes were read.
     * @param  code      the byte codes to be displayed.
     * @param  location  the Location being displayed.
     */
    public void showLocation(FileObject file, Code code, Location location) {
        // Show where these byte codes came from.
//            FileObject jar = FileUtil.getArchiveFile(file);
//            StringBuilder path = new StringBuilder();
//            if (jar != null) {
//                // File was actually inside an archive.
//                path.append(jar.getNameExt());
//                path.append("!");
//            }
//            path.append(file.getNameExt());
//            fileLabel.setText(path.toString());
        String name = FileUtil.getFileDisplayName(file);
        fileTextField.setText(name);

        // Show the method signature since that is what the byte code
        // interpreter will show in the view.
        StringBuilder sign = new StringBuilder();
        sign.append(location.declaringType().name());
        sign.append('.');
        sign.append(location.method().name());
        sign.append(location.method().signature());
        signatureTextField.setText(sign.toString());

        // Indicate if the byte codes match the debuggee version.
        if (Arrays.equals(code.getCode(), location.method().bytecodes())) {
            differLabel.setEnabled(false);
        } else {
            differLabel.setEnabled(true);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        fileLabel = new javax.swing.JLabel();
        fileTextField = new javax.swing.JTextField();
        differLabel = new javax.swing.JLabel();
        signatureLabel = new javax.swing.JLabel();
        signatureTextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        fileLabel.setLabelFor(fileTextField);
        fileLabel.setText(java.util.ResourceBundle.getBundle("com/bluemarsh/jswat/ui/views/Forms").getString("LBL_ByteCodes_File"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(fileLabel, gridBagConstraints);

        fileTextField.setColumns(10);
        fileTextField.setEditable(false);
        fileTextField.setToolTipText(java.util.ResourceBundle.getBundle("com/bluemarsh/jswat/ui/views/Forms").getString("HINT_ByteCodes_FileLabel"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(fileTextField, gridBagConstraints);

        differLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bluemarsh/jswat/views/resources/warning.gif")));
        differLabel.setToolTipText(java.util.ResourceBundle.getBundle("com/bluemarsh/jswat/ui/views/Forms").getString("HINT_ByteCodes_Differ"));
        differLabel.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bluemarsh/jswat/views/resources/empty.gif")));
        differLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 3);
        add(differLabel, gridBagConstraints);

        signatureLabel.setLabelFor(signatureTextField);
        signatureLabel.setText(java.util.ResourceBundle.getBundle("com/bluemarsh/jswat/ui/views/Forms").getString("LBL_ByteCodes_Signature"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(signatureLabel, gridBagConstraints);

        signatureTextField.setColumns(10);
        signatureTextField.setEditable(false);
        signatureTextField.setToolTipText(java.util.ResourceBundle.getBundle("com/bluemarsh/jswat/ui/views/Forms").getString("HINT_ByteCodes_SignatureField"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 3);
        add(signatureTextField, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel differLabel;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JLabel signatureLabel;
    private javax.swing.JTextField signatureTextField;
    // End of variables declaration//GEN-END:variables
}
