package negocio.constante.mensagens;


public abstract class MensagemErro {
	public static final String MSG_ERRO_INF_DUPLICADA = "Informação DUPLICADA no Banco de Dados!";
	public static final String MSG_ERRO_ENT_INEXISTENTE = "Dados INEXISTENTES no Banco de Dados!";
	public static final String MSG_ERRO_ENT_ATRIBUTO = "Atributos essenciais NÃO preenchidos!";
	public static final String MSG_ERRO_CPF_INVALIDO = "CPF INVÁLIDO!";
	public static final String MSG_ERRO_PERMISSAO_ABRIR_CAIXA = "O Usuário Logado possui um Caixa em ABERTO. Para poder abrir um Novo Caixa, deverá, primeiramente, FECHAR o que está em aberto.";
	public static final String MSG_ERRO_CNPJ_INVALIDO = "CNPJ INVÁLIDO!";
	public static final String MSG_ERRO_EMAIL_INVALIDO = "Email INVÁLIDO!";
	public static final String MSG_ERRO_ITEM_DUPLICADO = "Item DUPLICADO na lista!";
	public static final String MSG_ERRO_SENHA_INVALIDA = "Erro de Segurança: Informe uma senha DIFERENTE da provisória!";
	public static final String MSG_ERRO_DATA_INVALIDA = "Data INVÁLIDA!";
	public static final String MSG_ERRO_ENVIO_EMAIL = "Erro ao ENVIAR o Email!";
	public static final String MSG_ERRO_ENVIO_EMAIL_DESTINATARIO = "Nenhum DESTINATÁRIO para o E-mail!";
	public static final String MSG_ERRO_ENVIO_EMAIL_TITULO = "Nenhum TÍTULO para o E-mail!";
	public static final String MSG_ERRO_ENVIO_EMAIL_MSG = "Nenhuma MENSAGEM para o E-mail!";
	public static final String MSG_ERRO_ENVIO_TORPEDO = "Erro ao ENVIAR o Torpedo!";
	public static final String MSG_ERRO_PACIENTE_DUPLICADO_LISTA = "Paciente DUPLICADO na Lista!";
	public static final String MSG_ERRO_PACIENTE_PENDENCIA = "Paciente com PENDÊNCIAS FINANCEIRAS!";
	public static final String MSG_ERRO_MENSALIDADE = "Erro ao CARREGAR Mensalidades do Plano, favor incluir " +
			"o Plano NOVAMENTE!";
	public static final String MSG_ERRO_VALOR_TOTAL = "Favor PREENCHER a quantidade de meses com valor superior a ZERO!";
	public static final String MSG_ERRO_SENHA_ATUAL_VAZIA = "Erro de Segurança: Senha atual Não pode estar VAZIA!";
	public static final String MSG_ERRO_SENHA_ATUAL_INCORRETA = "Erro de Segurança: Senha atual INCORRETA!";
	public static final String MSG_ERRO_SISTEMA = "Erro INESPERADO do Sistema! ";
	public static final String MSG_ERRO_SISTEMA_ANALISTA = "Um EMAIL, reportando detalhes do ERRO, foi enviado ao Analista Responsável: HÉLIO MIRANDA. "
			+ "Que em breve estará resolvendo o problema!";
	public static final String MSG_ERRO_KEY = "Servidor SOBRECARREGADO, necessitando de Manutenção URGENTE!";
	
}
