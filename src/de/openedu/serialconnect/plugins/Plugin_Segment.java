package de.openedu.serialconnect.plugins;


public class Plugin_Segment extends Plugin_IO
{

	public Plugin_Segment(int viewMode, int m_id, String name) {
		super(viewMode, m_id, name);
	}

	@Override
	protected void initIOView()
	{	
		portFrame = new PortFrame("Segment", ioPortList, super.viewMode, m_id);
		portFrame.addWindowListener(this);
		portFrame.setVisible(true);
	}	
}