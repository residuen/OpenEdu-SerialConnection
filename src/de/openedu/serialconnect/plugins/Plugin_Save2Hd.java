package de.openedu.serialconnect.plugins;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Plugin_Save2Hd extends JPanel implements Plugin, ActionListener {

	private JCheckBox saveMode = null;
	private BufferedWriter fileWriter = null;
	
	public Plugin_Save2Hd()
	{		
		setLayout(new BorderLayout());
				
		initComponents();
	}
	
	private void initComponents()
	{
		saveMode = new JCheckBox("save on hdd", false);
		saveMode.addActionListener(this);
		add(saveMode);
	}
	
	private void setDataWrite()
	{
		if(fileWriter == null)
		{
			try
			{
				System.out.println("opening uart-logfile");
				
				String file = System.getProperty("user.home")+"\\uart_log_"+System.currentTimeMillis()+".txt";
//				System.out.println(file);
				fileWriter = new BufferedWriter(new FileWriter(new File(file)));
			}
			catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	private void closeDataWrite()
	{
		try
		{
			if(fileWriter != null)
			{
				System.out.println("closing uart-logfile");
				
				fileWriter.flush();
				fileWriter.close();
				fileWriter = null;
			}
		}
		catch (IOException e) { e.printStackTrace(); }
	}

	
	@Override
	public void receiveData(String s) {
		
//		SwingUtilities.invokeLater(new Runnable() {
//			
//			public void run() {
				
				if(saveMode.isSelected())
				{
					try {
						fileWriter.append(s);
						fileWriter.newLine();
					} catch (IOException e) { e.printStackTrace(); }
				}
//			}
//		});
	}

	@Override
	public void sendData(OutputStream outputStream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(saveMode.isSelected())
			setDataWrite();
		else
			closeDataWrite();

	}

}
