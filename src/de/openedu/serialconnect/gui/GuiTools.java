package de.openedu.serialconnect.gui;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author bettray
 *
 */
public class GuiTools
{
	// Look&Feel java-arguments
	public static final String NIMBUS ="nimbus";
	public static final String GTK ="gtk";
	public static final String MAC ="mac";
	public static final String WIN ="win";
	public static final String SYS ="sys";
	public static final String CROSS ="cross";
	
	// Look&Feel constants
	private static final String NIMBUS_LF = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	private static final String GTK_LF    = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
	private static final String MAC_LF    = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
	private static final String WIN_LF    = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	private static final String SYS_LF    = UIManager.getSystemLookAndFeelClassName();
	private static final String CROSS_LF  = UIManager.getCrossPlatformLookAndFeelClassName();

	public static void setLookAndFeel(String args[])
	{
		
		try
		{
			if(args!=null && args.length>0)
			{
				if(args[0].toLowerCase().indexOf(NIMBUS)>-1)
				{
					System.out.println("Setting NIMBUS look&feel");
						UIManager.setLookAndFeel(NIMBUS_LF);
				}
				else if(args[0].toLowerCase().indexOf(GTK)>-1)
				{
					System.out.println("Setting GTK look&feel");
						UIManager.setLookAndFeel(GTK_LF);
				}
				else if(args[0].toLowerCase().indexOf(MAC)>-1)
				{
					System.out.println("Setting Macintosh look&feel");
					UIManager.setLookAndFeel(MAC_LF);
				}
				else if(args[0].toLowerCase().indexOf(WIN)>-1)
				{
					System.out.println("Setting Windows look&feel");
					UIManager.setLookAndFeel(WIN_LF);
				}
				else if(args[0].toLowerCase().indexOf(SYS)>-1)
				{
					System.out.println("Setting System look&feel");
					UIManager.setLookAndFeel(SYS_LF);
				}
				else if(args[0].toLowerCase().indexOf(CROSS)>-1)
				{
					System.out.println("Setting crossplatform look&feel");
					UIManager.setLookAndFeel(CROSS_LF);
				}
			}
			else
			{
				System.out.println("Setting crossplatform look&feel");
				UIManager.setLookAndFeel(CROSS_LF);
			}
		}
		catch (ClassNotFoundException e) { System.err.println("ClassNotFoundException"); }
		catch (InstantiationException e) { System.err.println("InstantiationException"); }
		catch (IllegalAccessException e) { System.err.println("IllegalAccessException"); }
		catch (UnsupportedLookAndFeelException e) { System.err.println("UnsupportedLookAndFeelException"); }
		
	}
}
