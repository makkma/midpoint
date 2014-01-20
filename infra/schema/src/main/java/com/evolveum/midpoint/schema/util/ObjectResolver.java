/*
 * Copyright (c) 2010-2013 Evolveum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.evolveum.midpoint.schema.util;

import java.util.Collection;
import java.util.List;

import com.evolveum.midpoint.prism.PrismObject;
import com.evolveum.midpoint.prism.query.ObjectQuery;
import com.evolveum.midpoint.schema.GetOperationOptions;
import com.evolveum.midpoint.schema.ResultHandler;
import com.evolveum.midpoint.schema.SelectorOptions;
import com.evolveum.midpoint.schema.result.OperationResult;
import com.evolveum.midpoint.util.exception.CommunicationException;
import com.evolveum.midpoint.util.exception.ConfigurationException;
import com.evolveum.midpoint.util.exception.ObjectNotFoundException;
import com.evolveum.midpoint.util.exception.SchemaException;
import com.evolveum.midpoint.util.exception.SecurityViolationException;
import com.evolveum.midpoint.xml.ns._public.common.common_2a.ObjectReferenceType;
import com.evolveum.midpoint.xml.ns._public.common.common_2a.ObjectType;

/**
 * The callback from some of the object utilities to resolve objects.
 * 
 * The classes implementing this will most likely fetch the objects from the
 * repository or from some kind of object cache.
 * 
 * @author Radovan Semancik
 */
public interface ObjectResolver {
	
	/**
	 * Resolve the provided reference to object (ObjectType).
	 * 
	 * Note: The reference is used instead of just OID because the reference
	 * also contains object type. This speeds up the repository operations.
	 * 
	 * @param ref object reference to resolve
	 * @param contextDescription short description of the context of resolution, e.g. "executing expression FOO". Used in error messages.
	 * @return resolved object
	 * @throws ObjectNotFoundException
	 *             requested object does not exist
	 * @throws SchemaException
	 *             error dealing with storage schema
	 * @throws IllegalArgumentException
	 *             wrong OID format, etc.
	 */
	<T extends ObjectType> T resolve(ObjectReferenceType ref, Class<T> expectedType, String contextDescription, OperationResult result) 
			throws ObjectNotFoundException, SchemaException;
	
	<O extends ObjectType> void searchIterative(Class<O> type, ObjectQuery query, Collection<SelectorOptions<GetOperationOptions>> options, ResultHandler<O> handler, OperationResult parentResult) 
			throws SchemaException, ObjectNotFoundException, CommunicationException, ConfigurationException, SecurityViolationException;
	
}
