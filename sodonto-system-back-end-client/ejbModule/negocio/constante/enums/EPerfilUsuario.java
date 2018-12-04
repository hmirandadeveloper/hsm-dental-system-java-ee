package negocio.constante.enums;

public enum EPerfilUsuario {
	A ("Administrador"),
	G ("Gestor"),
	D ("Atendente"),
	O ("Operador"),
	C ("Atendente Consult�rio"),
	T ("Tempor�rio");
	
	private final String perfil;
	
	private EPerfilUsuario(String perfil)
	{
		this.perfil = perfil;
	}

	public String getPerfil() {
		return perfil;
	}
}
