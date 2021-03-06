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
 * The Original Software is the JSwat Core Module. The Initial Developer of the
 * Software is Nathan L. Fiedler. Portions created by Nathan L. Fiedler
 * are Copyright (C) 2002-2010. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id$
 */
package com.bluemarsh.jswat.core.expr;

import com.bluemarsh.jswat.parser.node.Token;
import com.sun.jdi.BooleanValue;
import com.sun.jdi.ByteValue;
import com.sun.jdi.CharValue;
import com.sun.jdi.DoubleValue;
import com.sun.jdi.FloatValue;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.LongValue;
import com.sun.jdi.PrimitiveValue;
import com.sun.jdi.ShortValue;
import com.sun.jdi.StringReference;
import org.openide.util.NbBundle;

/**
 * Class OperatorNode is the base class for all operators.
 *
 * @author  Nathan Fiedler
 */
abstract class OperatorNode extends ParentNode {

    /**
     * Determines the type of 'o' and returns true if it is a boolean.
     *
     * @param  o  object to test.
     * @return  true if 'o' is a boolean.
     */
    public static boolean isBoolean(Object o) {
        return (o instanceof Boolean || o instanceof BooleanValue);
    }

    /**
     * Determines the type of 'o' and returns true if it is a character.
     *
     * @param  o  object to test.
     * @return  true if 'o' is a character.
     */
    public static boolean isCharacter(Object o) {
        return (o instanceof Character || o instanceof CharValue);
    }

    /**
     * Determines the type of 'o' and returns true if it is a double.
     *
     * @param  o  object to test.
     * @return  true if 'o' is a double.
     */
    public static boolean isDouble(Object o) {
        return (o instanceof Double || o instanceof DoubleValue);
    }

    /**
     * Determines the type of 'o' and returns true if it is a floating
     * point number.
     *
     * @param  o  object to test.
     * @return  true if 'o' is a number.
     */
    public static boolean isFloating(Object o) {
        return (o instanceof Double || o instanceof DoubleValue
                || o instanceof Float || o instanceof FloatValue);
    }

    /**
     * Determines the type of 'o' and returns true if it is a long.
     *
     * @param  o  object to test.
     * @return  true if 'o' is a long.
     */
    public static boolean isLong(Object o) {
        return (o instanceof Long || o instanceof LongValue);
    }

    /**
     * Determines the type of 'o' and returns true if it is some type of
     * number, either integral, floating, or character.
     *
     * @param  o  object to test.
     * @return  true if 'o' is a numeric value.
     */
    public static boolean isNumber(Object o) {
        return (o instanceof Number || o instanceof ByteValue
                || o instanceof DoubleValue || o instanceof FloatValue
                || o instanceof IntegerValue || o instanceof LongValue
                || o instanceof ShortValue || o instanceof Character
                || o instanceof CharValue);
    }

    /**
     * Determines the type of 'o' and returns true if it is either a
     * String or a StringReference.
     *
     * @param  o  object to test.
     * @return  true if 'o' is a numeric value.
     */
    public static boolean isString(Object o) {
        return (o instanceof String || o instanceof StringReference);
    }

    /**
     * Constructs a OperatorNode associated with the given token.
     *
     * @param  node  lexical token.
     */
    OperatorNode(Token node) {
        super(node);
    }

    /**
     * Returns the boolean value from the given object.
     *
     * @param  o  object that had better be a Boolean type.
     * @return  boolean value of object.
     * @throws  EvaluationException
     *          if o is not a boolean type.
     */
    public boolean getBooleanValue(Object o) throws EvaluationException {
        if (o instanceof Boolean) {
            return ((Boolean) o).booleanValue();
        } else if (o instanceof BooleanValue) {
            return ((BooleanValue) o).value();
        } else {
            throw new EvaluationException(
                    NbBundle.getMessage(getClass(), "error.oper.bool"), getToken(), o);
        }
    }

