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
import de.openedu.serialconnect.plugins.Plugin_Save2Hd;
import de.openedu.serialconnect.plugins.Plugin_Segment;
import de.openedu.serialconnect.plugins.Plugin_XYPlotter;

public class Plugin_Gui extends JPanel implements ActionListener, MessageIO, WindowListener
{
	private ArrayList<Plugin> plugins = new ArrayList<Plugin>();
	
	public Plugin_Gui()
	{		
		setLayout(new BorderLayout());
				
		initComponents();
		
		setVisible(true);
	}
	
	private void initComponents()
	{
		JPanel panel = new JPanel(new GridLayout(3,2));
		
		plugins.add(new Plugin_Save2Hd());
		plugins.add(new Plugin_XYPlotter());
		plugins.add(new Plugin_IO(Plugin_IO.PIN_MODE, "ATmega16/32 OUT"));
		plugins.add(new Plugin_Segment(Plugin_IO.SEGMENT_MODE, "7-Segment"));
		plugins.add(new Plugin_ADC(Plugin_ADC.REGISTER_MODE, "view ADC-reg"));
		plugins.add(new Plugin_ADC(Plugin_ADC.PEAK_MODE, "adc peak-view"));
		
		for(Plugin p : plugins)
			panel.add((Component) p);
		
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
	public void message(final String s) {

		for(final Plugin p : plugins)
		{
			p.receiveData(s);
		}
	}

}