package negocio.constante.enums;

public enum ESituacaoAgendamento {
	E ("Em Espera"),
	R ("Pré-Pagamento"),
	P ("Pagamento Realizado"),
	N ("Pagamento Confirmado"),
	T ("Em Atendimento"),
	Z ("Finalizado"),
	F ("Faltoso"),
	G ("Pagou e não foi Atendido"),
	S ("Pagamento Transferido"),
	M ("Faltou e remarcou"),
	C ("Cancelado");
	
	private final String situacao;
	
	private ESituacaoAgendamento(String situacao)
	{
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao.toUpperCase();
	}
}
