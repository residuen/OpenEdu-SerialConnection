package de.openedu.serialconnect.plugins;


public class Plugin_Segment extends Plugin_IO
{

	public Plugin_Segment(boolean viewMode, String name) {
		super(viewMode, name);
	}

	@Override
	protected void initIOView()
	{	
//		keys.add("porta");
		keys.add("portc");
//		keys.add("portc");
//		keys.add("portd");
		
		for(String s : keys)
			ioPortList.put(s, new Output(s, viewMode, "OUTPUT", new IOPorts()));
		
		portFrame = new PortFrame("Segment", ioPortList, keys);
		portFrame.addWindowListener(this);
		portFrame.setVisible(true);
	}	
}