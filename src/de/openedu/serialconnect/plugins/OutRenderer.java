package de.openedu.serialconnect.plugins;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class OutRenderer {
	
	private final int SPACER_LEFT = 5;
	private final int SPACER_TOP_OUTPUT = 10;
	private final int SPACER_TOP_SEGMENT = 20;
	
	private Image led_on = (new ImageIcon(getClass().getResource("/de/openedu/serialconnect/plugins/images/led-on.png"))).getImage();
	private Image led_off = (new ImageIcon(getClass().getResource("/de/openedu/serialconnect/plugins/images/led-off.png"))).getImage();

	public void drawOutPort(Graphics2D g2d, boolean[] bool, String idAsSting, String portName, int fontHeight)
	{
		int i;
		for(i=0; i < bool.length; i++)
		{
//			System.out.println(bool[i]);
//			System.out.println("Zeichne Ausgang");
			if(bool[i])
				g2d.drawImage(led_on, SPACER_LEFT, SPACER_TOP_OUTPUT + i*led_on.getHeight(null), null);
			else
				g2d.drawImage(led_off, SPACER_LEFT, SPACER_TOP_OUTPUT + i*led_off.getHeight(null), null);
			
			g2d.drawString(" "+idAsSting.toUpperCase()+" "+i, SPACER_LEFT + led_on.getWidth(null), SPACER_TOP_OUTPUT + (int)(i*led_on.getHeight(null) + fontHeight));
		}
		
		g2d.drawString(" "+portName, SPACER_LEFT, SPACER_TOP_OUTPUT + (++i)*led_on.getHeight(null));
		
		g2d.drawString(" "+idAsSting.toUpperCase(), SPACER_LEFT, SPACER_TOP_OUTPUT + (++i)*led_on.getHeight(null));
	}

	public void drawSegment(Graphics2D g2d, boolean[] bool, String idAsSting, String portName, int fontHeight)
	{
		int width = 50;
		int height = 7;
		int radix = 7;
		int i = bool.length-1;
				
//		Color color = null;
		
		g2d.setColor(bool[0] ? Color.RED : Color.WHITE);
		g2d.fillRoundRect(SPACER_LEFT + radix, SPACER_TOP_SEGMENT, width, height, radix, radix);	// A
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(SPACER_LEFT + radix, SPACER_TOP_SEGMENT, width, height, radix, radix);	// A
		
		g2d.setColor(bool[1] ? Color.RED : Color.WHITE);
		g2d.fillRoundRect(SPACER_LEFT + width + radix, SPACER_TOP_SEGMENT + radix, height, width - radix, radix, radix);	// B
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(SPACER_LEFT + width + radix, SPACER_TOP_SEGMENT + radix, height, width - radix, radix, radix);	// B

		g2d.setColor(bool[2] ? Color.RED : Color.WHITE);
		g2d.fillRoundRect(SPACER_LEFT + width + radix, SPACER_TOP_SEGMENT + radix + width, height, width - radix, radix, radix);	// C
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(SPACER_LEFT + width + radix, SPACER_TOP_SEGMENT + radix + width, height, width - radix, radix, radix);	// C

		g2d.setColor(bool[3] ? Color.RED : Color.WHITE);
		g2d.fillRoundRect(SPACER_LEFT + radix, SPACER_TOP_SEGMENT + width + width, width, height, radix, radix);	// D
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(SPACER_LEFT + radix, SPACER_TOP_SEGMENT + width + width, width, height, radix, radix);	// D

		g2d.setColor(bool[4] ? Color.RED : Color.WHITE);
		g2d.fillRoundRect(SPACER_LEFT, SPACER_TOP_SEGMENT + radix + width, height, width - radix, radix, radix);	// E
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(SPACER_LEFT, SPACER_TOP_SEGMENT + radix + width, height, width - radix, radix, radix);	// E

		g2d.setColor(bool[5] ? Color.RED : Color.WHITE);
		g2d.fillRoundRect(SPACER_LEFT, SPACER_TOP_SEGMENT + radix, height, width - radix, radix, radix);	// F
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(SPACER_LEFT, SPACER_TOP_SEGMENT + radix, height, width - radix, radix, radix);	// F

		g2d.setColor(bool[6] ? Color.RED : Color.WHITE);
		g2d.fillRoundRect(SPACER_LEFT + radix, SPACER_TOP_SEGMENT + width, width, height, radix, radix);	// G
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(SPACER_LEFT + radix, SPACER_TOP_SEGMENT + width, width, height, radix, radix);	// G
		
		g2d.drawString(" "+portName, SPACER_LEFT, SPACER_TOP_SEGMENT + (++i)*led_on.getHeight(null));
		
		g2d.drawString(" "+idAsSting.toUpperCase(), SPACER_LEFT, SPACER_TOP_SEGMENT + (++i)*led_on.getHeight(null));
	}
}
