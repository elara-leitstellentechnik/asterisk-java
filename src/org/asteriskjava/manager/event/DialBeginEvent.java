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
 * A dial begin event is triggered whenever a phone attempts to dial someone.<p>
 * Available since Asterisk 13.
 *
 * @author Asteria Solutions Group, Inc. http://www.asteriasgi.com/
 * @version $Id$
 * @since 1.0.0 - 10 (Elara)
 */
public class DialBeginEvent extends ManagerEvent
{
    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 1L;

    public static final String DIALSTATUS_CHANUNAVAIL = "CHANUNAVAIL";
    public static final String DIALSTATUS_CONGESTION = "CONGESTION";
    public static final String DIALSTATUS_NOANSWER = "NOANSWER";
    public static final String DIALSTATUS_BUSY = "BUSY";
    public static final String DIALSTATUS_ANSWER = "ANSWER";
    public static final String DIALSTATUS_CANCEL = "CANCEL";
    public static final String DIALSTATUS_DONTCALL = "DONTCALL";
    public static final String DIALSTATUS_TORTURE = "TORTURE";
    public static final String DIALSTATUS_INVALIDARGS = "INVALIDARGS";

    private String connectedlinename;
    private String connectedlinenum;
    private String context;
    private String destContext;
    private Integer priority;
    private Integer destPriority;
    private String destCallerIdNum;
    private String destCallerIdName;
    private Integer channelState;
    private String channelStateDesc;
    private Integer destChannelState;
    private String destChannelStateDesc;
    private String systemName;
    private String language;
    private String accountCode; 
    private String exten;
    private String destLanguage;
    private String destAccountCode; 
    private String destExten;
    
    
    /**
     * The name of the source channel.
     */
    private String channel;

    /**
     * The name of the destination channel.
     */
    private String destination;

    /**
     * The new Caller*ID.
     */
    private String callerIdNum;

    /**
     * The new Caller*ID Name.
     */
    private String callerIdName;

    /**
     * The unique id of the source channel.
     */
    private String uniqueId;

    /**
     * The unique id of the destination channel.
     */
    private String destUniqueId;

    private String dialString;
    private String dialStatus;

    public DialBeginEvent(Object source)
    {
        super(source);
    }

    /**
     * Returns the name of the source channel.
     *
     * @return the name of the source channel.
     * @since 1.0.0
     */
    public String getChannel()
    {
        return channel;
    }

    /**
     * Returns the name of the source channel.
     *
     * @param channel the name of the source channel.
     * @since 1.0.0
     */
    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    /**
     * Returns the name of the source channel.
     *
     * @return the name of the source channel.
     * @deprecated as of 1.0.0, use {@link #getChannel()} instead.
     */
    @Deprecated public String getSrc()
    {
        return channel;
    }

    /**
     * Sets the name of the source channel.<p>
     * Asterisk versions up to 1.4 use the "Source" property instead of "Channel".
     *
     * @param src the name of the source channel.
     */
    public void setSrc(String src)
    {
        this.channel = src;
    }

    /**
     * Returns the name of the destination channel.
     *
     * @return the name of the destination channel.
     */
    public String getDestination()
    {
        return destination;
    }

    /**
     * Sets the name of the destination channel.
     *
     * @param destination the name of the destination channel.
     */
    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    /**
     * Returns the the Caller*ID Number.
     *
     * @return the the Caller*ID Number or "&lt;unknown&gt;" if none has been set.
     * @since 1.0.0
     */
    public String getCallerIdNum()
    {
        return callerIdNum;
    }

    public void setCallerIdNum(String callerIdNum)
    {
        this.callerIdNum = callerIdNum;
    }

    /**
     * Returns the Caller*ID.
     *
     * @return the Caller*ID or "&lt;unknown&gt;" if none has been set.
     * @deprecated as of 1.0.0, use {@link #getCallerIdNum()} instead.
     */
    @Deprecated public String getCallerId()
    {
        return callerIdNum;
    }

    /**
     * Sets the caller*ID.
     *
     * @param callerId the caller*ID.
     */
    public void setCallerId(String callerId)
    {
        this.callerIdNum = callerId;
    }

    /**
     * Returns the Caller*ID Name.
     *
     * @return the Caller*ID Name or "&lt;unknown&gt;" if none has been set.
     */
    public String getCallerIdName()
    {
        return callerIdName;
    }

    /**
     * Sets the Caller*Id Name.
     *
     * @param callerIdName the Caller*Id Name to set.
     */
    public void setCallerIdName(String callerIdName)
    {
        this.callerIdName = callerIdName;
    }

    /**
     * Returns the unique ID of the source channel.
     *
     * @return the unique ID of the source channel.
     * @since 1.0.0
     */
    public String getUniqueId()
    {
        return uniqueId;
    }

    /**
     * Sets the unique ID of the source channel.
     *
     * @param srcUniqueId the unique ID of the source channel.
     * @since 1.0.0
     */
    public void setUniqueId(String srcUniqueId)
    {
        this.uniqueId = srcUniqueId;
    }

    /**
     * Returns the unique ID of the source channel.
     *
     * @return the unique ID of the source channel.
     * @deprecated as of 1.0.0, use {@link #getUniqueId()} instead.
     */
    @Deprecated public String getSrcUniqueId()
    {
        return uniqueId;
    }

