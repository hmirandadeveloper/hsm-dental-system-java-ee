package gui.managedbeans;

import gui.managedbeans.atributo.ConstantesControleSodontoSystem;
import gui.managedbeans.atributo.ConstantesSodontoSystem;
import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.enums.EConstanteMensagem;
import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IMsgPreTorpedoFachada;
import dto.MsgPreTorpedoDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class MsgPreTorpedoMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|MSG TORPEDO-MB|";
	
	@EJB
	private IMsgPreTorpedoFachada msgPreTorpedoSB;
	
	private MsgPreTorpedoDTO msgPreTorpedoDTO;	
	
	private String nomeMsgPreTorpedoFiltro;
	
	public void limparMB()
	{
		this.msgPreTorpedoDTO = null;
	}
	
	public int getLimiteMensagem()
	{
		int tamanhoMsg = ConstantesControleSodontoSystem.SISTEMA_SMS_LIMITE;
		if(getMsgPreTorpedoDTO().getMsg() != null)
		{
			if(getMsgPreTorpedoDTO().getMsg().contains(EConstanteMensagem.NOME.getConstanteMensagem()))
			{
				tamanhoMsg -= 20;
			}
			
			if(getMsgPreTorpedoDTO().getMsg().contains(EConstanteMensagem.DATA.getConstanteMensagem()))
			{
				tamanhoMsg -= 10;
			}
			
			if(getMsgPreTorpedoDTO().getMsg().contains(EConstanteMensagem.HORARIO.getConstanteMensagem()))
			{
				tamanhoMsg -= 18;
			}
		}
		
		return tamanhoMsg;
	}
	
	public String salvar()
	{
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			this.msgPreTorpedoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.msgPreTorpedoSB.salvar(this.msgPreTorpedoDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.msgPreTorpedoDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR MSG TORPEDO", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarMsgPreTorpedo(MsgPreTorpedoDTO msgPreTorpedoDTOAlteracao)
	{
		this.msgPreTorpedoDTO = msgPreTorpedoDTOAlteracao;
	}
	
	public String alterar()
	{
		try {
			this.msgPreTorpedoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.msgPreTorpedoSB.alterar(this.msgPreTorpedoDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.msgPreTorpedoDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR MSG TORPEDO", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerMsgPreTorpedo(MsgPreTorpedoDTO msgPreTorpedoDTOAlteracao)
	{
		this.msgPreTorpedoDTO = msgPreTorpedoDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.msgPreTorpedoDTO.getIdMsgPreTorpedo() != null)
		{
			try {
				this.msgPreTorpedoSB.inativar(this.msgPreTorpedoDTO.getIdMsgPreTorpedo(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.msgPreTorpedoDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<MsgPreTorpedoDTO> getMsgPreTorpedosFiltrados()
	{

		if(!getNomeMsgPreTorpedoFiltro().equals(""))
		{
			setNomeMsgPreTorpedoFiltro(!getNomeMsgPreTorpedoFiltro().equals("") ? "%" + this.nomeMsgPreTorpedoFiltro + "%" : "");
			return buscarMsgPreTorpedosPeloNome();
		}
		else
		{
			return buscarMsgPreTorpedos();
		}
		
	}
	
	public List<MsgPreTorpedoDTO> buscarMsgPreTorpedos()
	{
		List<MsgPreTorpedoDTO>  msgPreTorpedoDTOsBusca = null;
			msgPreTorpedoDTOsBusca = this.msgPreTorpedoSB.buscarAtivos();
			
		return msgPreTorpedoDTOsBusca != null 
				? msgPreTorpedoDTOsBusca 
						: new ArrayList<MsgPreTorpedoDTO>();
	}
	
	public List<MsgPreTorpedoDTO> buscarMsgPreTorpedosPeloNome()
	{
		List<MsgPreTorpedoDTO>  msgPreTorpedoDTOsBusca = null;
		
		if(!this.getNomeMsgPreTorpedoFiltro().equals(""))
		{
			msgPreTorpedoDTOsBusca = this.msgPreTorpedoSB.buscarPorDescricao(this.nomeMsgPreTorpedoFiltro);

		}
		return msgPreTorpedoDTOsBusca != null 
				? msgPreTorpedoDTOsBusca 
						: new ArrayList<MsgPreTorpedoDTO>();
	}
	
	public MsgPreTorpedoDTO buscarPeloId()
	{
		MsgPreTorpedoDTO msgPreTorpedoDTOBusca = null;
		
		if(this.msgPreTorpedoDTO.getIdMsgPreTorpedo() != null)
		{
			msgPreTorpedoDTOBusca = this.msgPreTorpedoSB.buscarPorId(this.msgPreTorpedoDTO.getIdMsgPreTorpedo());
			if(msgPreTorpedoDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return msgPreTorpedoDTOBusca;
	}
	
	public MsgPreTorpedoDTO getMsgPreTorpedoDTO() {
		
		String header = ConstantesSodontoSystem.SISTEMA_SMS_HEADER;
		
		if(msgPreTorpedoDTO == null)
		{
			msgPreTorpedoDTO = DTOFactory.getMsgPreTorpedoDTO(header);
		}
		
		if(!this.msgPreTorpedoDTO.getMsg().contains(header))
		{
			this.msgPreTorpedoDTO.setMsg(header);
		}
		
		return msgPreTorpedoDTO;
	}

	public void setMsgPreTorpedoDTO(MsgPreTorpedoDTO msgPreTorpedoDTO) {
		
		String header = ConstantesSodontoSystem.SISTEMA_SMS_HEADER;
		
		this.msgPreTorpedoDTO = msgPreTorpedoDTO;
		
		if(!this.msgPreTorpedoDTO.getMsg().contains(header))
		{
			this.msgPreTorpedoDTO.setMsg(header);
		}
	}

	public String getNomeMsgPreTorpedoFiltro() {
		if(nomeMsgPreTorpedoFiltro == null)
		{
			this.nomeMsgPreTorpedoFiltro = "";
		}
		return nomeMsgPreTorpedoFiltro;
	}

	public void setNomeMsgPreTorpedoFiltro(String nomeMsgPreTorpedoFiltro) {
		this.nomeMsgPreTorpedoFiltro = nomeMsgPreTorpedoFiltro;
	}
}
