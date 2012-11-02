//
//  ========================================================================
//  Copyright (c) 1995-2012 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package javax.net.websocket.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This method level annotation can be used to decorate a Java method that
 * wishes to be called when a web socket session has closed.
 * <p>
 * The method may only take the following parameters:-
 * <p>
 * <ul>
 * <li>optional Session parameter</li>
 * <li>optional CloseReason parameter</li>
 * <li>Zero to n String parameters annotated with the @WebSocketPathParam
 * annotation.</li>
 * </ul>
 * in any order.
 * 
 * @since Draft 002
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebSocketClose {
}
