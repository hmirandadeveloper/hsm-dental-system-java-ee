package negocio.constante.enums;

public enum ETipoObsPaciente {
	G ("AGENDAMENTO"),
	T ("ATENDIMENTO"),
	C ("CADASTRO"),
	X ("CAIXA"),
	A ("ACORDO");
	
	private final String tipoObs;
	
	private ETipoObsPaciente(String tipoObs)
	{
		this.tipoObs = tipoObs;
	}

	public String getTipoObs() {
		return tipoObs;
	}
}
