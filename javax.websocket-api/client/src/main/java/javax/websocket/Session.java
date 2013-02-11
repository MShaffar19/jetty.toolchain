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

package javax.websocket;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A Web Socket session represents a conversation between two web socket
 * endpoints. As soon as the websocket handshake completes successfully, the web
 * socket implementation provides the endpoint an open websocket session. The
 * endpoint can then register interest in incoming messages that are part of
 * this newly created session by providing a MessageHandler to the session, and
 * can send messages to the other end of the conversation by means of the
 * RemoteEndpoint object obtained from this session.<br>
 * <p>
 * Once the session is closed, it is no longer valid for use by applications.
 * Calling any of its methods once the session has been closed will result in an
 * {@link java.lang.IllegalStateException} being thrown. Developers should
 * retrieve any information from the session during the
 * {@link Endpoint#onClose(javax.websocket.Session, javax.websocket.CloseReason) }
 * method.
 * 
 * @since DRAFT 001
 * @see DRAFT 012
 */
public interface Session {
    /**
     * Register to handle to incoming messages in this conversation. A maximum
     * of one message handler per native websocket message type (text, binary,
     * pong) may be added to each Session. I.e. a maximum of one message handler
     * to handle incoming text messages a maximum of one message handler for
     * handling incoming binary messages, and a maximum of one for handling
     * incoming pong messages. For further details of which message handlers
     * handle which of the native websocket message types please see
     * {@link MessageHandler.Basic} and {@link MessageHandler.Async}. Adding
     * more than one of any one type will result in a runtime exception.
     * 
     * @param handler
     *            the MessageHandler to be added.
     * @throws IllegalStateException
     *             if there is already a MessageHandler registered for the same
     *             native websocket message type as this handler.
     * @see DRAFT 012
     */
    void addMessageHandler(MessageHandler listener)
	    throws IllegalStateException;

    /**
     * Close the current conversation with a normal status code and no reason
     * phrase.
     * 
     * @throws IOException
     *             if there was a connection error closing the connection.
     * @see DRAFT 012
     */
    void close() throws IOException;

    /**
     * Close the current conversation, giving a reason for the closure. Note the
     * websocket spec defines the acceptable uses of status codes and reason
     * phrases. If the application cannot determine a suitable close code to use
     * for the closeReason, it is recommended to use
     * {@link CloseReason.CloseCodes#NO_STATUS_CODE}.
     * 
     * @param closeReason
     *            the reason for the closure.
     * @throws IOException
     *             if there was a connection error closing the connection
     * @see DRAFT 012
     */
    void close(CloseReason closeStatus) throws IOException;

    /**
     * Return the container that this session is part of.
     * 
     * @return the container
     * @see DRAFT 012
     */
    WebSocketContainer getContainer();

    /**
     * Returns a string containing the unique identifier assigned to this
     * session. The identifier is assigned by the web socket implementation and
     * is implementation dependent.
     * 
     * @return the unique identifier for this session instance.
     * @see DRAFT 012
     */
    String getId();

    /**
     * The maximum length of incoming binary messages that this Session can
     * buffer.
     * 
     * @return the message size.
     * @see DRAFT 012
     */
    int getMaxBinaryMessageBufferSize();

    /**
     * The maximum length of incoming text messages that this Session can
     * buffer.
     * 
     * @return the message size.
     * @see DRAFT 012
     */
    int getMaxTextMessageBufferSize();

    /**
     * Return an unmodifiable copy of the set of MessageHandlers for this
     * Session.
     * 
     * @return the set of message handlers
     * @see DRAFT 012
     */
    Set<MessageHandler> getMessageHandlers();

    /**
     * Return the list of extensions currently in use for this conversation.
     * 
     * @return the negotiated extensions
     * @see DRAFT 012
     */
    List<String> getNegotiatedExtensions();

    /**
     * Return the sub protocol agreed during the websocket handshake for this
     * conversation.
     * 
     * @return the negotiated subprotocol
     * @see DRAFT 012
     */
    String getNegotiatedSubprotocol();

    /**
     * Return a copy of the Set of all the open web socket sessions that
     * represent connections to the same endpoint to which this session
     * represents a connection. The Set includes the session this method is
     * called on. These sessions may not still be open at any point after the
     * return of this method. For example, iterating over the set at a later
     * time may yield one or more closed sessions. Developers should use
     * session.isOpen() to check.
     * 
     * @return the set of sessions, open at the time of return.
     * @see DRAFT 012
     */
    Set<Session> getOpenSessions();

    /**
     * Return a map of the path parameter names and values used if the server
     * endpoint was deployed with a URI-template and the client connected with a
     * particular matching URL.
     * 
     * @return the unmodifiable map of path parameters. The key of the map is
     *         the parameter name, the values in the map are the parameter
     *         values.
     * @see DRAFT 012
     */
    Map<String, String> getPathParameters();

    /**
     * Returns the version of the websocket protocol currently being used. This
     * is taken as the value of the Sec-WebSocket-Version header used in the
     * opening handshake. i.e. "13".
     * 
     * @return the protocol version
     * @see DRAFT 012
     */
    String getProtocolVersion();

    /**
     * Return the query string associated with the request this session was
     * opened under.
     * 
     * @return the query string
     * @see DRAFT 012
     */
    String getQueryString();

    /**
     * Return a reference to the RemoteEndpoint object representing the other
     * end of this conversation.
     * 
     * @return the remote endpoint
     * @see DRAFT 012
     */
    RemoteEndpoint getRemote();

    /**
     * Return the request parameters associated with the request this session
     * was opened under.
     * 
     * @return the unmodifiable map of the request parameters
     * @see DRAFT 012
     */
    Map<String, List<String>> getRequestParameterMap();

    /**
     * Return the URI that this session was opened under.
     * 
     * @return the request URI.
     * @see DRAFT 012
     */
    // FIXME strip query string?
    URI getRequestURI();

    /**
     * Return the number of milliseconds before this conversation will be closed
     * by the container if it is inactive, ie no messages are either sent or
     * received in that time.
     * 
     * @return the timeout in milliseconds.
     * @see DRAFT 012
     */
    long getTimeout();

    /**
     * Return the authenticated user for this Session or null if no user is
     * authenticated for this session.
     * 
     * @return the user principal.
     * @see DRAFT 012
     */
    Principal getUserPrincipal();

    /**
     * While the session is open, this method returns a Map that the developer
     * may use to store application specific information relating to this
     * session instance. The developer may retrieve information from this Map at
     * any time between the opening of the session and during the onClose()
     * method. But outside that time, any information stored using this Map may
     * no longer be kept by the container. Web socket applications running on
     * distributed implementations of the web container should make any
     * application specific objects stored here java.io.Serializable, or the
     * object may not be recreated after a failover.
     * 
     * @return an editable Map of application data.
     * @see DRAFT 012
     */
    Map<String, Object> getUserProperties();

    /**
     * Return true if and only if the underlying socket is open.
     * 
     * @return whether the session is active
     * @see DRAFT 012
     */
    boolean isOpen();

    /**
     * Return true if and only if the underlying socket is using a secure
     * transport.
     * 
     * @return whether its using a secure transport
     * @see DRAFT 012
     */
    boolean isSecure();

    /**
     * Remove the given MessageHandler from the set belonging to this session.
     * This method may block if the given handler is processing a message until
     * it is no longer in use.
     * 
     * @param handler
     *            the handler to be removed
     * @see DRAFT 012
     */
    void removeMessageHandler(MessageHandler handler);

    /**
     * Sets the maximum length of incoming binary messages that this Session can
     * buffer.
     * 
     * @param length
     *            the maximum length.
     * @see DRAFT 012
     */
    void setMaxBinaryMessageBufferSize(int length);

    /**
     * Sets the maximum length of incoming text messages that this Session can
     * buffer.
     * 
     * @param length
     *            the maximum length.
     * @see DRAFT 012
     */
    void setMaxTextMessageBufferSize(int length);

    /**
     * Set the number of milliseconds before this conversation will be closed by
     * the container if it is inactive, ie no messages are either sent or
     * received.
     * 
     * @param milliseconds
     *            the number of milliseconds.
     * @see DRAFT 012
     */
    void setTimeout(long milliseconds);
}