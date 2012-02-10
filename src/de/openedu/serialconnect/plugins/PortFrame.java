package de.openedu.serialconnect.plugins;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PortFrame extends JFrame implements ComponentListener
{
//	private SharedData sharedData = null;
	private HashMap<String, IOInterface> ioPortList = null;
	
	public PortFrame(String arg0, HashMap<String, IOInterface> ioPortList)
	{
		super(arg0);
		
		this.ioPortList = ioPortList;
//		this.sharedData = sharedData;

		initFrame();
	}

	public void initFrame() //SharedData sharedData)
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		addComponentListener(this);
		setAlwaysOnTop(true);
		setSize(ioPortList.keySet().size()*100, 220);
		
		JPanel cTest = new JPanel();
		cTest.setLayout(new BoxLayout(cTest, BoxLayout.X_AXIS));
		
//		System.out.println("sharedData.getIoPortList().size()="+sharedData.getIoPortList().size());
		
		for(String io : ioPortList.keySet()) //sharedData.getIoPortList())
		{
			cTest.add((Component)ioPortList.get(io));
		}
		
		add(cTest);
	}

	@Override
	public void componentHidden(ComponentEvent arg0)
	{
//		((JCheckBoxMenuItem)sharedData.getComponentens().get(SharedData.IO_PORTS_MENU)).setState(false);
	}

	@Override
	public void componentMoved(ComponentEvent arg0) { }

	@Override
	public void componentResized(ComponentEvent arg0) { }

	@Override
	public void componentShown(ComponentEvent arg0) { }
}
