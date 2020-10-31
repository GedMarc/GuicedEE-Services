/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
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

package com.sun.faces.taglib.html_basic;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

/*
 * ******* GENERATED CODE - DO NOT EDIT *******
 */

public class MessageTag extends UIComponentELTag {

    // Setter Methods
    // PROPERTY: for
    private javax.el.ValueExpression _for;

    public void setFor(javax.el.ValueExpression _for) {
        this._for = _for;
    }

    // PROPERTY: showDetail
    private javax.el.ValueExpression showDetail;

    public void setShowDetail(javax.el.ValueExpression showDetail) {
        this.showDetail = showDetail;
    }

    // PROPERTY: showSummary
    private javax.el.ValueExpression showSummary;

    public void setShowSummary(javax.el.ValueExpression showSummary) {
        this.showSummary = showSummary;
    }

    // PROPERTY: dir
    private javax.el.ValueExpression dir;

    public void setDir(javax.el.ValueExpression dir) {
        this.dir = dir;
    }

    // PROPERTY: errorClass
    private javax.el.ValueExpression errorClass;

    public void setErrorClass(javax.el.ValueExpression errorClass) {
        this.errorClass = errorClass;
    }

    // PROPERTY: errorStyle
    private javax.el.ValueExpression errorStyle;

    public void setErrorStyle(javax.el.ValueExpression errorStyle) {
        this.errorStyle = errorStyle;
    }

    // PROPERTY: fatalClass
    private javax.el.ValueExpression fatalClass;

    public void setFatalClass(javax.el.ValueExpression fatalClass) {
        this.fatalClass = fatalClass;
    }

    // PROPERTY: fatalStyle
    private javax.el.ValueExpression fatalStyle;

    public void setFatalStyle(javax.el.ValueExpression fatalStyle) {
        this.fatalStyle = fatalStyle;
    }

    // PROPERTY: infoClass
    private javax.el.ValueExpression infoClass;

    public void setInfoClass(javax.el.ValueExpression infoClass) {
        this.infoClass = infoClass;
    }

    // PROPERTY: infoStyle
    private javax.el.ValueExpression infoStyle;

    public void setInfoStyle(javax.el.ValueExpression infoStyle) {
        this.infoStyle = infoStyle;
    }

    // PROPERTY: lang
    private javax.el.ValueExpression lang;

    public void setLang(javax.el.ValueExpression lang) {
        this.lang = lang;
    }

    // PROPERTY: role
    private javax.el.ValueExpression role;

    public void setRole(javax.el.ValueExpression role) {
        this.role = role;
    }

    // PROPERTY: style
    private javax.el.ValueExpression style;

    public void setStyle(javax.el.ValueExpression style) {
        this.style = style;
    }

    // PROPERTY: styleClass
    private javax.el.ValueExpression styleClass;

    public void setStyleClass(javax.el.ValueExpression styleClass) {
        this.styleClass = styleClass;
    }

    // PROPERTY: title
    private javax.el.ValueExpression title;

    public void setTitle(javax.el.ValueExpression title) {
        this.title = title;
    }

    // PROPERTY: tooltip
    private javax.el.ValueExpression tooltip;

    public void setTooltip(javax.el.ValueExpression tooltip) {
        this.tooltip = tooltip;
    }

    // PROPERTY: warnClass
    private javax.el.ValueExpression warnClass;

    public void setWarnClass(javax.el.ValueExpression warnClass) {
        this.warnClass = warnClass;
    }

    // PROPERTY: warnStyle
    private javax.el.ValueExpression warnStyle;

    public void setWarnStyle(javax.el.ValueExpression warnStyle) {
        this.warnStyle = warnStyle;
    }

    // General Methods
    @Override
    public String getRendererType() {
        return "javax.faces.Message";
    }

    @Override
    public String getComponentType() {
        return "javax.faces.HtmlMessage";
    }

    @Override
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        javax.faces.component.UIMessage message = null;
        try {
            message = (javax.faces.component.UIMessage) component;
        } catch (ClassCastException cce) {
            throw new IllegalStateException(
                    "Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UIMessage.  Perhaps you're missing a tag?");
        }

        if (_for != null) {
            message.setValueExpression("for", _for);
        }
        if (showDetail != null) {
            message.setValueExpression("showDetail", showDetail);
        }
        if (showSummary != null) {
            message.setValueExpression("showSummary", showSummary);
        }
        if (dir != null) {
            message.setValueExpression("dir", dir);
        }
        if (errorClass != null) {
            message.setValueExpression("errorClass", errorClass);
        }
        if (errorStyle != null) {
            message.setValueExpression("errorStyle", errorStyle);
        }
        if (fatalClass != null) {
            message.setValueExpression("fatalClass", fatalClass);
        }
        if (fatalStyle != null) {
            message.setValueExpression("fatalStyle", fatalStyle);
        }
        if (infoClass != null) {
            message.setValueExpression("infoClass", infoClass);
        }
        if (infoStyle != null) {
            message.setValueExpression("infoStyle", infoStyle);
        }
        if (lang != null) {
            message.setValueExpression("lang", lang);
        }
        if (role != null) {
            message.setValueExpression("role", role);
        }
        if (style != null) {
            message.setValueExpression("style", style);
        }
        if (styleClass != null) {
            message.setValueExpression("styleClass", styleClass);
        }
        if (title != null) {
            message.setValueExpression("title", title);
        }
        if (tooltip != null) {
            message.setValueExpression("tooltip", tooltip);
        }
        if (warnClass != null) {
            message.setValueExpression("warnClass", warnClass);
        }
        if (warnStyle != null) {
            message.setValueExpression("warnStyle", warnStyle);
        }
    }

    // Methods From TagSupport
    @Override
    public int doStartTag() throws JspException {
        try {
            return super.doStartTag();
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }
            throw new JspException(root);
        }
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            return super.doEndTag();
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }
            throw new JspException(root);
        }
    }

    // RELEASE
    @Override
    public void release() {
        super.release();

        // component properties
        _for = null;
        showDetail = null;
        showSummary = null;

        // rendered attributes
        dir = null;
        errorClass = null;
        errorStyle = null;
        fatalClass = null;
        fatalStyle = null;
        infoClass = null;
        infoStyle = null;
        lang = null;
        role = null;
        style = null;
        styleClass = null;
        title = null;
        tooltip = null;
        warnClass = null;
        warnStyle = null;
    }

    public String getDebugString() {
        return "id: " + getId() + " class: " + this.getClass().getName();
    }

}
