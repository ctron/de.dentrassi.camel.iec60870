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

import java.util.Objects;
import java.util.TimeZone;

import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;
import org.eclipse.neoscada.protocol.iec60870.ASDUAddressType;
import org.eclipse.neoscada.protocol.iec60870.CauseOfTransmissionType;
import org.eclipse.neoscada.protocol.iec60870.InformationObjectAddressType;
import org.eclipse.neoscada.protocol.iec60870.ProtocolOptions;
import org.eclipse.neoscada.protocol.iec60870.client.data.DataModuleOptions;

@UriParams
public class Options {

	/**
	 * Protocol options
	 */
	@UriParam(javaType = "ProtocolOptions")
	private ProtocolOptions.Builder protocolOptions;

	/**
	 * Data module options
	 */
	@UriParam(javaType = "DataModuleOptions")
	private DataModuleOptions.Builder dataModuleOptions;

	public Options() {
		this.protocolOptions = new ProtocolOptions.Builder();
		this.dataModuleOptions = new DataModuleOptions.Builder();
	}

	public Options(final Options other) {
		this(other.getProtocolOptions(), other.getDataModuleOptions());
	}

	public Options(final ProtocolOptions protocolOptions, final DataModuleOptions dataOptions) {
		Objects.requireNonNull(protocolOptions);
		Objects.requireNonNull(dataOptions);

		this.protocolOptions = new ProtocolOptions.Builder(protocolOptions);
		this.dataModuleOptions = new DataModuleOptions.Builder(dataOptions);
	}

	public void setProtocolOptions(final ProtocolOptions protocolOptions) {
		Objects.requireNonNull(protocolOptions);

		this.protocolOptions = new ProtocolOptions.Builder(protocolOptions);
	}

	public ProtocolOptions getProtocolOptions() {
		return this.protocolOptions.build();
	}

	public void setDataModuleOptions(final DataModuleOptions dataModuleOptions) {
		Objects.requireNonNull(dataModuleOptions);

		this.dataModuleOptions = new DataModuleOptions.Builder(dataModuleOptions);
	}

	public DataModuleOptions getDataModuleOptions() {
		return this.dataModuleOptions.build();
	}

	// wrapper methods - DataModuleOptions

	public void setCauseSourceAddress(final Byte causeSourceAddress) {
		this.dataModuleOptions.setCauseSourceAddress(causeSourceAddress);
	}

	public Byte getCauseSourceAddress() {
		return this.dataModuleOptions.getCauseSourceAddress();
	}

	public void setIgnoreBackgroundScan(final boolean ignoreBackgroundScan) {
		this.dataModuleOptions.setIgnoreBackgroundScan(ignoreBackgroundScan);
	}

	public boolean isIgnoreBackgroundScan() {
		return this.dataModuleOptions.isIgnoreBackgroundScan();
	}

	// wrapper methods - ProtocolOptions

	public int getTimeout1() {
		return this.protocolOptions.getTimeout1();
	}

	public void setTimeout1(final int timeout1) {
		this.protocolOptions.setTimeout1(timeout1);
	}

	public int getTimeout2() {
		return this.protocolOptions.getTimeout2();
	}

	public void setTimeout2(final int timeout2) {
		this.protocolOptions.setTimeout2(timeout2);
	}

	public int getTimeout3() {
		return this.protocolOptions.getTimeout3();
	}

	public void setTimeout3(final int timeout3) {
		this.protocolOptions.setTimeout3(timeout3);
	}

	public short getAcknowledgeWindow() {
		return this.protocolOptions.getAcknowledgeWindow();
	}

	public void setAcknowledgeWindow(final short acknowledgeWindow) {
		this.protocolOptions.setAcknowledgeWindow(acknowledgeWindow);
	}

	public short getMaxUnacknowledged() {
		return this.protocolOptions.getMaxUnacknowledged();
	}

	public void setMaxUnacknowledged(final short maxUnacknowledged) {
		this.protocolOptions.setMaxUnacknowledged(maxUnacknowledged);
	}

	// dummy for doc generation
	/**
	 * The common ASDU address size
	 */
	@UriParam(enums = "SIZE_1, SIZE_2")
	private ASDUAddressType adsuAddressType;

	public ASDUAddressType getAdsuAddressType() {
		return this.protocolOptions.getAdsuAddressType();
	}

	public void setAdsuAddressType(final ASDUAddressType adsuAddressType) {
		this.protocolOptions.setAdsuAddressType(adsuAddressType);
	}

	// dummy for doc generation
	/**
	 * The information address size
	 */
	@UriParam(enums = "SIZE_1, SIZE_2, SIZE_3")
	private InformationObjectAddressType informationObjectAddressType;

	public InformationObjectAddressType getInformationObjectAddressType() {
		return this.protocolOptions.getInformationObjectAddressType();
	}

	public void setInformationObjectAddressType(final InformationObjectAddressType informationObjectAddressType) {
		this.protocolOptions.setInformationObjectAddressType(informationObjectAddressType);
	}

	// dummy for doc generation
	/**
	 * The cause of transmission type
	 */
	@UriParam(enums = "SIZE_1, SIZE_2")
	private CauseOfTransmissionType causeOfTransmissionType;

	public CauseOfTransmissionType getCauseOfTransmissionType() {
		return this.protocolOptions.getCauseOfTransmissionType();
	}

	public void setCauseOfTransmissionType(final CauseOfTransmissionType causeOfTransmissionType) {
		this.protocolOptions.setCauseOfTransmissionType(causeOfTransmissionType);
	}

	// dummy for doc generation
	/**
	 * The timezone to use
	 */
	@UriParam
	private TimeZone timeZone;

	public TimeZone getTimeZone() {
		return this.protocolOptions.getTimeZone();
	}

	public void setTimeZone(final TimeZone timeZone) {
		this.protocolOptions.setTimeZone(timeZone);
	}

	// dummy for doc generation
	/**
	 * Whether to ignore or respect DST
	 */
	@UriParam
	private boolean ignoreDaylightSavingTime;

	public void setIgnoreDaylightSavingTime(final boolean ignoreDaylightSavingTime) {
		this.protocolOptions.setIgnoreDaylightSavingTime(ignoreDaylightSavingTime);
	}

	public boolean isIgnoreDaylightSavingTime() {
		return this.protocolOptions.isIgnoreDaylightSavingTime();
	}

}
