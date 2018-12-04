package gui.managedbeans.atributo.provedor.sms;

import gui.util.properties.PropertiesPath;
import negocio.util.file.PropertiesManager;


public abstract class Connect {
	
	private static final String PROVEDOR_SMS_PRO_DIR = "connect";
	
	public static final String PROVEDOR_SMS_NOME = PropertiesManager
											 	.getInstancia()
											 	.getPropertiesFromFileName(
											 			PropertiesPath.getInstance()
											 			.getProvedorSMSPathToFileName(PROVEDOR_SMS_PRO_DIR))
											 	.getProperty("nome");
	
	public static final String PROVEDOR_SMS_URL = PropertiesManager
		 											.getInstancia()
		 											.getPropertiesFromFileName(
		 													PropertiesPath.getInstance()
		 													.getProvedorSMSPathToFileName(PROVEDOR_SMS_PRO_DIR))
			 										.getProperty("url");
	
	public static final String PROVEDOR_SMS_USER = PropertiesManager
		 												.getInstancia()
		 												.getPropertiesFromFileName(
		 														PropertiesPath.getInstance()
		 														.getProvedorSMSPathToFileName(PROVEDOR_SMS_PRO_DIR))
														.getProperty("user");
	public static final String PROVEDOR_SMS_PASS = PropertiesManager
		 											.getInstancia()
		 											.getPropertiesFromFileName(
		 													PropertiesPath.getInstance()
		 													.getProvedorSMSPathToFileName(PROVEDOR_SMS_PRO_DIR))
													.getProperty("pass");
	
	public static final String PROVEDOR_RETORNO_SUCESSO = PropertiesManager
														.getInstancia()
														.getPropertiesFromFileName(
																PropertiesPath.getInstance()
																.getProvedorSMSPathToFileName(PROVEDOR_SMS_PRO_DIR))
																.getProperty("retorno-sucesso");
}
