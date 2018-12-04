package negocio.constante.enums;

public enum ESituacaoMensalidadePaciente {
	E ("Em Espera"),
	P ("Paga"),
	A ("Abonada"),
	T ("Pagamento Atrasado"),
	D ("Devendo");
	
	private final String situacao;
	
	private ESituacaoMensalidadePaciente(String situacao)
	{
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao.toUpperCase();
	}
}
