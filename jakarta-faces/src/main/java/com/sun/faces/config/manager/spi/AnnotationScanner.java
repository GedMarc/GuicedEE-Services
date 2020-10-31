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

package com.sun.faces.config.manager.spi;

import static com.sun.faces.config.WebConfiguration.WebContextInitParameter.AnnotationScanPackages;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.spi.AnnotationProvider;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;

import javax.faces.bean.ManagedBean;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.convert.FacesConverter;
import javax.faces.event.NamedEvent;
import javax.faces.render.FacesBehaviorRenderer;
import javax.faces.render.FacesRenderer;
import javax.faces.validator.FacesValidator;
import javax.faces.view.facelets.FaceletsResourceResolver;
import javax.servlet.ServletContext;

/**
 * This class is responsible for ensuring that the class file bytes of classes contained within the web application are
 * scanned for any of the known Faces configuration Annotations:
 * <ul>
 * <li>javax.faces.component.FacesBehavior</li>
 * <li>javax.faces.render.FacesBehaviorRenderer</li>
 * <li>javax.faces.component.FacesComponent</li>
 * <li>javax.faces.convert.FacesConverter</li>
 * <li>javax.faces.validator.FacesValidator</li>
 * <li>javax.faces.render.FacesRenderer</li>
 * <li>javax.faces.bean.ManagedBean</li>
 * <li>javax.faces.event.NamedEvent</li>
 * <li>javax.faces.view.facelets.FaceletsResourceResolver</li>
 * </ul>
 */
public abstract class AnnotationScanner extends AnnotationProvider {

    private static final Logger LOGGER = FacesLogger.CONFIG.getLogger();
    private static final String WILDCARD = "*";

    protected static final Set<String> FACES_ANNOTATIONS;
    protected static final Set<Class<? extends Annotation>> FACES_ANNOTATION_TYPE;

    static {
        HashSet<String> annotations = new HashSet<>(8, 1.0f);
        // JAVASERVERFACES-1835 this collection has the same information twice.
        // Once in javap -s format, and once as fully qualified Java class names.
        Collections.addAll(annotations, "Ljavax/faces/component/FacesComponent;", "Ljavax/faces/convert/FacesConverter;",
                "Ljavax/faces/validator/FacesValidator;", "Ljavax/faces/render/FacesRenderer;", "Ljavax/faces/bean/ManagedBean;",
                "Ljavax/faces/event/NamedEvent;", "Ljavax/faces/component/behavior/FacesBehavior;", "Ljavax/faces/render/FacesBehaviorRenderer;",
                "Ljavax/faces/view/facelets/FaceletsResourceResolver;", "javax.faces.component.FacesComponent", "javax.faces.convert.FacesConverter",
                "javax.faces.validator.FacesValidator", "javax.faces.render.FacesRenderer", "javax.faces.bean.ManagedBean",
                "javax.faces.event.NamedEvent", "javax.faces.component.behavior.FacesBehavior", "javax.faces.render.FacesBehaviorRenderer",
                "javax.faces.view.facelets.FaceletsResourceResolver");
        FACES_ANNOTATIONS = Collections.unmodifiableSet(annotations);
        HashSet<Class<? extends Annotation>> annotationInstances = new HashSet<>(8, 1.0f);
        Collections.addAll(annotationInstances, FacesComponent.class, FacesConverter.class, FacesValidator.class, FacesRenderer.class, ManagedBean.class,
                NamedEvent.class, FacesBehavior.class, FacesBehaviorRenderer.class, FaceletsResourceResolver.class);
        FACES_ANNOTATION_TYPE = Collections.unmodifiableSet(annotationInstances);
    }

    private boolean isAnnotationScanPackagesSet = false;
    private String[] webInfClassesPackages;
    private Map<String, String[]> classpathPackages;

    /**
     * Creates a new <code>AnnotationScanner</code> instance.
     *
     * @param sc the <code>ServletContext</code> for the application to be scanned
     */
    public AnnotationScanner(ServletContext sc) {
        super(sc);

        WebConfiguration webConfig = WebConfiguration.getInstance(sc);
        initializeAnnotationScanPackages(sc, webConfig);

    }

