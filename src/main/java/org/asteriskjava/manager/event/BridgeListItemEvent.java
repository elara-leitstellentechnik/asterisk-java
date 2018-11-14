/*
 *  Copyright 2004-2006 Stefan Reuter
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.asteriskjava.manager.event;

/**
 * A BridgeListItemEvent is triggered for each active bridgeCreator in response to a
 * BridgeListAction.
 *
 * @author srt
 * @version $Id$
 * @see org.asteriskjava.manager.action.BridgeListAction
 */
public class BridgeListItemEvent extends ResponseEvent {
	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 0L;

	private String bridgeUniqueId;
	private String bridgeType;
	private Integer bridgeNumChannels;
	private String bridgeCreator;
	private String bridgeName;
	private String bridgeTechnology;
	private String accountCode;

	public BridgeListItemEvent(Object source) {
		super(source);
	}

	public String getBridgeUniqueId() {
		return bridgeUniqueId;
	}

	public void setBridgeUniqueId(String bridgeUniqueId) {
		this.bridgeUniqueId = bridgeUniqueId;
	}

	public String getBridgeType() {
		return bridgeType;
	}

	public void setBridgeType(String bridgeType) {
		this.bridgeType = bridgeType;
	}

	public Integer getBridgeNumChannels() {
		return bridgeNumChannels;
	}

	public void setBridgeNumChannels(Integer bridgeNumChannels) {
		this.bridgeNumChannels = bridgeNumChannels;
	}

	public String getBridgeCreator() {
		return bridgeCreator;
	}

	public void setBridgeCreator(String bridgeCreator) {
		this.bridgeCreator = bridgeCreator;
	}

	public String getBridgeName() {
		return bridgeName;
	}

	public void setBridgeName(String bridgeName) {
		this.bridgeName = bridgeName;
	}

	public String getBridgeTechnology() {
		return bridgeTechnology;
	}

	public void setBridgeTechnology(String bridgeTechnology) {
		this.bridgeTechnology = bridgeTechnology;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

}
