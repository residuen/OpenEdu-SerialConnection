/*
XYValues: Parsen und transformieren der Messwerte im csv-Format

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

package de.openedu.serialconnect.parser;

import javax.swing.JFrame;

import de.openedu.serialconnect.interfaces.GrabberInterface;
import de.openedu.serialconnect.lib.XYPlotter;

public class TrimedValues implements GrabberInterface
{
	private XYPlotter plot = new XYPlotter(JFrame.DISPOSE_ON_CLOSE);
	private StringBuffer sb = new StringBuffer();
	
//	private int count = 0;
	
	public TrimedValues()
	{
		plot.showPlotter();
	}
		
	public String buildCurrentStream(char[] inputChars)
	{
		return buildValue(inputChars, 8);
	}
	
  	private String buildValue(char[] inputChars, int dataBits)
  	{
		sb.delete(0, sb.length());
 	  	
		// entfernen der Endmarke '0' und erzeugen eines Ergebnis-Strings
  		for(char c : inputChars)	
  		{
  			if( c != 0)
  				sb.append(c);
  		}
  		
  		// 2 Messungen ueberspringen
//  		if(count <2)
//  		{
//  			count++;
//  			return sb.toString();
//  		}
  		
  		// Splitten des Strings nach Komma
  		String[] hexStrSplit = sb.toString().split(",");
   		
  		// Zeichnen eines xy-Plots
   		plot.addFunctionValue(Double.parseDouble(hexStrSplit[0]), Double.parseDouble(hexStrSplit[1]));
		
  		return sb.toString();	// Empfangene Zahl zurueckgeben
  	}

	@Override
	public String buildCurrentStream(String inputChars) {
		// TODO Auto-generated method stub
		return null;
	}
}
