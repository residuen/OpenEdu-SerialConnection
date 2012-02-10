package de.openedu.serialconnect.plugins;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class Plugin_IO extends JPanel implements Plugin, ActionListener, WindowListener {

	private HashMap<String, IOInterface> ioPortList = new HashMap<String, IOInterface>();
	private ArrayList<String> keys = new ArrayList<String>();

	private JCheckBox avrMode = null;
//	private BufferedWriter fileWriter = null;
	
	private PortFrame portFrame = null;
	
	public Plugin_IO()
	{		
		setLayout(new BorderLayout());
				
		initComponents();
	}
	
	private void initComponents()
	{
		avrMode = new JCheckBox("Debug ATmega16/32", false);
		avrMode.addActionListener(this);
		add(avrMode);
	}

	private void initIOView()
	{	
//		keys.add("porta");
		keys.add("portb");
		keys.add("portc");
		keys.add("portd");
		
		for(String s : keys)
			ioPortList.put(s, new Output(s, "OUTPUT", new IOPorts()));
		
		portFrame = new PortFrame("DEBUG_IO", ioPortList, keys);
		portFrame.addWindowListener(this);
		portFrame.setVisible(true);
	}

	private void closeIOView()
	{
		portFrame.setVisible(false);
		portFrame.dispose();
	}

	@Override
	public void receiveData(String s) {
		
		String swap = null;
		
		if(s.contains("port") && s.indexOf('[') == 0 && s.indexOf(']')>0)
		{
			swap = s.substring(1, s.length()-1);
			
//			if(swap.contains("port"))
			portMatcher(swap.substring(0, swap.length()));
		}
	}
	
	private void portMatcher(String s)
	{
		String[] sp = s.split(":");
		
//		System.out.println(s+ " "+sp[0]+" "+Integer.parseInt(sp[1], 16));
		
		ioPortList.get(sp[0]).setAllBit(int2BoolArr(Integer.toBinaryString(Integer.parseInt(sp[1], 16))));
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
	public boolean isEnable() {

		return avrMode.isSelected();
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