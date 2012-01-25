package de.openedu.serialconnect.interfaces;

public interface GrabberInterface 
{
//	public GrabberInterface grabValue(int typ);
//	public String transformCurrentStream(char[] inputChars, int dataBits);
	public String buildCurrentStream(char[] inputChars);
	public String buildCurrentStream(String inputChars);
	public boolean isPlugin();
	public void setPlugin(boolean plugin);
}
