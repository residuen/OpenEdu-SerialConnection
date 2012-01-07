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

public class BBCValues implements GrabberInterface
{
	public String buildCurrentStream(char[] inputChars)
	{
		return buildValue(inputChars, 8);
	}
	
  	private String buildValue(char[] inputChars, int dataBits)
  	{
   		String hexStr = "";
  		
  		StringBuffer retStr = new StringBuffer();
  		
  		hexStr = Integer.toHexString(inputChars[1]);
  		
  		// Testen, ob Ausgabe von Messgeraet gueltig ist
  		if(hexStr.equals("4f"))
  		{
   			retStr = null;
  		}
  		else	// wenn ja, Vorzeichen abfragen und setzen
  		{
  			if(hexStr.equals("20"))
  				retStr.append("");
  			else
  				retStr.append("-");
  		}
  		
  		// durch das char-Array laufen und zeichenweise
  		// die Ziffern fuer den Messwert extrahieren
  		// und in String schreiben
  		for(int i=2; i<inputChars.length; i++)
  		{
  			hexStr = Integer.toHexString(inputChars[i]);
 
  			// Testen, ob ein komma gesendet wurde
  			if(!hexStr.contains("ae"))
  				retStr.append(hexStr.charAt(1));	// Komma in String schreiben
  			else
  				retStr.append(".");					// Ziffer in String schreiben
  		}

  		return retStr.toString();	// Empfangene Zahl zurueckgeben
  	}

	@Override
	public String buildCurrentStream(String inputChars) {
		// TODO Auto-generated method stub
		return null;
	}
}
