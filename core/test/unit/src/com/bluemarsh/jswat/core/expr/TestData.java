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
 * are Copyright (C) 2003-2009. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id$
 */

package com.bluemarsh.jswat.core.expr;

/**
 * Structure to hold test parameters.
 *
 * @author Nathan Fiedler
 */
public class TestData {
    /** Expression to be evaluated. */
    public String expr;
    /** The expected result as the expected type. */
    public Object result;
    /** The error message to display if the expression does not
     * evaluate to be equal to the reslt. */
    public String message;
    /** True if the expression is expected to cause an exception. */
    public boolean fail;
    /** True if expression evaluator should do debugging. */
    public boolean debug;

    /**
     * Creates a TestData that evaluates the given expression, which is
     * assumed to fail (because it has no expected result).
     *
     * @param  expr  expression expected to fail.
     */
    public TestData(String expr) {
        this.expr = expr;
        fail = true;
    }

    /**
     * Creates a TestData for the given expression which is expected to
     * evaluate to the given result.
     *
     * @param  expr    expression to evaluate.
     * @param  result  expected result.
     */
    public TestData(String expr, Object result) {
        this.expr = expr;
        this.result = result;
    }

    /**
     * Creates a TestData for the given expression which is expected to
     * evaluate to the given result. Shows the message if test fails.
     *
     * @param  expr     expression to evaluate.
     * @param  result   expected result.
     * @param  message  displayed when expression fails.
     */
    public TestData(String expr, Object result, String message) {
        this.expr = expr;
        this.result = result;
        this.message = message;
    }

    /**
     * Creates a TestData for the given expression which is expected to
     * evaluate to the given result. Shows parsed syntax tree.
     *
     * @param  expr    expression to evaluate.
     * @param  result  expected result.
     * @param  debug   true to show the parsed syntax tree.
     */
    public TestData(String expr, Object result, boolean debug) {
        this.expr = expr;
        this.result = result;
        this.debug = debug;
    }
}
