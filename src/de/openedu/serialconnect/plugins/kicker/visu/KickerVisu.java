package de.openedu.serialconnect.plugins.kicker.visu;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class KickerVisu implements ComponentListener, WindowListener  {
	
	final VisuInterface vp = new VisuPanel();
	final Point punkte = new Point(0,0);
	
	private JFrame frame = new JFrame("KickerVisu");
	
	private Component comp = null;

	public JFrame getFrame() {
		return frame;
	}

	public KickerVisu(int closeOperation, Component comp)
	{
		this.comp = comp;
		
		frame.addComponentListener(this);
		frame.setDefaultCloseOperation(closeOperation);
		
//		frame.setUndecorated(true);
		if(frame.isUndecorated())
			frame.setSize(800, 480);
		else
			frame.setSize(816, 518);
		
//		frame.setLocation(1360, 0);

		frame.addWindowListener(this);
		frame.getContentPane().add((Component)vp);
		
		frame.setVisible(true);
		
		((Component)vp).repaint();
	}
	
	public void responseUartData(String data)
	{
		System.out.println("[team1="+punkte.x+",team2="+punkte.y+",zeit="+System.currentTimeMillis()+"]");
		vp.responseUartData("[team1="+punkte.x+",team2="+punkte.y+",zeit="+System.currentTimeMillis()+"]");
	}
		
	private void loop()
	{
		final Random rand = new Random();

		new Thread() {
			public void run() {
				
				try { Thread.sleep(100); } catch (InterruptedException e1) { e1.printStackTrace(); }
				
				while(true) {
					
//					System.out.println("[team1="+punkte.x+",team2="+punkte.y+",zeit="+System.currentTimeMillis()+"]");
					vp.responseUartData("[team1="+punkte.x+",team2="+punkte.y+",zeit="+System.currentTimeMillis()+"]");
					
					final int time = (rand.nextInt(500)+500);	//  (rand.nextInt(2000)+1000);
					
					if((time/2)%2==0)
						punkte.setLocation(punkte.x + 1, punkte.y);
					else
						punkte.setLocation(punkte.x, punkte.y + 1);
					
					try { Thread.sleep(time); } catch (InterruptedException e) { e.printStackTrace(); }
					
					if(punkte.x > 10 || punkte.y > 10)
					{
						punkte.x = 0; punkte.y = 0;
						
						try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
						
						break;
					}

				}
			}
		}.start();
	}
	
	
	
	public static void main(String[] args) {
		
		KickerVisu kv = new KickerVisu(JFrame.EXIT_ON_CLOSE, null);
		
		kv.loop();
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		System.out.println("ENDE");
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
//		System.out.println(arg0.getComponent().getSize());
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		
	}

	public void dispose() {
		frame.dispose();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		frame.dispose();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if(comp instanceof JCheckBox)
			((JCheckBox)comp).setSelected(false);
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {

		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {


	}

	@Override
	public void windowIconified(WindowEvent arg0) {


	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
