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

package com.sun.faces.facelets.tag.ui;

import java.io.IOException;

import com.sun.faces.facelets.tag.TagHandlerImpl;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;

/**
 * @author Jacob Hookom
 */
public class ParamHandler extends TagHandlerImpl {

    private final TagAttribute name;

    private final TagAttribute value;

    /**
     * @param config
     */
    public ParamHandler(TagConfig config) {
        super(config);
        name = getRequiredAttribute("name");
        value = getRequiredAttribute("value");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sun.facelets.FaceletHandler#apply(com.sun.facelets.FaceletContext, javax.faces.component.UIComponent)
     */
    @Override
    public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
        String nameStr = name.getValue(ctx);
        ValueExpression valueVE = value.getValueExpression(ctx, Object.class);
        ctx.getVariableMapper().setVariable(nameStr, valueVE);
    }

}
