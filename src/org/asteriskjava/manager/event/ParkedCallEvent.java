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
 * A ParkedCallEvent is triggered when a channel is parked (in this case no
 * action id is set) and in response to a ParkedCallsAction.<p>
 * It is implemented in <code>res/res_features.c</code>
 * 
 * @see org.asteriskjava.manager.action.ParkedCallsAction
 * @author srt
 * @version $Id$
 */
public class ParkedCallEvent extends AbstractParkedCallEvent
{
    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 0L;
    
    private Integer timeout;
    
    /**
     * Enro 2015-03: Modified ParkedCallEvent Structure to support Asterisk 13
     */
    private String systemName;
    // Previously: Channel
    private String parkeeChannel;
    private Integer parkeeChannelState;
    private String parkeeChannelStateDesc;
    // previously CallerID
    private String parkeeCallerIDNum;
    // Previously CallerIDName
    private String parkeeCallerIDName;
    private Integer parkeeConnectedLineNum;
    private String parkeeConnectedLineName;
    private String parkeeLanguage;
    private String parkeeAccountCode; 
    private String parkeeContext;
    private String parkeeExten;
    private Integer parkeePriority;
    private String parkeeUniqueid;
    // Previously: From
    private String parkerDialString;
    private String parkinglot;
    // previously Exten
    private String parkingSpace;
    private Long parkingTimeout;
    private Long parkingDuration;


    /**
     * @param source
     */
    public ParkedCallEvent(Object source)
    {
        super(source);
    }

    /**
     * Returns the name of the channel that parked the call.
     */
    @Deprecated
    public String getFrom()
    {
        return getParkerDialString();
    }

    /**
     * Sets the name of the channel that parked the call.
     */
    @Deprecated
    public void setFrom(String from)
    {
        this.setParkerDialString(from);
    }

    /**
     * Returns the number of seconds this call will be parked.<p>
     * This corresponds to the <code>parkingtime</code> option in
     * <code>features.conf</code>.
     */
    public Integer getTimeout()
    {
        return timeout;
    }

    /**
     * Sets the number of seconds this call will be parked.
     */
    public void setTimeout(Integer timeout)
    {
        this.timeout = timeout;
    }
  
    /**
     * Sets the unique id of the parked channel as a
     * workaround for a typo in asterisk manager event.
     */
    public void setUnqiueId(String unqiueId)
    {
        setUniqueId(unqiueId);
    }
    
    @Override
    @Deprecated
    public void setChannel(String channel) {
    	this.setParkeeChannel(channel);
    }
    
    @Override
    @Deprecated
    public String getChannel() {
    	return this.getParkeeChannel();
    }

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getParkeeChannel() {
		return parkeeChannel;
	}

	public void setParkeeChannel(String parkeeChannel) {
		this.parkeeChannel = parkeeChannel;
	}

	public Integer getParkeeChannelState() {
		return parkeeChannelState;
	}

	public void setParkeeChannelState(Integer parkeeChannelState) {
		this.parkeeChannelState = parkeeChannelState;
	}

	public String getParkeeChannelStateDesc() {
		return parkeeChannelStateDesc;
	}

	public void setParkeeChannelStateDesc(String parkeeChannelStateDesc) {
		this.parkeeChannelStateDesc = parkeeChannelStateDesc;
	}

	public String getParkeeCallerIDNum() {
		return parkeeCallerIDNum;
	}

	public void setParkeeCallerIDNum(String parkeeCallerIDNum) {
		this.parkeeCallerIDNum = parkeeCallerIDNum;
	}

	public String getParkeeCallerIDName() {
		return parkeeCallerIDName;
	}

	public void setParkeeCallerIDName(String parkeeCallerIDName) {
		this.parkeeCallerIDName = parkeeCallerIDName;
	}

	public Integer getParkeeConnectedLineNum() {
		return parkeeConnectedLineNum;
	}

	public void setParkeeConnectedLineNum(Integer parkeeConnectedLineNum) {
		this.parkeeConnectedLineNum = parkeeConnectedLineNum;
	}

	public String getParkeeConnectedLineName() {
		return parkeeConnectedLineName;
	}

	public void setParkeeConnectedLineName(String parkeeConnectedLineName) {
		this.parkeeConnectedLineName = parkeeConnectedLineName;
	}

	public String getParkeeLanguage() {
		return parkeeLanguage;
	}

	public void setParkeeLanguage(String parkeeLanguage) {
		this.parkeeLanguage = parkeeLanguage;
	}

	public String getParkeeAccountCode() {
		return parkeeAccountCode;
	}

	public void setParkeeAccountCode(String parkeeAccountCode) {
		this.parkeeAccountCode = parkeeAccountCode;
	}

	public String getParkeeContext() {
		return parkeeContext;
	}

	public void setParkeeContext(String parkeeContext) {
		this.parkeeContext = parkeeContext;
	}

	public String getParkeeExten() {
		return parkeeExten;
	}

	public void setParkeeExten(String parkeeExten) {
		this.parkeeExten = parkeeExten;
	}

	public Integer getParkeePriority() {
		return parkeePriority;
	}

	public void setParkeePriority(Integer parkeePriority) {
		this.parkeePriority = parkeePriority;
	}

	public String getParkeeUniqueid() {
		return parkeeUniqueid;
	}

	public void setParkeeUniqueid(String parkeeUniqueid) {
		this.parkeeUniqueid = parkeeUniqueid;
	}

	public String getParkerDialString() {
		return parkerDialString;
	}

	public void setParkerDialString(String parkerDialString) {
		this.parkerDialString = parkerDialString;
	}

	public String getParkinglot() {
		return parkinglot;
	}

	public void setParkinglot(String parkinglot) {
		this.parkinglot = parkinglot;
	}

	public String getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(String parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public Long getParkingTimeout() {
		return parkingTimeout;
	}

	public void setParkingTimeout(Long parkingTimeout) {
		this.parkingTimeout = parkingTimeout;
	}

	public Long getParkingDuration() {
		return parkingDuration;
	}

	public void setParkingDuration(Long parkingDuration) {
		this.parkingDuration = parkingDuration;
	}

	@Override
	@Deprecated
	public String getCallerId() {
		return getParkeeCallerIDNum();
	}
	
	@Override
	@Deprecated
	public void setCallerId(String callerId) {
		setParkeeCallerIDNum(callerId);
	}
	
	@Override
	@Deprecated
	public String getCallerIdName() {
		return getParkeeCallerIDName();
	}
	
	@Override
	@Deprecated
	public void setCallerIdName(String callerIdName) {
		setParkeeCallerIDName(callerIdName);
	}

	@Override
	@Deprecated
	public String getExten() {
		return getParkingSpace();
	}
	
	@Override
	@Deprecated
	public void setExten(String exten) {
		setParkingSpace(exten);
	}
}
