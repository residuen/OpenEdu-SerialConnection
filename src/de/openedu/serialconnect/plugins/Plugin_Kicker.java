package de.openedu.serialconnect.plugins;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.OutputStream;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.openedu.serialconnect.plugins.kicker.visu.KickerVisu;

public class Plugin_Kicker extends JPanel implements Plugin, ActionListener, WindowListener {

	private JCheckBox kickerMode = new JCheckBox("Kicker", false);
	
//	private BufferedWriter fileWriter = null;
	
	private KickerVisu kicker; //  = new KickerVisu(JFrame.DISPOSE_ON_CLOSE);
	
	private boolean doPlot = false;
	
	private int count = 1;
	
	public Plugin_Kicker()
	{		
		setLayout(new BorderLayout());
				
		initComponents();
	}
	
	private void initComponents()
	{
//		kickerMode = new JCheckBox("Kicker", false);
		kickerMode.addActionListener(this);
		add(kickerMode);
	}
	
//	private void initXYPlotter()
//	{
//		if(!doPlot)
//		{
////			if(plot !=null)
////			{
////				plot.hidePlotter();
////				plot = null;
////			}
//			
//			System.out.println("new plotter");
//			plot = new XYPlotter(JFrame.DISPOSE_ON_CLOSE);
//			plot.setTitle("My serial plotwindow: "+(count++));
//			plot.setScopeMode(false);
//			plot.addWindowListener(this);
//			plot.showPlotter();
//			
//			doPlot = true;
//		}
//		
//	}
	
	private void closeXYPlotter()
	{
		System.out.println("close plotter");
		
		if(doPlot)
			doPlot = false;
	}

	
	@Override
	public void receiveData(String s) {
		
		kicker.responseUartData(s);
	}

	@Override
	public void sendData(OutputStream outputStream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnable() {

		return kickerMode.isSelected();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
//		
		if(kickerMode.isSelected())
			kicker = new KickerVisu(JFrame.DISPOSE_ON_CLOSE, kickerMode);
		else
			kicker.getFrame().dispose();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		kickerMode.setSelected(false);
		
		closeXYPlotter();
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
