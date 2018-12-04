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
import negocio.fachada.local.IEspecialidadeFachada;
import dto.EspecialidadeDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class EspecialidadeMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|ESPECIALIDADE-MB|";
	
	@EJB
	private IEspecialidadeFachada especialidadeSB;
	
	private EspecialidadeDTO especialidadeDTO;	
	
	private String nomeEspecialidadeFiltro;
	
	public void limparMB()
	{
		this.especialidadeDTO = null;
	}
	
	public String salvar()
	{
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			this.especialidadeDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.especialidadeSB.salvar(this.especialidadeDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.especialidadeDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR ESPECIALIDADE", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarEspecialidade(EspecialidadeDTO especialidadeDTOAlteracao)
	{
		this.especialidadeDTO = especialidadeDTOAlteracao;
	}
	
	public String alterar()
	{
		try {
			this.especialidadeDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.especialidadeSB.alterar(this.especialidadeDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.especialidadeDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR ESPECIALIDADE", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerEspecialidade(EspecialidadeDTO especialidadeDTOAlteracao)
	{
		this.especialidadeDTO = especialidadeDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.especialidadeDTO.getIdEspecialidade() != null)
		{
			try {
				this.especialidadeSB.inativar(this.especialidadeDTO.getIdEspecialidade(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.especialidadeDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<EspecialidadeDTO> getEspecialidadesFiltrados()
	{

		if(!getNomeEspecialidadeFiltro().equals(""))
		{
			setNomeEspecialidadeFiltro(!getNomeEspecialidadeFiltro().equals("") ? "%" + this.nomeEspecialidadeFiltro + "%" : "");
			return buscarEspecialidadesPeloNome();
		}
		else
		{
			return buscarEspecialidades();
		}
		
	}
	
	public List<EspecialidadeDTO> buscarEspecialidades()
	{
		List<EspecialidadeDTO>  especialidadeDTOsBusca = null;
			especialidadeDTOsBusca = this.especialidadeSB.buscarAtivos();
			
		return especialidadeDTOsBusca != null 
				? especialidadeDTOsBusca 
						: new ArrayList<EspecialidadeDTO>();
	}
	
	public List<EspecialidadeDTO> buscarEspecialidadesPeloNome()
	{
		List<EspecialidadeDTO>  especialidadeDTOsBusca = null;
		
		if(!this.getNomeEspecialidadeFiltro().equals(""))
		{
			especialidadeDTOsBusca = this.especialidadeSB.buscarPorNome(this.nomeEspecialidadeFiltro);

		}
		return especialidadeDTOsBusca != null 
				? especialidadeDTOsBusca 
						: new ArrayList<EspecialidadeDTO>();
	}
	
	public EspecialidadeDTO buscarPeloId()
	{
		EspecialidadeDTO especialidadeDTOBusca = null;
		
		if(this.especialidadeDTO.getIdEspecialidade() != null)
		{
			especialidadeDTOBusca = this.especialidadeSB.buscarPorId(this.especialidadeDTO.getIdEspecialidade());
			if(especialidadeDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return especialidadeDTOBusca;
	}
	
	public EspecialidadeDTO getEspecialidadeDTO() {
		if(especialidadeDTO == null)
		{
			especialidadeDTO = DTOFactory.getEspecialidadeDTO();
		}
		
		return especialidadeDTO;
	}

	public void setEspecialidadeDTO(EspecialidadeDTO especialidadeDTO) {
		this.especialidadeDTO = especialidadeDTO;
	}

	public String getNomeEspecialidadeFiltro() {
		if(nomeEspecialidadeFiltro == null)
		{
			this.nomeEspecialidadeFiltro = "";
		}
		return nomeEspecialidadeFiltro;
	}

	public void setNomeEspecialidadeFiltro(String nomeEspecialidadeFiltro) {
		this.nomeEspecialidadeFiltro = nomeEspecialidadeFiltro;
	}
}
