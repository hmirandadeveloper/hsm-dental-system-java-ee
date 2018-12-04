package negocio.constante.enums;

public enum ESituacaoPaciente {
	A ("Antigo"),
	E ("Efetivo"),
	F ("Falecido");
	
	private final String situacao;
	
	private ESituacaoPaciente(String situacao)
	{
		this.situacao = situacao;
	}

	public String getPerfil() {
		return situacao;
	}
}
