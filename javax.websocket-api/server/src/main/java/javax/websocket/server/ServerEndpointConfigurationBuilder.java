//
//  ========================================================================
//  Copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd.
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

package javax.websocket.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Extension;

/**
 * The ServerEndpointConfigurationBuilder is a class used for creating {@link ServerEndpointConfigurationBuilder} objects for the purposes of deploying a client
 * endpoint. <br>
 * <br>
 * Here are some examples:<br>
 * <br>
 * Building a plain configuration for an endpoint with just a path.<br>
 * <br>
 * <code>
 * ServerEndpointConfiguration config = ServerEndpointConfigurationBuilder.create(ProgrammaticEndpoint.class, "/foo").build();
<br>
 *</code>
 * 
 * <br>
 * <br>
 * Building a configuration with no subprotocols and a custom configurator.<br>
 * <br>
 * <code>
 * ServerEndpointConfiguration config = ServerEndpointConfigurationBuilder.create(ProgrammaticEndpoint.class, "/bar")<br>
 *              .subprotocols(subprotocols)<br>
 *              .serverEndpointConfigurator(new MyServerConfigurator())<br>
 *              .build();<br>
 * </code>
 * 
 * @see DRAFT 013
 */
public final class ServerEndpointConfigurationBuilder
{
    /**
     * Creates the builder with the mandatory information of the endpoint class (programmatic or annotated), the relative URI or URI-template to use, and with
     * no subprotocols, extensions, encoders, decoders or custom configurator.
     * 
     * @param endpointClass
     *            the class of the endpoint to configure
     * @param path
     *            The URI or URI template where the endpoint will be deployed. A trailing "/" will be ignored and the path must begin with /.
     * @return a new instance of ServerEndpointConfigurationBuilder
     */
    public static ServerEndpointConfigurationBuilder create(Class<?> endpointClass, String path)
    {
        return new ServerEndpointConfigurationBuilder(endpointClass,path);
    }

    private String path;
    private Class<?> endpointClass;
    private List<String> subprotocols = new ArrayList<String>();
    private List<Extension> extensions = new ArrayList<Extension>();
    private List<Encoder> encoders = new ArrayList<Encoder>();
    private List<Decoder> decoders = new ArrayList<Decoder>();

    private ServerEndpointConfigurator serverEndpointConfigurator;

    // only one way to build them
    private ServerEndpointConfigurationBuilder()
    {

    }

    private ServerEndpointConfigurationBuilder(Class<?> endpointClass, String path)
    {
        if (endpointClass == null)
        {
            throw new IllegalArgumentException("endpointClass cannot be null");
        }
        this.endpointClass = endpointClass;
        if ((path == null) || !path.startsWith("/"))
        {
            throw new IllegalStateException("Path cannot be null and must begin with /");
        }
        this.path = path;
    }

    /**
     * Builds the configuration object using the current attributes that have been set on this builder object.
     * 
     * @return a new ServerEndpointConfiguration object.
     */
    public ServerEndpointConfiguration build()
    {
        return new DefaultServerEndpointConfiguration(this.endpointClass,this.path,Collections.unmodifiableList(this.subprotocols),
                Collections.unmodifiableList(this.extensions),Collections.unmodifiableList(this.encoders),Collections.unmodifiableList(this.decoders),
                this.serverEndpointConfigurator);
    }

    /**
     * Sets the decoders to use in the configuration.
     * 
     * @param decoders
     *            the decoders
     * @return this builder instance.
     */
    public ServerEndpointConfigurationBuilder decoders(List<Decoder> decoders)
    {
        this.decoders = (decoders == null)?new ArrayList<Decoder>():decoders;
        return this;
    }

    /**
     * Sets the list of encoders for this builder.
     * 
     * @param encoders
     *            the encoders
     * @return this builder instance
     */
    public ServerEndpointConfigurationBuilder encoders(List<Encoder> encoders)
    {
        this.encoders = (encoders == null)?new ArrayList<Encoder>():encoders;
        return this;
    }

    /**
     * Sets the extensions to use in the configuration.
     * 
     * @param extensions
     *            the extensions to use.
     * @return this builder instance.
     */
    public ServerEndpointConfigurationBuilder extensions(List<Extension> extensions)
    {
        this.extensions = (extensions == null)?new ArrayList<Extension>():extensions;
        return this;
    }

    /**
     * Sets the custom configurator to use on the configuration object built by this builder.
     * 
     * @param serverEndpointConfigurator
     *            the configurator
     * @return this builder instance
     */
    public ServerEndpointConfigurationBuilder serverEndpointConfigurator(ServerEndpointConfigurator serverEndpointConfigurator)
    {
        this.serverEndpointConfigurator = serverEndpointConfigurator;
        return this;
    }

    /**
     * Sets the subprotocols to use in the configuration.
     * 
     * @param subprotocols
     *            the subprotocols.
     * @return this builder instance
     */
    public ServerEndpointConfigurationBuilder subprotocols(List<String> subprotocols)
    {
        this.subprotocols = (subprotocols == null)?new ArrayList<String>():subprotocols;
        return this;
    }

}