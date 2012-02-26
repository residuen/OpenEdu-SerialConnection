package de.openedu.serialconnect.plugins;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Output extends JComponent implements IOInterface
{
	private final int SPACER_LEFT = 3;
	private final int SPACER_TOP = SPACER_LEFT;
	
	private IOPorts ports = null;
//	private Image led_on = (new ImageIcon(getClass().getResource("/de/openedu/serialconnect/plugins/images/led-on.png"))).getImage();
//	private Image led_off = (new ImageIcon(getClass().getResource("/de/openedu/serialconnect/plugins/images/led-off.png"))).getImage();
	private Image background = (new ImageIcon(getClass().getResource("/de/openedu/serialconnect/plugins/images/blow-gray.png"))).getImage();
//	private String[] portNames = new String[] {"A", "B", "C", "D" };
	
	private int id = -1;
	private String idAsSting = null;
	private String portName = "";
	
	private int viewMode = 0;
	
	private OutRenderer outRenderer = new OutRenderer();
	
//	public Output(int ioId, String portName, IOPorts ports)
//	{
//		this.id = ioId;
//		this.portName = portName;
//		this.ports = ports;
//		
//		init();
//	}
	
	public Output(String ioIdAsString, int viewMode, String portName, IOPorts ports)
	{
		this.idAsSting = ioIdAsString;
		this.viewMode= viewMode; 
		this.portName = portName;
		this.ports = ports;
		
		System.out.println("portName="+portName);
		
		init();
	}

//	public Output(int ioId, String portName, boolean[] preset)
//	{
//		this.id = ioId;
//		this.portName = portName;
//		this.ports = new IOPorts(Integer.toString(ioId), preset);
//		
//		init();
//	}
	
	private void init()
	{
		setPreferredSize(new Dimension(97, 300)); //(int)(1.8*background.getHeight(null))));
//		setPreferredSize(new Dimension((int)(3.5*led_on.getWidth(null)), (int)(1.8*background.getHeight(null))));
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
		Boolean retV = ports.setAllBit(states);
		
//		for(boolean b : states)
//			System.out.print(b ? 1 : 0);
//		
//		System.out.println();
		
		repaint();
		
		return retV;
	}

	@Override
	public Boolean setBit(int bit, boolean state) {
		
		Boolean retV = ports.setBit(bit, state);
		
		repaint();
		
		return retV;
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
//		System.out.println("Zeichne Ausgang");
		Graphics2D g2d = (Graphics2D)g;
		
		int fontHeight = getFont().getSize();
		
		boolean[] bool = this.ports.getAllBit();
		
		g2d.drawImage(background, 0, 0, null);
		g2d.drawImage(background, 0, background.getHeight(null), null);
		
		if(viewMode == Plugin_IO.OUTPORT_PIN_MODE || viewMode == Plugin_IO.ADC_REGISTER_MODE)
			outRenderer.drawOutPort(g2d,bool, idAsSting, portName, fontHeight);
		else
			if(viewMode == Plugin_IO.OUTPORT_SEGMENT_MODE)
				outRenderer.drawSegment(g2d, bool, idAsSting, portName, fontHeight);
		
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(2.0f));
		g2d.drawRect(0, 0, background.getWidth(null), 2*background.getHeight(null));
		g2d.drawRect(1, 1, background.getWidth(null)-1, 2*background.getHeight(null)-1);
	}
}
