package de.openedu.serialconnect.plugins.kicker.visu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;


import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.openedu.serialconnect.plugins.kicker.interfaces.VisuInterface;

public class VisuPanel extends JPanel implements VisuInterface, MouseListener {

	private String data = "Fill me";
	
	private long sTime = 0;
	
	private boolean menu = false;
	
	private ArrayList<Goal> goals = new ArrayList<Goal>();
	
	private Image background = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Mittelkreis_800x480.png"));
	
	CanvasMenu cMenu = new CanvasMenu(this);
	
	RoundRectangle2D.Double menuShape =  new RoundRectangle2D.Double(-13, -13, 75, 65, 25, 25);
	RoundRectangle2D.Double restartShape =  new RoundRectangle2D.Double(-13, menuShape.getHeight() + 5, 75, 65, 25, 25);
	RoundRectangle2D.Double exitShape =  new RoundRectangle2D.Double(-13, 480 - 65 + 13, 75, 65, 25, 25);
	
	public VisuPanel()
	{
		addMouseListener(this);
		
		resetView();
		
//		repaint();
	}
	
	private void resetView()
	{
		sTime = System.currentTimeMillis();
		
		goals.add(new Goal());
		

	}
		
	@Override
	public void ResponseUartData(String data) {

		this.data = data;
		
		long time = System.currentTimeMillis();
		boolean checkData = data.contains("[") & data.contains("]");
		Goal goal = new Goal();
		
		String[] splitData;
		String[] splitSingle;
		
		if(checkData)
		{
			splitData = data.split(",");
			
			goal.setSeconds((time - sTime)/1000d);
			
			splitSingle = splitData[0].split("=");
			goal.setTeam1(Integer.parseInt(splitSingle[1]));
			
			splitSingle = splitData[1].split("=");
			goal.setTeam2(Integer.parseInt(splitSingle[1]));
		}
		
		System.out.println(goal);
		
		goals.add(goal);
		
		repaint();
	}
	
	@Override
	public void reset() {
		// Do what u have to do!
	}
	
	@Override
	public void paint(Graphics g) {
		
		super.paintComponents(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.drawImage(background, 0, 0, null);
		
		if(cMenu.isRunMode())
		{

//							g2d.setColor(new Color((Math.abs((r2-r1))/100f)*i + r2, g2, b2));
			g2.setColor(cMenu.getColor());
			g2.fill(menuShape);
			g2.setColor(cMenu.getTextColor());
			g2.drawString("MENUE", 5, 27);
			
			g2.setColor(cMenu.getColor());
			g2.fill(restartShape);
			g2.setColor(cMenu.getTextColor());
			g2.drawString("RESTART", 5, (int)(restartShape.getY() + restartShape.getHeight()/2 + 5));
	
			g2.setColor(cMenu.getColor());
			g2.fill(exitShape);
			g2.setColor(cMenu.getTextColor());
			g2.drawString("EXIT", 10, 480 - 12);
		}
			
			g2.setColor(Color.WHITE);
			g2.drawString(goals.get(goals.size()-1).toString(), 575, 27);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
		if(menuShape.contains(arg0.getX(), arg0.getY()))
		{
			if(!cMenu.isRunMode())
			{ 
				cMenu.fadeIn();
				
				menu=true;
			}
			else
			{ 
				cMenu.fadeOut();
				
				menu=false;
			}
			
			repaint();
			
		}
		else
		{
			if(exitShape.contains(arg0.getX(), arg0.getY()) && menu)
			{
				if(JOptionPane.showConfirmDialog(null, "Visu beenden?", "Input", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					System.exit(0);

				repaint();
			}
			else
			{
				if(restartShape.contains(arg0.getX(), arg0.getY()) && menu)
				{
					
					if(JOptionPane.showConfirmDialog(null, "Spiel neu starten??", "Input", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					{
						resetView();

						cMenu.fadeOut();

						menu=false;
					}
					
					repaint();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
