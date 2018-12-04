package negocio.constante.enums;

public enum EPermissaoUsuario {
	ADM ("Permissão para Administrador"),
	GES ("Permissão para Gestor"),
	ATE ("Permissão para Atendente"),
	OPE ("Permissão para Operador"),
	CON ("Permissão para Atendente Consultório"),
	TOD ("Permissão para Todos os Usuários Ativos"),
	TEM ("Permissão para Temporário");
	
	private final String permissao;
	
	private EPermissaoUsuario(String permissao)
	{
		this.permissao = permissao;
	}

	public String getPermissao() {
		return permissao;
	}
}
