package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.enums.EProvedorSMS;
import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IAtributoOperacionalFachada;
import dto.AtributoOperacionalDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class AtributoOperacionalMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|ATRIBUTO OPERACIONAL-MB|";
	
	@EJB
	private IAtributoOperacionalFachada atributoOperacionalSB;
	
	private AtributoOperacionalDTO atributoOperacionalDTO;	
		
	public void limparMB()
	{
		this.atributoOperacionalDTO = null;
	}
	
	public EProvedorSMS[] getProvedoresSMS()
	{
		return EProvedorSMS.values();
	}
	
	public List<EProvedorSMS> getProvedoresSMSAsList()
	{
		List<EProvedorSMS> provedoresSMS = Arrays.asList(EProvedorSMS.values());
		
		return new ArrayList<EProvedorSMS>(provedoresSMS);
	}
	
	public String salvar()
	{
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			this.atributoOperacionalDTO.setUsuarioDTO(getFuncionarioLogado().getUsuarioPerfil());
			this.atributoOperacionalSB.salvar(this.atributoOperacionalDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.atributoOperacionalDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR ATRIBUTO OPERACIONAL", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarAtributoOperacional(AtributoOperacionalDTO atributoOperacionalDTOAlteracao)
	{
		this.atributoOperacionalDTO = atributoOperacionalDTOAlteracao;
	}
	
	public void selecionarAtributoOperacional(AtributoOperacionalDTO atributoOperacionalDTOAlteracao)
	{
		atributoOperacionalDTOAlteracao.setSelecionado(true);
		
		for(AtributoOperacionalDTO ao : buscarAtributoOperacionaisAtivos())
		{
			if(ao.getIdAtributoOperacional() != 
					atributoOperacionalDTOAlteracao
					.getIdAtributoOperacional())
			{
				ao.setSelecionado(false);
				alterar(ao, false, null);
			}
		}
		
		alterar(atributoOperacionalDTOAlteracao, true, MensagemInformativa.MSG_ATRIBUTO_OPERACIONAL_SELECIONADO);
	}
	
	public void selecionarProvedorSMS(EProvedorSMS provedorSMSSelecionado)
	{
		AtributoOperacionalDTO atributoOperacionalSelecionado = getAtributoOperacionalSelecionado();
		atributoOperacionalSelecionado.setProvedorSMS(provedorSMSSelecionado);
		
		alterar(atributoOperacionalSelecionado, true, MensagemInformativa.MSG_PROVEDOR_SMS_SELECIONADO);
	}
	
	public String alterar()
	{
		try {
			this.atributoOperacionalDTO.setUsuarioDTO(getFuncionarioLogado().getUsuarioPerfil());
			this.atributoOperacionalSB.alterar(this.atributoOperacionalDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.atributoOperacionalDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR ATRIBUTO OPERACIONAL", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public String alterar(
			AtributoOperacionalDTO atributoOperacionalSelecionadoDTO,
			boolean enviarMsg, String msg)
	{
		try {
			atributoOperacionalSelecionadoDTO.setUsuarioDTO(getFuncionarioLogado().getUsuarioPerfil());
			this.atributoOperacionalSB.alterar(atributoOperacionalSelecionadoDTO);
			if(enviarMsg)
			{
				enviarMenssagemInformativa(msg == null ? MensagemInformativa.MSG_ATUALIZAR_SUCESSO : msg);
			}
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
			reportarErroAoAnalista(MBName + " ALTERAR ATRIBUTO OPERACIONAL", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerAtributoOperacional(AtributoOperacionalDTO atributoOperacionalDTOAlteracao)
	{
		this.atributoOperacionalDTO = atributoOperacionalDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.atributoOperacionalDTO.getIdAtributoOperacional() != null)
		{
			try {
				this.atributoOperacionalSB.inativar(this.atributoOperacionalDTO.getIdAtributoOperacional(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.atributoOperacionalDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<AtributoOperacionalDTO> buscarAtributoOperacionaisAtivos()
	{
		List<AtributoOperacionalDTO>  atributoOperacionalDTOsBusca = null;
			atributoOperacionalDTOsBusca = this.atributoOperacionalSB.buscarPorEstado(true);
			
		return atributoOperacionalDTOsBusca != null 
				? atributoOperacionalDTOsBusca 
						: new ArrayList<AtributoOperacionalDTO>();
	}
	
	public List<AtributoOperacionalDTO> buscarAtributoOperacionaisInativos()
	{
		List<AtributoOperacionalDTO>  atributoOperacionalDTOsBusca = null;
			atributoOperacionalDTOsBusca = this.atributoOperacionalSB.buscarPorEstado(false);
			
		return atributoOperacionalDTOsBusca != null 
				? atributoOperacionalDTOsBusca 
						: new ArrayList<AtributoOperacionalDTO>();
	}
	
	public AtributoOperacionalDTO getAtributoSelecionado()
	{
		return super.getAtributoOperacionalSelecionado();
	}
	
	public AtributoOperacionalDTO buscarPeloId()
	{
		AtributoOperacionalDTO atributoOperacionalDTOBusca = null;
		
		if(this.atributoOperacionalDTO.getIdAtributoOperacional() != null)
		{
			atributoOperacionalDTOBusca = this.atributoOperacionalSB.buscarPorId(this.atributoOperacionalDTO.getIdAtributoOperacional());
			if(atributoOperacionalDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return atributoOperacionalDTOBusca;
	}
	
	public AtributoOperacionalDTO getAtributoOperacionalDTO() {
		if(atributoOperacionalDTO == null)
		{
			atributoOperacionalDTO = DTOFactory.getAtributoOperacionalDTO();
		}
		
		return atributoOperacionalDTO;
	}

	public void setAtributoOperacionalDTO(AtributoOperacionalDTO atributoOperacionalDTO) {
		this.atributoOperacionalDTO = atributoOperacionalDTO;
	}
}
