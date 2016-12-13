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

package de.dentrassi.camel.iec60870.server;

import java.util.Objects;

import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.neoscada.protocol.iec60870.ProtocolOptions;
import org.eclipse.neoscada.protocol.iec60870.server.data.DataModuleOptions;

import de.dentrassi.camel.iec60870.BaseOptions;

@UriParams
public class ServerOptions extends BaseOptions<ServerOptions> {

	/**
	 * Data module options
	 */
	@UriParam(javaType = "DataModuleOptions", label = "data")
	private DataModuleOptions.Builder dataModuleOptions;

	/**
	 * A time period in "ms" the protocol layer will buffer change events in
	 * order to send out aggregated change messages
	 */
	@UriParam(label = "data")
	private Integer bufferingPeriod;

	public ServerOptions() {
		this.dataModuleOptions = new DataModuleOptions.Builder();
	}

	public ServerOptions(final ServerOptions other) {
		this(other.getProtocolOptions(), other.getDataModuleOptions());
	}

	public ServerOptions(final ProtocolOptions protocolOptions, final DataModuleOptions dataModuleOptions) {
		super(protocolOptions);

		Objects.requireNonNull(dataModuleOptions);

		this.dataModuleOptions = new DataModuleOptions.Builder(dataModuleOptions);
	}

	@Override
	public @NonNull ServerOptions copy() {
		return new ServerOptions(this);
	}

	public void setDataModuleOptions(final DataModuleOptions dataModuleOptions) {
		Objects.requireNonNull(dataModuleOptions);

		this.dataModuleOptions = new DataModuleOptions.Builder(dataModuleOptions);
	}

	public void setBufferingPeriod(final Integer bufferingPeriod) {
		this.bufferingPeriod = bufferingPeriod;
	}

	public Integer getBufferingPeriod() {
		return this.bufferingPeriod;
	}

	// wrapper methods - DataModuleOptions

	public DataModuleOptions getDataModuleOptions() {
		return this.dataModuleOptions.build();
	}

	public void setBooleansWithTimestamp(final boolean booleansWithTimestamp) {
		this.dataModuleOptions.setBooleansWithTimestamp(booleansWithTimestamp);
	}

	public boolean isBooleansWithTimestamp() {
		return this.dataModuleOptions.isBooleansWithTimestamp();
	}

	public void setFloatsWithTimestamp(final boolean floatsWithTimestamp) {
		this.dataModuleOptions.setFloatsWithTimestamp(floatsWithTimestamp);
	}

	public boolean isFloatsWithTimestamp() {
		return this.dataModuleOptions.isFloatsWithTimestamp();
	}

	public void setSpontaneousDuplicates(final int spontaneousDuplicates) {
		this.dataModuleOptions.setSpontaneousDuplicates(spontaneousDuplicates);
	}

	public int getSpontaneousDuplicates() {
		return this.dataModuleOptions.getSpontaneousDuplicates();
	}

	public void setBackgroundScanPeriod(final int backgroundScanPeriod) {
		this.dataModuleOptions.setBackgroundScanPeriod(backgroundScanPeriod);
	}

	public int getBackgroundScanPeriod() {
		return this.dataModuleOptions.getBackgroundScanPeriod();
	}

}
