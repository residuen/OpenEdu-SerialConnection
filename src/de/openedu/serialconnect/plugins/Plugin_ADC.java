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

public class Plugin_ADC extends JPanel implements Plugin, ActionListener, WindowListener {

	public static final boolean REGISTER_MODE = false;
	public static final boolean PEAK_MODE = true;
	
	private HashMap<String, IOInterface> ioPortList = new HashMap<String, IOInterface>();
	private ArrayList<String> keys = new ArrayList<String>();

	private JCheckBox adcMode = null;
	
	private PortFrame portFrame = null;
	
	private boolean viewMode = REGISTER_MODE;
	
	public Plugin_ADC(boolean viewMode, String name)
	{
		this.viewMode = viewMode;
		
		setLayout(new BorderLayout());
				
		initComponents(name);
	}
	
	private void initComponents(String name)
	{
		adcMode = new JCheckBox(name, false);
		adcMode.addActionListener(this);
		add(adcMode);
	}

	private void initIOView()
	{	
		if(!viewMode)
		{
			keys.add("adcl");
			keys.add("adch");
			
			for(String s : keys)
				ioPortList.put(s, new Output(s, viewMode, "channel 0", new IOPorts()));
		}	
		else
		{
			keys.add("adcw");
			
			for(String s : keys)
				ioPortList.put(s, new ADCInput(s, "channel 0", new IOPorts(10)));
		}
		
		portFrame = new PortFrame("ADC-Out", ioPortList, keys);
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
		
		if(s.contains("adc") && s.indexOf('[') == 0 && s.indexOf(']')>0)
		{
			swap = s.substring(1, s.length()-1);

			portMatcher(swap.substring(0, swap.length()));
		}
	}
	
	private void portMatcher(String s)
	{
		String[] sp = s.split(":");
		String l = sp[1], h = sp[2], chn = sp[3];
		
		int adcl = Integer.parseInt(l, 16);
		int adch = Integer.parseInt(h, 16);
		int adcw = Integer.parseInt(h+l, 16);
//		int channel = Integer.parseInt(chn, 16);
		
		if(!viewMode)
		{
			ioPortList.get("adcl").setAllBit(int2BoolArr(Integer.toBinaryString(adcl), 8));
			ioPortList.get("adch").setAllBit(int2BoolArr(Integer.toBinaryString(adch), 8));
		}
		else
			ioPortList.get("adcw").setAllBit(int2BoolArr(Integer.toBinaryString(adcw), 10));
	}
	
	private boolean[] int2BoolArr(String v, int radix)
	{
		v = buildByteString(v, radix);

		char[] va = v.toCharArray();
		int size = va.length;
		boolean [] retV = new boolean[size];
		
		for(int i=0; i<size; i++)
			retV[size-1-i] = (va[i]=='1') ? true : false;
			
		return retV;
	}
	
	private String buildByteString(String v, int radix)
	{
		for(int i=0, n=(radix - v.length()); i<n; i++)
			v = "0" + v;
		
		return v;
	}

	@Override
	public void sendData(OutputStream outputStream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnable() {

		return adcMode.isSelected();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(adcMode.isSelected())
			initIOView();
		else
			closeIOView();

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		adcMode.setSelected(false);
		
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
