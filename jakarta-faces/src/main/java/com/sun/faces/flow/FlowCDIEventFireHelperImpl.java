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

package com.sun.faces.flow;

import java.io.Serializable;

import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;

public class FlowCDIEventFireHelperImpl implements Serializable, FlowCDIEventFireHelper {

    private static final long serialVersionUID = -5689195252450178355L;

    @Inject
    @Initialized(FlowScoped.class)
    Event<Flow> flowInitializedEvent;
    @Inject
    @Destroyed(FlowScoped.class)
    Event<Flow> flowDestroyedEvent;

    @Override
    public void fireInitializedEvent(Flow currentFlow) {
        flowInitializedEvent.fire(currentFlow);
    }

    @Override
    public void fireDestroyedEvent(Flow currentFlow) {
        flowDestroyedEvent.fire(currentFlow);
    }

}
