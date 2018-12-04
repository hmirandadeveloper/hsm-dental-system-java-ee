package negocio.constante.enums;

public enum EConstanteMensagem {
	NOME ("#NOME", "A CONSTANTE #NOME, ser� substitu�da por qualquer NOME enviado, de forma AUTOM�TICA."),
	DATA ("#DATA", "A CONSTANTE #DATA, ser� substitu�da por qualquer DATA enviada, de forma AUTOM�TICA."),
	HORARIO ("#HORARIO", "A CONSTANTE #HORARIO, ser� substitu�da por qualquer HOR�RIO enviado, de forma AUTOM�TICA.");
	
	private final String constanteMensagem;
	private final String descricao;
	
	private EConstanteMensagem(String constanteMensagem, String descricao)
	{
		this.constanteMensagem = constanteMensagem;
		this.descricao = descricao;
	}

	public String getConstanteMensagem() {
		return constanteMensagem;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
