package negocio.constante.mensagens;


public abstract class MensagemErro {
	public static final String MSG_ERRO_INF_DUPLICADA = "Informa��o DUPLICADA no Banco de Dados!";
	public static final String MSG_ERRO_ENT_INEXISTENTE = "Dados INEXISTENTES no Banco de Dados!";
	public static final String MSG_ERRO_ENT_ATRIBUTO = "Atributos essenciais N�O preenchidos!";
	public static final String MSG_ERRO_CPF_INVALIDO = "CPF INV�LIDO!";
	public static final String MSG_ERRO_PERMISSAO_ABRIR_CAIXA = "O Usu�rio Logado possui um Caixa em ABERTO. Para poder abrir um Novo Caixa, dever�, primeiramente, FECHAR o que est� em aberto.";
	public static final String MSG_ERRO_CNPJ_INVALIDO = "CNPJ INV�LIDO!";
	public static final String MSG_ERRO_EMAIL_INVALIDO = "Email INV�LIDO!";
	public static final String MSG_ERRO_ITEM_DUPLICADO = "Item DUPLICADO na lista!";
	public static final String MSG_ERRO_SENHA_INVALIDA = "Erro de Seguran�a: Informe uma senha DIFERENTE da provis�ria!";
	public static final String MSG_ERRO_DATA_INVALIDA = "Data INV�LIDA!";
	public static final String MSG_ERRO_ENVIO_EMAIL = "Erro ao ENVIAR o Email!";
	public static final String MSG_ERRO_ENVIO_EMAIL_DESTINATARIO = "Nenhum DESTINAT�RIO para o E-mail!";
	public static final String MSG_ERRO_ENVIO_EMAIL_TITULO = "Nenhum T�TULO para o E-mail!";
	public static final String MSG_ERRO_ENVIO_EMAIL_MSG = "Nenhuma MENSAGEM para o E-mail!";
	public static final String MSG_ERRO_ENVIO_TORPEDO = "Erro ao ENVIAR o Torpedo!";
	public static final String MSG_ERRO_PACIENTE_DUPLICADO_LISTA = "Paciente DUPLICADO na Lista!";
	public static final String MSG_ERRO_PACIENTE_PENDENCIA = "Paciente com PEND�NCIAS FINANCEIRAS!";
	public static final String MSG_ERRO_MENSALIDADE = "Erro ao CARREGAR Mensalidades do Plano, favor incluir " +
			"o Plano NOVAMENTE!";
	public static final String MSG_ERRO_VALOR_TOTAL = "Favor PREENCHER a quantidade de meses com valor superior a ZERO!";
	public static final String MSG_ERRO_SENHA_ATUAL_VAZIA = "Erro de Seguran�a: Senha atual N�o pode estar VAZIA!";
	public static final String MSG_ERRO_SENHA_ATUAL_INCORRETA = "Erro de Seguran�a: Senha atual INCORRETA!";
	public static final String MSG_ERRO_SISTEMA = "Erro INESPERADO do Sistema! ";
	public static final String MSG_ERRO_SISTEMA_ANALISTA = "Um EMAIL, reportando detalhes do ERRO, foi enviado ao Analista Respons�vel: H�LIO MIRANDA. "
			+ "Que em breve estar� resolvendo o problema!";
	public static final String MSG_ERRO_KEY = "Servidor SOBRECARREGADO, necessitando de Manuten��o URGENTE!";
	
}
