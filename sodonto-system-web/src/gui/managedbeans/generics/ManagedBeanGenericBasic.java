package gui.managedbeans.generics;

import gui.managedbeans.atributo.ConstantesSodontoSystem;
import gui.util.email.EmailFactory;
import gui.util.email.EmailUtil;
import gui.util.security.KeyValidation;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.exception.AtributoIncompletoException;
import negocio.fachada.local.IAtributoOperacionalFachada;
import negocio.fachada.local.ILogOperacionalFachada;
import negocio.util.DataUtil;
import negocio.util.email.EmailSimples;

import org.apache.commons.mail.EmailException;

import dto.AtributoOperacionalDTO;
import dto.FuncionarioDTO;
import dto.LogOperacionalDTO;
import dto.factory.DTOFactory;

public abstract class ManagedBeanGenericBasic {
	
	@EJB
	private IAtributoOperacionalFachada atributoOperacionalSB;
	@EJB
	private ILogOperacionalFachada logOperacionalSB;
	
	private KeyValidation keyValidation;
	private LogOperacionalDTO logOperacionalDTO;
	
	public AtributoOperacionalDTO getAtributoOperacionalSelecionado()
	{
		return this.atributoOperacionalSB.buscarAtributoSelecionado();
	}
	
	protected void incluirLogOperacional(String tituloOperacao, String detalhesOperacao)
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return;
		}
		
		if(isPermissaoHelioMiranda())
		{
			return;
		}
		
		String dataEHoraOperacao = " [DATA/HORA DA OPERAÇÃO: "
				+ DataUtil.getDataEHoraFormatada(DataUtil.getDataAtual()) + ".";
		String complementoCabecalho = "O(A) FUNCIONÁRIO(A): " 
				+ getFuncionarioLogado().getNome() + ", ";
		
		getLogOperacionalDTO().setTituloOperacao(tituloOperacao);
		getLogOperacionalDTO().setDetalhesOperacao(
				ConstantesSodontoSystem.SISTEMA_LOG_HEADER
				+ complementoCabecalho
				+ detalhesOperacao  
				+ dataEHoraOperacao);
		getLogOperacionalDTO().setDataLog(DataUtil.getDataAtual());
		salvarLogOperacional();
	}
	
	private void salvarLogOperacional()
	{
		try {
			this.logOperacionalDTO.setUsuarioLogDTO(getFuncionarioLogado().getUsuarioPerfil());
			this.logOperacionalSB.salvar(this.logOperacionalDTO);
			this.logOperacionalDTO = null;
		} catch (AtributoIncompletoException e) {
			enviarMenssagemErro(e.getMsgRetorno());
			for(String s : e.getAtributoIncompleto().getAtibutosNaoPreenchidos())
			{
				enviarMenssagemAlerta("Atributo obrigatório: " + s + ";");
			}
			e.printStackTrace();
		} catch (EJBTransactionRolledbackException e) {
			if(e.getMessage().contains("ConstraintViolation"))
			{
				enviarMenssagemErro(MensagemErro.MSG_ERRO_INF_DUPLICADA);
			}
			else
			{
				enviarMenssagemErroGrave(MensagemErro.MSG_ERRO_SISTEMA);
			}
			e.printStackTrace();
		} catch (Exception e) {
			enviarMenssagemErroGrave(MensagemErro.MSG_ERRO_SISTEMA);
			reportarErroAoAnalista("|GENERIC MB| SALVAR LOG OPERACIONAL", e);
			
			e.printStackTrace();
		}
	}
	
	public FuncionarioDTO getFuncionarioLogado()
	{
		FacesContext context = getContext();
		HttpSession session = 
				(HttpSession)context.getExternalContext().getSession(false);

		return 	((FuncionarioDTO)session.getAttribute("funcionario"));
	}
	
	protected void logout()
	{
		FacesContext context = getContext();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
		session.invalidate();
	}
	
	protected void redirecionar(String caminho)
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(caminho);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String logoutSistema()
	{
		logout();
		
		return "index-protected";
	}
	
	protected void errorKey()
	{
		if(!getKeyValidation().isActive())
		{
			enviarMenssagemErroGrave(MensagemErro.MSG_ERRO_SISTEMA);
			reportarErroAoAnalista("CHAVE EXPIRADA", 
					new Exception("CHAVE DO SISTEMA EXPIRADA PARA O MÊS ATUAL"));
			enviarMenssagemErroGrave(MensagemErro.MSG_ERRO_KEY);
		}
	}
	
	protected boolean reportarErroAoAnalista(String localErro, Exception exception)
	{
		if(exception == null)
		{
			return false;
		}
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		
		EmailSimples email = EmailFactory.getEmail(
				ConstantesSodontoSystem.SISTEMA_ANALISTA_EMAIL,
				//Título Email;
				ConstantesSodontoSystem.SISTEMA_EMAIL_TITULO +
				"[ERRO INESPERADO]", 
				//Título Mensagem;
				localErro,
				//Mensagem;
				sw.toString() + "\n#####\n" +
				exception.getLocalizedMessage() + "\n#####\n" + 
				exception.getMessage() + "\n#####\n" +
				exception.getLocalizedMessage() + "\n#####\n" +
				exception.toString() + "\n#####\nEND");
		
		boolean enviado = false;
		
		try {
			EmailUtil.enviarEmail(email);
			enviado = true;
			enviarMenssagemInformativa(MensagemErro.MSG_ERRO_SISTEMA_ANALISTA);
		} catch (EmailException e) {
			e.printStackTrace();
			enviado = false;
		}
		
		return enviado;
	}
	
	protected boolean enviarEmail(EmailSimples email)
	{
		if(email.getDestinatario().isEmpty() || email.getDestinatario() == null)
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENVIO_EMAIL_DESTINATARIO);
			return false;
		}
		
		if(email.getTitulo().isEmpty() || email.getTitulo() == null)
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENVIO_EMAIL_TITULO);
			return false;
		}
		
		if(email.getMsgEmail() == null)
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENVIO_EMAIL_MSG);
			return false;
		}
		
		boolean enviado = false;
		
		try {
			EmailUtil.enviarEmail(email);
			enviado = true;
			enviarMenssagemInformativa("");
		} catch (EmailException e) {
			e.printStackTrace();
			enviado = false;
		}
		
		return enviado;
	}
	
	protected FacesContext getContext()
	{
		return FacesContext.getCurrentInstance();
	}

	protected void enviarMenssagemInformativa(String msg)
	{
		FacesContext context = getContext();

		context.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, msg, msg));
	}

	protected void enviarMenssagemAlerta(String msg)
	{
		FacesContext context = getContext();

		context.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_WARN, msg, msg));
	}

	protected void enviarMenssagemErro(String msg)
	{
		FacesContext context = getContext();

		context.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_ERROR, msg, msg));
	}

	protected void enviarMenssagemErroGrave(String msg)
	{
		FacesContext context = getContext();

		context.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_FATAL, msg, msg));
	}
	
	protected boolean isPermissaoHelioMiranda()
	{
		boolean retornoValidacao = false;
		
		if(getFuncionarioLogado() != null)
		{
			if(getFuncionarioLogado().getUsuarioPerfil().getIdUsuario() == 1 &&
				getFuncionarioLogado().getUsuarioPerfil().getUsuario().equals("hmiranda") &&
				getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.A
				&& getFuncionarioLogado().getNomeCompleto().equals("HÉLIO MIRANDA")
				&& getFuncionarioLogado().getCpf().equals("067.892.094-09"))
			{
				retornoValidacao = true;
			}
		}
		
		return retornoValidacao;
	}

	public KeyValidation getKeyValidation() {
		
		this.keyValidation = KeyValidation.getInstance();
		
		return keyValidation;
	}

	public LogOperacionalDTO getLogOperacionalDTO() {
		if(logOperacionalDTO == null)
		{
			this.logOperacionalDTO = DTOFactory.getLogOperacionalDTO();
		}
		
		return logOperacionalDTO;
	}

	public void setLogOperacionalDTO(LogOperacionalDTO logOperacionalDTO) {
		this.logOperacionalDTO = logOperacionalDTO;
	}
}
