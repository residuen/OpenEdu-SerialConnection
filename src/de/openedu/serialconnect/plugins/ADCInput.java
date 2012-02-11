package de.openedu.serialconnect.plugins;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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

public class ADCInput extends JComponent implements IOInterface, MouseListener
{
	private final int SPACER_LEFT = 3;
	private final int SPACER_TOP = SPACER_LEFT;
//	private final double HEIGHT_OF_BAR = 100;
	
	private Font font1 = getFont();
	private Font font2 = new Font(Font.MONOSPACED, Font.BOLD, 18);
	
	private IOPorts ports = null;
	private Image background = (new ImageIcon(getClass().getResource("/de/openedu/serialconnect/plugins/images/blow-gray.png"))).getImage();
	private String[] portNames = new String[] {"A", "B", "C", "D" };
	
	private int id = -1;
	private String idAsSting = "ADC";
	private String portName = "ADC";
	
	private double factor = 1;
	
	private Color veryLightGray = new Color(0.95f, 0.95f, 0.9f);
	
	public ADCInput(int ioId, String portName, IOPorts ports)
	{
		this.id = ioId;
		this.portName = portName;
		this.ports = ports;
		
		init();
	}
	
	public ADCInput(String ioIdAsString, String portName, IOPorts ports)
	{
		this.idAsSting = ioIdAsString;
		this.portName = portName;
		this.ports = ports;
		
		init();
	}

	public ADCInput(int ioId, String portName, int bitRange)
	{
		this.portName = portName;
		this.ports = new IOPorts(idAsSting, new boolean[bitRange]);
		
		init();
	}
	
	public ADCInput(int ioId, String portName, boolean[] preset)
	{
		this.id = ioId;
		this.portName = portName;
		this.ports = new IOPorts(Integer.toString(ioId), preset);
		
		init();
	}
	
	private void init()
	{
		setPreferredSize(new Dimension(16, (int)(1.8*background.getHeight(null))));
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
		
		repaint();
		
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
		
//		int fontHeight = getFont().getSize();
		
		int i;
		
		boolean[] bool = this.ports.getAllBit();
		
		factor = (double)(getHeight()-70) / (double)ports.getMaxValue();	
		double v = ports.getValue();
		int barHeight = (int)(v*factor);
		float cValue;
		
		g2d.drawImage(background, 0, 0, null);
		g2d.drawImage(background, 0, background.getHeight(null), null);
		
		// Fuellung Hintergrund
		g2d.setColor(veryLightGray);
		g2d.fillRect(2*SPACER_LEFT, 2*SPACER_TOP, 20, getHeight()-70);
		
		for(i=0; i<=barHeight; i++)
		{
			cValue = (barHeight==0) ? 0f : (float)i/(float)barHeight;

			g2d.setColor(new Color(cValue, 0.1f*(cValue), 0.1f*(cValue)));
			g2d.fillRect(2*SPACER_LEFT, 2*SPACER_TOP + i, 20, 1);
		}
		
		// aeusserer Rahmen
		g2d.setColor(Color.BLACK);
		g2d.drawRect(2*SPACER_LEFT, 2*SPACER_TOP, 20, getHeight()-70);
		
		g2d.setFont(font1);
		g2d.drawString(" "+portName, SPACER_LEFT, SPACER_TOP + getHeight()-48);
		
		if(id == -1)
			g2d.drawString(" "+idAsSting.toUpperCase(), SPACER_LEFT, SPACER_TOP + getHeight()-32);
		else
			g2d.drawString(" PORT"+portNames[id], SPACER_LEFT, SPACER_TOP + getHeight()-32);
		
		g2d.setFont(font2);
		g2d.drawString(""+ports.getValue(), 3*SPACER_LEFT + 30, getHeight()/2 - 20);
		
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(2.0f));
		g2d.drawRect(0, 0, background.getWidth(null), 2*background.getHeight(null));
		g2d.drawRect(1, 1, background.getWidth(null)-1, 2*background.getHeight(null)-1);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
