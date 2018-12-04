package negocio.constante.enums;

public enum EConstanteMensagem {
	NOME ("#NOME", "A CONSTANTE #NOME, será substituída por qualquer NOME enviado, de forma AUTOMÁTICA."),
	DATA ("#DATA", "A CONSTANTE #DATA, será substituída por qualquer DATA enviada, de forma AUTOMÁTICA."),
	HORARIO ("#HORARIO", "A CONSTANTE #HORARIO, será substituída por qualquer HORÁRIO enviado, de forma AUTOMÁTICA.");
	
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
