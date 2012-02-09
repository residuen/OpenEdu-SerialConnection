package de.openedu.serialconnect.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.openedu.serialconnect.connection.SerialConnect;

public class GuiListener implements ActionListener {
	
//	SerialConnect serialConnect = null;
	
	public void actionPerformed(ActionEvent arg0)
	{
//		String cmd = arg0.getActionCommand();
//		
//		System.out.println(cmd);
//		
//		if(serialConnect == null)
//		{
//			if(cmd.equals("start"))
//			{
//				System.out.println("START serial-connection");
//				
//				String portName = (String)comportBox.getValue();
//				int baudRate = Integer.parseInt(((String)baudBox.getValue()).split(" ")[0]);
//				char parityTyp = ((String)parityBox.getValue()).charAt(0);
//				int dataBits = Integer.parseInt(((String)dataBitsBox.getValue()));
//				float stopBits = Integer.parseInt(((String)stopBitBox.getValue()).split(" ")[0]);
//				
//				serialConnect = new SerialConnect();
//				
//				serialConnect.setComponent(this);
//				serialConnect.initSerialConnect(portName, baudRate, parityTyp, dataBits, stopBits);
//				serialConnect.startConnection();
//			}
//		}
//		else if(cmd.equals("stop"))
//		{
//			if(serialConnect != null)
//			{
//				System.out.println("STOP serial-connection");
//				
//				closeDataWrite();
//				saveMode.setSelected(false);
//				
//				serialConnect.interrupt();
//				serialConnect = null;
//			}
//		}
//		else if(cmd.equals("default"))
//		{
//			setDefaultValues();
//		}
//		
//		// Log-File von seriellem Datenstrom in Datei schreiben
//		if(cmd.equals("save on hdd"))
//		{
//			if(saveMode.isSelected())
//			{
//				setDataWrite();
//			}
//			else
//			{
//				closeDataWrite();
//			}
//		}
	}
}
