package org.asteriskjava.manager.event;

/**
 * Created by plhk on 1/15/15.
 */
public class AgiExecEndEvent extends AgiExecEvent
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public AgiExecEndEvent()
	{
		setSubEvent(SUB_EVENT_END);
	}
}
