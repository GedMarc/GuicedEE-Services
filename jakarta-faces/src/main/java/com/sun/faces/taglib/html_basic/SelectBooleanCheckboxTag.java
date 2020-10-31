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
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.MethodExpressionValueChangeListener;
import javax.faces.validator.MethodExpressionValidator;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

/*
 * ******* GENERATED CODE - DO NOT EDIT *******
 */

public class SelectBooleanCheckboxTag extends UIComponentELTag {

    // Setter Methods
    // PROPERTY: converter
    private javax.el.ValueExpression converter;

    public void setConverter(javax.el.ValueExpression converter) {
        this.converter = converter;
    }

    // PROPERTY: converterMessage
    private javax.el.ValueExpression converterMessage;

    public void setConverterMessage(javax.el.ValueExpression converterMessage) {
        this.converterMessage = converterMessage;
    }

    // PROPERTY: immediate
    private javax.el.ValueExpression immediate;

    public void setImmediate(javax.el.ValueExpression immediate) {
        this.immediate = immediate;
    }

    // PROPERTY: required
    private javax.el.ValueExpression required;

    public void setRequired(javax.el.ValueExpression required) {
        this.required = required;
    }

    // PROPERTY: requiredMessage
    private javax.el.ValueExpression requiredMessage;

    public void setRequiredMessage(javax.el.ValueExpression requiredMessage) {
        this.requiredMessage = requiredMessage;
    }

    // PROPERTY: validator
    private javax.el.MethodExpression validator;

    public void setValidator(javax.el.MethodExpression validator) {
        this.validator = validator;
    }

    // PROPERTY: validatorMessage
    private javax.el.ValueExpression validatorMessage;

    public void setValidatorMessage(javax.el.ValueExpression validatorMessage) {
        this.validatorMessage = validatorMessage;
    }

    // PROPERTY: value
    private javax.el.ValueExpression value;

    public void setValue(javax.el.ValueExpression value) {
        this.value = value;
    }

    // PROPERTY: valueChangeListener
    private javax.el.MethodExpression valueChangeListener;

