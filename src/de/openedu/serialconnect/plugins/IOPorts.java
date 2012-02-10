package de.openedu.serialconnect.plugins;

public class IOPorts implements IOInterface
{
	private boolean[] ports = new boolean[8];
	
	private int ioId = 0;
	
	public IOPorts()
	{
		
	}
	
	public IOPorts(int ioId, int bitRange)
	{
		this.ioId = ioId;
		this.ports = new boolean[bitRange];
	}
	
	public IOPorts(int ioId, boolean[] preset)
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
			return null;
		else
		{
			ports[bit] = state;
			
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
	
	public int getId()
	{
		return ioId;
	}
}