    private void initializeAnnotationScanPackages(ServletContext sc, WebConfiguration webConfig) {
        if (!webConfig.isSet(AnnotationScanPackages)) {
            return;
        }
        isAnnotationScanPackagesSet = true;
        classpathPackages = new HashMap<>(4);
        webInfClassesPackages = new String[0];
        String[] options = webConfig.getOptionValue(AnnotationScanPackages, "\\s+");
        List<String> packages = new ArrayList<>(4);
        for (String option : options) {
            if (option.length() == 0) {
                continue;
            }
            if (option.startsWith("jar:")) {
                String[] parts = Util.split(sc, option, ":");
                if (parts.length != 3) {
                    if (LOGGER.isLoggable(Level.WARNING)) {
                        LOGGER.log(Level.WARNING, "jsf.annotation.scanner.configuration.invalid",
                                new String[] { AnnotationScanPackages.getQualifiedName(), option });
                    }
                } else {
                    if (WILDCARD.equals(parts[1]) && !classpathPackages.containsKey(WILDCARD)) {
                        classpathPackages.clear();
                        classpathPackages.put(WILDCARD, normalizeJarPackages(Util.split(sc, parts[2], ",")));
                    } else if (WILDCARD.equals(parts[1]) && classpathPackages.containsKey(WILDCARD)) {
                        if (LOGGER.isLoggable(Level.WARNING)) {
                            LOGGER.log(Level.WARNING, "jsf.annotation.scanner.configuration.duplicate.wildcard",
                                    new String[] { AnnotationScanPackages.getQualifiedName(), option });
                        }
                    } else {
                        if (!classpathPackages.containsKey(WILDCARD)) {
                            classpathPackages.put(parts[1], normalizeJarPackages(Util.split(sc, parts[2], ",")));
                        }
                    }
                }
            } else {
                if (WILDCARD.equals(option) && !packages.contains(WILDCARD)) {
                    packages.clear();
                    packages.add(WILDCARD);
                } else {
                    if (!packages.contains(WILDCARD)) {
                        packages.add(option);
                    }
                }
            }
        }
        webInfClassesPackages = packages.toArray(new String[packages.size()]);
    }

    private String[] normalizeJarPackages(String[] packages) {

        if (packages.length == 0) {
            return packages;
        }
        List<String> normalizedPackages = new ArrayList<>(packages.length);
        for (String pkg : packages) {
            if (WILDCARD.equals(pkg)) {
                normalizedPackages.clear();
                normalizedPackages.add(WILDCARD);
                break;
            } else {
                normalizedPackages.add(pkg);
            }
        }
        return normalizedPackages.toArray(new String[normalizedPackages.size()]);

    }

    // --------------------------------------------------------- Protected Methods

    protected boolean processJar(String entry) {
        return classpathPackages == null || classpathPackages.containsKey(entry) || classpathPackages.containsKey(WILDCARD);
    }

    /**
     * @param candidate the class that should be processed
     * @return <code>true</code> if the class should be processed further, otherwise, <code>false</code>
     */
    protected boolean processClass(String candidate) {
        return processClass(candidate, webInfClassesPackages);
    }

    protected boolean processClass(String candidate, String[] packages) {

        if (packages == null) {
            return true;
        }

        for (String packageName : packages) {
            if (candidate.startsWith(packageName) || WILDCARD.equals(packageName)) {
                return true;
            }
        }

        return false;
    }

    protected Map<Class<? extends Annotation>, Set<Class<?>>> processClassList(Set<String> classList) {

        Map<Class<? extends Annotation>, Set<Class<?>>> annotatedClasses = null;
        if (classList.size() > 0) {
            annotatedClasses = new HashMap<>(6, 1.0f);
            for (String className : classList) {
                try {
                    Class<?> clazz = Util.loadClass(className, this);
                    Annotation[] annotations = clazz.getAnnotations();
                    for (Annotation annotation : annotations) {
                        Class<? extends Annotation> annoType = annotation.annotationType();
                        if (FACES_ANNOTATION_TYPE.contains(annoType)) {
                            Set<Class<?>> classes = annotatedClasses.get(annoType);
                            if (classes == null) {
                                classes = new HashSet<>();
                                annotatedClasses.put(annoType, classes);
                            }
                            classes.add(clazz);
                        }
                    }
                } catch (ClassNotFoundException cnfe) {
                    // shouldn't happen..
                    if (LOGGER.isLoggable(Level.SEVERE)) {
                        LOGGER.log(Level.SEVERE, "Unable to load annotated class: {0}", className);
                        LOGGER.log(Level.SEVERE, "", cnfe);
                    }
                } catch (NoClassDefFoundError ncdfe) {
                    // this is more likely
                    if (LOGGER.isLoggable(Level.SEVERE)) {
                        LOGGER.log(Level.SEVERE, "Unable to load annotated class: {0}, reason: {1}", new Object[] { className, ncdfe.toString() });
                    }
                }
            }
        }

        return annotatedClasses != null ? annotatedClasses : Collections.<Class<? extends Annotation>, Set<Class<?>>>emptyMap();
    }

    protected boolean isAnnotationScanPackagesSet() {
        return isAnnotationScanPackagesSet;
    }

    protected Map<String, String[]> getClasspathPackages() {
        return classpathPackages;
    }

    protected String[] getWebInfClassesPackages() {
        return webInfClassesPackages;
    }

}
