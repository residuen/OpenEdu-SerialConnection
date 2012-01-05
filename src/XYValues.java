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

public class XYValues implements GrabberInterface
{

	public String buildCurrentStream(char[] inputChars)
	{
		return buildValue(inputChars, 8);
	}
	
  	private String buildValue(char[] inputChars, int dataBits)
  	{
   		StringBuffer string = new StringBuffer();
   		string.append(inputChars);
   		
//   		System.out.println("string="+inputChars);

  		return string.toString();	// Empfangene Zahl zurueckgeben
  	}

	@Override
	public String buildCurrentStream(String inputChars) {
		// TODO Auto-generated method stub
		return null;
	}
}
