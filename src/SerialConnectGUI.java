/**
 * JOscilloscope - a plugable software-oscilloskope (supported by: http://www.wvs-koeln.de)
 * 
 * Class: SerialConnectGUI.java
 * 
 * @author Karsten Bettray
 * &copy; 2007 Karsten Bettray
 * 
 *  * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

public class SerialConnectGUI extends JFrame implements ActionListener
{
//	private ChannelController channelController = null;
//	
//	private ComponentsEnability groupedButtons = null;

	private SpinnerListModel model1 = new SpinnerListModel( new String[] {"150 BAUD", "300 BAUD", "600 BAUD", "1200 BAUD", "2400 BAUD", "4800 BAUD", "9600 BAUD" } );
	private JSpinner baudBox = null;
	
	private SpinnerListModel model2 = new SpinnerListModel( new String[] {"1 Bit", "2 Bit" } );
	private JSpinner stopBitBox = null;
	
	private SpinnerListModel model3 = new SpinnerListModel(new String[] {"Even", "Odd" } );
	private JSpinner parityBox = null;
	
	private SpinnerListModel model4 = new SpinnerListModel(new String[] {"4", "8" } );
	private JSpinner dataBitsBox = null;
	
	private SpinnerListModel model5 = new SpinnerListModel(new String[] {"COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9" } );
	private JSpinner comportBox = null;

	private SerialConnect serialConnect = null;
	
//	public SerialConnectGUI()
//	{
//	}
	
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
		setSize(240, 210);
				
		initComponents();
		
		setVisible(true);
	}
	
	private void initComponents()
	{
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

		vBox.add(Box.createVerticalStrut(5));
		vBox.add(hBox);
		
		add(vBox, BorderLayout.NORTH);
	}
	
	private void setDefaultValues()
	{
		comportBox.setValue("COM1");
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
				
//				channelController.getIoController().stopIOReader();
//				
//				groupedButtons.setButtonState(false);
				
				String portName = (String)comportBox.getValue();
				int baudRate = Integer.parseInt(((String)baudBox.getValue()).split(" ")[0]);
				char parityTyp = ((String)parityBox.getValue()).charAt(0);
				int dataBits = Integer.parseInt(((String)dataBitsBox.getValue()));
				float stopBits = Integer.parseInt(((String)stopBitBox.getValue()).split(" ")[0]);
				
				serialConnect = new SerialConnect(); // channelController.getIoController());
	//			channelController.getPlotterModel().setSerialConnect(serialConnect);
				
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
	
	public static void main(String[] args)
	{
		new SerialConnectGUI();
	}

}
