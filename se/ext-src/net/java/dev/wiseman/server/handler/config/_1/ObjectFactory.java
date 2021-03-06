//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.10.24 at 09:31:40 PM CEST 
//
/*
 * Copyright (C) 2006, 2007 Hewlett-Packard Development Company, L.P.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 ** Copyright (C) 2006, 2007 Hewlett-Packard Development Company, L.P.
 **  
 ** Authors: Simeon Pinder (simeon.pinder@hp.com), Denis Rachal (denis.rachal@hp.com), 
 ** Nancy Beers (nancy.beers@hp.com), William Reichardt 
 **
 **$Log: not supported by cvs2svn $
 ** 
 *
 * $Id: ObjectFactory.java,v 1.2 2007-05-31 19:47:46 nbeers Exp $
 */


package net.java.dev.wiseman.server.handler.config._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import net.java.dev.wiseman.server.handler.config._1.ResourceHandlersType.ResourceHandler;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.java.dev.wiseman.server.handler.config._1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ResourceHandlers_QNAME = new QName("http://wiseman.dev.java.net/server/handler/config/1/resource-handler-config.xsd", "resource-handlers");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.java.dev.wiseman.server.handler.config._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResourceHandlersType }
     * 
     */
    public ResourceHandlersType createResourceHandlersType() {
        return new ResourceHandlersType();
    }

    /**
     * Create an instance of {@link ResourceHandler }
     * 
     */
    public ResourceHandler createResourceHandlersTypeResourceHandler() {
        return new ResourceHandler();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceHandlersType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wiseman.dev.java.net/server/handler/config/1/resource-handler-config.xsd", name = "resource-handlers")
    public JAXBElement<ResourceHandlersType> createResourceHandlers(ResourceHandlersType value) {
        return new JAXBElement<ResourceHandlersType>(_ResourceHandlers_QNAME, ResourceHandlersType.class, null, value);
    }

}
