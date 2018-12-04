package negocio.constante.enums;

public enum ESituacaoAtendimento {
	A ("Em Atendimento"),
	F ("Finalizado"),
	C ("Cancelado");
	
	private final String situacao;
	
	private ESituacaoAtendimento(String situacao)
	{
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao.toUpperCase();
	}
}
