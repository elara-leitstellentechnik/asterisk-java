package org.asteriskjava.manager.event;

/**
 * This event is sent when a user joins a conference - either one already in
 * progress or as the first user to join a newly instantiated bridge.
 *
 * @since 1.0.0
 */
public class ConfbridgeJoinEvent extends AbstractConfbridgeEvent
{
    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 1L;

    private Boolean admin;
    private Boolean muted;

	public ConfbridgeJoinEvent(Object source)
    {
        super(source);
    }

	/**
	 * @return the admin
	 */
	public Boolean getAdmin()
	{
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(Boolean admin)
	{
		this.admin = admin;
	}

	/**
	 * @return the muted
	 */
	public Boolean getMuted()
	{
		return muted;
	}

	/**
	 * @param muted the muted to set
	 */
	public void setMuted(Boolean muted)
	{
		this.muted = muted;
	}

}
