package negocio.constante.enums;

public enum EProvedorSMS {
	CONNECT ("CONNECT"),
	MOBIPRONTO ("MOBI PRONTO");
	
	private final String nome;
	
	private EProvedorSMS(String nome)
	{
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
