/*
 * Copyright (C) 2016 Jens Reimann <jreimann@redhat.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dentrassi.camel.iec60870.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;
import org.eclipse.neoscada.protocol.iec60870.ProtocolOptions;
import org.eclipse.neoscada.protocol.iec60870.client.data.DataModuleOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dentrassi.camel.iec60870.ObjectAddress;
import de.dentrassi.camel.iec60870.Options;
import de.dentrassi.camel.iec60870.client.internal.ClientConnection;
import de.dentrassi.camel.iec60870.client.internal.ClientConnectionMultiplexor;
import de.dentrassi.camel.iec60870.client.internal.ConnectionId;

public class ClientComponent extends UriEndpointComponent {

	private final static Logger logger = LoggerFactory.getLogger(ClientComponent.class);

	private final Map<ConnectionId, ClientConnectionMultiplexor> connections = new HashMap<>();

	private Options defaultConnectionOptions = new Options();

	public ClientComponent(final CamelContext context) {
		super(context, ClientEndpoint.class);
	}

	public ClientComponent() {
		super(ClientEndpoint.class);
	}

	/**
	 * Default connection options
	 */
	public void setDefaultConnectionOptions(final Options defaultConnectionOptions) {
		this.defaultConnectionOptions = defaultConnectionOptions;
	}

	public Options getDefaultConnectionOptions() {
		return this.defaultConnectionOptions;
	}

	@Override
	protected Endpoint createEndpoint(final String uri, final String remaining, final Map<String, Object> parameters)
			throws Exception {

		logger.info("Create endpoint - uri: {}, remaining: {}, parameters: {}", uri, remaining, parameters);

		final ClientConnectionMultiplexor connection = parseConnection(uri, parameters);
		final ObjectAddress address = parseAddress(uri);

		return new ClientEndpoint(uri, this, connection, address);
	}

	private ClientConnectionMultiplexor parseConnection(final String fullUri, final Map<String, Object> parameters)
			throws Exception {

		logger.debug("parse connection - '{}'", fullUri);

		if (fullUri == null || fullUri.isEmpty()) {
			throw new IllegalArgumentException("Invalid URI: " + fullUri);
		}

		final ConnectionId id = parseConnectionId(fullUri, parameters);

		logger.debug("parse connection - fullUri: {} -> {}", fullUri, id);

		synchronized (this) {
			logger.debug("Locating connection - {}", id);

			ClientConnectionMultiplexor connection = this.connections.get(id);

			logger.debug("Result - {} -> {}", id, connection);

			if (connection == null) {
				final Options options = parseOptions(id, parameters);
				logger.debug("Creating new connection: {}", options);

				connection = createConnection(id, options);
				this.connections.put(id, connection);
			}
			return connection;
		}
	}

	private ClientConnectionMultiplexor createConnection(final ConnectionId id, final Options options) {
		logger.debug("Create new connection - id: {}", id);

		return new ClientConnectionMultiplexor(new ClientConnection(id.getHost(), id.getPort(), options));
	}

	private ConnectionId parseConnectionId(final String fullUri, final Map<String, Object> parameters) {
		final URI uri = URI.create(fullUri);

		final Object connectionId = parameters.get("connectionId");

		return new ConnectionId(uri.getHost(), uri.getPort(),
				connectionId instanceof String ? (String) connectionId : null);
	}

	private ObjectAddress parseAddress(final String fullUri) {
		final URI uri = URI.create(fullUri);

		String path = uri.getPath();
		path = path.replaceAll("^\\/+", "");

		return ObjectAddress.valueOf(path);
	}

	private Options parseOptions(final ConnectionId id, final Map<String, Object> parameters) throws Exception {

		{
			final Object o = parameters.get("connectionOptions");
			if (o != null) {
				if (o instanceof Options) {
					return (Options) o;
				} else {
					throw new IllegalArgumentException(
							"'connectionOptions' must by of type " + Options.class.getName());
				}
			}
		}

		final Options options = new Options(this.defaultConnectionOptions);

		if (parameters.get("protocolOptions") instanceof ProtocolOptions) {
			options.setProtocolOptions((ProtocolOptions) parameters.get("protocolOptions"));
		}

		if (parameters.get("dataModuleOptions") instanceof DataModuleOptions) {
			options.setDataModuleOptions((DataModuleOptions) parameters.get("protocolModuleOptions"));
		}

		setProperties(options, parameters);

		return options;
	}

}
