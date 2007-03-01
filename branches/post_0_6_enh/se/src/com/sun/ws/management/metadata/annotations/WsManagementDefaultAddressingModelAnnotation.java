/*
 * Copyright 2007 Hewlett-Packard Company, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * $Id: WsManagementDefaultAddressingModelAnnotation.java,v 1.1 2007-03-01 06:01:25 simeonpinder Exp $
 */
package com.sun.ws.management.metadata.annotations;

import java.lang.annotation.Retention;

import javax.xml.namespace.QName;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** This annotation helps implementers describe useful metadata about their WS-Management 
 *  Server/Endpoint implementations.  This type is meant to encapsulate the 
 *  components/information for the Default Addressing Model as defined in the 
 *  WS-Management specification version A.
 *  
 *  Ex.
 *  @WsManagementDefaultAddressingModelAnnotation(
 *		getDefaultAddressDefinition = 
 *			@WsManagementAddressDetailsAnnotation(
 *					referenceParametersContents={""}, 
 *					referencePropertiesContents={""}, 
 *					wsaTo="http://localhost:8080/wsman/", 
 *					wsmanResourceURI="wsman:auth/user", 
 *					wsmanSelectorSetContents={"firstname=Get",
 *							"lastname=Guy"}
 *			), 
 *			metaDataCategory = "PERSON_RESOURCES", 
 *			metaDataDescription = "This resource exposes people information " +
 *					"stored in an LDAP repository.", 
 *			resourceMetaDataUniqueName = 
 *				"http://some.company.xyz/ldap/repository/2006/uid_00000000007"
 *			resourceMiscellaneousInformation =
 *				"This default addressing instance is miscellaneous."
 *	)
 * 
 */
@Retention(RUNTIME)
public @interface WsManagementDefaultAddressingModelAnnotation {
	
	/** This defines the Addressing details for this specific EndPoint.
	 * 
	 * Ex.@WsManagementAddressDetailsAnnotation(
	 *		referenceParametersContents={"nameA=val1","nameB=val2"}, 
	 *		referencePropertiesContents={"colA=val3","colB=val4"}, 
	 *		wsaTo="http://localhost:8080/wsman/", 
	 *		wsmanResourceURI="wsman:auth/user", 
	 *		wsmanSelectorSetContents={"firstname=Get",
	 *			"lastname=Guy"}
	 *	  )  
	 */
	public WsManagementAddressDetailsAnnotation getDefaultAddressDefinition();
	
	/**This is an optional flag that my be used by consumers to help organize metadata 
	 * information into useful groups.
	 * 
	 * Ex. metaDataCategory="TRANSFER_RESOURCES" 
	 */
	public String metaDataCategory()  default "DEFAULT_CATEGORY";
	
	/**This is an optional flag that my be used by consumers to help describe metadata 
	 * information in a succinct human readable fashion.
	 * 
	 * Ex. metaDataDescription="This resource exposes create/remove/update/delete functionality 
	 *     for employees in an LDAP repository" 
	 */
	public String metaDataDescription() default "(INSERT DESCRIPTION HERE)";
	
	/** This is a required field is used as a mechanism of uniquely identifying each 
	 *  resource/entity that exposes metadata in a uniform way. There are currently 
	 *  no constraints as to whether numeric/string content is to be used exclusively.
	 *  
	 * Ex. resourceMetaDataUniqueName="http://www.hp.com/resource/metadata/ldap/respository/2004" 
	 */
	public String resourceMetaDataUID() default "(INSERT UNIQUE IDENTIFIER)";
	
	/** This is an optional field for miscellaneous information. 
	 *  
	 * Ex. resourceMiscellaneousInformation="This resource will be retired on 11/12/2007." 
	 */
	public String resourceMiscellaneousInformation() default "(INSERT MISCELLANEOUS INFO)";
	
}
