package de.openedu.serialconnect.plugins;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Segment extends JComponent implements IOInterface
{
	private final int SPACER_LEFT = 3;
	private final int SPACER_TOP = SPACER_LEFT;
	
	private IOPorts ports = null;
	private Image led_on = (new ImageIcon(getClass().getResource("/de/openedu/serialconnect/plugins/images/led-on.png"))).getImage();
	private Image led_off = (new ImageIcon(getClass().getResource("/de/openedu/serialconnect/plugins/images/led-off.png"))).getImage();
	private Image background = (new ImageIcon(getClass().getResource("/de/openedu/serialconnect/plugins/images/blow-gray.png"))).getImage();
	private String[] portNames = new String[] {"A", "B", "C", "D" };
	
	private int id = -1;
	private String idAsSting = null;
	private String portName = "";
	
	public Segment(int ioId, String portName, IOPorts ports)
	{
		this.id = ioId;
		this.portName = portName;
		this.ports = ports;
		
		init();
	}
	
	public Segment(String ioIdAsString, String portName, IOPorts ports)
	{
		this.idAsSting = ioIdAsString;
		this.portName = portName;
		this.ports = ports;
		
		init();
	}

	public Segment(int ioId, String portName, int bitRange)
	{
		this.portName = portName;
		this.ports = new IOPorts(idAsSting, new boolean[bitRange]);
		
		init();
	}
	
	public Segment(int ioId, String portName, boolean[] preset)
	{
		this.id = ioId;
		this.portName = portName;
		this.ports = new IOPorts(Integer.toString(ioId), preset);
		
		init();
	}
	
	private void init()
	{
		setPreferredSize(new Dimension((int)(3.5*led_on.getWidth(null)), (int)(1.8*background.getHeight(null))));
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
		
//		System.out.println("states:"+states);
		
		repaint();
		
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
//		System.out.println("paintThePort");
		Graphics2D g2d = (Graphics2D)g;
		
		int fontHeight = getFont().getSize();
		
		boolean[] bool = this.ports.getAllBit();
		
		g2d.drawImage(background, 0, 0, null);
		g2d.drawImage(background, 0, background.getHeight(null), null);
		
		int i;
		for(i=0; i < bool.length; i++)
		{
//			System.out.println(bool[i]);
			
			g2d.drawRoundRect(SPACER_LEFT, SPACER_TOP, 20, 5, 5, 5);
//			if(bool[i])
//				g2d.drawImage(led_on, SPACER_LEFT, SPACER_TOP + i*led_on.getHeight(null), null);
//			else
//				g2d.drawImage(led_off, SPACER_LEFT, SPACER_TOP + i*led_off.getHeight(null), null);
//			
//			if(id == -1)
//				g2d.drawString(" "+idAsSting.toUpperCase()+" "+i, SPACER_LEFT + led_on.getWidth(null), SPACER_TOP + (int)(i*led_on.getHeight(null) + fontHeight));
//			else
//				g2d.drawString(" P"+portNames[id]+" "+i, SPACER_LEFT + led_on.getWidth(null), SPACER_TOP + (int)(i*led_on.getHeight(null) + fontHeight));
		}
		
		g2d.drawString(" "+portName, SPACER_LEFT, SPACER_TOP + (++i)*led_on.getHeight(null));
		
		if(id == -1)
			g2d.drawString(" "+idAsSting.toUpperCase(), SPACER_LEFT, SPACER_TOP + (++i)*led_on.getHeight(null));
		else
			g2d.drawString(" PORT"+portNames[id], SPACER_LEFT, SPACER_TOP + (++i)*led_on.getHeight(null));
		
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(2.0f));
		g2d.drawRect(0, 0, background.getWidth(null), 2*background.getHeight(null));
		g2d.drawRect(1, 1, background.getWidth(null)-1, 2*background.getHeight(null)-1);
	}
}
