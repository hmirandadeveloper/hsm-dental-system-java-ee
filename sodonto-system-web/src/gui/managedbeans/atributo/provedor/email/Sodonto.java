package gui.managedbeans.atributo.provedor.email;

import gui.util.properties.PropertiesPath;
import negocio.util.file.PropertiesManager;


public abstract class Sodonto {
	
	private static final String PROVEDOR_EMAIL_PRO_DIR = "sodonto";
	
	public static final String PROVEDOR_EMAIL_NOME = PropertiesManager
											 	.getInstancia()
											 	.getPropertiesFromFileName(
											 			PropertiesPath.getInstance()
											 			.getProvedorEmailPathToFileName(PROVEDOR_EMAIL_PRO_DIR))
											 	.getProperty("nome");
	
	public static final String PROVEDOR_EMAIL_HOSTNAME = PropertiesManager
		 											.getInstancia()
		 											.getPropertiesFromFileName(
		 													PropertiesPath.getInstance()
		 													.getProvedorEmailPathToFileName(PROVEDOR_EMAIL_PRO_DIR))
			 										.getProperty("hostname");
	
	public static final int PROVEDOR_EMAIL_SMTP_PORT = Integer.valueOf(PropertiesManager
															.getInstancia()
															.getPropertiesFromFileName(
																	PropertiesPath.getInstance()
																	.getProvedorEmailPathToFileName(PROVEDOR_EMAIL_PRO_DIR))
																	.getProperty("smtp-port"));
	
	public static final String PROVEDOR_EMAIL_USER = PropertiesManager
		 												.getInstancia()
		 												.getPropertiesFromFileName(
		 														PropertiesPath.getInstance()
		 														.getProvedorEmailPathToFileName(PROVEDOR_EMAIL_PRO_DIR))
														.getProperty("user");
	public static final String PROVEDOR_EMAIL_PASS = PropertiesManager
		 											.getInstancia()
		 											.getPropertiesFromFileName(
		 													PropertiesPath.getInstance()
		 													.getProvedorEmailPathToFileName(PROVEDOR_EMAIL_PRO_DIR))
													.getProperty("pass");
	
	public static final String PROVEDOR_EMAIL_ORIGEM = PropertiesManager
													.getInstancia()
													.getPropertiesFromFileName(
															PropertiesPath.getInstance()
															.getProvedorEmailPathToFileName(PROVEDOR_EMAIL_PRO_DIR))
															.getProperty("origem");
}
