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

package com.sun.faces.renderkit;

import static com.sun.faces.config.WebConfiguration.WebContextInitParameter.StateSavingMethod;

import java.io.IOException;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.RenderKitUtils.PredefinedPostbackParameter;
import com.sun.faces.util.RequestStateManager;

import javax.faces.FacesException;
import javax.faces.application.StateManager;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;

/**
 * <p>
 * A <code>ResonseStateManager</code> implementation for the default HTML render kit.
 */
public class ResponseStateManagerImpl extends ResponseStateManager {

    private StateHelper helper;

    public ResponseStateManagerImpl() {

        WebConfiguration webConfig = WebConfiguration.getInstance();
        String stateMode = webConfig.getOptionValue(StateSavingMethod);
        helper = StateManager.STATE_SAVING_METHOD_CLIENT.equalsIgnoreCase(stateMode) ? new ClientSideStateHelper() : new ServerSideStateHelper();

    }

    // --------------------------------------- Methods from ResponseStateManager

    /**
     * @see ResponseStateManager#isPostback(javax.faces.context.FacesContext)
     */
    @Override
    public boolean isPostback(FacesContext context) {

        return context.getExternalContext().getRequestParameterMap().containsKey(PredefinedPostbackParameter.VIEW_STATE_PARAM.getName(context));

    }

    @Override
    public String getCryptographicallyStrongTokenFromSession(FacesContext context) {
        return helper.getCryptographicallyStrongTokenFromSession(context);
    }

    /**
     * @see ResponseStateManager#getState(javax.faces.context.FacesContext, java.lang.String)
     */
    @Override
    public Object getState(FacesContext context, String viewId) {

        Object state = RequestStateManager.get(context, RequestStateManager.FACES_VIEW_STATE);
        if (state == null) {
            try {
                state = helper.getState(context, viewId);
                if (state != null) {
                    RequestStateManager.set(context, RequestStateManager.FACES_VIEW_STATE, state);
                }
            } catch (IOException e) {
                throw new FacesException(e);
            }
        }
        return state;

    }

    /**
     * @see ResponseStateManager#writeState(javax.faces.context.FacesContext, java.lang.Object)
     */
    @Override
    public void writeState(FacesContext context, Object state) throws IOException {

        helper.writeState(context, state, null);

    }

    /**
     * @see ResponseStateManager#getViewState(javax.faces.context.FacesContext, java.lang.Object)
     */
    @Override
    public String getViewState(FacesContext context, Object state) {

        StringBuilder sb = new StringBuilder(32);
        try {
            helper.writeState(context, state, sb);
        } catch (IOException e) {
            throw new FacesException(e);
        }
        return sb.toString();

    }

    @SuppressWarnings({ "deprecation" })
    @Override
    public Object getTreeStructureToRestore(FacesContext context, String viewId) {

        Object[] state = (Object[]) getState(context, viewId);
        if (state != null) {
            return state[0];
        }
        return null;

    }

    /**
     * @param facesContext the Faces context.
     * @param viewId the view id.
     * @return true if "stateless" was found, false otherwise.
     * @throws IllegalStateException when the request is not a postback.
     */
    @Override
    public boolean isStateless(FacesContext facesContext, String viewId) {
        return helper.isStateless(facesContext, viewId);
    }
}
