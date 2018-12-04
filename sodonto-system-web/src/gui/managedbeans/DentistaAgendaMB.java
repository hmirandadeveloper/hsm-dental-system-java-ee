package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.DataInvalidaException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IDentistaAgendaFachada;
import negocio.util.DataUtil;
import dto.DentistaAgendaDTO;
import dto.DentistaDTO;
import dto.EstabelecimentoDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class DentistaAgendaMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|AGENDA DE DENTISTA-MB|";
	
	@EJB
	private IDentistaAgendaFachada dentistaAgendaSB;
	
	private DentistaAgendaDTO dentistaAgendaDTO;	
	
	private DentistaDTO dentistaSelecionadoDTO;
	
	private Date dataFiltro;
	
	private EstabelecimentoDTO estabelecimentoFiltroDTO;
		
	public void limparMB()
	{
		this.dentistaAgendaDTO = null;
		this.dataFiltro = null;
		this.estabelecimentoFiltroDTO = null;
	}
	
	public void selecionarDentista(DentistaDTO dentistaSelecionadoDTO)
	{
		this.dentistaSelecionadoDTO = dentistaSelecionadoDTO;
	}
	
	public void removerSelecaoDentista()
	{
		this.dentistaSelecionadoDTO = null;
	}
	
	public int getTotalAtendimentos(DentistaAgendaDTO dentistaAgendaSelecionadoDTO)
	{
		int totalAtendimentos = 0;
		
		totalAtendimentos = DataUtil.getDiferencaHorarioEmMinutos(dentistaAgendaSelecionadoDTO.getHorarioI(), 
				dentistaAgendaSelecionadoDTO.getHorarioF()) / getAtributoOperacionalSelecionado().getDuracaoAtendimento();
		
		return totalAtendimentos;
	}
	
	public String salvar()
	{
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			this.dentistaAgendaDTO.setDentista(getDentistaSelecionadoDTO());
			this.dentistaAgendaDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.dentistaAgendaSB.salvar(this.dentistaAgendaDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.dentistaAgendaDTO = null;
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
		} catch (DataInvalidaException e) {
			enviarMenssagemErro(e.getMessage());
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
			reportarErroAoAnalista(MBName + " SALVAR AGENDA DE DENTISTA", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarDentistaAgenda(DentistaAgendaDTO dentistaAgendaDTOAlteracao)
	{
		this.dentistaAgendaDTO = dentistaAgendaDTOAlteracao;
	}
	
	public String alterar()
	{
		try {
			this.dentistaAgendaDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.dentistaAgendaSB.alterar(this.dentistaAgendaDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.dentistaAgendaDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR AGENDA DE DENTISTA", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerDentistaAgenda(DentistaAgendaDTO dentistaAgendaDTOAlteracao)
	{
		this.dentistaAgendaDTO = dentistaAgendaDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.dentistaAgendaDTO.getIdDentistaAgenda() != null)
		{
			try {
				this.dentistaAgendaSB.inativar(this.dentistaAgendaDTO.getIdDentistaAgenda(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_ITEM);
				this.dentistaAgendaDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<DentistaAgendaDTO> getDentistaAgendasFiltrados()
	{

		if(getDataFiltro() != null && getDentistaSelecionadoDTO().getIdDentista() != null)
		{
			getDentistaAgendaDTO().setDataAgenda(this.dataFiltro);
			return buscarPorDentistaEData();
		}
		else if(getDentistaSelecionadoDTO().getIdDentista() != null)
		{
			return buscarPorDentista();
		}
		else if(getDataFiltro() != null)
		{
			getDentistaAgendaDTO().setDataAgenda(this.dataFiltro);
			return buscarPorData();
		}
		else
		{
			return buscarDentistaAgendas();
		}
		
	}
	
	public List<DentistaAgendaDTO> buscarDentistaAgendas()
	{
		List<DentistaAgendaDTO>  dentistaAgendaDTOsBusca = null;
			dentistaAgendaDTOsBusca = this.dentistaAgendaSB.buscarAtivos(getEstabelecimentoFiltroDTO().getIdEstabelecimento());
			
		return dentistaAgendaDTOsBusca != null 
				? dentistaAgendaDTOsBusca 
						: new ArrayList<DentistaAgendaDTO>();
	}
	
	public List<DentistaAgendaDTO> buscarPorData()
	{
		List<DentistaAgendaDTO>  dentistaAgendaDTOsBusca = null;
		
		
		if(getDentistaAgendaDTO().getDataAgenda() != null)
		{
			dentistaAgendaDTOsBusca = this.dentistaAgendaSB.buscarPorData(getDentistaAgendaDTO().getDataAgenda(),
					getDentistaAgendaDTO().getDataAgenda(), getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return dentistaAgendaDTOsBusca != null 
				? dentistaAgendaDTOsBusca 
						: new ArrayList<DentistaAgendaDTO>();
	}
	
	public List<DentistaAgendaDTO> buscarPorDentista()
	{
		List<DentistaAgendaDTO>  dentistaAgendaDTOsBusca = null;
		
		if(getDentistaSelecionadoDTO().getIdDentista() != null)
		{
			dentistaAgendaDTOsBusca = this.dentistaAgendaSB.buscarPorDentista(this.dentistaSelecionadoDTO.getIdDentista(),
					getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return dentistaAgendaDTOsBusca != null 
				? dentistaAgendaDTOsBusca 
						: new ArrayList<DentistaAgendaDTO>();
	}
	
	public List<DentistaAgendaDTO> buscarPorDentistaEData()
	{
		List<DentistaAgendaDTO>  dentistaAgendaDTOsBusca = null;
		
		if(getDentistaSelecionadoDTO().getIdDentista() != null
				&& getDentistaAgendaDTO().getDataAgenda() != null)
		{
			dentistaAgendaDTOsBusca = this.dentistaAgendaSB.buscarPorDentistaEData(this.dentistaSelecionadoDTO.getIdDentista(),
					getDentistaAgendaDTO().getDataAgenda(), getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return dentistaAgendaDTOsBusca != null 
				? dentistaAgendaDTOsBusca 
						: new ArrayList<DentistaAgendaDTO>();
	}
	
	public DentistaAgendaDTO buscarPeloId()
	{
		DentistaAgendaDTO dentistaAgendaDTOBusca = null;
		
		if(this.dentistaAgendaDTO.getIdDentistaAgenda() != null)
		{
			dentistaAgendaDTOBusca = this.dentistaAgendaSB.buscarPorId(this.dentistaAgendaDTO.getIdDentistaAgenda());
			if(dentistaAgendaDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return dentistaAgendaDTOBusca;
	}
	
	public DentistaAgendaDTO getDentistaAgendaDTO() {
		if(dentistaAgendaDTO == null)
		{
			dentistaAgendaDTO = DTOFactory.getDentistaAgendaDTO();
		}
		
		return dentistaAgendaDTO;
	}

	public void setDentistaAgendaDTO(DentistaAgendaDTO dentistaAgendaDTO) {
		this.dentistaAgendaDTO = dentistaAgendaDTO;
	}

	public DentistaDTO getDentistaSelecionadoDTO() {
		if(dentistaSelecionadoDTO == null)
		{
			this.dentistaSelecionadoDTO = DTOFactory.getDentistaDTO();
		}
		return dentistaSelecionadoDTO;
	}

	public void setDentistaSelecionadoDTO(DentistaDTO dentistaSelecionadoDTO) {
		this.dentistaSelecionadoDTO = dentistaSelecionadoDTO;
	}

	public Date getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(Date dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public EstabelecimentoDTO getEstabelecimentoFiltroDTO() {
		if(this.estabelecimentoFiltroDTO == null)
		{
			this.estabelecimentoFiltroDTO = getFuncionarioLogado().getEstabelecimentoDTO();
		}
		
		return estabelecimentoFiltroDTO;
	}

	public void setEstabelecimentoFiltroDTO(EstabelecimentoDTO estabelecimentoFiltroDTO) {
		this.estabelecimentoFiltroDTO = estabelecimentoFiltroDTO;
	}
}
