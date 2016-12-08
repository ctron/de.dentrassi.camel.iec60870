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

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.eclipse.neoscada.protocol.iec60870.client.data.DataModuleOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.dentrassi.camel.iec60870.AbstractIecComponent;
import de.dentrassi.camel.iec60870.Constants;
import de.dentrassi.camel.iec60870.ObjectAddress;
import de.dentrassi.camel.iec60870.internal.ConnectionId;
import de.dentrassi.camel.iec60870.internal.client.ClientConnection;
import de.dentrassi.camel.iec60870.internal.client.ClientConnectionMultiplexor;

public class ClientComponent extends AbstractIecComponent<ClientConnectionMultiplexor, ClientOptions> {

	private final static Logger logger = LoggerFactory.getLogger(ClientComponent.class);

	public ClientComponent(final CamelContext context) {
		super(ClientOptions.class, new ClientOptions(), context, ClientEndpoint.class);
	}

	public ClientComponent() {
		super(ClientOptions.class, new ClientOptions(), ClientEndpoint.class);
	}

	@Override
	protected void applyDataModuleOptions(final ClientOptions options, final Map<String, Object> parameters) {
		if (parameters.get(Constants.PARAM_DATA_MODULE_OPTIONS) instanceof DataModuleOptions) {
			options.setDataModuleOptions((DataModuleOptions) parameters.get(Constants.PARAM_DATA_MODULE_OPTIONS));
		}
	}

	@Override
	protected Endpoint createEndpoint(final String uri, final ClientConnectionMultiplexor connection,
			final ObjectAddress address) {
		return new ClientEndpoint(uri, this, connection, address);
	}

	@Override
	protected ClientConnectionMultiplexor createConnection(final ConnectionId id, final ClientOptions options) {
		logger.debug("Create new connection - id: {}", id);

		return new ClientConnectionMultiplexor(new ClientConnection(id.getHost(), id.getPort(), options));
	}

}
