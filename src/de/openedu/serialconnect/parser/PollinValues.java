package de.openedu.serialconnect.parser;
import de.openedu.serialconnect.interfaces.GrabberInterface;

/*
BBCValues: Parsen und transformieren der Messwerte des BBC GOERZ METRAWATT

Copyright (C) 2011 Karsten Bettray

Dieses Programm ist freie Software. Sie koennen es unter den Bedingungen der GNU General Public License,
wie von der Free Software Foundation veroeffentlicht, weitergeben und/oder modifizieren, entweder gemaess
Version 3 der Lizenz oder (nach Ihrer Option) jeder spaeteren Version.
Die Veroeffentlichung dieses Programms erfolgt in der Hoffnung, dass es Ihnen von Nutzen sein wird, aber
OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FUER
EINEN BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm erhalten haben.
Falls nicht, siehe <http://www.gnu.org/licenses/>.
*/

public class PollinValues implements GrabberInterface
{

//	XYPlotter plot = new XYPlotter();

	public PollinValues()
	{
//		plot.showPlotter();
	}
	
	public String buildCurrentStream(char[] inputChars)
	{
		return buildValue(inputChars, 8);
	}
	
  	private String buildValue(char[] inputChars, int dataBits)
  	{
   		StringBuffer hexStr = new StringBuffer();
   		hexStr.append(inputChars);
   		
   		System.out.println("hexStr="+hexStr);
   		
   		for(char c : inputChars)
   			System.out.println("hexStrAsASCII="+c);
   		
//   		String[] hexStrSplit = hexStr.toString().split(",");
   		
//   		plot.addFunctionValue(Double.parseDouble(hexStrSplit[0]), Double.parseDouble(hexStrSplit[1])-600);
  		
  		StringBuffer retStr = new StringBuffer();
  		
  		retStr.append(inputChars);

  		return retStr.toString();	// Empfangene Zahl zurueckgeben
  	}

	@Override
	public String buildCurrentStream(String inputChars) {
		// TODO Auto-generated method stub
		return null;
	}
}
