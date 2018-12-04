package negocio.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import negocio.util.security.Key;

public class PropertiesManager {
	
	private static PropertiesManager propertiesLoaderSingleton;
	private Properties properties;
	
	private PropertiesManager(){}
	
	public static PropertiesManager getInstancia()
	{
		if(propertiesLoaderSingleton == null)
		{
			propertiesLoaderSingleton = new PropertiesManager();
		}
		
		return propertiesLoaderSingleton;
	}
	
	public Properties getPropertiesFromFileName(String fullFileName)
	{
		this.properties = new Properties();
		
		try
		{
			File file = new File(fullFileName);
			FileInputStream inputStream = new FileInputStream(file);
			this.properties.load(inputStream);
			inputStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return this.properties;
	}
	
	public boolean setPropertiesToFileName(String fullFileName, String prop, String value, String title)
	{
		boolean output = false;
		
		this.properties = new Properties();
		
		try
		{
			File file = new File(fullFileName);
			FileInputStream inputStream = new FileInputStream(file);
			this.properties.load(inputStream);
			inputStream.close();
			
			FileOutputStream outputStream = new FileOutputStream(file);
			this.properties.setProperty(prop, value);
			this.properties.store(outputStream, title);
			
			outputStream.close();
			output = true;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return output;
	}	
	
	public void setPropertiesToFileName(String fullFileName, List<Key> keys, String title)
	{
		
		this.properties = new Properties();
		
		try
		{
			File file = new File(fullFileName);
			
			FileInputStream inputStream = new FileInputStream(file);
			this.properties.load(inputStream);
			inputStream.close();
			
			for(int i = 0; i < keys.size(); i++)
			{
				this.properties.setProperty(keys.get(i).getName().toString(), keys.get(i).getKey().toString());				
			}
			
			Properties orderFile = new Properties(){

				private static final long serialVersionUID = 1L;

				@Override
				public synchronized java.util.Enumeration<Object> keys() {
					return Collections.enumeration(new TreeSet<Object>(super.keySet()));
				}
			};
			
			orderFile.putAll(this.properties);
			
			FileOutputStream outputStream = new FileOutputStream(file);

			orderFile.store(outputStream, title);
			outputStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
