package de.openedu.serialconnect.plugins;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class Plugin_Avr_IO extends JPanel implements Plugin, ActionListener, WindowListener {

	private HashMap<String, IOInterface> ioPortList = new HashMap<String, IOInterface>();

	private JCheckBox avrMode = null;
//	private BufferedWriter fileWriter = null;
	
	private PortFrame portFrame = null;
	
	public Plugin_Avr_IO()
	{		
		setLayout(new BorderLayout());
				
		initComponents();
	}
	
	private void initComponents()
	{
		avrMode = new JCheckBox("AVR-IO", false);
		avrMode.addActionListener(this);
		add(avrMode);
	}

	private void initIOView()
	{	
		ioPortList.put(Integer.toString(0), new Output(0, new IOPorts()));
		ioPortList.put(Integer.toString(1), new Output(1, new IOPorts()));
		ioPortList.put(Integer.toString(2), new Output(2, new IOPorts()));
		ioPortList.put(Integer.toString(3), new Output(3, new IOPorts()));
		
		portFrame = new PortFrame("AVR_DEBUG_IO", ioPortList);
		portFrame.setVisible(true);
	}

	private void closeIOView()
	{
		portFrame.setVisible(false);
		portFrame.dispose();
	}

	@Override
	public void receiveData(String s) {
		
//		String expr = "";
//		System.out.println(s);
//		System.out.println("Plugin_Avr_IO: "+s);
		
		String swap = null;
		
		if(s.indexOf('[') ==0 && s.indexOf(']')>0)
		{
			swap = s.substring(1, s.length()-1);
			
			if(swap.contains("port"))
				portMatcher(swap.substring(4, swap.length()));
			
//			System.out.println(swap);
		}
//			System.out.println(s.indexOf('[') >=0 && s.indexOf(']')>0);// +" "+ s.contains("\\]"));
	}
	
	private void portMatcher(String s)
	{
		String[] sp = s.split(":");
		int id = sp[0].equals("a") ? 0 : (sp[0].equals("b") ? 1 : (sp[0].equals("c") ? 2 : (sp[0].equals("d") ? 3 : -1)));
		
//		System.out.println(s+ " "+sp[0]+" "+id+" "+Integer.parseInt(sp[1], 16));
		
		ioPortList.get(Integer.toString(id)).setAllBit(int2BoolArr(Integer.toBinaryString(Integer.parseInt(sp[1], 16))));
	}
	
	private boolean[] int2BoolArr(String v)
	{
		
		for(int i=0, n=(8 - v.length()); i<n; i++)
			v = "0" + v;
		
//		System.out.println(v);

		char[] va = v.toCharArray();
		int size = va.length;
		boolean [] retV = new boolean[size];
		
		for(int i=0; i<size; i++)
			retV[size-1-i] = (va[i]=='1') ? true : false;
		
//		System.out.println(retV);
			
		return retV;
	}

	@Override
	public void sendData(OutputStream outputStream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(avrMode.isSelected())
			initIOView();
		else
			closeIOView();

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		avrMode.setSelected(false);
		
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
