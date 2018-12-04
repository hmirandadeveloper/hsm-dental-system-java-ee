package gui.util.security;

import gui.util.properties.PropertiesPath;
import negocio.util.file.PropertiesManager;
import negocio.util.security.Key;

public class KeyValidation {
	
	private static KeyValidation keyValidation;
	
	private KeyValidation(){}
	
	public static KeyValidation getInstance()
	{
		if(keyValidation == null)
		{
			keyValidation = new KeyValidation();
		}
		
		return keyValidation;
	}
	
	public boolean isActive()
	{
		boolean active = false;
		Key currentKey = KeyManager.getInstance().getCurrentKey();
		String systemKey = PropertiesManager
							.getInstancia()
							.getPropertiesFromFileName(
									PropertiesPath.getInstance()
									.getSecurityPathToFileName("chave"))
										.getProperty(currentKey.getName());
		
		if(currentKey.getKey().equals(systemKey))
		{
			active = true;
		}
		
		return active;
	}
}
