package de.openedu.serialconnect.gui;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;

import de.openedu.serialconnect.connection.SerialConnect;

public class SerialConnectGUI extends JFrame implements WindowListener
{
	
	
	public SerialConnectGUI()
	{
		super("SerialConnect-GUI");
		
		if(System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0)
			GuiTools.setLookAndFeel(new String[] {"gtk"});
		else if(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0)
			GuiTools.setLookAndFeel(new String[] {"win"});
		
		JTabbedPane tp = new JTabbedPane();
		
		Uart_Gui ug = new Uart_Gui();
		Plugin_Gui pg = new Plugin_Gui();

		ug.setPlugins(pg.getPlugins());
		
		setLayout(new BorderLayout());
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 360);
		addWindowListener(this);
		
		tp.add(ug, "UART-Init");
		tp.add(pg, "Select Plugins");
		
		add(tp);
		
		setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
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
	
	public static void main(String[] args)
	{
		new SerialConnectGUI();
	}
}