    public void setValueChangeListener(javax.el.MethodExpression valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    // PROPERTY: accesskey
    private javax.el.ValueExpression accesskey;

    public void setAccesskey(javax.el.ValueExpression accesskey) {
        this.accesskey = accesskey;
    }

    // PROPERTY: dir
    private javax.el.ValueExpression dir;

    public void setDir(javax.el.ValueExpression dir) {
        this.dir = dir;
    }

    // PROPERTY: disabled
    private javax.el.ValueExpression disabled;

    public void setDisabled(javax.el.ValueExpression disabled) {
        this.disabled = disabled;
    }

    // PROPERTY: label
    private javax.el.ValueExpression label;

    public void setLabel(javax.el.ValueExpression label) {
        this.label = label;
    }

    // PROPERTY: lang
    private javax.el.ValueExpression lang;

    public void setLang(javax.el.ValueExpression lang) {
        this.lang = lang;
    }

    // PROPERTY: onblur
    private javax.el.ValueExpression onblur;

    public void setOnblur(javax.el.ValueExpression onblur) {
        this.onblur = onblur;
    }

    // PROPERTY: onchange
    private javax.el.ValueExpression onchange;

    public void setOnchange(javax.el.ValueExpression onchange) {
        this.onchange = onchange;
    }

    // PROPERTY: onclick
    private javax.el.ValueExpression onclick;

    public void setOnclick(javax.el.ValueExpression onclick) {
        this.onclick = onclick;
    }

    // PROPERTY: ondblclick
    private javax.el.ValueExpression ondblclick;

    public void setOndblclick(javax.el.ValueExpression ondblclick) {
        this.ondblclick = ondblclick;
    }

    // PROPERTY: onfocus
    private javax.el.ValueExpression onfocus;

    public void setOnfocus(javax.el.ValueExpression onfocus) {
        this.onfocus = onfocus;
    }

    // PROPERTY: onkeydown
    private javax.el.ValueExpression onkeydown;

    public void setOnkeydown(javax.el.ValueExpression onkeydown) {
        this.onkeydown = onkeydown;
    }

    // PROPERTY: onkeypress
    private javax.el.ValueExpression onkeypress;

    public void setOnkeypress(javax.el.ValueExpression onkeypress) {
        this.onkeypress = onkeypress;
    }

    // PROPERTY: onkeyup
    private javax.el.ValueExpression onkeyup;

    public void setOnkeyup(javax.el.ValueExpression onkeyup) {
        this.onkeyup = onkeyup;
    }

    // PROPERTY: onmousedown
    private javax.el.ValueExpression onmousedown;

    public void setOnmousedown(javax.el.ValueExpression onmousedown) {
        this.onmousedown = onmousedown;
    }

    // PROPERTY: onmousemove
    private javax.el.ValueExpression onmousemove;

    public void setOnmousemove(javax.el.ValueExpression onmousemove) {
        this.onmousemove = onmousemove;
    }

    // PROPERTY: onmouseout
    private javax.el.ValueExpression onmouseout;

    public void setOnmouseout(javax.el.ValueExpression onmouseout) {
        this.onmouseout = onmouseout;
    }

    // PROPERTY: onmouseover
    private javax.el.ValueExpression onmouseover;

    public void setOnmouseover(javax.el.ValueExpression onmouseover) {
        this.onmouseover = onmouseover;
    }

    // PROPERTY: onmouseup
    private javax.el.ValueExpression onmouseup;

    public void setOnmouseup(javax.el.ValueExpression onmouseup) {
        this.onmouseup = onmouseup;
    }

    // PROPERTY: onselect
    private javax.el.ValueExpression onselect;

    public void setOnselect(javax.el.ValueExpression onselect) {
        this.onselect = onselect;
    }

    // PROPERTY: readonly
    private javax.el.ValueExpression readonly;

    public void setReadonly(javax.el.ValueExpression readonly) {
        this.readonly = readonly;
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

    // PROPERTY: tabindex
    private javax.el.ValueExpression tabindex;

    public void setTabindex(javax.el.ValueExpression tabindex) {
        this.tabindex = tabindex;
    }

    // PROPERTY: title
    private javax.el.ValueExpression title;

    public void setTitle(javax.el.ValueExpression title) {
        this.title = title;
    }

    // General Methods
    @Override
    public String getRendererType() {
        return "javax.faces.Checkbox";
    }

    @Override
    public String getComponentType() {
        return "javax.faces.HtmlSelectBooleanCheckbox";
    }

    @Override
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        javax.faces.component.UISelectBoolean selectboolean = null;
        try {
            selectboolean = (javax.faces.component.UISelectBoolean) component;
        } catch (ClassCastException cce) {
            throw new IllegalStateException("Component " + component.toString()
                    + " not expected type.  Expected: javax.faces.component.UISelectBoolean.  Perhaps you're missing a tag?");
        }

        if (converter != null) {
            if (!converter.isLiteralText()) {
                selectboolean.setValueExpression("converter", converter);
            } else {
                Converter conv = FacesContext.getCurrentInstance().getApplication().createConverter(converter.getExpressionString());
                selectboolean.setConverter(conv);
            }
        }

        if (converterMessage != null) {
            selectboolean.setValueExpression("converterMessage", converterMessage);
        }
        if (immediate != null) {
            selectboolean.setValueExpression("immediate", immediate);
        }
        if (required != null) {
            selectboolean.setValueExpression("required", required);
        }
        if (requiredMessage != null) {
            selectboolean.setValueExpression("requiredMessage", requiredMessage);
        }
        if (validator != null) {
            selectboolean.addValidator(new MethodExpressionValidator(validator));
        }
        if (validatorMessage != null) {
            selectboolean.setValueExpression("validatorMessage", validatorMessage);
        }
        if (value != null) {
            selectboolean.setValueExpression("value", value);
        }
        if (valueChangeListener != null) {
            selectboolean.addValueChangeListener(new MethodExpressionValueChangeListener(valueChangeListener));
        }
        if (accesskey != null) {
            selectboolean.setValueExpression("accesskey", accesskey);
        }
        if (dir != null) {
            selectboolean.setValueExpression("dir", dir);
        }
        if (disabled != null) {
            selectboolean.setValueExpression("disabled", disabled);
        }
        if (label != null) {
            selectboolean.setValueExpression("label", label);
        }
        if (lang != null) {
            selectboolean.setValueExpression("lang", lang);
        }
        if (onblur != null) {
            selectboolean.setValueExpression("onblur", onblur);
        }
        if (onchange != null) {
            selectboolean.setValueExpression("onchange", onchange);
        }
        if (onclick != null) {
            selectboolean.setValueExpression("onclick", onclick);
        }
        if (ondblclick != null) {
            selectboolean.setValueExpression("ondblclick", ondblclick);
        }
        if (onfocus != null) {
            selectboolean.setValueExpression("onfocus", onfocus);
        }
        if (onkeydown != null) {
            selectboolean.setValueExpression("onkeydown", onkeydown);
        }
        if (onkeypress != null) {
            selectboolean.setValueExpression("onkeypress", onkeypress);
        }
        if (onkeyup != null) {
            selectboolean.setValueExpression("onkeyup", onkeyup);
        }
        if (onmousedown != null) {
            selectboolean.setValueExpression("onmousedown", onmousedown);
        }
        if (onmousemove != null) {
            selectboolean.setValueExpression("onmousemove", onmousemove);
        }
        if (onmouseout != null) {
            selectboolean.setValueExpression("onmouseout", onmouseout);
        }
        if (onmouseover != null) {
            selectboolean.setValueExpression("onmouseover", onmouseover);
        }
        if (onmouseup != null) {
            selectboolean.setValueExpression("onmouseup", onmouseup);
        }
        if (onselect != null) {
            selectboolean.setValueExpression("onselect", onselect);
        }
        if (readonly != null) {
            selectboolean.setValueExpression("readonly", readonly);
        }
        if (role != null) {
            selectboolean.setValueExpression("role", role);
        }
        if (style != null) {
            selectboolean.setValueExpression("style", style);
        }
        if (styleClass != null) {
            selectboolean.setValueExpression("styleClass", styleClass);
        }
        if (tabindex != null) {
            selectboolean.setValueExpression("tabindex", tabindex);
        }
        if (title != null) {
            selectboolean.setValueExpression("title", title);
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
        converter = null;
        converterMessage = null;
        immediate = null;
        required = null;
        requiredMessage = null;
        validator = null;
        validatorMessage = null;
        value = null;
        valueChangeListener = null;

        // rendered attributes
        accesskey = null;
        dir = null;
        disabled = null;
        label = null;
        lang = null;
        onblur = null;
        onchange = null;
        onclick = null;
        ondblclick = null;
        onfocus = null;
        onkeydown = null;
        onkeypress = null;
        onkeyup = null;
        onmousedown = null;
        onmousemove = null;
        onmouseout = null;
        onmouseover = null;
        onmouseup = null;
        onselect = null;
        readonly = null;
        role = null;
        style = null;
        styleClass = null;
        tabindex = null;
        title = null;
    }

    public String getDebugString() {
        return "id: " + getId() + " class: " + this.getClass().getName();
    }

}
