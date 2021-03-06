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
 * $Id: NoMatchFoundException.java,v 1.3 2007-05-30 20:30:31 nbeers Exp $
 */
package com.sun.ws.management.client.exceptions;

import com.sun.ws.management.NoMatchFoundExceptionServer;

public class NoMatchFoundException extends NoMatchFoundExceptionServer {

	public NoMatchFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoMatchFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoMatchFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoMatchFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
