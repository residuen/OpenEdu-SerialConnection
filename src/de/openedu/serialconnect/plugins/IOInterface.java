package de.openedu.serialconnect.plugins;

public interface IOInterface
{
	public Boolean getBit(int bit);
	public Boolean setBit(int bit, boolean state);
	public boolean[] getAllBit();
	public Boolean setAllBit(boolean[] states);
	public int getId();
}
