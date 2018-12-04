package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.enums.ESituacaoMensalidadePaciente;
import negocio.constante.enums.ETipoObsPaciente;
import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IMensalidadePacienteFachada;
import negocio.fachada.local.IObsPacienteFachada;
import negocio.fachada.local.IPlanoMensalidadeFachada;
import negocio.util.DataUtil;
import dto.MensalidadePacienteDTO;
import dto.ObsPacienteDTO;
import dto.PacienteDTO;
import dto.PlanoDTO;
import dto.PlanoMensalidadeDTO;
import dto.PlanoPacienteDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class MensalidadePacienteMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|MENSALIDADE DE PACIENTE-MB|";
	
	@EJB
	private IMensalidadePacienteFachada mensalidadePacienteSB;
	@EJB
	private IPlanoMensalidadeFachada planoMensalidadeSB;
	@EJB
	private IObsPacienteFachada obsPacienteSB;
	
	private MensalidadePacienteDTO mensalidadePacienteDTO;	
	
	private PlanoPacienteDTO planoPacienteDTO;
	private List<PlanoMensalidadeDTO> planoMensalidadesDTO;
	private ObsPacienteDTO obsPacienteDTO;
	
	public void limparMB()
	{
		this.mensalidadePacienteDTO = null;
	}
	
	public void salvarObservacaoAutomatica(PacienteDTO pacienteDTO, ETipoObsPaciente tipoObsPaciente, String obs, boolean enviarMensagem)
	{
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			getObsPacienteDTO().setUsuarioDTO(getFuncionarioLogado().getUsuarioPerfil());
			getObsPacienteDTO().setPacienteDTO(pacienteDTO);
			getObsPacienteDTO().setData(new Date());
			getObsPacienteDTO().setTipo(tipoObsPaciente);
			getObsPacienteDTO().setObs(obs);
			this.obsPacienteSB.salvar(this.obsPacienteDTO);
			if(enviarMensagem)
			enviarMenssagemInformativa("Observação incluída com sucesso!");
			this.obsPacienteDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR AUTO OBSERVAÇÃO DE PACIENTE", e);
			
			e.printStackTrace();
		}
	}
	
	public void validarMensalidades(PlanoPacienteDTO planoPacienteDTO)
	{
		List<MensalidadePacienteDTO> mensalidadesDoPaciente = buscarPorPlanoEPaciente(planoPacienteDTO.getPlano(), planoPacienteDTO.getPaciente());
		
		if(mensalidadesDoPaciente.size() > 0)
		{
			for(MensalidadePacienteDTO mp : mensalidadesDoPaciente)
			{
				if(DataUtil.menorQueMesAtual(mp.getDataMensalidade()) && mp.getSituacao() == ESituacaoMensalidadePaciente.E)
				{
					mp.setSituacao(ESituacaoMensalidadePaciente.D);
					alterar(mp, null);
				}
			}
			
			enviarMenssagemInformativa("Mensalidades VALIDADADAS com Sucesso!");
		}
	}
	
	public void selecionarPlanoPaciente(PlanoPacienteDTO planoPacienteDTO)
	{
		this.planoPacienteDTO = planoPacienteDTO;
	}
	
	public void removerSelecaoPlanoPaciente()
	{
		this.planoPacienteDTO = null;
	}
	
	public void abonarMensalidade(MensalidadePacienteDTO mensalidadePacienteDTO)
	{
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"ABONO DE MENSALIDADE", 
				"ABONOU A MENSALIDADE REFERENTE A DATA: "
				+ DataUtil.getDataFormatada(mensalidadePacienteDTO.getDataMensalidade())
				+ ", DO PACIENTE: "
				+ mensalidadePacienteDTO.getPlanoPaciente().getPaciente().getNomeCompleto()
				+ ", NO PLANO:"
				+ mensalidadePacienteDTO.getPlanoMensalidade().getPlano().getNomePlano()
				);
