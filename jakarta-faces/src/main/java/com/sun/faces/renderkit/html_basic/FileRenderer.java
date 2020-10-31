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

// FileRenderer.java

package com.sun.faces.renderkit.html_basic;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.sun.faces.renderkit.RenderKitUtils;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class FileRenderer extends TextRenderer {

    // ---------------------------------------------------------- Public Methods

    @Override
    public void decode(FacesContext context, UIComponent component) {

        rendererParamsNotNull(context, component);

        if (!shouldDecode(component)) {
            return;
        }

        String clientId = decodeBehaviors(context, component);

        if (clientId == null) {
            clientId = component.getClientId(context);
        }

        assert clientId != null;
        ExternalContext externalContext = context.getExternalContext();
        Map<String, String> requestMap = externalContext.getRequestParameterMap();

        if (requestMap.containsKey(clientId)) {
            setSubmittedValue(component, requestMap.get(clientId));
        }

        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            Collection<Part> parts = request.getParts();
            for (Part cur : parts) {
                if (clientId.equals(cur.getName())) {
                    // The cause of 3404 is here: the component should not be
                    // transient, rather, the value should not saved as part of
                    // the state
                    // component.setTransient(true);
                    setSubmittedValue(component, cur);
                }
            }
        } catch (IOException | ServletException ioe) {
            throw new FacesException(ioe);
        }

    }

    // If we are in Project Stage Development mode, the parent form
    // must have an enctype of "multipart/form-data" for this component.
    // If not, produce a message.
    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (context.isProjectStage(ProjectStage.Development)) {
            boolean produceMessage = false;
            UIForm form = RenderKitUtils.getForm(component, context);
            if (null != form) {
                String encType = (String) form.getAttributes().get("enctype");
                if (null == encType || !encType.equals("multipart/form-data")) {
                    produceMessage = true;
                }
            } else {
                produceMessage = true;
            }

            if (produceMessage) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "File upload component requires a form with an enctype of multipart/form-data",
                        "File upload component requires a form with an enctype of multipart/form-data");
                context.addMessage(component.getClientId(context), message);
            }
        }
        super.encodeBegin(context, component);
    }

    @Override
    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
        if (submittedValue instanceof Part) {
            Part part = (Part) submittedValue;
            if ((part.getHeader("content-disposition") == null || part.getHeader("content-disposition").endsWith("filename=\"\"")) && part.getSize() <= 0) {
                return null;
            }
        }
        return submittedValue;
    }

}
