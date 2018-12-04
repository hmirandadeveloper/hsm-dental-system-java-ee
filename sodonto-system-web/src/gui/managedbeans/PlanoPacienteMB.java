package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PostLoad;

import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IPlanoPacienteFachada;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import dao.util.Filtro;
import dto.PacienteDTO;
import dto.PlanoDTO;
import dto.PlanoPacienteDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class PlanoPacienteMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|PLANO DE PACIENTE-MB|";
	
	@EJB
	private IPlanoPacienteFachada planoPacienteSB;
	
	private PlanoPacienteDTO planoPacienteDTO;	
	
	private PacienteDTO pacienteDTO;
	private PlanoDTO planoDTO;
	
	//2.2.00
	private String pacienteNomeFiltro;
	//
	
	private LazyDataModel<PlanoPacienteDTO> model;
	private LazyDataModel<PlanoPacienteDTO> modelComFiltro;
	private DataTable dataTable;
	private List<PlanoPacienteDTO> planoPacienteDTOsBusca;
	
	public PlanoPacienteMB()
	{
		this.model = new LazyDataModel<PlanoPacienteDTO>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public List<PlanoPacienteDTO> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, String> filters) {
				
				Filtro filtro = new Filtro();
				
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				
				setRowCount(planoPacienteSB.buscarTotalPorPlano(getPlanoDTO().getIdPlano()));
				
				return planoPacienteSB.buscarPorPlano(getPlanoDTO().getIdPlano(), filtro);
			}
			
		};
		
		//2.2.00
		this.modelComFiltro = new LazyDataModel<PlanoPacienteDTO>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public List<PlanoPacienteDTO> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, String> filters) {
				
				Filtro filtro = new Filtro();
				
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				
				setPacienteNomeFiltro(!getPacienteNomeFiltro().equals("") ? "%" + getPacienteNomeFiltro() + "%" : "");
				
				setRowCount(planoPacienteSB.buscarTotalPorPlanoEPacienteNome(getPlanoDTO().getIdPlano(), getPacienteNomeFiltro()));
				
				return planoPacienteSB.buscarPorPlanoEPacienteNome(getPlanoDTO().getIdPlano(), getPacienteNomeFiltro(),filtro);
			}
			
		};		
		//
	}
	
	public void limparMB()
	{
		this.planoPacienteDTO = null;
		this.planoPacienteDTOsBusca = null;
	}
	
	public int getCurrentPage()
	{	
		return this.dataTable != null ? this.dataTable.getPage() : 0;
	}
	
	public void selecionarPlano(PlanoDTO planoDTOSelecionado)
	{
		this.planoDTO = planoDTOSelecionado;
	}
	
	public void removerSelecaoPlano()
	{
		this.planoDTO = null;
		this.planoPacienteDTOsBusca = null;
	}
	
	public void selecionarPaciente(PacienteDTO pacienteDTOSelecionado)
	{
		this.pacienteDTO = pacienteDTOSelecionado;
	}
	
	public void removerSelecaoPaciente()
	{
		this.pacienteDTO = null;
	}
	
	public void vincularPacienteAoPlano(PacienteDTO pacienteDTOSelecionado)
	{		
		this.pacienteDTO = pacienteDTOSelecionado;
		
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"VÍNCULO DE PACIENTE A PLANO", 
				"VINCULOU O PACIENTE: "
				+ this.pacienteDTO.getNomeCompleto()
				+ " AO PLANO: "
				+ getPlanoDTO().getNomePlano()
				);