    /**
     * Sets the unique ID of the source channel.<p>
     * Asterisk versions up to 1.4 use the "SrcUniqueId" property instead of "UniqueId".
     *
     * @param srcUniqueId the unique ID of the source channel.
     */
    public void setSrcUniqueId(String srcUniqueId)
    {
        this.uniqueId = srcUniqueId;
    }

    /**
     * Returns the unique ID of the destination channel.
     *
     * @return the unique ID of the destination channel.
     */
    public String getDestUniqueId()
    {
        return destUniqueId;
    }

    /**
     * Sets the unique ID of the destination channel.
     *
     * @param destUniqueId the unique ID of the destination channel.
     */
    public void setDestUniqueId(String destUniqueId)
    {
        this.destUniqueId = destUniqueId;
    }

    /**
     * Returns the dial string passed to the Dial application.<p>
     * Available since Asterisk 1.6.
     *
     * @return the dial string passed to the Dial application.
     * @since 1.0.0
     */
    public String getDialString()
    {
        return dialString;
    }

    /**
     * Sets the dial string passed to the Dial application.
     *
     * @param dialString the dial string passed to the Dial application.
     * @since 1.0.0
     */
    public void setDialString(String dialString)
    {
        this.dialString = dialString;
    }

    /**
     * For end subevents this returns whether the completion status of the dial application.<br>
     * Possible values are:
     * <ul>
     * <li>CHANUNAVAIL</li>
     * <li>CONGESTION</li>
     * <li>NOANSWER</li>
     * <li>BUSY</li>
     * <li>ANSWER</li>
     * <li>CANCEL</li>
     * <li>DONTCALL</li>
     * <li>TORTURE</li>
     * <li>INVALIDARGS</li>
     * </ul>
     * It corresponds the the DIALSTATUS variable used in the dialplan.<p>
     * Available since Asterisk 1.6.
     *
     * @return the completion status of the dial application.
     * @since 1.0.0
     */
    public String getDialStatus()
    {
        return dialStatus;
    }

    public void setDialStatus(String dialStatus)
    {
        this.dialStatus = dialStatus;
    }

    /**
     * Returns the Caller*ID name of the channel connected if set.
     * If the channel has no caller id set "unknown" is returned.
     *
     * @since 1.0.0
     */
    public String getConnectedlinename()
    {
        return connectedlinename;
    }

    public void setConnectedlinename(String connectedlinename)
    {
        this.connectedlinename = connectedlinename;
    }

    /**
     * Returns the Caller*ID number of the channel connected if set.
     * If the channel has no caller id set "unknown" is returned.
     *
     * @since 1.0.0
     */
    public String getConnectedlinenum()
    {
        return connectedlinenum;
    }

    public void setConnectedlinenum(String connectedlinenum)
    {
        this.connectedlinenum = connectedlinenum;
    }
    
    public String getContext() {
		return context;
	}
    
    public void setContext(String context) {
		this.context = context;
	}
    
    public String getDestContext() {
		return destContext;
	}
    
    public void setDestContext(String destContext) {
		this.destContext = destContext;
	}
    
    public Integer getPriority() {
		return priority;
	}
    
    public void setPriority(Integer priority) {
		this.priority = priority;
	}
    
    public Integer getDestPriority() {
		return destPriority;
	}
    
    public void setDestPriority(Integer destPriority) {
		this.destPriority = destPriority;
	}
    
    public String getDestConnectedLineName(){
    	return getCallerIdName();
    }
    
    public void setDestConnectedLineName(String destConnectedLineName){
    	setCallerIdName(destConnectedLineName);
    }
    
    public String getDestConnectedLineNum(){
    	return getCallerId();
    }
    
    public void setDestConnectedLineNum(String destConnectedLineNum){
    	setCallerId(destConnectedLineNum);
    }
    
    public String getDestCallerIdName() {
		return destCallerIdName;
	}
    
    public void setDestCallerIdName(String destCallerIdName) {
		this.destCallerIdName = destCallerIdName;
	}
    
    public String getDestCallerIdNum() {
		return destCallerIdNum;
	}
    
    public void setDestCallerIdNum(String destCallerIdNum) {
		this.destCallerIdNum = destCallerIdNum;
	}

	public Integer getDestChannelState() {
		return destChannelState;
	}

	public void setDestChannelState(Integer destChannelState) {
		this.destChannelState = destChannelState;
	}

	public String getDestChannelStateDesc() {
		return destChannelStateDesc;
	}

	public void setDestChannelStateDesc(String destChannelStateDesc) {
		this.destChannelStateDesc = destChannelStateDesc;
	}
	
	public String getDestChannel(){
		return getDestination();
	}
	
	public void setDestChannel(String destChannel){
		setDestination(destChannel);
	}

	public Integer getChannelState() {
		return channelState;
	}

	public void setChannelState(Integer channelState) {
		this.channelState = channelState;
	}

	public String getChannelStateDesc() {
		return channelStateDesc;
	}

	public void setChannelStateDesc(String channelStateDesc) {
		this.channelStateDesc = channelStateDesc;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getExten() {
		return exten;
	}

	public void setExten(String exten) {
		this.exten = exten;
	}

	public String getDestLanguage() {
		return destLanguage;
	}

	public void setDestLanguage(String destLanguage) {
		this.destLanguage = destLanguage;
	}

	public String getDestAccountCode() {
		return destAccountCode;
	}

	public void setDestAccountCode(String destAccountCode) {
		this.destAccountCode = destAccountCode;
	}

	public String getDestExten() {
		return destExten;
	}

	public void setDestExten(String destExten) {
		this.destExten = destExten;
	}
}

