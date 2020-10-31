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

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

/**
 * <p>
 * This <code>Renderer</code> handles the rendering of <code>script</code> references.
 * </p>
 */
public class ScriptRenderer extends ScriptStyleBaseRenderer {

    @Override
    protected void startInlineElement(ResponseWriter writer, UIComponent component) throws IOException {
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", "type");
    }

    @Override
    protected void endInlineElement(ResponseWriter writer, UIComponent component) throws IOException {
        writer.endElement("script");
    }

    @Override
    protected void startExternalElement(ResponseWriter writer, UIComponent component) throws IOException {
        startInlineElement(writer, component);
    }

    @Override
    protected void endExternalElement(ResponseWriter writer, UIComponent component, String resourceUrl) throws IOException {
        writer.writeURIAttribute("src", resourceUrl, "src");
        endInlineElement(writer, component);
    }

}
