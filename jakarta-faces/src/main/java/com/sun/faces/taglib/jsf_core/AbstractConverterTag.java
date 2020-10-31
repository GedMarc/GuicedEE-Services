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

package com.sun.faces.taglib.jsf_core;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.webapp.ConverterELTag;
import javax.servlet.jsp.JspException;

/**
 * <p>
 * Base class for all <code>ConverterTag<code>s.
 * </p>
 */
public class AbstractConverterTag extends ConverterELTag {

    private static final long serialVersionUID = -8219789624438804540L;

    private static final Logger LOGGER = FacesLogger.TAGLIB.getLogger();

    /**
     * <p>
     * The {@link javax.el.ValueExpression} that evaluates to an object that implements
     * {@link javax.faces.convert.Converter}.
     * </p>
     */
    protected ValueExpression binding = null;

    /**
     * <p>
     * The identifier of the {@link javax.faces.convert.Converter} instance to be created.
     * </p>
     */
    protected ValueExpression converterId = null;

    // ---------------------------------------------------------- Public Methods

    /**
     * <p>
     * Set the expression that will be used to create a {@link javax.el.ValueExpression} that references a backing bean
     * property of the {@link javax.faces.convert.Converter} instance to be created.
     * </p>
     *
     * @param binding The new expression
     */
    public void setBinding(ValueExpression binding) {

        this.binding = binding;

    }

    /**
     * <p>
     * Set the identifer of the {@link javax.faces.convert.Converter} instance to be created.
     *
     * @param converterId The identifier of the converter instance to be created.
     */
    public void setConverterId(ValueExpression converterId) {

        this.converterId = converterId;

    }

    // --------------------------------------------- Methods from ConverterELTag

    @Override
    protected Converter createConverter() throws JspException {

        try {
            return createConverter(converterId, binding, FacesContext.getCurrentInstance());
        } catch (FacesException fe) {
            throw new JspException(fe.getCause());
        }

    }

    protected static Converter createConverter(ValueExpression converterId, ValueExpression binding, FacesContext facesContext) {

        ELContext elContext = facesContext.getELContext();
        Converter converter = null;

        // If "binding" is set, use it to create a converter instance.
        if (binding != null) {
            try {
                converter = (Converter) binding.getValue(elContext);
                if (converter != null) {
                    return converter;
                }
            } catch (Exception e) {
                throw new FacesException(e);
            }
        }

        // If "converterId" is set, use it to create the converter
        // instance. If "converterId" and "binding" are both set, store the
        // converter instance in the value of the property represented by
        // the ValueExpression 'binding'.
        if (converterId != null) {
            try {
                String converterIdVal = (String) converterId.getValue(elContext);
                converter = facesContext.getApplication().createConverter(converterIdVal);
                if (converter != null && binding != null) {
                    binding.setValue(elContext, converter);
                }
            } catch (Exception e) {
                throw new FacesException(e);
            }
        }

        if (converter == null) {
            if (LOGGER.isLoggable(Level.WARNING)) {
                LOGGER.log(Level.WARNING, MessageUtils.getExceptionMessageString(MessageUtils.CANNOT_CONVERT_ID,
                        converterId != null ? converterId.getExpressionString() : "", binding != null ? binding.getExpressionString() : ""));
            }
        }

        return converter;

    }

}
