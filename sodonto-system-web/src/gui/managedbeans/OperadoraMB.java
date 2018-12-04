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
import negocio.fachada.local.IOperadoraFachada;
import dto.OperadoraDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class OperadoraMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|OPERADORA-MB|";
	
	@EJB
	private IOperadoraFachada operadoraSB;
	
	private OperadoraDTO operadoraDTO;	
	
	private String nomeOperadoraFiltro;
	
	public void limparMB()
	{
		this.operadoraDTO = null;
	}
	
	public String salvar()
	{
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			this.operadoraDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.operadoraSB.salvar(this.operadoraDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.operadoraDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR OPERADORA", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarOperadora(OperadoraDTO operadoraDTOAlteracao)
	{
		this.operadoraDTO = operadoraDTOAlteracao;
	}
	
	public String alterar()
	{
		try {
			this.operadoraDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.operadoraSB.alterar(this.operadoraDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.operadoraDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR OPERADORA", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerOperadora(OperadoraDTO operadoraDTOAlteracao)
	{
		this.operadoraDTO = operadoraDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.operadoraDTO.getIdOperadora() != null)
		{
			try {
				this.operadoraSB.inativar(this.operadoraDTO.getIdOperadora(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.operadoraDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<OperadoraDTO> getOperadorasFiltrados()
	{

		if(!getNomeOperadoraFiltro().equals(""))
		{
			setNomeOperadoraFiltro(!getNomeOperadoraFiltro().equals("") ? "%" + this.nomeOperadoraFiltro + "%" : "");
			return buscarOperadorasPeloNome();
		}
		else
		{
			return buscarOperadoras();
		}
		
	}
	
	public List<OperadoraDTO> buscarOperadoras()
	{
		List<OperadoraDTO>  operadoraDTOsBusca = null;
			operadoraDTOsBusca = this.operadoraSB.buscarAtivos();
			
		return operadoraDTOsBusca != null 
				? operadoraDTOsBusca 
						: new ArrayList<OperadoraDTO>();
	}
	
	public List<OperadoraDTO> buscarOperadorasPeloNome()
	{
		List<OperadoraDTO>  operadoraDTOsBusca = null;
		
		if(!this.getNomeOperadoraFiltro().equals(""))
		{
			operadoraDTOsBusca = this.operadoraSB.buscarPorNome(this.nomeOperadoraFiltro);

		}
		return operadoraDTOsBusca != null 
				? operadoraDTOsBusca 
						: new ArrayList<OperadoraDTO>();
	}
	
	public OperadoraDTO buscarPeloId()
	{
		OperadoraDTO operadoraDTOBusca = null;
		
		if(this.operadoraDTO.getIdOperadora() != null)
		{
			operadoraDTOBusca = this.operadoraSB.buscarPorId(this.operadoraDTO.getIdOperadora());
			if(operadoraDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return operadoraDTOBusca;
	}
	
	public OperadoraDTO getOperadoraDTO() {
		if(operadoraDTO == null)
		{
			operadoraDTO = DTOFactory.getOperadoraDTO();
		}
		
		return operadoraDTO;
	}

	public void setOperadoraDTO(OperadoraDTO operadoraDTO) {
		this.operadoraDTO = operadoraDTO;
	}

	public String getNomeOperadoraFiltro() {
		if(nomeOperadoraFiltro == null)
		{
			this.nomeOperadoraFiltro = "";
		}
		return nomeOperadoraFiltro;
	}

	public void setNomeOperadoraFiltro(String nomeOperadoraFiltro) {
		this.nomeOperadoraFiltro = nomeOperadoraFiltro;
	}
}