    /**
     * Returns the char value from the given object.
     *
     * @param  o  object that had better be a Character type.
     * @return  char value of object.
     * @throws  EvaluationException
     *          if o is not a character type.
     */
    public char getCharValue(Object o) throws EvaluationException {
        if (o instanceof Character) {
            return ((Character) o).charValue();
        } else if (o instanceof CharValue) {
            return ((CharValue) o).value();
        } else {
            throw new EvaluationException(
                    NbBundle.getMessage(getClass(), "error.oper.char"), getToken(), o);
        }
    }

    /**
     * Returns the double value from the given object.
     *
     * @param  o  object that had better be a number.
     * @return  double value of object.
     * @throws  EvaluationException
     *          if o is not a number.
     */
    public double getDoubleValue(Object o) throws EvaluationException {
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        } else if (o instanceof Character) {
            return (double) ((Character) o).charValue();
        } else if (o instanceof PrimitiveValue) {
            return ((PrimitiveValue) o).doubleValue();
        } else {
            throw new EvaluationException(
                    NbBundle.getMessage(getClass(), "error.oper.floating"), getToken(), o);
        }
    }

    /**
     * Returns the float value from the given object.
     *
     * @param  o  object that had better be a number.
     * @return  float value of object.
     * @throws  EvaluationException
     *          if o is not a number.
     */
    public float getFloatValue(Object o) throws EvaluationException {
        if (o instanceof Number) {
            return ((Number) o).floatValue();
        } else if (o instanceof Character) {
            return (float) ((Character) o).charValue();
        } else if (o instanceof PrimitiveValue) {
            return ((PrimitiveValue) o).floatValue();
        } else {
            throw new EvaluationException(
                    NbBundle.getMessage(getClass(), "error.oper.floating"), getToken(), o);
        }
    }

    /**
     * Returns the integer value from the given object.
     *
     * @param  o  object that had better be an integral value.
     * @return  integer value of object.
     * @throws  EvaluationException
     *          if o is not an integral number.
     */
    public int getIntValue(Object o) throws EvaluationException {
        if (o instanceof Number) {
            return ((Number) o).intValue();
        } else if (o instanceof Character) {
            return (int) ((Character) o).charValue();
        } else if (o instanceof PrimitiveValue) {
            return ((PrimitiveValue) o).intValue();
        } else {
            throw new EvaluationException(
                    NbBundle.getMessage(getClass(), "error.oper.intval"), getToken(), o);
        }
    }

    /**
     * Returns the long value from the given object.
     *
     * @param  o  object that had better be an integral value.
     * @return  long value of object.
     * @throws  EvaluationException
     *          if o is not an integral number.
     */
    public long getLongValue(Object o) throws EvaluationException {
        if (o instanceof Number) {
            return ((Number) o).longValue();
        } else if (o instanceof Character) {
            return (long) ((Character) o).charValue();
        } else if (o instanceof PrimitiveValue) {
            return ((PrimitiveValue) o).longValue();
        } else {
            throw new EvaluationException(
                    NbBundle.getMessage(getClass(), "error.oper.intval"), getToken(), o);
        }
    }

    /**
     * Returns the String value from the given object.
     *
     * @param  o  object that had better be an integral value.
     * @return  long value of object.
     * @throws  EvaluationException
     *          if o is not an integral number.
     */
    public String getStringValue(Object o) throws EvaluationException {
        if (o instanceof String) {
            return (String) o;
        } else if (o instanceof StringReference) {
            return ((StringReference) o).value();
        } else {
            throw new EvaluationException(
                    NbBundle.getMessage(getClass(), "error.oper.strval"), getToken(), o);
        }
    }

    /**
     * Returns true if this operator does not do any operation but
     * instead acts as a sentinel, delineating portions of an
     * expression. This includes (), [], and commas.
     *
     * @return  true if sentinel, false otherwise.
     */
    public boolean isSentinel() {
        return false;
    }

    /**
     * Returns this operator's precedence value. The lower the value the
     * higher the precedence. The values are equivalent to those
     * described in the Java Language Reference book (2nd ed.), p 106.
     *
     * @return  precedence value.
     */
    public abstract int precedence();
}
