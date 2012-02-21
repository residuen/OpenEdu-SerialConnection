package de.openedu.serialconnect.plugins;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.org.apache.xerces.internal.impl.RevalidationHandler;

public class PortFrame extends JFrame implements ComponentListener, ItemListener
{
	private HashMap<String, IOInterface> ioPortList = null;
	private ArrayList<JCheckBox> ioPorts = new ArrayList<JCheckBox>();
	private ArrayList<JCheckBox> adcChanels = new ArrayList<JCheckBox>();
	private JPanel cTest = new JPanel();
	private String[] portNamesAtmega32 = new String[] { "porta", "portb", "portc", "portd" };
	private String[] portNames8051 = new String[] { "p0", "p1", "p2", "p3" };

	private int countPorts = 0;
	
	private int viewMode = 0;
	
	private int m_id = Plugin_IO.M_ATMEGA;
	
	public PortFrame(String arg0, HashMap<String, IOInterface> ioPortList, int viewMode, int m_id)
	{
		super(arg0);
		
		this.ioPortList = ioPortList;
		this.viewMode = viewMode;
		this.m_id = m_id;
		
		System.out.println("viewMode="+viewMode);

		initFrame();
	}

	public void initFrame() //SharedData sharedData)
	{
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setResizable(false);
		addComponentListener(this);
		setAlwaysOnTop(true);
		
		JPanel portSelectPanel = new JPanel();
		JPanel adcSelectPanel = new JPanel();
		
		JCheckBox jb;
		
		String name = (m_id==Plugin_IO.M_ATMEGA) ? Plugin_IO.M_ATMEGA_PREFIX : Plugin_IO.M_8051_PREFIX;
		String[] arr = null;
		
		if(viewMode == Plugin_IO.OUTPORT_PIN_MODE || viewMode == Plugin_IO.OUTPORT_SEGMENT_MODE)
		{
			if(m_id==Plugin_IO.M_ATMEGA)
				arr = portNamesAtmega32;
			else
				arr = portNames8051;

			for(String s : arr)
			{
				jb = new JCheckBox(s);
				jb.addItemListener(this);
				jb.setName(s);
				ioPorts.add(jb);
				portSelectPanel.add(jb);
			}

			setSize(300, 260);
			
			add(portSelectPanel, BorderLayout.SOUTH);
		}
		else
		if(viewMode == Plugin_IO.ADC_REGISTER_MODE || viewMode == Plugin_IO.ADC_PEAK_MODE)
		{
			adcSelectPanel.add(new JLabel("channel:"));
			
			jb = new JCheckBox("0");
			jb.addItemListener(this);
			jb.setName("adc0");
			adcChanels.add(jb);
			adcSelectPanel.add(jb);
			
			jb = new JCheckBox("1");
			jb.addItemListener(this);
			jb.setName("adc1");
			adcChanels.add(jb);
			adcSelectPanel.add(jb);
			
			jb = new JCheckBox("2");
			jb.addItemListener(this);
			jb.setName("adc2");
			adcChanels.add(jb);
			adcSelectPanel.add(jb);
			
			jb = new JCheckBox("3");
			jb.addItemListener(this);
			jb.setName("adc3");
			adcChanels.add(jb);
			adcSelectPanel.add(jb);
			
			jb = new JCheckBox("4");
			jb.addItemListener(this);
			jb.setName("adc4");
			adcChanels.add(jb);
			adcSelectPanel.add(jb);
			
			jb = new JCheckBox("5");
			jb.addItemListener(this);
			jb.setName("adc5");
			adcChanels.add(jb);
			adcSelectPanel.add(jb);
			
			jb = new JCheckBox("6");
			jb.addItemListener(this);
			jb.setName("adc6");
			adcChanels.add(jb);
			adcSelectPanel.add(jb);
			
			jb = new JCheckBox("7");
			jb.addItemListener(this);
			jb.setName("adc7");
			adcChanels.add(jb);
			adcSelectPanel.add(jb);

			setSize(400, 260);
			
			add(adcSelectPanel, BorderLayout.SOUTH);
		}

		cTest.setLayout(new BoxLayout(cTest, BoxLayout.X_AXIS));
		
//		System.out.println("sharedData.getIoPortList().size()="+sharedData.getIoPortList().size());
				
		add(cTest, BorderLayout.WEST);		
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

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
//		boolean viewMode = false;
		String name;
		countPorts = 0;
		
		cTest.removeAll();
		
		// Zufuegen von Digital-Ausgaengen
		if(viewMode == Plugin_IO.OUTPORT_PIN_MODE || viewMode == Plugin_IO.OUTPORT_SEGMENT_MODE)
		{
			for(JCheckBox jb : ioPorts)
			{
				if(jb.isSelected())
				{
					countPorts++;
					
					name = jb.getName();
					
					System.out.println(viewMode);
					
					ioPortList.put(name, new Output(name, viewMode, "OUTPUT", new IOPorts()));
					
					cTest.add((Component)ioPortList.get(name));
				}
			}
			
			if(countPorts >= 3)
				setSize(countPorts*100, 260);
		}
		
		// Zufuegen von Analogwert-Anzeigen
		if(viewMode == Plugin_IO.ADC_REGISTER_MODE || viewMode == Plugin_IO.ADC_PEAK_MODE)
		{
			System.out.println("ADC zufuegen");
			for(JCheckBox jb : adcChanels)
			{
				if(jb.isSelected())
				{
					countPorts++;
					
					name = jb.getName();
					
					if(viewMode == Plugin_IO.ADC_PEAK_MODE)
						ioPortList.put(name, new ADCInput("adcw", name, new IOPorts(10)));
					else
					{
						ioPortList.put("adcl", new Output("adcl", viewMode, name, new IOPorts(10)));
						ioPortList.put("adch", new Output("adch", viewMode, name, new IOPorts(10)));
					}

					cTest.add((Component)ioPortList.get(name));

					if(countPorts >= 5)
						setSize(countPorts*100, 260);
					else
						setSize(400, 260);
				}
			}
			
		}
		
		
		System.out.println(countPorts);
		
		this.validate();
	}
}
