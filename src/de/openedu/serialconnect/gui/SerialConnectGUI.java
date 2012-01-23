package de.openedu.serialconnect.gui;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
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
import javax.swing.JTextArea;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;

import de.openedu.serialconnect.connection.SerialConnect;

public class SerialConnectGUI extends JFrame implements ActionListener, MessageIO, WindowListener
{
	private JSpinner baudBox = null;	
	private JSpinner stopBitBox = null;
	private JSpinner parityBox = null;
	private JSpinner dataBitsBox = null;
	private JSpinner comportBox = null;

	private JTextArea textArea = null;
	
	private JCheckBox showValues = null;
	private JCheckBox autoScroll = null;
	private JCheckBox saveMode = null;
	
	private SerialConnect serialConnect = null;
	
	private BufferedWriter fileWriter = null;
	
	public SerialConnectGUI()
	{
		super("SerialConnect-GUI");
		
		if(System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0)
			GuiTools.setLookAndFeel(new String[] {"gtk"});
		else if(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0)
			GuiTools.setLookAndFeel(new String[] {"win"});
		
		setLayout(new BorderLayout());
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 360);
		addWindowListener(this);
				
		initComponents();
		
		setVisible(true);
	}
	
	private void initComponents()
	{
		SpinnerListModel model1 = new SpinnerListModel( new String[] {"150 BAUD", "300 BAUD", "600 BAUD", "1200 BAUD", "2400 BAUD", "4800 BAUD", "9600 BAUD" } );
		SpinnerListModel model2 = new SpinnerListModel( new String[] {"1 Bit", "2 Bit" } );
		SpinnerListModel model3 = new SpinnerListModel(new String[] {"Even", "Odd", "None" } );
		SpinnerListModel model4 = new SpinnerListModel(new String[] {"4", "8" } );
		SpinnerListModel model5 = new SpinnerListModel(new String[] {"COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "COM10", "COM11", "COM12", "COM13", "COM14", "COM15", "COM16" } );

		textArea = new JTextArea("");
		
		showValues = new JCheckBox("values", true);
		autoScroll = new JCheckBox("autoscroll", true);
		saveMode = new JCheckBox("save on hdd", false);
		
		saveMode.addActionListener(this);
		
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
		hBox.add(saveMode);
		vBox.add(hBox);
		
		vBox.add(Box.createVerticalStrut(5));
		vBox.add(hBox);
		
		add(vBox, BorderLayout.NORTH);
		
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	private void setDefaultValues()
	{
		comportBox.setValue("COM1");
		baudBox.setValue("9600 BAUD");
		stopBitBox.setValue("1 Bit");
		parityBox.setValue("None");
		dataBitsBox.setValue("8");
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

	public void actionPerformed(ActionEvent arg0)
	{
		String cmd = arg0.getActionCommand();
		
		System.out.println(cmd);
		
		if(serialConnect == null)
		{
			if(cmd.equals("start"))
			{
				System.out.println("START serial-connection");
				
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
				
				closeDataWrite();
				saveMode.setSelected(false);
				
				serialConnect.interrupt();
				serialConnect = null;
			}

		}
		else if(cmd.equals("default"))
		{
			setDefaultValues();
		}
		
		// Log-File von seriellem Datenstrom in Datei schreiben
		if(cmd.equals("save on hdd"))
		{
			if(saveMode.isSelected())
			{
				setDataWrite();
			}
			else
			{
				closeDataWrite();
			}
		}
	}
	
	public void message(final String s) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				
				
				if(saveMode.isSelected())
				{
					try {
						fileWriter.append(s);
						fileWriter.newLine();
					} catch (IOException e) { e.printStackTrace(); }
				}
				
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

	@Override
	public void windowClosing(WindowEvent arg0) {
		closeDataWrite();
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