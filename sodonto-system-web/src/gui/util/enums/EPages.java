package gui.util.enums;

import negocio.constante.enums.EPermissaoUsuario;

public enum EPages {
	
	AGE_GERENCIAR ("Agendamento: Gerenciar", "gerenciar-agendamento", EPermissaoUsuario.CON),
	AGE_VISUALIZAR ("Agendamento: Visualizar","visualizar-agendamento", EPermissaoUsuario.CON),
	
	ATE_VISUALIZAR("Atendimento: Visualizar","visualizar-atendimento", EPermissaoUsuario.CON),
	
	ATR_NOVO ("Atributos Operacionais: Cadastrar", "cadastrar-atributo-operacional", EPermissaoUsuario.ADM),
	ATR_MANTER("Atributos Operacionais: Manter (Alterar)", "manter-atributo-operacional", EPermissaoUsuario.ADM),
	ATR_GERENCIAR ("Atributos Operacionais: Gerenciar", "gerenciar-atributo-operacional", EPermissaoUsuario.ADM),
	
	CAI_ABRIR ("Caixa: Abrir", "abrir-caixa", EPermissaoUsuario.OPE),
	CAI_GERENCIAR ("Caixa: Gerenciar", "gerenciar-caixa", EPermissaoUsuario.OPE),
	CAI_MANTER ("Caixa: Manter (Alterar)", "manter-caixa", EPermissaoUsuario.GES),
	CAI_VISUALIZAR ("Caixa: Visualizar meus Caixas", "visualizar-caixa", EPermissaoUsuario.OPE),
	
	CAR_NOVO ("Cargo: Cadastrar", "cadastrar-cargo", EPermissaoUsuario.GES),
	CAR_MANTER ("Cargo: Manter (Alterar)", "manter-cargo", EPermissaoUsuario.GES),
	
	DEN_NOVO ("Dentista: Cadastrar", "cadastrar-dentista", EPermissaoUsuario.GES),
	DEN_MANTER ("Dentista: Manter (Alterar)", "manter-dentista", EPermissaoUsuario.GES),
	DEN_AGENDA_NOVA ("Dentista: Cadastrar Agenda", "nova-agenda", EPermissaoUsuario.GES),
	DEN_AGENDA_MANTER ("Dentista: Manter Agenda", "manter-agenda", EPermissaoUsuario.GES),
	DEN_ESPECIALIDADE ("Dentista: Vincular Especialidade", "vincular-especialidade", EPermissaoUsuario.GES),
	
	EMA_NOVO ("E-mail: Cadastrar", "cadastrar-msg-email", EPermissaoUsuario.GES),
	EMA_MANTER ("E-mail: Manter (Alterar)", "manter-msg-email", EPermissaoUsuario.GES),
	
	ESP_NOVO ("Especialidade: Cadastrar","cadastrar-especialidade", EPermissaoUsuario.GES),
	ESP_MANTER ("Especialidade: Manter (Alterar)", "manter-especialidade", EPermissaoUsuario.GES),
	
	EST_NOVO ("Estabelecimento: Cadastrar", "cadastrar-estabelecimento", EPermissaoUsuario.ADM),
	EST_MANTER ("Estabelecimento: Manter (Alterar)", "manter-estabelecimento", EPermissaoUsuario.ADM),
	
	FUN_NOVO ("Funcionário: Cadastrar", "cadastrar-funcionario", EPermissaoUsuario.GES),
	FUN_MANTER ("Funcionário: Manter (Alterar)", "manter-funcionario", EPermissaoUsuario.GES),
	
	LOG_VISUALIZAR ("Log Operacional: Visualizar", "visualizar-log-operacional", EPermissaoUsuario.ADM),
	
	OPE_NOVO ("Operadora: Cadastrar", "cadastrar-operadora", EPermissaoUsuario.GES),
	OPE_MANTER ("Operadora: Manter (Alterar)", "manter-operadora", EPermissaoUsuario.GES),
		
	PAC_NOVO ("Paciente: Cadastrar", "cadastrar-paciente", EPermissaoUsuario.ATE),
	PAC_MANTER ("Paciente: Manter (Alterar)", "manter-paciente", EPermissaoUsuario.OPE),
	PAC_VISUALIZAR ("Paciente: Visualizar", "visualizar-paciente", EPermissaoUsuario.ATE),
	PAC_VISUALIZAR_AGE ("Paciente: Agendamentos", "visualizar-agendamento-paciente", EPermissaoUsuario.CON),
	PAC_VISUALIZAR_ATE ("Paciente: Atendimentos", "visualizar-atendimento-paciente", EPermissaoUsuario.CON),
	PAC_VINCULAR_PLA ("Paciente: vincular Plano", "vincular-plano", EPermissaoUsuario.OPE),
	PAC_SMS ("Paciente: Enviar Torpedo", "enviar-torpedo-paciente", EPermissaoUsuario.ATE),
	
	PAG_INDEX ("Home: Index", "index-protected", EPermissaoUsuario.TOD),
	
	PLA_GERENCIAR ("Plano: Gerenciar","gerenciar-plano", EPermissaoUsuario.GES),
	
	PRO_GERENCIAR ("Provedor SMS: Gerenciar", "gerenciar-provedor-sms", EPermissaoUsuario.ADM),
	
	REL_PAC_FALTOSO ("Relatório: Pacientes Faltosos", "rl-paciente-faltoso", EPermissaoUsuario.GES),
	REL_PAC_AGENDADO ("Relatório: Pacientes Agendados", "rl-paciente-agendado", EPermissaoUsuario.GES),
	REL_AGE_CANCELADO ("Relatório: Agendamentos Cancelados", "rl-agendamento-cancelado", EPermissaoUsuario.GES),
	REL_MARCACAO_VOLTA ("Relatório: Marcação de Volta", "rl-marcacao-volta", EPermissaoUsuario.GES),
	REL_ATE_DEN ("Relatório: Atendimento por Dentista", "rl-atendimento-dentista", EPermissaoUsuario.GES),
	REL_ATE_CANCELADO ("Relatório: Atendimento Finalizado", "rl-atendimento-finalizado", EPermissaoUsuario.GES),
	REL_PAC_NAO_REAGENDADO ("Relatório: Paciente não Reagendado", "rl-paciente-nao-reagendado", EPermissaoUsuario.GES),
	
	SMS_NOVO ("SMS (TORPEDO): Cadastrar","cadastrar-msg-torpedo", EPermissaoUsuario.GES),
	SMS_MANTER ("SMS (TORPEDO): Manter (Alterar)","manter-msg-torpedo", EPermissaoUsuario.GES),
	
	USU_ALTERAR_DADOS ("Usuário: Alterar Dados", "alterar-dados", EPermissaoUsuario.TOD),
	USU_ALTERAR_SENHA ("Usuário: Aterar Senha", "alterar-senha", EPermissaoUsuario.TOD),
	
	USU_TEM_ATIVAR ("Usuário Temporário: Ativar Perfil", "ativar-usuario", EPermissaoUsuario.TEM);
	
	private final String nome;
	private final String url;
	private EPermissaoUsuario permissao;
	
	private EPages(String nome, String url, EPermissaoUsuario permissao)
	{
		this.nome = nome;
		this.url = url;
		this.permissao = permissao;
	}

	public String getNome() {
		return nome.toUpperCase();
	}
	
	public String getUrl()
	{
		return url.toLowerCase();
	}
	
	public EPermissaoUsuario getPermissao()
	{
		return permissao;
	}
}
