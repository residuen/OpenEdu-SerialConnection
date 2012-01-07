package de.openedu.serialconnect.interfaces;
import de.openedu.serialconnect.parser.BBCValues;
import de.openedu.serialconnect.parser.TrimedValues;


public class ReaderFactory
{
	public static final int BBC_DEVICE = 0;
	public static final int TRIMED_FORMAT = 1;

	public GrabberInterface getInstance(int typ)
	{
		GrabberInterface retV = null;
		
		switch(typ)
		{
			case BBC_DEVICE:
				retV = new BBCValues();
				break;
			
			case TRIMED_FORMAT:
				retV = new TrimedValues();
				break;
		}
		
		return retV;
	}

}
