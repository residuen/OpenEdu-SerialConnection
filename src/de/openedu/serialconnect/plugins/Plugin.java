package de.openedu.serialconnect.plugins;

import java.io.OutputStream;

public interface Plugin {
	
	public void receiveData(String res);
	public void sendData(OutputStream outputStream);
}
