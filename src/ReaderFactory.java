
public class ReaderFactory
{
	public static final int BBC_DEVICE = 0;
	public static final int XY_CSV_FORMAT = 1;

	public GrabberInterface getInstance(int typ)
	{
		GrabberInterface retV = null;
		
		switch(typ)
		{
			case BBC_DEVICE:
				retV = new BBCValues();
				break;
			
			case XY_CSV_FORMAT:
				retV = new XYValues();
				break;
		}
		
		return retV;
	}

}
