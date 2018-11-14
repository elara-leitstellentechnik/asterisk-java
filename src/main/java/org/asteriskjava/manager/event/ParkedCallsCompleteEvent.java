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
 * A ParkedCallsCompleteEvent is triggered after all parked calls have been
 * reported in response to a ParkedCallsAction.
 *
 * @see org.asteriskjava.manager.action.ParkedCallsAction
 * @see org.asteriskjava.manager.event.ParkedCallEvent
 * @author srt
 * @version $Id$
 */
public class ParkedCallsCompleteEvent extends ResponseEvent {
    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = -2736388036245367249L;
	private Integer listitems;
	private String eventlist;

    /**
     * @param source
     */
    public ParkedCallsCompleteEvent(Object source)
    {
	    super(source);
    }

	/**
	 * Returns if the status of the eventlist (should be Complete).<p>
	 *
	 * @return the status of the list.
	 */
	public String getEventlist()
	{
		return eventlist;
	}

	public void setEventlist(String eventlist)
	{
		this.eventlist = eventlist;
	}

	/**
	 * Returns the number of channels reported.<p>
	 *
	 * @return the number of channels reported.
	 */
	public Integer getListitems()
	{
		return listitems;
	}

	/**
	 * Sets the number of channels reported.<p>
	 *
	 * @param listitems the number of channels reported.
	 */
	public void setListitems(Integer listitems)
	{
		this.listitems = listitems;
	}
}
