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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

/**
 * Concrete implementation of {@link javax.el.ELContext}. ELContext's constructor is protected to control creation of
 * ELContext objects through their appropriate factory methods. This version of ELContext forces construction through
 * FacesContextImpl.
 *
 */
public class ELContextImpl extends ELContext {

    private FunctionMapper functionMapper = new NoopFunctionMapper();
    private VariableMapper variableMapper;
    private ELResolver resolver;

    // ------------------------------------------------------------ Constructors

    /**
     * Constructs a new ELContext associated with the given ELResolver.
     *
     * @param resolver the ELResolver to return from {@link #getELResolver()}
     */
    public ELContextImpl(ELResolver resolver) {
        this.resolver = resolver;
    }

    // -------------------------------------------------- Methods from ELContext

    @Override
    public FunctionMapper getFunctionMapper() {
        return functionMapper;
    }

    @Override
    public VariableMapper getVariableMapper() {
        if (variableMapper == null) {
            variableMapper = new VariableMapperImpl();
        }
        return variableMapper;
    }

    @Override
    public ELResolver getELResolver() {
        return resolver;
    }

    // ---------------------------------------------------------- Public Methods

    public void setFunctionMapper(FunctionMapper functionMapper) {

        this.functionMapper = functionMapper;

    }

    // ----------------------------------------------------------- Inner Classes

    private static class VariableMapperImpl extends VariableMapper {

        private Map<String, ValueExpression> variables;

        public VariableMapperImpl() {

            // noinspection CollectionWithoutInitialCapacity
            variables = new HashMap<>();

        }

        @Override
        public ValueExpression resolveVariable(String s) {
            return variables.get(s);
        }

        @Override
        public ValueExpression setVariable(String s, ValueExpression valueExpression) {
            return variables.put(s, valueExpression);
        }
    }

    private static class NoopFunctionMapper extends FunctionMapper {

        @Override
        public Method resolveFunction(String s, String s1) {
            return null;
        }

    }

}
