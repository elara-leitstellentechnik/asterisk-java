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
package org.asteriskjava.manager.action;

import org.asteriskjava.manager.event.BridgeListCompleteEvent;
import org.asteriskjava.manager.event.ResponseEvent;

public class BridgeListAction extends AbstractManagerAction implements EventGeneratingAction {
	/**
	 * Serializable version identifier.
	 */
	private static final long serialVersionUID = 0L;

	private String bridgeType;

	public BridgeListAction() {
	}

	public BridgeListAction(String bridgeType) {
		this.bridgeType = bridgeType;
	}

	/**
	 * Returns the name of this action, i.e. "BridgeList".
	 */
	@Override
	public String getAction() {
		return "BridgeList";
	}

	public String getBridgeType() {
		return bridgeType;
	}

	public void setBridgeType(String bridgeType) {
		this.bridgeType = bridgeType;
	}

	@Override
	public Class<? extends ResponseEvent> getActionCompleteEventClass() {
		return BridgeListCompleteEvent.class;
	}
}