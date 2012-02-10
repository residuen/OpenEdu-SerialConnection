package de.openedu.serialconnect.plugins;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

//import javax.script.SimpleScriptContext;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Input extends JComponent implements IOInterface, MouseListener
{
	private IOPorts ports = null;
	private Image led_on = (new ImageIcon(getClass().getResource("/de/jrobot/microController/images/green-led-on.png"))).getImage();
	private Image led_off = (new ImageIcon(getClass().getResource("/de/jrobot/microController/images/green-led-off.png"))).getImage();
	private Image background = (new ImageIcon(getClass().getResource("/de/jrobot/microController/images/blow-gray.png"))).getImage();
	
	private int ledDimension = led_on.getHeight(null);

//	private SharedData sharedData = null;
	
	private String id = "0";
	
	public Input(String ioId, IOPorts ports)
	{
		this.id = ioId;
		this.ports = ports;
		
		init();
	}
	
	public Input(String ioId, int bitRange)
	{
		this.ports = new IOPorts(ioId, new boolean[bitRange]);
		
		init();
	}
	
	public Input(String ioId, boolean[] preset) // , SharedData sharedData)
	{
		this.id = ioId;
		this.ports = new IOPorts(this.id, preset);
//		this.sharedData = sharedData;
		
		init();
	}
	
	private void init()
	{
		setPreferredSize(new Dimension((int)(3.5*led_on.getWidth(null)), (int)(1.8*background.getHeight(null))));
		
		addMouseListener(this);
	}

	@Override
	public boolean[] getAllBit() {
		return ports.getAllBit();
	}

	@Override
	public Boolean getBit(int bit) {
		return ports.getBit(bit);
	}

	@Override
	public Boolean setAllBit(boolean[] states) {
		return ports.setAllBit(states);
	}

	@Override
	public Boolean setBit(int bit, boolean state) {
		return ports.setBit(bit, state);
	}
	
	@Override
	public String getId()
	{
		return ports.getId();
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		int fontHeight = getFont().getSize();
		
		boolean[] bool = this.ports.getAllBit();
		
		g2d.drawImage(background, 0, 0, null);
		g2d.drawImage(background, 0, background.getHeight(null), null);
		
		int i;
		for(i=0; i < bool.length; i++)
		{
			if(bool[i])
				g2d.drawImage(led_on, 0, i*ledDimension, null);
			else
				g2d.drawImage(led_off, 0, i*ledDimension, null);
			
//			ledAreas.add(new Rectangle2D.Float(0, i*ledDimension, ledDimension, ledDimension));
			
			g2d.drawString(" E "+id+"."+i, ledDimension, (int)(i*ledDimension + fontHeight));
		}
		
		g2d.drawString(" Input", 0, (++i)*ledDimension);
		g2d.drawString(" Port "+ports.getId(), 0, (++i)*ledDimension);
		
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(2.0f));
		g2d.drawRect(0, 0, background.getWidth(null), 2*background.getHeight(null));
		g2d.drawRect(1, 1, background.getWidth(null)-1, 2*background.getHeight(null)-1);

	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
//		ObjectController objController = new ObjectController();

		int x = arg0.getX() / ledDimension;
		int y = arg0.getY() / ledDimension;
		
		if(x == 0 && y <= ports.getAllBit().length)
		{
			ports.setBit(y, !ports.getBit(y));
			
			System.out.println("skiped bit "+ y + " to " + ports.getBit(y));
			
//			if(ports.getBit(y))
//				objController.compile(sharedData, y);
			
			repaint();
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
