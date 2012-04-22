package de.openedu.serialconnect.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import de.openedu.serialconnect.connection.SerialConnect;
import de.openedu.serialconnect.plugins.Plugin;

public class Uart_Gui extends JPanel implements ActionListener, MessageIO, WindowListener
{
	private JSpinner baudBox = null;	
	private JSpinner stopBitBox = null;
	private JSpinner parityBox = null;
	private JSpinner dataBitsBox = null;
	private JSpinner comportBox = null;

	private JTextArea textArea = null;
	
	private JCheckBox showValues = null;
	private JCheckBox autoScroll = null;
	
	private SerialConnect serialConnect = null;
	
//	private BufferedWriter fileWriter = null;
	
	private ArrayList<Plugin> plugins = new ArrayList<Plugin>();

	public Uart_Gui()
	{		
		setLayout(new BorderLayout());
				
		initComponents();
		
		setVisible(true);
	}
	
	private void initComponents()
	{
		SpinnerListModel model1 = new SpinnerListModel( new String[] {"150 BAUD", "300 BAUD", "600 BAUD", "1200 BAUD", "2400 BAUD", "4800 BAUD", "9600 BAUD", "14400 BAUD", "19200 BAUD" } );
		SpinnerListModel model2 = new SpinnerListModel( new String[] {"1 Bit", "2 Bit" } );
		SpinnerListModel model3 = new SpinnerListModel(new String[] {"Even", "Odd", "None" } );
		SpinnerListModel model4 = new SpinnerListModel(new String[] {"5", "6", "7", "8", "9" } );
		SpinnerListModel model5 = new SpinnerListModel(new String[] {"COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "COM10", "COM11", "COM12", "COM13", "COM14", "COM15", "COM16" } );

		textArea = new JTextArea("");
		
		showValues = new JCheckBox("values", true);
		autoScroll = new JCheckBox("autoscroll", true);
		
		baudBox = new JSpinner(model1);	
		stopBitBox = new JSpinner(model2);	
		parityBox = new JSpinner(model3);	
		dataBitsBox = new JSpinner(model4);
		comportBox = new JSpinner(model5);
		
		setDefaultValues();
		
		JPanel swap = null;
		JButton btn = null;

		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		
		swap = new JPanel(new GridLayout(1, 2));
		swap.setBackground(vBox.getBackground());
		swap.add(new JLabel(" COM-Port"));
		swap.add(comportBox);
		vBox.add(Box.createVerticalStrut(4));
		vBox.add(swap);
		vBox.add(Box.createVerticalStrut(4));

		swap = new JPanel(new GridLayout(1, 2));
		swap.setBackground(vBox.getBackground());
		swap.add(new JLabel(" Baud-Rate"));
		swap.add(baudBox);
		vBox.add(swap);
		vBox.add(Box.createVerticalStrut(4));
		
		swap = new JPanel(new GridLayout(1, 2));
		swap.setBackground(vBox.getBackground());
		swap.add(new JLabel(" Data-Bits"));
		swap.add(dataBitsBox);
		vBox.add(swap);
		vBox.add(Box.createVerticalStrut(4));

		swap = new JPanel(new GridLayout(1, 2));
		swap.setBackground(vBox.getBackground());
		swap.add(new JLabel(" Stop-Bit"));
		swap.add(stopBitBox);
		vBox.add(swap);
		vBox.add(Box.createVerticalStrut(4));

		swap = new JPanel(new GridLayout(1, 2));
		swap.setBackground(vBox.getBackground());
		swap.add(new JLabel(" Parity"));
		swap.add(parityBox);
		vBox.add(swap);
		vBox.add(Box.createVerticalStrut(4));
		
		btn = new JButton("start");
		btn.addActionListener(this);
		hBox.add(btn);
		
		btn = new JButton("stop");
		btn.addActionListener(this);
		hBox.add(btn);

		btn = new JButton("default");
		btn.addActionListener(this);
		hBox.add(btn);
		vBox.add(hBox);

		hBox = Box.createHorizontalBox();
		hBox.add(showValues);
		hBox.add(autoScroll);
		vBox.add(hBox);
		
		vBox.add(Box.createVerticalStrut(5));
		vBox.add(hBox);
		
		add(vBox, BorderLayout.NORTH);
		
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	private void setDefaultValues()
	{
		comportBox.setValue("COM1");
//		baudBox.setValue("19200 BAUD");
		baudBox.setValue("9600 BAUD");
		stopBitBox.setValue("1 Bit");
		parityBox.setValue("None");
		dataBitsBox.setValue("8");
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		String cmd = arg0.getActionCommand();
		
		System.out.println(cmd);
		
		if(serialConnect == null)
		{
			if(cmd.equals("start"))
			{
				System.out.println("START serial-connection with "+baudBox.getValue());
				
				String portName = (String)comportBox.getValue();
				int baudRate = Integer.parseInt(((String)baudBox.getValue()).split(" ")[0]);
				char parityTyp = ((String)parityBox.getValue()).charAt(0);
				int dataBits = Integer.parseInt(((String)dataBitsBox.getValue()));
				float stopBits = Integer.parseInt(((String)stopBitBox.getValue()).split(" ")[0]);
				
				serialConnect = new SerialConnect();
				
				serialConnect.setComponent(this);
				serialConnect.initSerialConnect(portName, baudRate, parityTyp, dataBits, stopBits);
				serialConnect.startConnection();
			}
		}
		else if(cmd.equals("stop"))
		{
			if(serialConnect != null)
			{
				System.out.println("STOP serial-connection");
				
				serialConnect.interrupt();
				serialConnect = null;
			}
		}
		else if(cmd.equals("default"))
		{
			setDefaultValues();
		}
	}
	
	public void message(final String s) {
		
		for(Plugin p : plugins)	// Uebergabe der Dsaten des SerialEvents an die aktiven Plugins
			if(p.isEnable())
				p.receiveData(s);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {

				if(showValues.isSelected())
				{
					textArea.append(s+"\n");

					if(autoScroll.isSelected())
					{
						textArea.setCaretPosition(textArea.getDocument().getLength());
					}
				}
			}
		});
	}

	public void setPlugins(ArrayList<Plugin> plugins) {
		this.plugins = plugins;
	}

	public ArrayList<Plugin> getPlugins() {
		return plugins;
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
//		closeDataWrite();
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