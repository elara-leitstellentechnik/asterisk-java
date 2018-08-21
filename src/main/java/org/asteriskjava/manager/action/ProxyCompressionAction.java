package org.asteriskjava.manager.action;

public class ProxyCompressionAction extends AbstractManagerAction
{
    private String type;

    public ProxyCompressionAction()
    {
    }

    public ProxyCompressionAction(String type)
    {
        this.type = type;
    }

    @Override
    public String getAction()
    {
        return "ProxyCompression";
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