/*
 * FIM DO REGISTRO DE LOG
*/		
		
		salvar();
	}
	
	public String salvar()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return "";
		}
		
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			getPlanoPacienteDTO().setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			getPlanoPacienteDTO().setPlano(getPlanoDTO());
			getPlanoPacienteDTO().setPaciente(getPacienteDTO());
			getPlanoPacienteDTO().setDataAssinatura(new Date());
			System.out.println("Plano: " + this.planoPacienteDTO.getPlano());
			System.out.println("Paciente: " + this.planoPacienteDTO.getPaciente());
			System.out.println("Usuário: " + this.planoPacienteDTO.getUsuario());
			this.planoPacienteSB.salvar(this.planoPacienteDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.planoPacienteDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR PLANO DE PACIENTE", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarPlanoPaciente(PlanoPacienteDTO planoPacienteDTOAlteracao)
	{
		this.planoPacienteDTO = planoPacienteDTOAlteracao;
	}
	
	public String alterar()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return "";
		}
		
		try {
			this.planoPacienteDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.planoPacienteDTO.setPlano(getPlanoDTO());
			this.planoPacienteDTO.setPaciente(getPacienteDTO());
			this.planoPacienteSB.alterar(this.planoPacienteDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.planoPacienteDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR PLANO DE PACIENTE", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerPlanoPaciente(PlanoPacienteDTO planoPacienteDTOAlteracao)
	{
		this.planoPacienteDTO = planoPacienteDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.planoPacienteDTO.getIdPlanoPaciente() != null)
		{
			try {
				this.planoPacienteSB.inativar(this.planoPacienteDTO.getIdPlanoPaciente(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.planoPacienteDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<PlanoPacienteDTO> getPlanoPacientesFiltrados()
	{

		if(getPacienteDTO().getIdPaciente() != null)
		{
			return buscarPorPaciente();
		}
		else if(getPlanoDTO().getIdPlano() != null)
		{
			return buscarPorPlano();
		}
		else
		{
			return buscarPlanoPacientes();
		}
		
	}
	
	public List<PlanoPacienteDTO> buscarPlanoPacientes()
	{
		if(planoPacienteDTOsBusca != null)
		{
			return this.planoPacienteDTOsBusca;
		}
		
		planoPacienteDTOsBusca = this.planoPacienteSB.buscarAtivos();
			
		return planoPacienteDTOsBusca != null 
				? planoPacienteDTOsBusca 
						: new ArrayList<PlanoPacienteDTO>();
	}
	
	public List<PlanoPacienteDTO> buscarPorPaciente(PacienteDTO pacienteDTO)
	{
		if(planoPacienteDTOsBusca != null)
		{
			return this.planoPacienteDTOsBusca;
		}
		
		if(pacienteDTO != null)
		{
			if(pacienteDTO.getIdPaciente() != null)
			{
				planoPacienteDTOsBusca = this.planoPacienteSB.buscarPorPaciente(pacienteDTO.getIdPaciente());
			}
		}
		return planoPacienteDTOsBusca != null 
				? planoPacienteDTOsBusca 
						: new ArrayList<PlanoPacienteDTO>();
	}
	
	public List<PlanoPacienteDTO> buscarPorPaciente()
	{
		if(planoPacienteDTOsBusca != null)
		{
			return this.planoPacienteDTOsBusca;
		}
		
		if(getPacienteDTO().getIdPaciente() != null)
		{
			planoPacienteDTOsBusca = this.planoPacienteSB.buscarPorPaciente(getPacienteDTO().getIdPaciente());
		}
		
		return planoPacienteDTOsBusca != null 
				? planoPacienteDTOsBusca 
						: new ArrayList<PlanoPacienteDTO>();
	}
	
	@PostLoad
	public LazyDataModel<PlanoPacienteDTO> getPorPlano()
	{
		return model;
	}
	
	@PostLoad
	public LazyDataModel<PlanoPacienteDTO> getPorPlanoEPacienteNome()
	{
		if(!this.pacienteNomeFiltro.equals(""))
		{
			return modelComFiltro;
		}
		
		return model;
	}
	
	public List<PlanoPacienteDTO> buscarPorPlano()
	{
		if(planoPacienteDTOsBusca != null)
		{
			return this.planoPacienteDTOsBusca;
		}
		
		if(getPlanoDTO().getIdPlano() != null)
		{
			planoPacienteDTOsBusca = this.planoPacienteSB.buscarPorPlano(getPlanoDTO().getIdPlano());
		}
			
		return planoPacienteDTOsBusca != null 
				? planoPacienteDTOsBusca 
						: new ArrayList<PlanoPacienteDTO>();
	}
	
	public PlanoPacienteDTO buscarPeloId()
	{
		PlanoPacienteDTO planoPacienteDTOBusca = null;
		
		if(this.planoPacienteDTO.getIdPlanoPaciente() != null)
		{
			planoPacienteDTOBusca = this.planoPacienteSB.buscarPorId(this.planoPacienteDTO.getIdPlanoPaciente());
			if(planoPacienteDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return planoPacienteDTOBusca;
	}
	
	public PlanoPacienteDTO getPlanoPacienteDTO() {
		if(planoPacienteDTO == null)
		{
			planoPacienteDTO = DTOFactory.getPlanoPacienteDTO();
		}
		
		return planoPacienteDTO;
	}

	public void setPlanoPacienteDTO(PlanoPacienteDTO planoPacienteDTO) {
		this.planoPacienteDTO = planoPacienteDTO;
	}

	public PacienteDTO getPacienteDTO() {
		if(pacienteDTO == null)
		{
			pacienteDTO = DTOFactory.getPacienteDTO();
		}
		
		return pacienteDTO;
	}

	public void setPacienteDTO(PacienteDTO pacienteDTO) {
		this.pacienteDTO = pacienteDTO;
	}

	public PlanoDTO getPlanoDTO() {
		if(planoDTO == null)
		{
			planoDTO = DTOFactory.getPlanoDTO();
		}
		
		return planoDTO;
	}

	public void setPlanoDTO(PlanoDTO planoDTO) {
		this.planoDTO = planoDTO;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
	
	public String getPacienteNomeFiltro() {
		if(this.pacienteNomeFiltro == null)
		{
			this.pacienteNomeFiltro = "";
		}
		
		return pacienteNomeFiltro;
	}

	public void setPacienteNomeFiltro(String pacienteNomeFiltro) {
		this.pacienteNomeFiltro = pacienteNomeFiltro;
	}	
}
