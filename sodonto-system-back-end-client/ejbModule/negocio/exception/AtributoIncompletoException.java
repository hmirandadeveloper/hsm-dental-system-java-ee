package negocio.exception;

import negocio.util.GerenciadorAtributo;

public class AtributoIncompletoException extends Exception {

	private static final long serialVersionUID = 1L;
	private String msgRetorno;
	private GerenciadorAtributo atributoIncompleto;
	
	public AtributoIncompletoException()
	{
		super(AtributoIncompletoException.class.toString());
	}
	
	public AtributoIncompletoException(GerenciadorAtributo atributoIncompleto)
	{
		this.atributoIncompleto = atributoIncompleto;
		String msg = "";
		
		if(atributoIncompleto.getAtibutosNaoPreenchidos().size() == 1)
		{
			msg = "(01) Atributo obrigatório não foi preenchido:"; 

		}
		else if (atributoIncompleto.getAtibutosNaoPreenchidos().size() > 1)
		{
			msg = "(" + (atributoIncompleto.getAtibutosNaoPreenchidos().size() > 9 ? "" : "0") +
		atributoIncompleto.getAtibutosNaoPreenchidos().size() + ") Atributos obrigatórios não foram preenchidos:";
		}	
		
		this.msgRetorno = msg;
	}
	
	public AtributoIncompletoException(String msg)
	{
		super(msg);
	}

	public String getMsgRetorno() {
		return msgRetorno;
	}

	public void setMsgRetorno(String msgRetorno) {
		this.msgRetorno = msgRetorno;
	}

	public GerenciadorAtributo getAtributoIncompleto() {
		return atributoIncompleto;
	}

	public void setAtributoIncompleto(GerenciadorAtributo atributoIncompleto) {
		this.atributoIncompleto = atributoIncompleto;
	}

}
