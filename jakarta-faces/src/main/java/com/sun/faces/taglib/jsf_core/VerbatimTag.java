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

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

/**
 * <p>
 * Tag implementation that creates a {@link UIOutput} instance and allows the user to write raw markup.
 * </p>
 */

public class VerbatimTag extends UIComponentELTag {

    // ------------------------------------------------------------- Attributes

    private ValueExpression escape = null;

    public void setEscape(ValueExpression escape) {
        this.escape = escape;
    }

    /**
     * Holds value of property rendered.
     */
    private ValueExpression rendered;

    /**
     * Setter for property rendered.
     *
     * @param rendered New value of property rendered.
     */
    @Override
    public void setRendered(ValueExpression rendered) {

        this.rendered = rendered;
    }

    // --------------------------------------------------------- Public Methods

    @Override
    public String getRendererType() {
        return "javax.faces.Text";
    }

    @Override
    public String getComponentType() {
        return "javax.faces.Output";
    }

    @Override
    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        if (null != escape) {
            component.setValueExpression("escape", escape);
        } else {
            component.getAttributes().put("escape", Boolean.FALSE);
        }
        if (null != rendered) {
            component.setValueExpression("rendered", rendered);
        }
        component.setTransient(true);

    }

    /**
     * <p>
     * Set the local value of this component to reflect the nested body content of this JSP tag.
     * </p>
     */
    @Override
    public int doAfterBody() throws JspException {

        if (getBodyContent() != null) {
            String value = getBodyContent().getString();
            if (value != null) {
                UIOutput output = (UIOutput) getComponentInstance();
                output.setValue(value);
                getBodyContent().clearBody();
            }
        }
        return getDoAfterBodyValue();

    }

}
