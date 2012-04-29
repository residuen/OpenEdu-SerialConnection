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

public class VisuPanel extends JPanel implements VisuInterface, MouseListener {

	private String data = "Fill me";
	
	private long sTime = 0;
	
	private boolean menu = false;
	
	private ArrayList<Goal> goals = new ArrayList<Goal>();
	
	private Image background = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Mittelkreis_800x480.png"));
	
	private CanvasMenu cMenu = new CanvasMenu(this);
	
	private Color shapeColor = new Color(10284444);

//	private RoundRectangle2D.Double goalShape;

	public VisuPanel()
	{
		addMouseListener(this);
		
		reset();
		
		repaint();
	}

	@Override
	public void responseUartData(String data) {

		this.data = data;
		
		int a, b;
		
		long time = System.currentTimeMillis();
		boolean checkData = data.contains("[") & data.contains("]");
		Goal goal = new Goal();
		
		data.replace("[", "").replace("]", "");
		
		String[] splitData;
		String[] splitSingle;
		
		if(checkData)
		{
			splitData = data.split(",");
			
			goal.setSeconds((time - sTime)/1000d);
			
			splitSingle = splitData[0].split("=");
			a = Integer.parseInt(splitSingle[1]);
			splitSingle = splitData[1].split("=");
			b = Integer.parseInt(splitSingle[1]);
			
			goal.setTeam1(a);
			goal.setTeam2(b);
			
			if((a+b) > 0)
				goals.add(goal);
		}

		repaint();
	}
	
	@Override
	public void reset() {
		
		sTime = System.currentTimeMillis();

		goals.clear();
		goals.add(new Goal());
	}
	
	@Override
	public void paint(Graphics g) {
		
		super.paintComponents(g);
		
		int yOffset = 40;
		int xOffset = 0;
		int xStart = 90;
		int yStart = 0;

		Graphics2D g2 = (Graphics2D)g;
		
		g2.clipRect(0, 0, 800, 480);
		
		g2.drawImage(background, 0, 0, null);
		
		if(cMenu.isRunMode())
			cMenu.drawMenu(g2);
		
		for(int i = 0; i < goals.size(); i++)
		{
			g2.setColor(shapeColor);
			g2.fill(new RoundRectangle2D.Double(xOffset + xStart, (i+yStart)*yOffset + 10, 160, 35, 13, 13));
			
			g2.setColor(Color.BLACK);
			if((goals.get(i).getTeam1()+goals.get(i).getTeam2())==0)
				g2.drawString("Team 1 gegen Team 2" .toString(), xOffset + xStart + 10, (i+yStart)*yOffset + 24);
			else
				g2.drawString("Tor in der "+goals.get(i).getSeconds()+" Sekunde" .toString(), xOffset + xStart + 10, (i+yStart)*yOffset + 24);

			g2.drawString("Punktestand: "+goals.get(i).getTeam1()+":"+goals.get(i).getTeam2(), xOffset + xStart + 10, (i+yStart)*yOffset + 38);
//				g2.drawString(goals.get(i).toString(), 575, i*30 + 30);

			if((goals.get(i).getTeam1() == 5 || goals.get(i).getTeam2() == 5) && yStart == 0)
			{
				xOffset = 460;
				yStart = -(i+1);
			}	
			
//			System.out.println("goals.get(i)="+goals.get(i));
			
			if(goals.get(i).getTeam1() >= 10 || goals.get(i).getTeam2() >= 10)
			{
//				System.out.println("RESETTEN!");
				reset();
				
				break;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) { }

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		
		if(cMenu.getMenuShape().contains(arg0.getX(), arg0.getY()))
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
			if(cMenu.getExitShape().contains(arg0.getX(), arg0.getY()) && menu)
			{
				if(JOptionPane.showConfirmDialog(null, "Visu beenden?", "Input", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					System.exit(0);

				repaint();
			}
			else
			{
				if(cMenu.getRestartShape().contains(arg0.getX(), arg0.getY()) && menu)
				{
					
					if(JOptionPane.showConfirmDialog(null, "Spiel neu starten??", "Input", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					{
						reset();

						cMenu.fadeOut();

						menu=false;
					}
					
					repaint();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) { }
}
