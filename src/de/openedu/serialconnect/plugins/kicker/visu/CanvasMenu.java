package de.openedu.serialconnect.plugins.kicker.visu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class CanvasMenu {
	
	private Component c;
	
	private RoundRectangle2D.Double menuShape = new RoundRectangle2D.Double(-13, -13, 75, 65, 25, 25);
	public RoundRectangle2D.Double getRestartShape() {
		return restartShape;
	}

	public void setRestartShape(RoundRectangle2D.Double restartShape) {
		this.restartShape = restartShape;
	}

	public RoundRectangle2D.Double getMenuShape() {
		return menuShape;
	}

	public RoundRectangle2D.Double getExitShape() {
		return exitShape;
	}

	private RoundRectangle2D.Double restartShape = new RoundRectangle2D.Double(-13, menuShape.getY() + menuShape.getHeight() + 5, 75, 65, 25, 25);
	private RoundRectangle2D.Double exitShape = new RoundRectangle2D.Double(-13, 480 - 65 + 13, 75, 65, 25, 25);

	private Color buttonColor = new Color(10284444);
	private Color backgroundColor = new Color(32768);
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
	
	public void drawMenu(Graphics2D g2)
	{
		g2.setColor(getColor());
		g2.fill(menuShape);
		g2.setColor(getTextColor());
		g2.drawString("MENUE", 5, 27);
		
		g2.setColor(getColor());
		g2.fill(restartShape);
		g2.setColor(getTextColor());
		g2.drawString("RESTART", 5, (int)(restartShape.getY() + restartShape.getHeight()/2 + 5));

		g2.setColor(getColor());
		g2.fill(exitShape);
		g2.setColor(getTextColor());
		g2.drawString("EXIT", 10, 480 - 12);
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
		r1 = buttonColor.getRed();
		g1 = buttonColor.getGreen();
		b1 = buttonColor.getBlue();

		r2 = backgroundColor.getRed();
		g2 = backgroundColor.getGreen();
		b2 = backgroundColor.getBlue();
		
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
	
	public Color getButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(Color buttonColor) {
		this.buttonColor = buttonColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}


}
