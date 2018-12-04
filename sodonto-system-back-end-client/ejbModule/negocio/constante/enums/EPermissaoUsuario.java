package negocio.constante.enums;

public enum EPermissaoUsuario {
	ADM ("Permiss�o para Administrador"),
	GES ("Permiss�o para Gestor"),
	ATE ("Permiss�o para Atendente"),
	OPE ("Permiss�o para Operador"),
	CON ("Permiss�o para Atendente Consult�rio"),
	TOD ("Permiss�o para Todos os Usu�rios Ativos"),
	TEM ("Permiss�o para Tempor�rio");
	
	private final String permissao;
	
	private EPermissaoUsuario(String permissao)
	{
		this.permissao = permissao;
	}

	public String getPermissao() {
		return permissao;
	}
}
