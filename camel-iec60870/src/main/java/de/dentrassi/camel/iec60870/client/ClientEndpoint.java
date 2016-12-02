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

import java.util.Objects;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;

import de.dentrassi.camel.iec60870.ObjectAddress;
import de.dentrassi.camel.iec60870.Options;
import de.dentrassi.camel.iec60870.client.internal.AbstractConnectionMultiplexor.Handle;
import de.dentrassi.camel.iec60870.client.internal.ClientConnectionMultiplexor;

@UriEndpoint(scheme = "iec60870-client", syntax = "iec60870-client:host:port/00-00-00-00-00", title = "IEC 60870-5-104 client", consumerClass = ClientConsumer.class, label = "iot")
public class ClientEndpoint extends DefaultEndpoint {

	private final ClientConnectionMultiplexor connection;
	private Handle connectionHandle;

	/**
	 * The object information address
	 */
	@UriPath(name = "uriPath")
	@Metadata(required = "true")
	private final ObjectAddress address;

	// dummy for doc generation
	@UriParam
	private Options connectionOptions;

	/**
	 * An identifier grouping connection instances
	 */
	@UriParam
	private String connectionId;

	public ClientEndpoint(final String uri, final ClientComponent component,
			final ClientConnectionMultiplexor connection, final ObjectAddress address) {
		super(uri, component);

		Objects.requireNonNull(connection);
		Objects.requireNonNull(address);

		this.connection = connection;
		this.address = address;
	}

	public ObjectAddress getAddress() {
		return this.address;
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		this.connectionHandle = this.connection.register();
	}

	@Override
	protected void doStop() throws Exception {
		this.connectionHandle.unregister();
		super.doStop();
	}

	@Override
	public Producer createProducer() throws Exception {
		return new ClientProducer(this, this.connection.getConnection());
	}

	@Override
	public Consumer createConsumer(final Processor processor) throws Exception {
		return new ClientConsumer(this, processor, this.connection.getConnection());
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
