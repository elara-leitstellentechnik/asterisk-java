package org.asteriskjava.pbx.activities;

import java.util.HashSet;

import org.apache.log4j.Logger;
import org.asteriskjava.pbx.ActivityCallback;
import org.asteriskjava.pbx.Channel;
import org.asteriskjava.pbx.PBXException;
import org.asteriskjava.pbx.PBXFactory;
import org.asteriskjava.pbx.internal.agi.AgiChannelActivityHold;
import org.asteriskjava.pbx.internal.asterisk.wrap.events.ManagerEvent;
import org.asteriskjava.pbx.internal.core.AsteriskPBX;
import org.asteriskjava.pbx.internal.core.ListenerPriority;

public class HoldActivityImpl extends ActivityHelper<HoldActivity> implements HoldActivity
{
	static Logger logger = Logger.getLogger(HoldActivityImpl.class);
	private final Channel _channel;

	public HoldActivityImpl(final Channel channel, final ActivityCallback<HoldActivity> callback)
	{
		super("HoldCall", callback); //$NON-NLS-1$

		this._channel = channel;
		this.startActivity(false);
	}

	@Override
	public boolean doActivity() throws PBXException
	{
		boolean success = false;

		HoldActivityImpl.logger.info("*******************************************************************************"); //$NON-NLS-1$
		HoldActivityImpl.logger.info("***********                    begin Hold Audio               ****************"); //$NON-NLS-1$
		HoldActivityImpl.logger.info("***********                  " + this._channel + " ****************"); //$NON-NLS-1$ //$NON-NLS-2$
		HoldActivityImpl.logger.info("*******************************************************************************"); //$NON-NLS-1$
		try
		{
			AsteriskPBX pbx = (AsteriskPBX) PBXFactory.getActivePBX();

			if (!pbx.moveChannelToAgi(_channel))
			{
				throw new PBXException("Channel: " + _channel + " couldn't be moved to agi");
			}

			_channel.setCurrentActivityAction(new AgiChannelActivityHold());
			success = true;

		}
		catch (IllegalArgumentException | IllegalStateException e)
		{
			HoldActivityImpl.logger.error(e, e);
			HoldActivityImpl.logger.error(e, e);
			throw new PBXException(e);
		}
		return success;
	}

	@Override
	public HashSet<Class<? extends ManagerEvent>> requiredEvents()
	{
		HashSet<Class<? extends ManagerEvent>> required = new HashSet<>();
		// no events requried.

		return required;
	}

	@Override
	public ListenerPriority getPriority()
	{
		return ListenerPriority.NORMAL;
	}

	@Override
	public void onManagerEvent(ManagerEvent event)
	{
		// NOOP

	}

}
