package de.openedu.serialconnect.plugins;


public class Plugin_Segment extends Plugin_IO
{

	public Plugin_Segment(int viewMode, String name) {
		super(viewMode, name);
	}

	@Override
	protected void initIOView()
	{	
		portFrame = new PortFrame("Segment", ioPortList, viewMode);
		portFrame.addWindowListener(this);
		portFrame.setVisible(true);
	}	
}