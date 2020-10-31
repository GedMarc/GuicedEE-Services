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

package com.sun.faces.renderkit.html_basic;

import java.io.IOException;

import com.sun.faces.config.FaceletsConfiguration;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

/**
 * /**
 * <p>
 * This <code>Renderer</code> is responsible for rendering the standard HTML head elements as well as rendering any
 * resources that should be output before the <code>head</code> tag is closed.
 * </p>
 */
public class HeadRenderer extends Renderer {

    private static final Attribute[] HEAD_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTPUTHEAD);

    @Override
    public void decode(FacesContext context, UIComponent component) {
        // no-op
    }

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("head", component);
        RenderKitUtils.renderPassThruAttributes(context, writer, component, HEAD_ATTRIBUTES);
        WebConfiguration webConfig = WebConfiguration.getInstance(context.getExternalContext());
        FaceletsConfiguration faceletsConfig = webConfig.getFaceletsConfiguration();
        if (faceletsConfig.isOutputHtml5Doctype(context.getViewRoot().getViewId())) {
            String clientId = component.getClientId(context);
            writer.writeAttribute("id", clientId, "clientId");
        }
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        // no-op
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        encodeHeadResources(context);
        writer.endElement("head");
    }

    // --------------------------------------------------------- Private Methods

    private void encodeHeadResources(FacesContext context) throws IOException {

        UIViewRoot viewRoot = context.getViewRoot();
        for (UIComponent resource : viewRoot.getComponentResources(context, "head")) {
            resource.encodeAll(context);
        }

    }

}
