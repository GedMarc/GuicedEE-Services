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

package com.sun.faces.mgbean;

import javax.faces.FacesException;

/**
 * <p>
 * Indicates an error in the ManagedBean, be it a user error or runtime error.
 * </p>
 */
public class ManagedBeanCreationException extends FacesException {

    // ------------------------------------------------------------ Constructors

    private static final long serialVersionUID = -641675334387524657L;

    public ManagedBeanCreationException() {
        super();
    }

    public ManagedBeanCreationException(String message) {
        super(message);
    }

    public ManagedBeanCreationException(Throwable t) {
        super(t);
    }

    public ManagedBeanCreationException(String message, Throwable t) {
        super(message, t);
    }

}
