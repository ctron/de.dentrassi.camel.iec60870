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

package de.dentrassi.camel.iec60870;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.neoscada.protocol.iec60870.ProtocolOptions;
import org.eclipse.neoscada.protocol.iec60870.client.data.DataModuleOptions;

import de.dentrassi.camel.iec60870.client.ClientOptions;
import de.dentrassi.camel.iec60870.internal.AbstractConnectionMultiplexor;
import de.dentrassi.camel.iec60870.internal.AbstractConnectionMultiplexor.Handle;

public abstract class AbstractIecEndpoint<@NonNull T extends AbstractConnectionMultiplexor> extends DefaultEndpoint {

	/**
	 * The object information address
	 */
	@UriPath(name = "uriPath")
	@Metadata(required = "true")
	private final ObjectAddress address;

	// dummy for doc generation
	/**
	 * A full set of connection options
	 */
	@UriParam
	private ClientOptions connectionOptions;

	// dummy for doc generation
	/**
	 * A set of protocol options
	 */
	@UriParam
	private ProtocolOptions protocolOptions;

	// dummy for doc generation
	/**
	 * A set of data module options
	 */
	@UriParam
	private DataModuleOptions dataModuleOptions;

	// dummy for doc generation
	/**
	 * An identifier grouping connection instances
	 */
	@UriParam(label = "id")
	private String connectionId;

	private final T connection;

	private final AtomicReference<Handle> connectionHandle = new AtomicReference<>();

	public AbstractIecEndpoint(final String uri, final DefaultComponent component, final T connection,
			final ObjectAddress address) {
		super(uri, component);

		this.connection = requireNonNull(connection);
		this.address = requireNonNull(address);
	}

	public ObjectAddress getAddress() {
		return this.address;
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		this.connectionHandle.set(this.connection.register());
	}

	@Override
	protected void doStop() throws Exception {
		final Handle connectionHandle = this.connectionHandle.getAndSet(null);
		if (connectionHandle != null) {
			connectionHandle.unregister();
		}
		super.doStop();
	}

	protected T getConnection() {
		return this.connection;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
