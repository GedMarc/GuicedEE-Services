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

package com.sun.faces.facelets.tag.jsf.core;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.UIViewRoot;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;

/**
 * <p>
 * This is a specialized <code>FacetHandler</code> to enable <code>f:metadata</code> support.
 *
 * </p>
 */
public class MetadataHandler extends TagHandlerImpl {

    private static final Logger LOGGER = FacesLogger.TAGLIB.getLogger();

    // ------------------------------------------------------------ Constructors

    public MetadataHandler(TagConfig config) {
        super(config);
    }

    // ------------------------------------------------- Methods from TagHandler

    @Override
    public void apply(FaceletContext ctx, UIComponent parent) throws IOException {

        Util.notNull("parent", parent);
        UIViewRoot root;
        if (parent instanceof UIViewRoot) {
            root = (UIViewRoot) parent;
        } else {
            root = ctx.getFacesContext().getViewRoot();
        }
        if (root == null) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE, "jsf.metadata.uiviewroot.unavailable");
            }
            return;
        }

        UIComponent facetComponent = null;
        if (root.getFacetCount() > 0) {
            facetComponent = root.getFacets().get(UIViewRoot.METADATA_FACET_NAME);
        }
        if (facetComponent == null) {
            root.getAttributes().put(FacetHandler.KEY, UIViewRoot.METADATA_FACET_NAME);
            try {
                nextHandler.apply(ctx, root);
            } finally {
                root.getAttributes().remove(FacetHandler.KEY);
            }
            facetComponent = root.getFacets().get(UIViewRoot.METADATA_FACET_NAME);
            if (facetComponent != null && !(facetComponent instanceof UIPanel)) {
                Application app = ctx.getFacesContext().getApplication();
                UIComponent panelGroup = app.createComponent(UIPanel.COMPONENT_TYPE);
                panelGroup.getChildren().add(facetComponent);
                root.getFacets().put(UIViewRoot.METADATA_FACET_NAME, panelGroup);
                facetComponent = panelGroup;
            }
            if (null != facetComponent) {
                facetComponent.setId(UIViewRoot.METADATA_FACET_NAME);
            }
        }
    }
}
