package de.openedu.serialconnect.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.openedu.serialconnect.plugins.Plugin;
import de.openedu.serialconnect.plugins.Plugin_ADC;
import de.openedu.serialconnect.plugins.Plugin_IO;
import de.openedu.serialconnect.plugins.Plugin_Kicker;
import de.openedu.serialconnect.plugins.Plugin_Save2Hd;
import de.openedu.serialconnect.plugins.Plugin_Segment;
import de.openedu.serialconnect.plugins.Plugin_XYPlotter;

public class DefaultPlugin_Gui extends JPanel implements ActionListener, MessageIO, WindowListener
{
	public static final int DEFAULT_PLUGIN = 0;
	public static final int M_ATMEGA_PLUGIN = 1;
	public static final int M_8051_PLUGIN = 2;
//	public static final int KICKER_PLUGIN = 3;
	
	
	private int plugin = DEFAULT_PLUGIN;
	
	private ArrayList<Plugin> plugins = null; // new ArrayList<Plugin>();
	
	public DefaultPlugin_Gui(ArrayList<Plugin> plugins, int plugin)
	{	
		this.plugins = plugins;
		
		setLayout(new BorderLayout());
				
		initComponents(plugins, plugin);
		
		setVisible(true);
	}
	
	private void initComponents(ArrayList<Plugin> plugins, int plugin)
	{
		JPanel panel = new JPanel(new GridLayout(3,2));
		
		Plugin p;
		
//		System.out.println("plugin="+plugin);
		
		switch(plugin)
		{
			case DEFAULT_PLUGIN:
				System.out.println("2 defaultplugins");
				p = new Plugin_Save2Hd();	plugins.add(p); panel.add((Component) p);
				p = new Plugin_XYPlotter();	plugins.add(p); panel.add((Component) p);
				p = new Plugin_Kicker();	plugins.add(p); panel.add((Component) p);
					
				break;
			
			case M_ATMEGA_PLUGIN:
				System.out.println("4 atmegaplugins");
				p = new Plugin_IO(Plugin_IO.OUTPORT_PIN_MODE, Plugin_IO.M_ATMEGA, "ATmega16/32 OUT");	plugins.add(p); panel.add((Component) p);
				p = new Plugin_Segment(Plugin_IO.OUTPORT_SEGMENT_MODE, Plugin_IO.M_ATMEGA, "7-Segment");					plugins.add(p); panel.add((Component) p);
				p = new Plugin_ADC(Plugin_IO.ADC_REGISTER_MODE, Plugin_IO.M_ATMEGA, "view ADC-reg");						plugins.add(p); panel.add((Component) p);
				p = new Plugin_ADC(Plugin_IO.ADC_PEAK_MODE, Plugin_IO.M_ATMEGA, "adc peak-view");							plugins.add(p); panel.add((Component) p);
					
				break;

			case M_8051_PLUGIN:
				System.out.println("4 8051plugins");
				p = new Plugin_IO(Plugin_IO.OUTPORT_PIN_MODE, Plugin_IO.M_8051, "8051 OUT");	plugins.add(p); panel.add((Component) p);
				p = new Plugin_Segment(Plugin_IO.OUTPORT_SEGMENT_MODE, Plugin_IO.M_8051, "7-Segment");			plugins.add(p); panel.add((Component) p);
				p = new Plugin_ADC(Plugin_IO.ADC_REGISTER_MODE, Plugin_IO.M_8051, "view ADC-reg");				plugins.add(p); panel.add((Component) p);
				p = new Plugin_ADC(Plugin_IO.ADC_PEAK_MODE, Plugin_IO.M_8051, "adc peak-view");					plugins.add(p); panel.add((Component) p);
					
				break;
		}
		
		add(panel, BorderLayout.NORTH);
	}
	
	public ArrayList<Plugin> getPlugins() {
		return plugins;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		String cmd = arg0.getActionCommand();
		
		System.out.println(cmd);
	}

	@Override
	public void windowClosing(WindowEvent arg0) { }

	@Override
	public void windowActivated(WindowEvent arg0) { }

	@Override
	public void windowClosed(WindowEvent arg0) { }

	@Override
	public void windowDeactivated(WindowEvent arg0) { }

	@Override
	public void windowDeiconified(WindowEvent arg0) { }

	@Override
	public void windowIconified(WindowEvent arg0) { }

	@Override
	public void windowOpened(WindowEvent arg0) { }

	@Override
	public void message(String s) {

		for(final Plugin p : plugins)
		{
			p.receiveData(s);
		}
	}

}