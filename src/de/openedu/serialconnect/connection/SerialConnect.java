/**
 * Class: SerialConnect.java
 * 
 * @author Karsten Bettray
 * &copy; 2011 Karsten Bettray
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

package de.openedu.serialconnect.connection;

import de.openedu.serialconnect.interfaces.ReaderFactory;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.TooManyListenersException;
import java.util.Vector;

import de.openedu.serialconnect.gui.MessageIO;
import de.openedu.serialconnect.interfaces.GrabberInterface;

/**
 * Class-name: SerialConnect
 * This class provides methods to read the serial-output of microcontrollers
 * @author Karsten Bettray
 * &copy; Karsten Bettray - 2011
 * @version 0.1
 * Licence: GPL 3.0
 */
public class SerialConnect extends Thread implements SerialPortEventListener
{
	private static final boolean MESSAGE = true;
	private static final boolean DEBUGMESSAGE = false;

	private SerialPort port;

	// the properties of the serial connection 
//	private String portName = "/dev/tty0";
	private String portName = "COM1";
	private int baudRate = 9600;
	private char parityTyp = 'O';
	private int dataBits = 8;
	private float stopBits = 1;
	
	long startTime = 0;
	
	private ReaderFactory readerFactory = new ReaderFactory();

	private GrabberInterface gInterface = readerFactory.getInstance(ReaderFactory.TRIMED_FORMAT);
  		
	private Component component = null;
	
	// read buffer, streams and scanner 
	private InputStream inputStream;
	private OutputStream outputStream;
	private Scanner scanner;

	// print the control-messages
//	private boolean showInSystemOut = true;
	
	private boolean interrupted = false;
  		
	public SerialConnect()
	{
//		initSerialConnect(portName, baudRate, parityTyp, dataBits, stopBits); // PORT, BAUDRATE, PARITY-TYP, DATABITS, STOPBITS	  
	}

	/**
	 * @param portName
	 * @param baudRate
	 * @param parity
	 * @param dataBits
	 * @param stopBits
	 */
	public SerialConnect(String portName, int baudRate, char parityTyp, int dataBits, float stopBits)
	{
		initSerialConnect(portName, baudRate, parityTyp, dataBits, stopBits);
	}
	
	public void initSerialConnect()
	{
		initSerialConnect(portName, baudRate, parityTyp, dataBits, stopBits);
	}
	
	/**
	 * @param portName
	 * @param baudRate
	 * @param parity
	 * @param dataBits
	 * @param stopBits
	 */
	public void initSerialConnect(String portName, int baudRate, char parityTyp, int dataBits, float stopBits)
	{
		this.portName = portName;
		this.baudRate = baudRate;
		this.dataBits = dataBits;
		this.stopBits = stopBits;
		
		if (parityTyp == 'E' || parityTyp == 'e')
			this.parityTyp = SerialPort.PARITY_EVEN;
		
		if (parityTyp == 'O' || parityTyp == 'o')
			this.parityTyp = SerialPort.PARITY_ODD;

		if (parityTyp == 'N' || parityTyp == 'n') 
			this.parityTyp = SerialPort.PARITY_NONE;
    
		if(stopBits == 1)
			this.stopBits = SerialPort.STOPBITS_1;
    
		if(stopBits == 2)
			this.stopBits = SerialPort.STOPBITS_2;
		
		System.out.println("initSerialConnect");
	}
	
	/**
	 * Connecting to a serial port 
	 */
	public void startConnection()
	{
		try
		{
			// Portliste auslesen
			@SuppressWarnings("unchecked")
			Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
      
			while(portList.hasMoreElements())
			{
				CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();

				if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
				{
					if(MESSAGE)
						System.out.println("found " + portId.getName());
        
					if(portId.getName().equals(portName))
					{
						initPort(portId);
            
						break;
					}
				}
			}
		}
		catch (Exception e)
		{
			port = null;
			inputStream = null;
		}
	}

	private void initPort(CommPortIdentifier portId) throws PortInUseException, IOException, UnsupportedCommOperationException, TooManyListenersException
	{
		if(MESSAGE)
			System.out.println("searching "+this.portName);

		port = (SerialPort)portId.open(this.portName, 2000);
		inputStream = port.getInputStream();
		scanner = new Scanner(inputStream); 

		port.setSerialPortParams(this.baudRate, this.dataBits, (int)this.stopBits, this.parityTyp);
		port.addEventListener(this);
		port.notifyOnDataAvailable(true);
		port.enableReceiveTimeout(5000);

		if(MESSAGE)
			System.out.println("opened "+portName);
		
		startTime = System.currentTimeMillis();
		
	}
	
	public void dispose()
	{
		if(port != null)
			port.removeEventListener();
		
		try
		{
			if(inputStream != null)
				inputStream.close();
		}
		catch(Exception e) { e.printStackTrace(); }
    
		inputStream = null;

		try {
			if(port != null)
				port.close();  // Port schliessen
		}
		catch(Exception e) { e.printStackTrace(); }
    
		port = null;
	}
	
	/**
	 * UART-Event:
	 * Die gesendeten Daten werden als String empfangen und
	 */
  	synchronized public void serialEvent(SerialPortEvent serialEvent)
	{
//  		String str = null;
  		
  		String strTransformed = null;
  		
  		// Lesen solange Zeichenketten gesendet werden
 		while(scanner.hasNextLine())
		{
//	  		str = scanner.nextLine();
	  			  		
		  	strTransformed = gInterface.buildCurrentStream(scanner.nextLine().toCharArray());
	  		
		  	if(DEBUGMESSAGE)
		  		System.out.println(strTransformed);

		  	// Nachricht an Message-Componente
	  		((MessageIO)component).message(strTransformed);
		}			
	}

  	public SerialPort getPort() {
		return port;
	}

	public void setPort(SerialPort port) {
		this.port = port;
	}

	public boolean isInterrupted() {
		return interrupted;
	}

	public void interrupt() {
		this.interrupted = true;
		
		this.dispose();
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}
	
  	/**
  	 * @param argv
  	 */
//  	public static void main(String[] argv)
//	{
//		System.out.println("start");
//
//		new SerialConnect(null);
//	}

	public static String[] comPortList()
	{
		Vector<String> swap = new Vector<String>();
		
		String[] retValue = null;
		
		try
		{
			Enumeration portList = CommPortIdentifier.getPortIdentifiers();
			
			while(portList.hasMoreElements())
			{
				CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();

//				System.out.println("found " + portId.getName());

				if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
				{
					swap.add(portId.getName());
					
					if(MESSAGE)
						System.out.println("found " + portId.getName());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		retValue = new String[swap.size()];
		
		for(int i=0; i<retValue.length; i++)
			retValue[i] = swap.get(i);
		
		return retValue;
	}
	
	public void setComponent(Component component) {
		this.component = component;
	}
	
	public void setPlugin(boolean b)
	{
		gInterface.setPlugin(b);
	}
	
	public boolean isPlugin()
	{
		return gInterface.isPlugin();
	}
	
	public OutputStream getOutputStream() {
		return outputStream;
	}

}