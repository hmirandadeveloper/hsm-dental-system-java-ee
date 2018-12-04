package gui.util.security;

import gui.util.properties.PropertiesPath;

import java.util.ArrayList;
import java.util.List;

import negocio.util.DataUtil;
import negocio.util.file.PropertiesManager;
import negocio.util.security.Key;

public class KeyManager {
	
	private static KeyManager keyGenerator;
	
	private KeyManager(){}
	
	public static KeyManager getInstance()
	{
		if(keyGenerator == null)
		{
			keyGenerator = new KeyManager();
		}
		
		return keyGenerator;
	}
	
	public void insertKeysInFile(List<Key> keys)
	{
		PropertiesManager
		.getInstancia()
		.setPropertiesToFileName(
				PropertiesPath.getInstance()
				.getSecurityPathToFileName("chave"), 
				keys, "Key Generator");
	}
	
	public List<Key> loadKeysToMonths(int meses)
	{
		List<Key> keys = new ArrayList<Key>();
		
		int mes = DataUtil.getNumMesDaData(DataUtil.getDataAtual());
		int ano = DataUtil.getNumAnoDaData(DataUtil.getDataAtual());
		
		if(meses <= 0)
		{
			return null;
		}
		
		for(int i = 0; i < meses; i++)
		{
			if(mes > 12)
			{
				mes = 1;
				ano++;
			}
			
			keys.add(new Key(ano + (mes < 10 ? "0" : "") + mes, generateKey(mes, ano)));
			mes++;
		}
		
		return keys;
	}
	
	public Key getCurrentKey()
	{
		int mes = DataUtil.getNumMesDaData(DataUtil.getDataAtual());
		int ano = DataUtil.getNumAnoDaData(DataUtil.getDataAtual());
		
		return new Key(ano + (mes < 10 ? "0" : "") + mes, generateKey(mes, ano));
	}
	
	private String generateKey(int mes, int ano)
	{
		
		String key = getPrefixo(mes, ano) + getCenter(mes, ano) + getSufixo(mes, ano);
		
		return key;
	}
	
	private String getPrefixo(int mes, int ano)
	{
		String [] prefixoKey = {"%", "%", "@", "@", "@", "$", "$", "%", "@", "$", "$", "@"};
		int keyA = ano + mes;
		
		return prefixoKey[mes - 1] + keyA;
	}
	
	private String getSufixo(int mes, int ano)
	{
		String [] sufixoKey = {"A", "A", "D", "D", "D", "I", "I", "A", "D", "I", "I", "D"};
		int keyC = ((ano / 2) - mes) * 4;		
		
		return keyC + sufixoKey[mes - 1];
	}
	
	private String getCenter(int mes, int ano)
	{
		String [] centerKeyA = {"0", "0", "3", "3", "3", "4", "4", "0", "3", "4", "4", "3"};
		String [] centerKeyB = {"1", "1", "7", "7", "7", "9", "9", "1", "7", "9", "9", "7"};
		int keyB = ano - 1987 + (mes * 2);
		
		return centerKeyA[mes - 1] + keyB + centerKeyB[mes - 1];
	}
}
