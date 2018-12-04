package gui.util.email;

import gui.managedbeans.atributo.provedor.email.Sodonto;

import java.util.List;

import negocio.util.email.EmailSimples;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


public class EmailUtil {
	
	@SuppressWarnings("deprecation")
	public static Email conectaEmail() throws EmailException
	{
		Email email = new SimpleEmail();
		email.setHostName(Sodonto.PROVEDOR_EMAIL_HOSTNAME);
		email.setSmtpPort(Sodonto.PROVEDOR_EMAIL_SMTP_PORT);
		email.setAuthenticator(new DefaultAuthenticator(Sodonto.PROVEDOR_EMAIL_USER, Sodonto.PROVEDOR_EMAIL_PASS));
		email.setTLS(true);
		email.setFrom(Sodonto.PROVEDOR_EMAIL_ORIGEM);
		
		return email;
	}
	
	public static String enviarEmail(String destino, String titulo, String msg) throws EmailException
	{
		String resposta = null;
		
		Email email = new SimpleEmail();
		email = conectaEmail();
		email.setSubject(titulo);
		email.setMsg(msg);
		email.addTo(destino);
		resposta = email.send();
		
		return resposta;
	}
	
	public static String enviarEmail(EmailSimples emailSimples) throws EmailException
	{
		String resposta = null;
		
		Email email = new SimpleEmail();
		email = conectaEmail();
		email.setSubject(emailSimples.getTitulo());
		email.setMsg(emailSimples.getMsgEmail().getMsgMontada());
		email.addTo(emailSimples.getDestinatario());
		resposta = email.send();
		
		return resposta;
	}
	
	public static String enviarEmailVariosDestinatarios(List<String> destinos, String titulo, String msg) throws EmailException
	{
		String resposta = null;
		
		Email email = new SimpleEmail();
		email = conectaEmail();
		email.setSubject(titulo);
		email.setMsg(msg);
		for(int i = 0; i < destinos.size(); i++)
		{
			email.addTo(destinos.get(i));
		}
		resposta = email.send();
		
		return resposta;
	}
	
	public static void enviarEmailVariosDestinatarios(List<EmailSimples> emails) throws EmailException
	{
		for(EmailSimples es : emails)
		{
			enviarEmail(es);
		}
	}
}