/*
 * FIM DO REGISTRO DE LOG
*/		
		
		mensalidadePacienteDTO.setSituacao(ESituacaoMensalidadePaciente.A);
		alterar(mensalidadePacienteDTO, MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
	}
	
	public void pagarMensalidade(MensalidadePacienteDTO mensalidadePacienteDTO)
	{
		String obs = "";
		if(DataUtil.menorQueMesAtual(mensalidadePacienteDTO.getDataMensalidade()))
		{
			mensalidadePacienteDTO.setSituacao(ESituacaoMensalidadePaciente.T);
			obs = "O PACIENTE pagou a mensalidade referente a DATA: " 
			+ DataUtil.getDataFormatada(mensalidadePacienteDTO.getDataMensalidade())
			+ "ATRASADA. O pagamento foi realizado na DATA: "
			+ DataUtil.getDataFormatada(DataUtil.getDataAtual())
			+ ".";
			
			salvarObservacaoAutomatica(
					mensalidadePacienteDTO
					.getPlanoPaciente()
					.getPaciente(), 
					ETipoObsPaciente.X, 
					obs, 
					true);
		}
		else
		{
			mensalidadePacienteDTO.setSituacao(ESituacaoMensalidadePaciente.P);
		}
		mensalidadePacienteDTO.setValorPago(mensalidadePacienteDTO.getPlanoMensalidade().getValorReajustado() > 0 ? mensalidadePacienteDTO.getPlanoMensalidade().getValorReajustado() : mensalidadePacienteDTO.getPlanoMensalidade().getValorMes());
		alterar(mensalidadePacienteDTO, MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
	}
	
	public List<MensalidadePacienteDTO> carregarMensalidadesPaciente()
	{
		System.out.println("[SODONTO SYSTEM][2.0.00][MB] Carregando Mensalidades do paciente...");
		List<MensalidadePacienteDTO> mensalidadesPaciente = null;
		
		if(getPlanoPacienteDTO().getPlano().getIdPlano() == null)
		{
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Nenhum Plano vinculado...");
			return new ArrayList<MensalidadePacienteDTO>();
		}
		else
		{
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Plano Vinculado...");
			List<PlanoMensalidadeDTO> planoMensalidadesDTO = this.planoMensalidadeSB.buscarPorPlano(getPlanoPacienteDTO().getPlano().getIdPlano());
			mensalidadesPaciente = this.buscarPorPlanoEPaciente();
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Checando Mensalidades...");
			if(mensalidadesPaciente.size() <= 0 && planoMensalidadesDTO.size() > 0)
			{
				int mesAtual = Integer.valueOf(DataUtil.getMesDaData(new Date()));
				int anoAtual = Integer.valueOf(DataUtil.getAnoDaData(new Date()));
				int mesContador = this.planoMensalidadeSB.buscarPorPlano(getPlanoPacienteDTO().getPlano().getIdPlano()).size();
				int anoContador = anoAtual;
				String dataMensalidade = "";
				
				System.out.println("[SODONTO SYSTEM][2.0.00][MB] O Paciente não possui mensalidades...");
				System.out.println("[SODONTO SYSTEM][2.0.00][MB] Criando Mensalidades...");
				if(mesContador > 0)
				for(PlanoMensalidadeDTO pm : planoMensalidadesDTO)
				{
					System.out.println("[SODONTO SYSTEM][2.0.00][MB] Salvando Mensalidade criada...");
					
					dataMensalidade = "01/" + (mesAtual < 10 ? "0" : "") + mesAtual + "/" + anoContador;
					
					if(mesAtual == 12)
					{
						mesAtual = 1;
						anoContador++;
					}
					else
					{
						mesAtual++;
					}
					mesContador--;
					
					getMensalidadePacienteDTO().setDataMensalidade(DataUtil.getDataPorString(dataMensalidade));
					
					salvar(pm);
					System.out.println("[SODONTO SYSTEM][2.0.00][MB] Salva com sucesso!!!");
				}
				
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"ATIVAÇÃO DE MENSALIDADE", 
				"ATIVOU AS MENSALIDADES DO PACIENTE: "
				+ getPlanoPacienteDTO().getPaciente().getNomeCompleto()
				+ ", PARA O PLANO:"
				+ getPlanoPacienteDTO().getPlano().getNomePlano()
				);
/*
 * FIM DO REGISTRO DE LOG
*/					
				
				enviarMenssagemInformativa("Mensalidades do Paciente carregadas com sucesso!");
			}
			else
			{
				System.out.println("[SODONTO SYSTEM][2.0.00][MB] O Paciente já possui mensalidades...");
			}
			mensalidadesPaciente = this.buscarPorPlanoEPaciente();
		}
		return mensalidadesPaciente;
	}
	
	public String salvar(PlanoMensalidadeDTO planoMensalidadeDTO)
	{
		try {
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Iniciando Camada de Visualização...");
			getMensalidadePacienteDTO().setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			getMensalidadePacienteDTO().setPlanoPaciente(getPlanoPacienteDTO());
			getMensalidadePacienteDTO().setPlanoMensalidade(planoMensalidadeDTO);
			getMensalidadePacienteDTO().setSituacao(ESituacaoMensalidadePaciente.E);
			this.mensalidadePacienteSB.salvar(this.mensalidadePacienteDTO);
			this.mensalidadePacienteDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR MENSALIDADE DE PACIENTE", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String alterar(MensalidadePacienteDTO mensalidadePacienteDTO, String msg)
	{
		try {
			mensalidadePacienteDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.mensalidadePacienteSB.alterar(mensalidadePacienteDTO);
			if(msg != null)
			enviarMenssagemInformativa(msg);
			mensalidadePacienteDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR MENSALIDADE DE PACIENTE", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerMensalidadePaciente(MensalidadePacienteDTO mensalidadePacienteDTOAlteracao)
	{
		this.mensalidadePacienteDTO = mensalidadePacienteDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.mensalidadePacienteDTO.getIdMensalidadePaciente() != null)
		{
			try {
				this.mensalidadePacienteSB.inativar(this.mensalidadePacienteDTO.getIdMensalidadePaciente(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.mensalidadePacienteDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<MensalidadePacienteDTO> getMensalidadePacientesFiltrados()
	{

		if(getPlanoPacienteDTO().getPaciente().getIdPaciente() != null && getPlanoPacienteDTO().getPlano().getIdPlano() != null)
		{
			return buscarPorPlanoEPaciente();
		}
		else
		{
			return buscarMensalidadePacientes();
		}
		
	}
	
	public List<MensalidadePacienteDTO> buscarMensalidadePacientes()
	{
		List<MensalidadePacienteDTO>  mensalidadePacienteDTOsBusca = null;
			mensalidadePacienteDTOsBusca = this.mensalidadePacienteSB.buscarAtivos();
			
		return mensalidadePacienteDTOsBusca != null 
				? mensalidadePacienteDTOsBusca 
						: new ArrayList<MensalidadePacienteDTO>();
	}
	
	public int buscarTotalPorPlanoEPaciente(PlanoDTO planoDTO, PacienteDTO pacienteDTO)
	{
		int  totalMensalidadePacienteDTOsBusca = 0;
		if(pacienteDTO != null)
		{
			if(pacienteDTO.getIdPaciente() != null && planoDTO.getIdPlano() != null)
			{
				totalMensalidadePacienteDTOsBusca = this.mensalidadePacienteSB.buscarTotalPorPlanoEPaciente(planoDTO.getIdPlano(), pacienteDTO.getIdPaciente());
			}
		}
		return totalMensalidadePacienteDTOsBusca;
	}
	
	public List<MensalidadePacienteDTO> buscarPorPlanoEPaciente(PlanoDTO planoDTO, PacienteDTO pacienteDTO)
	{
		List<MensalidadePacienteDTO>  mensalidadePacienteDTOsBusca = null;
		if(pacienteDTO != null)
		{
			if(pacienteDTO.getIdPaciente() != null && planoDTO.getIdPlano() != null)
			{
				mensalidadePacienteDTOsBusca = this.mensalidadePacienteSB.buscarPorPlanoEPaciente(planoDTO.getIdPlano(), pacienteDTO.getIdPaciente());
			}
		}
		return mensalidadePacienteDTOsBusca != null 
				? mensalidadePacienteDTOsBusca 
						: new ArrayList<MensalidadePacienteDTO>();
	}
	
	public List<MensalidadePacienteDTO> buscarPorPlanoEPaciente()
	{
		List<MensalidadePacienteDTO>  mensalidadePacienteDTOsBusca = null;
		if(getPlanoPacienteDTO().getPaciente().getIdPaciente() != null && getPlanoPacienteDTO().getPlano().getIdPlano() != null)
		{
			mensalidadePacienteDTOsBusca = this.mensalidadePacienteSB.buscarPorPlanoEPaciente(getPlanoPacienteDTO().getPlano().getIdPlano(), getPlanoPacienteDTO().getPaciente().getIdPaciente());
		}
		
		return mensalidadePacienteDTOsBusca != null 
				? mensalidadePacienteDTOsBusca 
						: new ArrayList<MensalidadePacienteDTO>();
	}
	
	public List<MensalidadePacienteDTO> buscarPorPlanoPacienteEMes(PlanoDTO planoDTO, PacienteDTO pacienteDTO, int mes)
	{
		List<MensalidadePacienteDTO>  mensalidadePacienteDTOsBusca = null;
		if(pacienteDTO != null)
		{
			if(pacienteDTO.getIdPaciente() != null && planoDTO.getIdPlano() != null && mes > 0)
			{
				mensalidadePacienteDTOsBusca = this.mensalidadePacienteSB.buscarPorPlanoPacienteEMes(planoDTO.getIdPlano(), pacienteDTO.getIdPaciente(), mes);
			}
		}
		return mensalidadePacienteDTOsBusca != null 
				? mensalidadePacienteDTOsBusca 
						: new ArrayList<MensalidadePacienteDTO>();
	}
	
	public List<MensalidadePacienteDTO> buscarPorSituacaoPlanoEPaciente(String situacao, PlanoDTO planoDTO, PacienteDTO pacienteDTO, int mes)
	{
		List<MensalidadePacienteDTO>  mensalidadePacienteDTOsBusca = null;
		if(pacienteDTO != null)
		{
			if(pacienteDTO.getIdPaciente() != null && planoDTO.getIdPlano() != null && !situacao.equals(""))
			{
				mensalidadePacienteDTOsBusca = this.mensalidadePacienteSB.buscarPorSituacaoPlanoEPaciente(situacao, planoDTO.getIdPlano(), pacienteDTO.getIdPaciente());
			}
		}
		return mensalidadePacienteDTOsBusca != null 
				? mensalidadePacienteDTOsBusca 
						: new ArrayList<MensalidadePacienteDTO>();
	}
	
	public MensalidadePacienteDTO buscarPeloId()
	{
		MensalidadePacienteDTO mensalidadePacienteDTOBusca = null;
		
		if(this.mensalidadePacienteDTO.getIdMensalidadePaciente() != null)
		{
			mensalidadePacienteDTOBusca = this.mensalidadePacienteSB.buscarPorId(this.mensalidadePacienteDTO.getIdMensalidadePaciente());
			if(mensalidadePacienteDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return mensalidadePacienteDTOBusca;
	}
	
	public MensalidadePacienteDTO getMensalidadePacienteDTO() {
		if(mensalidadePacienteDTO == null)
		{
			mensalidadePacienteDTO = DTOFactory.getMensalidadePacienteDTO();
		}
		
		return mensalidadePacienteDTO;
	}

	public void setMensalidadePacienteDTO(MensalidadePacienteDTO mensalidadePacienteDTO) {
		this.mensalidadePacienteDTO = mensalidadePacienteDTO;
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

	public List<PlanoMensalidadeDTO> getPlanoMensalidadeDTO() {
		if(planoMensalidadesDTO == null)
		{
			planoMensalidadesDTO = new ArrayList<PlanoMensalidadeDTO>();
		}
		
		return planoMensalidadesDTO;
	}

	public void setPlanoMensalidadeDTO(List<PlanoMensalidadeDTO> planoMensalidadesDTO) {
		this.planoMensalidadesDTO = planoMensalidadesDTO;
	}
	
	public ObsPacienteDTO getObsPacienteDTO() {
		if(obsPacienteDTO == null)
		{
			obsPacienteDTO = DTOFactory.getObsPacienteDTO();
		}
		
		return this.obsPacienteDTO;
	}

	public void setObsPacienteDTO(ObsPacienteDTO obsPacienteDTO) {
		this.obsPacienteDTO = obsPacienteDTO;
	}
}
