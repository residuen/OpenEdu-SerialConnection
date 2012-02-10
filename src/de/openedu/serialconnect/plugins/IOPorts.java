package de.openedu.serialconnect.plugins;

public class IOPorts implements IOInterface
{
	private boolean[] ports = new boolean[8];
	
	private String ioId = "0";
	
	private int maxValue = 0;
	
	public IOPorts()
	{
		
	}
	
	public IOPorts(int bitRange)
	{
		this.ports = new boolean[bitRange];
		
		solveMaximum();
	}
	
	public IOPorts(String ioId, int bitRange)
	{
		this.ioId = ioId;
		this.ports = new boolean[bitRange];
		
		solveMaximum();
	}
	
	public IOPorts(String ioId, boolean[] preset)
	{
		this.ioId = ioId;
		this.ports = preset.clone();
	}
	
	public Boolean getBit(int bit)
	{
		if(bit >= ports.length)
			return null;
		else
			return ports[bit];
	}
	
	public Boolean setBit(int bit, boolean state)
	{
		if(bit >= ports.length)
		{	
			solveMaximum();

			return null;
		}
		else
		{
			ports[bit] = state;
			
			solveMaximum();
			
			return ports[bit];
		}
	}
	
	public boolean[] getAllBit()
	{
		return ports;
	}
	
	public Boolean setAllBit(boolean[] states)
	{
		if(states.length != ports.length)
			return null;
		else
		{
			this.ports = states.clone();
			
			return true;
		}
	}
	
	public String getId()
	{
		return ioId;
	}
	
	public int getValue()
	{
		int v = 0;
		
		for(int i=0, n=ports.length; i<ports.length; i++)
		{
//			System.out.print((ports[n-i-1] ? 1 : 0));
			if(ports[n-i-1])
				v += Math.pow(2, n-i-1);
		}
//		System.out.println("");
		return v;
	}
	
	private void solveMaximum()
	{
		maxValue = (int)Math.pow(2, ports.length);
	}
	
	public int getMaxValue()
	{
		return maxValue;
	}
	
}
