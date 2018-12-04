package gui.util.properties;

import javax.faces.context.FacesContext;

public class PropertiesPath {
	
	private static PropertiesPath propertiesPath;
	
	private PropertiesPath(){}
	
	public static PropertiesPath getInstance()
	{
		if(propertiesPath == null)
		{
			propertiesPath = new PropertiesPath();
		}
		
		return propertiesPath;
	}
	
	private String getRealPropertiesPath()
	{
		return FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRealPath("/") + 
				"WEB-INF/properties/";
	}
	
	public String getSystemPathToFileName(String fileName)
	{
		return getRealPropertiesPath() + 
				"system/" +
				fileName + ".properties";
	}
	
	public String getSecurityPathToFileName(String fileName)
	{
		return getRealPropertiesPath() + 
				"security/" +
				fileName + ".properties";
	}
	
	public String getProvedorSMSPathToFileName(String fileName)
	{
		return getRealPropertiesPath() + 
				"provedor/sms/" +
				fileName + ".properties";
	}
	
	public String getProvedorEmailPathToFileName(String fileName)
	{
		return getRealPropertiesPath() + 
				"provedor/email/" +
				fileName + ".properties";
	}
}
