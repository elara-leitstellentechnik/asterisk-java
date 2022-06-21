package org.asteriskjava.manager.event;

/**
 * A ReceiveFaxEvent is an event of Digium's Fax For Asterisk add-on.
 */
public class ReceiveFaxEvent extends AbstractFaxEvent
{
    private static final long serialVersionUID = 0L;
    private String remoteStationId;
    private String localStationId;
    private Integer pagesTransferred;
    private String resolution;
    private Integer transferRate;
    private String fileName;

    public ReceiveFaxEvent(Object source)
    {
        super(source);
    }

    public String getRemoteStationId()
    {
        return remoteStationId;
    }

    public void setRemoteStationId(String remoteStationId)
    {
        this.remoteStationId = remoteStationId;
    }

    public String getLocalStationId()
    {
        return localStationId;
    }

    public void setLocalStationId(String localStationId)
    {
        this.localStationId = localStationId;
    }

    public Integer getPagesTransferred()
    {
        return pagesTransferred;
    }

    public void setPagesTransferred(Integer pagesTransferred)
    {
        this.pagesTransferred = pagesTransferred;
    }

    public String getResolution()
    {
        return resolution;
    }

    public void setResolution(String resolution)
    {
        this.resolution = resolution;
    }

    public Integer getTransferRate()
    {
        return transferRate;
    }

    public void setTransferRate(Integer transferRate)
    {
        this.transferRate = transferRate;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
}
