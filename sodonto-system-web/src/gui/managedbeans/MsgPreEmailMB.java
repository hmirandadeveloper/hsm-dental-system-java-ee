package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IMsgPreEmailFachada;
import dto.MsgPreEmailDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class MsgPreEmailMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|MSG EMAIL-MB|";
	
	@EJB
	private IMsgPreEmailFachada msgPreEmailSB;
	
	private MsgPreEmailDTO msgPreEmailDTO;	
	
	private String nomeMsgPreEmailFiltro;
	
	public void limparMB()
	{
		this.msgPreEmailDTO = null;
	}
	
	public String salvar()
	{
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			this.msgPreEmailDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.msgPreEmailSB.salvar(this.msgPreEmailDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.msgPreEmailDTO = null;
		} catch (EntidadeCadastradaException e) {
			enviarMenssagemErro(e.getMessage());
			e.printStackTrace();
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
			reportarErroAoAnalista(MBName + " SALVAR MSG EMAIL", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarMsgPreEmail(MsgPreEmailDTO msgPreEmailDTOAlteracao)
	{
		this.msgPreEmailDTO = msgPreEmailDTOAlteracao;
	}
	
	public String alterar()
	{
		try {
			this.msgPreEmailDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.msgPreEmailSB.alterar(this.msgPreEmailDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.msgPreEmailDTO = null;
		} catch (EntidadeCadastradaException e) {
			enviarMenssagemErro(e.getMessage());
			e.printStackTrace();
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
			reportarErroAoAnalista(MBName + " ALTERAR MSG EMAIL", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerMsgPreEmail(MsgPreEmailDTO msgPreEmailDTOAlteracao)
	{
		this.msgPreEmailDTO = msgPreEmailDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.msgPreEmailDTO.getIdMsgPreEmail() != null)
		{
			try {
				this.msgPreEmailSB.inativar(this.msgPreEmailDTO.getIdMsgPreEmail(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.msgPreEmailDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<MsgPreEmailDTO> getMsgPreEmailsFiltrados()
	{

		if(!getNomeMsgPreEmailFiltro().equals(""))
		{
			setNomeMsgPreEmailFiltro(!getNomeMsgPreEmailFiltro().equals("") ? "%" + this.nomeMsgPreEmailFiltro + "%" : "");
			return buscarMsgPreEmailsPeloNome();
		}
		else
		{
			return buscarMsgPreEmails();
		}
		
	}
	
	public List<MsgPreEmailDTO> buscarMsgPreEmails()
	{
		List<MsgPreEmailDTO>  msgPreEmailDTOsBusca = null;
			msgPreEmailDTOsBusca = this.msgPreEmailSB.buscarAtivos();
			
		return msgPreEmailDTOsBusca != null 
				? msgPreEmailDTOsBusca 
						: new ArrayList<MsgPreEmailDTO>();
	}
	
	public List<MsgPreEmailDTO> buscarMsgPreEmailsPeloNome()
	{
		List<MsgPreEmailDTO>  msgPreEmailDTOsBusca = null;
		
		if(!this.getNomeMsgPreEmailFiltro().equals(""))
		{
			msgPreEmailDTOsBusca = this.msgPreEmailSB.buscarPorDescricao(this.nomeMsgPreEmailFiltro);

		}
		return msgPreEmailDTOsBusca != null 
				? msgPreEmailDTOsBusca 
						: new ArrayList<MsgPreEmailDTO>();
	}
	
	public MsgPreEmailDTO buscarPeloId()
	{
		MsgPreEmailDTO msgPreEmailDTOBusca = null;
		
		if(this.msgPreEmailDTO.getIdMsgPreEmail() != null)
		{
			msgPreEmailDTOBusca = this.msgPreEmailSB.buscarPorId(this.msgPreEmailDTO.getIdMsgPreEmail());
			if(msgPreEmailDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return msgPreEmailDTOBusca;
	}
	
	public MsgPreEmailDTO getMsgPreEmailDTO() {
		if(msgPreEmailDTO == null)
		{
			msgPreEmailDTO = DTOFactory.getMsgPreEmailDTO();
		}
		
		return msgPreEmailDTO;
	}

	public void setMsgPreEmailDTO(MsgPreEmailDTO msgPreEmailDTO) {
		this.msgPreEmailDTO = msgPreEmailDTO;
	}

	public String getNomeMsgPreEmailFiltro() {
		if(nomeMsgPreEmailFiltro == null)
		{
			this.nomeMsgPreEmailFiltro = "";
		}
		return nomeMsgPreEmailFiltro;
	}

	public void setNomeMsgPreEmailFiltro(String nomeMsgPreEmailFiltro) {
		this.nomeMsgPreEmailFiltro = nomeMsgPreEmailFiltro;
	}
}
