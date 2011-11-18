/*
SerialConnectGUI: GUI zum Einlesen von Daten per RS232

Copyright (C) 2011 Karsten Bettray

Dieses Programm ist freie Software. Sie koennen es unter den Bedingungen der GNU General Public License,
wie von der Free Software Foundation veroeffentlicht, weitergeben und/oder modifizieren, entweder gemaess
Version 3 der Lizenz oder (nach Ihrer Option) jeder spaeteren Version.
Die Veroeffentlichung dieses Programms erfolgt in der Hoffnung, dass es Ihnen von Nutzen sein wird, aber
OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FUER
EINEN BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm erhalten haben.
Falls nicht, siehe <http://www.gnu.org/licenses/>.
*/

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class SerialConnectGUI extends JFrame implements ActionListener, MessageIO
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
		setSize(360, 360);
				
		initComponents();
		
		setVisible(true);
	}
	
	private void initComponents()
	{
		SpinnerListModel model1 = new SpinnerListModel( new String[] {"150 BAUD", "300 BAUD", "600 BAUD", "1200 BAUD", "2400 BAUD", "4800 BAUD", "9600 BAUD" } );
		SpinnerListModel model2 = new SpinnerListModel( new String[] {"1 Bit", "2 Bit" } );
		SpinnerListModel model3 = new SpinnerListModel(new String[] {"Even", "Odd" } );
		SpinnerListModel model4 = new SpinnerListModel(new String[] {"4", "8" } );
		SpinnerListModel model5 = new SpinnerListModel(new String[] {"COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "COM10", "COM11", "COM12", "COM13" } );

		textArea = new JTextArea("");
		
		showValues = new JCheckBox("Values", true);
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

		hBox.add(showValues);
		hBox.add(autoScroll);
		
		vBox.add(Box.createVerticalStrut(5));
		vBox.add(hBox);
		
		add(vBox, BorderLayout.NORTH);
		
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	private void setDefaultValues()
	{
		comportBox.setValue("COM13");
		baudBox.setValue("9600 BAUD");
		stopBitBox.setValue("1 Bit");
		parityBox.setValue("Odd");
		dataBitsBox.setValue("8");
		
	}

	public void actionPerformed(ActionEvent arg0)
	{
		System.out.println(arg0.getActionCommand());
		if(serialConnect == null)
		{
			if(arg0.getActionCommand().equals("start"))
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
		else if(arg0.getActionCommand().equals("stop"))
		{
			if(serialConnect != null)
			{
				System.out.println("STOP serial-connection");
				
				serialConnect.interrupt();
				serialConnect = null;
			}

		}
		else if(arg0.getActionCommand().equals("default"))
		{
			setDefaultValues();
		}
	}
	
	public void message(final String s) {
		
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
	
	public static void main(String[] args)
	{
		new SerialConnectGUI();
	}
}
