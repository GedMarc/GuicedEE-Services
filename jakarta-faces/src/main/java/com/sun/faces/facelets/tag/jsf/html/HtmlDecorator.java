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

package com.sun.faces.facelets.tag.jsf.html;

import com.sun.faces.facelets.tag.TagAttributesImpl;

import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;
import javax.faces.view.facelets.TagDecorator;

/**
 * @author Jacob Hookom
 */
public final class HtmlDecorator implements TagDecorator {

    public final static String XhtmlNamespace = "http://www.w3.org/1999/xhtml";

    public final static HtmlDecorator Instance = new HtmlDecorator();

    /**
     *
     */
    public HtmlDecorator() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sun.facelets.tag.TagDecorator#decorate(com.sun.facelets.tag.Tag)
     */
    @Override
    public Tag decorate(Tag tag) {
        if (XhtmlNamespace.equals(tag.getNamespace())) {
            String n = tag.getLocalName();
            if ("a".equals(n)) {
                return new Tag(tag.getLocation(), HtmlLibrary.Namespace, "commandLink", tag.getQName(), tag.getAttributes());
            }
            if ("form".equals(n)) {
                return new Tag(tag.getLocation(), HtmlLibrary.Namespace, "form", tag.getQName(), tag.getAttributes());
            }
            if ("input".equals(n)) {
                TagAttribute attr = tag.getAttributes().get("type");
                if (attr != null) {
                    String t = attr.getValue();
                    TagAttributes na = removeType(tag.getAttributes());
                    if ("text".equals(t)) {
                        return new Tag(tag.getLocation(), HtmlLibrary.Namespace, "inputText", tag.getQName(), na);
                    }
                    if ("password".equals(t)) {
                        return new Tag(tag.getLocation(), HtmlLibrary.Namespace, "inputSecret", tag.getQName(), na);
                    }
                    if ("hidden".equals(t)) {
                        return new Tag(tag.getLocation(), HtmlLibrary.Namespace, "inputHidden", tag.getQName(), na);
                    }
                    if ("submit".equals(t)) {
                        return new Tag(tag.getLocation(), HtmlLibrary.Namespace, "commandButton", tag.getQName(), na);
                    }
                    if ("file".equals(t)) {
                        return new Tag(tag.getLocation(), HtmlLibrary.Namespace, "inputFile", tag.getQName(), na);
                    }
                }
            }
        }
        return null;
    }

    private TagAttributes removeType(TagAttributes attrs) {
        TagAttribute[] o = attrs.getAll();
        TagAttribute[] a = new TagAttribute[o.length - 1];
        int p = 0;
        for (int i = 0; i < o.length; i++) {
            if (!"type".equals(o[i].getLocalName())) {
                a[p++] = o[i];
            }
        }
        return new TagAttributesImpl(a);
    }

}
