package de.openedu.serialconnect.plugins.kicker.visu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

public class CanvasMenu {
	
	private Component c;
	
	private Color c1 = new Color(10284444);
	private Color c2 = new Color(32768);
	private Color text = Color.black;
	private Color textColor = Color.black;
	
	private Color color;
	
	private int r1=0, g1=0, b1=0;
	private int r2=0, g2=0, b2=0;
	private int tr=0, tg=0, tb=0;
	private int dtr=0, dtg=0, dtb=0;
	private int dr=0, dg=0, db=0;
	
	private boolean runMode = false;
	
	public CanvasMenu(Component c)
	{
		this.c = c;
		
		resetRGB();
	}
	
	public Color getColor() { return color; }
	
	public Color getTextColor() { return textColor; }

	public void fadeIn()
	{
		resetRGB();
		
		new Thread() {
			public void run() {
				
				runMode = true;
				
				for(int i=100; i>=0; i-=4)
				{
					compileColor(i);
					
					c.repaint();
					
					try { Thread.sleep(25); } catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}.start();
	}
	
	public void fadeOut()
	{
		resetRGB();
		
		new Thread() {
			public void run() {
				
				for(int i=0; i<=100; i+=4)
				{
					compileColor(i);
					
					c.repaint();
					
					try { Thread.sleep(25); } catch (InterruptedException e) { e.printStackTrace(); }
				}
				
				runMode = false;
			}
			
		}.start();
	}
	
	private void compileColor(int i)
	{
		int rValue = (int)(r1+(dr/100f)*i);
		int gValue = (int)(g1+(dg/100f)*i);
		int bValue = (int)(b1+(db/100f)*i);
		
//		System.out.println(rValue+" "+gValue+" "+bValue);
		color = new Color(rValue, gValue, bValue);
		
		textColor = new Color((int)(tr+(dtr/100f)*i), (int)(tg+(dtg/100f)*i), (int)(tb+(dtb/100f)*i));
	}
	
	private void resetRGB()
	{
		r1 = c1.getRed();
		g1 = c1.getGreen();
		b1 = c1.getBlue();

		r2 = c2.getRed();
		g2 = c2.getGreen();
		b2 = c2.getBlue();
		
		tr = text.getRed();
		tg = text.getGreen();
		tb = text.getBlue();
		
		dr = r2 - r1;
		dg = g2 - g1;
		db = b2 - b1;
		
		dtr = r2 - tr;
		dtg = g2 - tg;
		dtb = b2 - tb;
	}

	public boolean isRunMode() { return runMode; }
}
