package gui.managedbeans.atributo;

import gui.util.properties.PropertiesPath;
import negocio.util.file.PropertiesManager;


public abstract class ConstantesControleSodontoSystem {
	
	private static final String SISTEMA_PRO_DIR = "system-control";
	

	public static final int SISTEMA_TIME_POLL_AGENDAMENTO = Integer.valueOf(PropertiesManager
													.getInstancia()
													.getPropertiesFromFileName(
															PropertiesPath.getInstance()
															.getSystemPathToFileName(SISTEMA_PRO_DIR))
															.getProperty("time-poll-agendamento"));
	
	public static final int SISTEMA_TIME_POLL_CAIXA = Integer.valueOf(PropertiesManager
													.getInstancia()
													.getPropertiesFromFileName(
															PropertiesPath.getInstance()
															.getSystemPathToFileName(SISTEMA_PRO_DIR))
															.getProperty("time-poll-caixa"));
	
	public static final int SISTEMA_TIME_POLL_LOG = Integer.valueOf(PropertiesManager
														.getInstancia()
														.getPropertiesFromFileName(
																PropertiesPath.getInstance()
																.getSystemPathToFileName(SISTEMA_PRO_DIR))
																.getProperty("time-poll-log"));
	
	public static final int SISTEMA_TIME_POLL = Integer.valueOf(PropertiesManager
														.getInstancia()
														.getPropertiesFromFileName(
																PropertiesPath.getInstance()
																.getSystemPathToFileName(SISTEMA_PRO_DIR))
																.getProperty("time-poll"));
	
	public static final int SISTEMA_SMS_LIMITE = Integer.valueOf(PropertiesManager
													.getInstancia()
													.getPropertiesFromFileName(
															PropertiesPath.getInstance()
															.getSystemPathToFileName(SISTEMA_PRO_DIR))
															.getProperty("sms-limite"));
	
	
}
