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

package com.sun.faces.el;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.Map;

import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * ELResolver to resolve expressions like the following:
 * <ul>
 * <li>#{resource['library:resource']}</li>
 * <li>#{resource['resource']}
 * </ul>
 */
public class ResourceELResolver extends ELResolver {

    // ------------------------------------------------- Methods from ELResolver

    /**
     * If base and property are not <code>null</code> and base is an instance of {@link ResourceHandler}, perform the
     * following:
     * <ul>
     * <li>If <code>property</code> doesn't contain <code>:</code> treat <code>property</code> as the resource name and pass
     * <code>property</code> to {@link ResourceHandler#createResource(String)}</li>
     * <li>If <code>property</code> contains a single <code>:</code> treat the content before the <code>:</code> as the
     * library name, and the content after the <code>:</code> to be the resource name and pass both to
     * {@link javax.faces.application.ResourceHandler#createResource(String, String)}</li>
     * <li>If <code>property</code> contains more than one <code>:</code> then throw a <code>ELException</code></li>
     * <li>If one of the above steps resulted in the creation of a {@link Resource} instance, call
     * <code>ELContext.setPropertyResolved(true)</code> and return the result of
     * {@link javax.faces.application.Resource#getRequestPath()}</li>
     * </ul>
     *
     * @see ELResolver#getValue(javax.el.ELContext, Object, Object)
     */
    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (base == null && property == null) {
            String message = MessageUtils.getExceptionMessageString(MessageUtils.NULL_PARAMETERS_ERROR_MESSAGE_ID, "base and property"); // ?????
            throw new PropertyNotFoundException(message);
        }
        Object ret = null;
        if (base instanceof ResourceHandler) {
            ResourceHandler handler = (ResourceHandler) base;
            String prop = property.toString();
            Resource res;
            if (!prop.contains(":")) {
                res = handler.createResource(prop);
            } else {
                if (!isPropertyValid(prop)) {
                    // RELEASE_PENDING i18n
                    throw new ELException("Invalid resource format.  Property " + prop + " contains more than one colon (:)");
                }
                Map<String, Object> appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();

                String[] parts = Util.split(appMap, prop, ":");

                // If the enclosing entity for this expression is itself
                // a resource, the "this" syntax for the library name must
                // be supported.
                if (null != parts[0] && parts[0].equals("this")) {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    UIComponent currentComponent = UIComponent.getCurrentCompositeComponent(facesContext);
                    Resource componentResource = (Resource) currentComponent.getAttributes().get(Resource.COMPONENT_RESOURCE_KEY);
                    if (null != componentResource) {
                        String libName = null;
                        if (null != (libName = componentResource.getLibraryName())) {
                            parts[0] = libName;
                        }
                    }

                }

                res = handler.createResource(parts[1], parts[0]);
            }
            if (res != null) {
                FacesContext facesContext = (FacesContext) context.getContext(FacesContext.class);
                ExternalContext extContext = facesContext.getExternalContext();
                ret = extContext.encodeResourceURL(res.getRequestPath());
            }
            context.setPropertyResolved(true);
        }
        return ret;
    }

    /**
     * @return <code>null</code> as this resolver only performs lookups
     * @throws PropertyNotFoundException if base and property are null
     */
    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        if (base == null && property == null) {
            String message = MessageUtils.getExceptionMessageString(MessageUtils.NULL_PARAMETERS_ERROR_MESSAGE_ID, "base and property"); // ?????
            throw new PropertyNotFoundException(message);
        }
        return null;
    }

    /**
     * This is basically a no-op.
     *
     * @throws PropertyNotFoundException if base and property are null
     */
    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
        if (base == null && property == null) {
            String message = MessageUtils.getExceptionMessageString(MessageUtils.NULL_PARAMETERS_ERROR_MESSAGE_ID, "base and property"); // ?????
            throw new PropertyNotFoundException(message);
        }
    }

    /**
     * @return <code>false</code> (basically ignored by the EL system)
     * @throws PropertyNotFoundException if base and property are null
     */
    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (base == null && property == null) {
            String message = MessageUtils.getExceptionMessageString(MessageUtils.NULL_PARAMETERS_ERROR_MESSAGE_ID, "base and property"); // ?????
            throw new PropertyNotFoundException(message);
        }

        return false;
    }

    /**
     * @return <code>null</code> - there is no way to query the <code>ResourceManager</code> for all known resources
     */
    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {

        return null;

    }

    /**
     * @return <code>String.class</code> - getType() expects String properties
     */
    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {

        return String.class;

    }

    // --------------------------------------------------------- Private Methods

    private boolean isPropertyValid(String property) {

        int idx = property.indexOf(':');
        return property.indexOf(':', idx + 1) == -1;

    }

}
