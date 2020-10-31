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

package com.sun.faces.facelets.el;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.PropertyNotWritableException;
import javax.el.VariableMapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;

/**
 *
 *
 * @author Jacob Hookom
 * @version $Id$
 * @deprecated
 */
@Deprecated
public final class LegacyELContext extends ELContext {

    private static final String[] IMPLICIT_OBJECTS = new String[] { "application", "applicationScope", "cookie", "facesContext", "header", "headerValues",
            "initParam", "param", "paramValues", "request", "requestScope", "session", "sessionScope", "view" };

    private final static FunctionMapper FUNCTIONS = new EmptyFunctionMapper();

    private final FacesContext faces;

    private final ELResolver resolver;

    private final VariableMapper variables;

    public LegacyELContext(FacesContext faces) {
        this.faces = faces;
        resolver = new LegacyELResolver();
        variables = new DefaultVariableMapper();
    }

    @Override
    public ELResolver getELResolver() {
        return resolver;
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return FUNCTIONS;
    }

    @Override
    public VariableMapper getVariableMapper() {
        return variables;
    }

    public FacesContext getFacesContext() {
        return faces;
    }

    private final class LegacyELResolver extends ELResolver {

        @Override
        public Class getCommonPropertyType(ELContext context, Object base) {
            return Object.class;
        }

        @Override
        public Iterator getFeatureDescriptors(ELContext context, Object base) {
            return Collections.EMPTY_LIST.iterator();
        }

        private VariableResolver getVariableResolver() {
            return faces.getApplication().getVariableResolver();
        }

        private PropertyResolver getPropertyResolver() {
            return faces.getApplication().getPropertyResolver();
        }

        @Override
        public Class getType(ELContext context, Object base, Object property) {
            if (property == null) {
                return null;
            }
            try {
                context.setPropertyResolved(true);
                if (base == null) {
                    Object obj = getVariableResolver().resolveVariable(faces, property.toString());
                    return obj != null ? obj.getClass() : null;
                } else {
                    if (base instanceof List || base.getClass().isArray()) {
                        return getPropertyResolver().getType(base, Integer.parseInt(property.toString()));
                    } else {
                        return getPropertyResolver().getType(base, property);
                    }
                }
            } catch (PropertyNotFoundException e) {
                throw new javax.el.PropertyNotFoundException(e.getMessage(), e.getCause());
            } catch (EvaluationException e) {
                throw new ELException(e.getMessage(), e.getCause());
            }
        }

        @Override
        public Object getValue(ELContext context, Object base, Object property) {
            if (property == null) {
                return null;
            }
            try {
                context.setPropertyResolved(true);
                if (base == null) {
                    return getVariableResolver().resolveVariable(faces, property.toString());
                } else {
                    if (base instanceof List || base.getClass().isArray()) {
                        return getPropertyResolver().getValue(base, Integer.parseInt(property.toString()));
                    } else {
                        return getPropertyResolver().getValue(base, property);
                    }
                }
            } catch (PropertyNotFoundException e) {
                throw new javax.el.PropertyNotFoundException(e.getMessage(), e.getCause());
            } catch (EvaluationException e) {
                throw new ELException(e.getMessage(), e.getCause());
            }
        }

        @Override
        public boolean isReadOnly(ELContext context, Object base, Object property) {
            if (property == null) {
                return true;
            }
            try {
                context.setPropertyResolved(true);
                if (base == null) {
                    return false; // what can I do?
                } else {
                    if (base instanceof List || base.getClass().isArray()) {
                        return getPropertyResolver().isReadOnly(base, Integer.parseInt(property.toString()));
                    } else {
                        return getPropertyResolver().isReadOnly(base, property);
                    }
                }
            } catch (PropertyNotFoundException e) {
                throw new javax.el.PropertyNotFoundException(e.getMessage(), e.getCause());
            } catch (EvaluationException e) {
                throw new ELException(e.getMessage(), e.getCause());
            }
        }

        @Override
        public void setValue(ELContext context, Object base, Object property, Object value) {
            if (property == null) {
                throw new PropertyNotWritableException("Null Property");
            }
            try {
                context.setPropertyResolved(true);
                if (base == null) {
                    if (Arrays.binarySearch(IMPLICIT_OBJECTS, property.toString()) >= 0) {
                        throw new PropertyNotWritableException("Implicit Variable Not Setable: " + property);
                    } else {
                        Map scope = resolveScope(property.toString());
                        getPropertyResolver().setValue(scope, property, value);
                    }
                } else {
                    if (base instanceof List || base.getClass().isArray()) {
                        getPropertyResolver().setValue(base, Integer.parseInt(property.toString()), value);
                    } else {
                        getPropertyResolver().setValue(base, property, value);
                    }
                }
            } catch (PropertyNotFoundException e) {
                throw new javax.el.PropertyNotFoundException(e.getMessage(), e.getCause());
            } catch (EvaluationException e) {
                throw new ELException(e.getMessage(), e.getCause());
            }

        }

        private Map resolveScope(String var) {
            ExternalContext ext = faces.getExternalContext();

            // cycle through the scopes to find a match, if no
            // match is found, then return the requestScope
            Map map = ext.getRequestMap();
            if (!map.containsKey(var)) {
                map = ext.getSessionMap();
                if (!map.containsKey(var)) {
                    map = ext.getApplicationMap();
                    if (!map.containsKey(var)) {
                        map = ext.getRequestMap();
                    }
                }
            }
            return map;
        }
    }

    private final static class EmptyFunctionMapper extends FunctionMapper {

        @Override
        public Method resolveFunction(String prefix, String localName) {
            return null;
        }

    }

}
