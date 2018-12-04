package gui.util.email;

import gui.managedbeans.atributo.ConstantesSodontoSystem;
import negocio.util.email.EmailSimples;
import negocio.util.email.MsgEmail;

public abstract class EmailFactory {
	
	public static EmailSimples getEmail(String destinatario, 
			String tituloEmail, String tituloMsg, String conteudo)
	{
		MsgEmail msgEmail =  new MsgEmail();
		msgEmail.setCabecalho(
				ConstantesSodontoSystem.SISTEMA_EMAIL_HEADER + 
				tituloMsg + ":\n\n");
		msgEmail.setMsg(conteudo);
		msgEmail.setAssinatura(
				ConstantesSodontoSystem.SISTEMA_EMAIL_ASS);
		
		EmailSimples email = new EmailSimples();
		email.setDestinatario(destinatario);
		email.setTitulo(tituloEmail);
		email.setMsgEmail(msgEmail);
		
		return email;
	}
}
