package negocio.constante.enums;

public enum ESiglaAmbiente {
	D ("DS"),
	H ("HL"),
	P ("PR");
	
	private final String sigla;
	
	private ESiglaAmbiente(String sigla)
	{
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla.toUpperCase();
	}
}
