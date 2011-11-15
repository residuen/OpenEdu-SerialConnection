
public class BBCValues implements GrabberInterface
{

	public GrabberInterface grabValue(int typ)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String buildCurrentStream(char[] inputChars, int dataBits)
	{
		return buildValue(inputChars, dataBits);
	}
	
  	private String buildValue(char[] inputChars, int dataBits)
  	{
  		int komma = 0;
  		String retValue = "";
  		String hexStr = "";
  		
  		StringBuffer retStr = new StringBuffer();
  		
  		// gegebenenfalls Anzahl Datenbits gegen Array-Länge verrechnen
  		for(int i=0; i<inputChars.length; i++) // for(int i=0; i<dataBits; i++)
  		{
  			hexStr = Integer.toHexString(inputChars[i]);

  			if(hexStr.equals("ae"))
  				komma = i;
  		}
  		
  		hexStr = Integer.toHexString(inputChars[1]);
  		if(hexStr.equals("4f"))
  		{
  			retValue = null;
  			retStr = null;
  		}
  		else
  		{
  			if(hexStr.equals("20"))
  				retStr.append("");
  			else
  				retStr.append("-");
  		}
  		
  		for(int i=2; i<komma; i++)
  		{
  			hexStr = Integer.toHexString(inputChars[i]);
 
  			retStr.append(hexStr.charAt(1));
  		}
  		retValue +=".";
  		for(int i=komma+1; i<inputChars.length; i++)
  		{
  			hexStr = Integer.toHexString(inputChars[i]);
 
  			retStr.append(hexStr.charAt(1));
  		}
  		
  		return retStr.toString();
  	}

}
