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

public class Plugin_Avr_IO extends JPanel implements Plugin, ActionListener, WindowListener {

	private JCheckBox plotMode = null;
	private BufferedWriter fileWriter = null;
	
	public Plugin_Avr_IO()
	{		
		setLayout(new BorderLayout());
				
		initComponents();
	}
	
	private void initComponents()
	{
		plotMode = new JCheckBox("AVR-IO", false);
		plotMode.addActionListener(this);
		add(plotMode);
	}
	
	private void initIOView()
	{
		
	}
	
	private void closeIOView()
	{
		
	}

	
	@Override
	public void receiveData(String s) {
		
//		System.out.println("Plugin_Avr_IO: "+s);
	}

	@Override
	public void sendData(OutputStream outputStream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(plotMode.isSelected())
			initIOView();
		else
			closeIOView();

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		plotMode.setSelected(false);
		
		closeIOView();
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
