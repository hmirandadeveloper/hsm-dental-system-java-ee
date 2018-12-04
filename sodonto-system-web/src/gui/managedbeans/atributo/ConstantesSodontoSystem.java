package gui.managedbeans.atributo;

import gui.util.properties.PropertiesPath;
import negocio.constante.enums.ESiglaAmbiente;
import negocio.util.file.PropertiesManager;


public abstract class ConstantesSodontoSystem {
	
	private static final String SISTEMA_PRO_DIR = "system";
	
	public static final String SISTEMA_NOME = PropertiesManager
											 	.getInstancia()
											 	.getPropertiesFromFileName(
											 			PropertiesPath.getInstance()
											 			.getSystemPathToFileName(SISTEMA_PRO_DIR))
											 	.getProperty("nome");
	
	public static final String SISTEMA_NOME_FILE = PropertiesManager
		 										.getInstancia()
		 										.getPropertiesFromFileName(
		 												PropertiesPath.getInstance()
		 												.getSystemPathToFileName(SISTEMA_PRO_DIR))
		 												.getProperty("nome-url");
	
	public static final String SISTEMA_VERSAO = PropertiesManager
		 											.getInstancia()
		 											.getPropertiesFromFileName(
		 													PropertiesPath.getInstance()
		 													.getSystemPathToFileName(SISTEMA_PRO_DIR))
			 										.getProperty("versao");
	
	public static final String SISTEMA_VERSAO_DATA = PropertiesManager
		 												.getInstancia()
		 												.getPropertiesFromFileName(
		 														PropertiesPath.getInstance()
		 														.getSystemPathToFileName(SISTEMA_PRO_DIR))
														.getProperty("versao-data");
	public static final String SISTEMA_AMBIENTE = PropertiesManager
		 											.getInstancia()
		 											.getPropertiesFromFileName(
		 													PropertiesPath.getInstance()
		 													.getSystemPathToFileName(SISTEMA_PRO_DIR))
													.getProperty("ambiente");
	
	public static final String SISTEMA_ANALISTA = PropertiesManager
		 											.getInstancia()
		 											.getPropertiesFromFileName(
		 													PropertiesPath.getInstance()
		 													.getSystemPathToFileName(SISTEMA_PRO_DIR))
													.getProperty("analista");
	
	public static final String SISTEMA_ANALISTA_CPF = PropertiesManager
													.getInstancia()
													.getPropertiesFromFileName(
															PropertiesPath.getInstance()
															.getSystemPathToFileName(SISTEMA_PRO_DIR))
															.getProperty("analista-cpf");
	
	public static final String SISTEMA_ANALISTA_EMAIL = PropertiesManager
													.getInstancia()
													.getPropertiesFromFileName(
															PropertiesPath.getInstance()
															.getSystemPathToFileName(SISTEMA_PRO_DIR))
															.getProperty("analista-email");
	
	public static final String SISTEMA_SMS_HEADER = PropertiesManager
													.getInstancia()
													.getPropertiesFromFileName(
															PropertiesPath.getInstance()
															.getSystemPathToFileName(SISTEMA_PRO_DIR))
															.getProperty("sms-header");
	
	public static final String SISTEMA_LOG_HEADER = "[" + SISTEMA_NOME + "][" + SISTEMA_VERSAO + "][" + ESiglaAmbiente.valueOf(SISTEMA_AMBIENTE.substring(0, 1)).getSigla() + "]: ";
	
	public static final String SISTEMA_EMAIL_TITULO = SISTEMA_NOME + " - " + SISTEMA_VERSAO + ": ";
	
	public static final String SISTEMA_EMAIL_HEADER = 
			"----------------------------------------------------------\n"
			+ ".: " + SISTEMA_NOME + " [" + SISTEMA_VERSAO + "] :."
			+ " \n----------------------------------------------------------\n\n";
	
	public static final String SISTEMA_EMAIL_ASS = 
			"\n\n\nAtt. \n"
			+ SISTEMA_NOME;
	
}
