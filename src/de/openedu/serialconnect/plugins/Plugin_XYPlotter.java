package de.openedu.serialconnect.plugins;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.openedu.serialconnect.lib.XYPlotter;

public class Plugin_XYPlotter extends JPanel implements Plugin, ActionListener, WindowListener {

	private JCheckBox plotMode = null;
	private BufferedWriter fileWriter = null;
	private XYPlotter plot = null;
	
	public Plugin_XYPlotter()
	{		
		setLayout(new BorderLayout());
				
		initComponents();
	}
	
	private void initComponents()
	{
		plotMode = new JCheckBox("xy-Plotter", false);
		plotMode.addActionListener(this);
		add(plotMode);
	}
	
	private void initXYPlotter()
	{
		if(plot == null)
		{
			System.out.println("new plotter");
			plot = new XYPlotter(JFrame.DISPOSE_ON_CLOSE);	
			plot.setScopeMode(false);
			plot.addWindowListener(this);
			plot.showPlotter();
		}
	}
	
	private void closeInitXYPlotter()
	{
		System.out.println("close plotter");
		
		if(plot != null)
			plot.hidePlotter();
		
		plot = null;
	}

	
	@Override
	public void receiveData(String s) {
		
		String[] hexStrSplit = s.toString().split(",");
   		
  		// Zeichnen eines xy-Plots
  		if(plot.isVisible())
  			plot.addFunctionValue(Double.parseDouble(hexStrSplit[0]), Double.parseDouble(hexStrSplit[1]));
	}

	@Override
	public void sendData(OutputStream outputStream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(plotMode.isSelected())
			initXYPlotter();
		else
			closeInitXYPlotter();

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		plotMode.setSelected(false);
		
		closeInitXYPlotter();
	}

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
}
