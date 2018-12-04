package gui.util.torpedo;

import gui.managedbeans.atributo.ConstantesSodontoSystem;
import gui.managedbeans.atributo.provedor.sms.Connect;
import gui.managedbeans.atributo.provedor.sms.MobiPronto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;

import negocio.constante.enums.EConstanteMensagem;
import negocio.constante.enums.EProvedorSMS;
import dto.AtributoOperacionalDTO;


public class TorpedoUtil {
	
	public static String getRetornoSucessoOperadora(EProvedorSMS provedorSMSSelecionado)
	{
		String retornoSucessoOperadora = "";
		
		switch (provedorSMSSelecionado) {
		case CONNECT:
			retornoSucessoOperadora = Connect.PROVEDOR_RETORNO_SUCESSO;
			break;
		case MOBIPRONTO:
			retornoSucessoOperadora = MobiPronto.PROVEDOR_RETORNO_SUCESSO;
			break;
		}
		
		return retornoSucessoOperadora;
	}
	
	public static String enviarTorpedo(
			AtributoOperacionalDTO 
			atributoOperacionalSelecionadoDTO,
			String celularEnvio, String msg,
			String nomeDestinatario,
			String data,
			String horario)
	{
		msg = converterSimboloEmValor(nomeDestinatario, msg, data, horario);
		
		String retornoEnvio = "";
		
		switch (atributoOperacionalSelecionadoDTO.getProvedorSMS()) {
		case CONNECT:
			retornoEnvio = enviarTorpedoConnect(celularEnvio, msg);
			break;

			
		case MOBIPRONTO:
			retornoEnvio = enviarTorpedoMobiPronto(celularEnvio, msg);
			break;
			
		default:	
			break;
		}
		
		return retornoEnvio;
	}
	
	
	private static String enviarTorpedo(String getURLFull)
	{
		String retornoEnvio = "";
		URL url = null;
		HttpURLConnection connection = null;
		
		try {
			url = new URL(getURLFull);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestProperty("Request-Method", "GET");
			connection.setDoInput(true);
		    connection.setDoInput(true);
		    connection.setDoOutput(true);
			connection.connect();
			
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    String line;
		    StringBuffer response = new StringBuffer(); 
		    while((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		      
		    retornoEnvio = response.toString();
			
		} 
		catch (Exception e) 
		{
		  retornoEnvio = "ERRO";
	      e.printStackTrace();
		} 
		finally 
		{

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	      
		}
		
		return retornoEnvio;
	}
	
	
	private static String enviarTorpedoConnect(String celularEnvio, String msg)
	{
		
		String getURLFull = Connect.PROVEDOR_SMS_URL +
				"&lgn=" + Connect.PROVEDOR_SMS_USER +
				"&pwd=" + Connect.PROVEDOR_SMS_PASS +
				"&msg=" + converterEspacoEmSimbolo(msg) +
				"&numbers=" + formatarCelular(celularEnvio, null);
		
		System.out.println(ConstantesSodontoSystem.SISTEMA_LOG_HEADER
							+ "|TORPEDO| CONNECT: " + getURLFull);
		
		return enviarTorpedo(getURLFull);
	}
	
	private static String enviarTorpedoMobiPronto(String celularEnvio, String msg)
	{
		
		String getURLFull = MobiPronto.PROVEDOR_SMS_URL +
				"Credencial=" + MobiPronto.PROVEDOR_SMS_USER +
				"&Token=" + MobiPronto.PROVEDOR_SMS_PASS +
				"&Mobile=" + formatarCelular(celularEnvio, "55") +
				"&Message=" + converterEspacoEmSimbolo(msg);
		
		System.out.println(ConstantesSodontoSystem.SISTEMA_LOG_HEADER
				+ "|TORPEDO| MOBI PRONTO: " + getURLFull);
		
		return enviarTorpedo(getURLFull);
	}
	
	private static String converterSimboloEmValor(String nomeDestinatario, 
			String msg, String data, String horario)
	{
		if(nomeDestinatario.trim().indexOf(" ") > 0)
		{
			if(nomeDestinatario.indexOf(" ") < 
					nomeDestinatario.length())
			{
				nomeDestinatario = nomeDestinatario
						.substring(
								0, 
								nomeDestinatario.indexOf(" "));
			}
		}
			
		String msgFormatada = msg;
		if(nomeDestinatario != null)
		msgFormatada = msgFormatada.replace(EConstanteMensagem.NOME.getConstanteMensagem(), nomeDestinatario);
		if(data != null)
		msgFormatada = msgFormatada.replace(EConstanteMensagem.DATA.getConstanteMensagem(), data);
		if(horario != null)
		msgFormatada = msgFormatada.replace(EConstanteMensagem.HORARIO.getConstanteMensagem(), horario);
		
		return msgFormatada;
	}
	
	private static String converterEspacoEmSimbolo(String msg)
	{
		String msgFormatada = msg;
		msgFormatada = msgFormatada.replace(" ", "%20");	
		
		return removerAcentos(msgFormatada);
	}
	
	private static String removerAcentos(String msg)
	{
		
		return Normalizer.normalize(msg, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	private static String formatarCelular(String celular, String codigoPais)
	{
		String celularFormatado = celular;

		celularFormatado = celularFormatado.replace("-", "");
		celularFormatado = celularFormatado.replace("(", "");
		celularFormatado = celularFormatado.replace(")", "");
		celularFormatado = celularFormatado.replace(" ", "");
		
		if(celularFormatado.length() == 10)
		{
			celularFormatado = celularFormatado.substring(0, 2) + "9" + celularFormatado.substring(2);
		}
		
		if(codigoPais != null)
		{
			celularFormatado = codigoPais + celularFormatado;
		}
		
		return celularFormatado;
	}
}
