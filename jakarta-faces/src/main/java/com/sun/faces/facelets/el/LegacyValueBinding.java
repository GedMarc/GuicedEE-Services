/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.facelets.el;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;

/**
 *
 *
 * @author Jacob Hookom
 * @version $Id$
 * @deprecated
 */
@Deprecated
public final class LegacyValueBinding extends ValueBinding implements Externalizable {

    private static final long serialVersionUID = 1L;

    private ValueExpression delegate;

    public LegacyValueBinding() {
        super();
    }

    public LegacyValueBinding(ValueExpression ve) {
        delegate = ve;
    }

    @Override
    public Object getValue(FacesContext context) throws EvaluationException, PropertyNotFoundException {
        ELContext ctx = context.getELContext();
        try {
            return delegate.getValue(ctx);
        } catch (javax.el.PropertyNotFoundException e) {
            throw new PropertyNotFoundException(e.getMessage(), e.getCause());
        } catch (ELException e) {
            throw new EvaluationException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void setValue(FacesContext context, Object value) throws EvaluationException, PropertyNotFoundException {
        ELContext ctx = context.getELContext();
        try {
            delegate.setValue(ctx, value);
        } catch (PropertyNotWritableException | javax.el.PropertyNotFoundException e) {
            throw new PropertyNotFoundException(e.getMessage(), e.getCause());
        } catch (ELException e) {
            throw new EvaluationException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean isReadOnly(FacesContext context) throws EvaluationException, PropertyNotFoundException {
        ELContext ctx = context.getELContext();
        try {
            return delegate.isReadOnly(ctx);
        } catch (javax.el.PropertyNotFoundException e) {
            throw new PropertyNotFoundException(e.getMessage(), e.getCause());
        } catch (ELException e) {
            throw new EvaluationException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Class getType(FacesContext context) throws EvaluationException, PropertyNotFoundException {
        ELContext ctx = context.getELContext();
        try {
            return delegate.getType(ctx);
        } catch (javax.el.PropertyNotFoundException e) {
            throw new PropertyNotFoundException(e.getMessage(), e.getCause());
        } catch (ELException e) {
            throw new EvaluationException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        delegate = (ValueExpression) in.readObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(delegate);
    }

    @Override
    public String getExpressionString() {
        return delegate.getExpressionString();
    }
}
