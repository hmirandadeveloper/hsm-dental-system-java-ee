package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.enums.ESituacaoAgendamento;
import negocio.constante.enums.ESituacaoAtendimento;
import negocio.constante.enums.ETipoObsPaciente;
import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IAtendimentoFachada;
import negocio.fachada.local.IObsPacienteFachada;
import negocio.util.DataUtil;
import dto.AgendamentoDTO;
import dto.AtendimentoDTO;
import dto.EstabelecimentoDTO;
import dto.ObsPacienteDTO;
import dto.PacienteDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class AtendimentoMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|ATENDIMENTO-MB|";
	
	@EJB
	private IAtendimentoFachada atendimentoSB;
	@EJB
	private IObsPacienteFachada obsPacienteSB;
	
	private AtendimentoDTO atendimentoDTO;	
	private ObsPacienteDTO obsPacienteDTO;
	
	private Date dataFiltro;
	private String motivoCancelamento;
	
	private EstabelecimentoDTO estabelecimentoFiltroDTO;
	
	private PacienteDTO pacienteDTO;
		
	public void limparMB()
	{
		this.atendimentoDTO = null;
	}
	
	public void encerrarDia()
	{
		getAtendimentoDTO().getAgendamento().getDentistaAgenda().setDataAgenda(new Date());
		List<AtendimentoDTO> atendimentoDoDia = buscarPorData();
		
		if(atendimentoDoDia.size() > 0)
		{
			for(AtendimentoDTO at : atendimentoDoDia)
			{
				if(at.getSituacao() == ESituacaoAtendimento.A)
				{
					at.setSituacao(ESituacaoAtendimento.F);
					alterar(at, null);
				}
			}
		}
		else
		{
			enviarMenssagemAlerta("Não houve Atendimentos no dia!");
		}
	}
	
	public void selecionarPaciente(PacienteDTO pacienteSelecionadoDTO)
	{
		this.pacienteDTO = pacienteSelecionadoDTO;
	}
	
	public void selecionarPacienteVisualizacao(PacienteDTO pacienteDTOSelecionado)
	{
		getAtendimentoDTO().getAgendamento().setPaciente(pacienteDTOSelecionado);
	}
	
	public void removerSelecaoPacienteVisualizacao()
	{
		getAtendimentoDTO().getAgendamento().setPaciente(null);
	}
	
	public boolean isAtendimentoParaAgendamento(Long idAgendamentoSelecionado)
	{
		return this.atendimentoSB.buscarPorAgendamento(idAgendamentoSelecionado).size() > 0
				? true : false;
	}
	
	//2.2.00
	
	public void incluirAtendimentoFaltoso(AgendamentoDTO agendamentoSelecionadoDTO)
	{
		if(agendamentoSelecionadoDTO.getSituacao() == ESituacaoAgendamento.F)
		{
			this.atendimentoDTO = null;
			getAtendimentoDTO().setAgendamento(agendamentoSelecionadoDTO);
			getAtendimentoDTO().setSituacao(ESituacaoAtendimento.F);
			getAtendimentoDTO().getAgendamento().setSituacao(ESituacaoAgendamento.Z);
			salvar();
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATENDIMENTO_FINALIZADO);
		}
	}	
	
	//
	
	public void iniciarAtendimento(AgendamentoDTO agendamentoSelecionadoDTO)
	{
		if(agendamentoSelecionadoDTO.getSituacao() == ESituacaoAgendamento.N)
		{
			this.atendimentoDTO = null;
			getAtendimentoDTO().setAgendamento(agendamentoSelecionadoDTO);
			getAtendimentoDTO().setSituacao(ESituacaoAtendimento.A);
			getAtendimentoDTO().getAgendamento().setSituacao(ESituacaoAgendamento.T);
			salvar();
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATENDIMENTO_INICIADO);
		}
		else
		{
			enviarMenssagemErro("Favor confirmar o Pagamento da Mensalidade do Paciente: " + agendamentoSelecionadoDTO.getPaciente().getNomeCompleto());
		}
	}
	
	public void cancelarAtendimento()
	{
		if(getAtendimentoDTO().getIdAtendimento() != null && getAtendimentoDTO().getSituacao() == ESituacaoAtendimento.A)
		{
			String infFuncCancelamento = "[ATENDIMENTO (DIA: " + DataUtil.getDataFormatada(this.atendimentoDTO.getAgendamento().getDentistaAgenda().getDataAgenda()) + " / HORÁRIO: " + this.atendimentoDTO.getAgendamento().getDentistaAgenda().getIntervaloTempo() + " / DENTISTA: " + this.atendimentoDTO.getAgendamento().getDentistaAgenda().getDentista().getNomeComCROFormatado() + ") CANCELADO PELO FUNCIONÁRIO: " + getFuncionarioLogado().getNomeCompleto() + "] Motivo: " + (getMotivoCancelamento().equals("") ? "O Funcionário não informou!" : getMotivoCancelamento());
			
			salvarObservacaoAutomatica(getAtendimentoDTO().getAgendamento().getPaciente(), ETipoObsPaciente.T, infFuncCancelamento);
			
			this.atendimentoDTO.setSituacao(ESituacaoAtendimento.C);
			getAtendimentoDTO().getAgendamento().setSituacao(ESituacaoAgendamento.G);
			alterar();
			enviarMenssagemAlerta(MensagemInformativa.MSG_ATENDIMENTO_CANCELADO);
			enviarMenssagemAlerta(infFuncCancelamento);
			this.setMotivoCancelamento(null);
		}
	}
	
	public void reativarAtendimento(AtendimentoDTO atendimentoSelecionadoDTO)
	{
		if(atendimentoSelecionadoDTO != null && atendimentoSelecionadoDTO.getSituacao() == ESituacaoAtendimento.C)
		{
			String infFuncReativacao = "[ATENDIMENTO (DIA: " + DataUtil.getDataFormatada(atendimentoSelecionadoDTO.getAgendamento().getDentistaAgenda().getDataAgenda()) + " / HORÁRIO: " + atendimentoSelecionadoDTO.getAgendamento().getDentistaAgenda().getIntervaloTempo() + " / DENTISTA: " + atendimentoSelecionadoDTO.getAgendamento().getDentistaAgenda().getDentista().getNomeComCROFormatado() + ") REATIVADO PELO FUNCIONÁRIO: " + getFuncionarioLogado().getNomeCompleto() + "]";
			
			this.atendimentoDTO = atendimentoSelecionadoDTO;
			this.atendimentoDTO.setSituacao(ESituacaoAtendimento.A);
			getAtendimentoDTO().getAgendamento().setSituacao(ESituacaoAgendamento.T);
			salvarObservacaoAutomatica(getAtendimentoDTO().getAgendamento().getPaciente(), ETipoObsPaciente.T, infFuncReativacao);
			alterar();
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATENDIMENTO_REATIVADO);
			enviarMenssagemInformativa(infFuncReativacao);
			infFuncReativacao = null;
		}
	}
	
	public void finalizarAtendimento(AtendimentoDTO atendimentoDTOSelecionado)
	{
		this.atendimentoDTO = atendimentoDTOSelecionado;
		if(getAtendimentoDTO().getIdAtendimento() != null && getAtendimentoDTO().getSituacao() == ESituacaoAtendimento.A)
		{
			this.atendimentoDTO.setSituacao(ESituacaoAtendimento.F);
			getAtendimentoDTO().getAgendamento().setSituacao(ESituacaoAgendamento.Z);
			alterar();
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATENDIMENTO_FINALIZADO);
		}
	}
	
	public void salvarObservacaoAutomatica(PacienteDTO pacienteDTO, ETipoObsPaciente tipoObsPaciente, String obs)
	{
		try {
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Iniciando Camada de Visualização...");
			getObsPacienteDTO().setUsuarioDTO(getFuncionarioLogado().getUsuarioPerfil());
			getObsPacienteDTO().setPacienteDTO(pacienteDTO);
			getObsPacienteDTO().setData(new Date());
			getObsPacienteDTO().setTipo(tipoObsPaciente);
			getObsPacienteDTO().setObs(obs);
			this.obsPacienteSB.salvar(this.obsPacienteDTO);
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
	
	public String salvar()
	{
		try {
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Iniciando Camada de Visualização...");
			this.atendimentoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.atendimentoSB.salvar(this.atendimentoDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.atendimentoDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR ATENDIMENTO", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarAtendimento(AtendimentoDTO atendimentoDTOAlteracao)
	{
		this.atendimentoDTO = atendimentoDTOAlteracao;
	}
	
	public String alterar()
	{
		try {
			this.atendimentoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.atendimentoSB.alterar(this.atendimentoDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.atendimentoDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR ATENDIMENTO", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public String alterar(AtendimentoDTO atendimentoDTO, String msg)
	{
		try {
			atendimentoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.atendimentoSB.alterar(atendimentoDTO);
			if(msg != null)
			{
				enviarMenssagemInformativa(msg);
			}
			this.atendimentoDTO = null;
		} catch (EntidadeCadastradaException e) {
			enviarMenssagemErro(e.getMessage());
			e.printStackTrace();
		} catch (AtributoIncompletoException e) {
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
			reportarErroAoAnalista(MBName + " ALTERAR ATENDIMENTO", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerAtendimento(AtendimentoDTO atendimentoDTOAlteracao)
	{
		this.atendimentoDTO = atendimentoDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.atendimentoDTO.getIdAtendimento() != null)
		{
			try {
				this.atendimentoSB.inativar(this.atendimentoDTO.getIdAtendimento(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemAlerta(MensagemInformativa.MSG_REMOVER_ITEM);
				this.atendimentoDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<AtendimentoDTO> getAtendimentosFiltrados()
	{
		if(getAtendimentoDTO().getAgendamento().getIdAgendamento() != null)
		{
			return buscarPorAgendamento();
		}
		else if(getDataFiltro() != null)
		{
			getAtendimentoDTO().getAgendamento().getDentistaAgenda().setDataAgenda(this.dataFiltro);
			return buscarPorData();
		}
		else if(getAtendimentoDTO().getAgendamento().getPaciente().getIdPaciente() != null)
		{
			return buscarPorPaciente();
		}
		else if(getAtendimentoDTO().getSituacao() != null)
		{
			return buscarPorSituacao();
		}
		else if(getAtendimentoDTO().getAgendamento().getDentistaAgenda().getDentista().getIdDentista() != null)
		{
			return buscarPorDentista();
		}
		else
		{
			return buscarAtendimentos();
		}
		
	}
	
	public List<AtendimentoDTO> buscarAtendimentos()
	{
		List<AtendimentoDTO>  atendimentoDTOsBusca = null;
			atendimentoDTOsBusca = this.atendimentoSB.buscarAtivos(getEstabelecimentoFiltroDTO().getIdEstabelecimento());
			
		return atendimentoDTOsBusca != null 
				? atendimentoDTOsBusca 
						: new ArrayList<AtendimentoDTO>();
	}
	
	public AtendimentoDTO buscarPorAgendamento(AgendamentoDTO agendamentoDTO)
	{
		List<AtendimentoDTO>  atendimentoDTOsBusca = null;
		
		if(agendamentoDTO.getIdAgendamento() != null)
		{
			atendimentoDTOsBusca = this.atendimentoSB.buscarPorAgendamento(agendamentoDTO.getIdAgendamento());

		}
		return atendimentoDTOsBusca != null 
				? this.atendimentoSB.getEntidadeFromList(atendimentoDTOsBusca) 
						: DTOFactory.getAtendimentoDTO();
	}
	
	public List<AtendimentoDTO> buscarPorAgendamento()
	{
		List<AtendimentoDTO>  atendimentoDTOsBusca = null;
		
		if(getAtendimentoDTO().getAgendamento().getIdAgendamento() != null)
		{
			atendimentoDTOsBusca = this.atendimentoSB.buscarPorAgendamento(getAtendimentoDTO().getAgendamento().getIdAgendamento());

		}
		return atendimentoDTOsBusca != null 
				? atendimentoDTOsBusca 
						: new ArrayList<AtendimentoDTO>();
	}
	
	public List<AtendimentoDTO> buscarPorData()
	{
		List<AtendimentoDTO>  atendimentoDTOsBusca = null;
		
		if(getAtendimentoDTO().getAgendamento().getDentistaAgenda().getDataAgenda() != null)
		{
			atendimentoDTOsBusca = this.atendimentoSB.buscarPorData(getAtendimentoDTO().getAgendamento().getDentistaAgenda().getDataAgenda(),
					getAtendimentoDTO().getAgendamento().getDentistaAgenda().getDataAgenda(), getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return atendimentoDTOsBusca != null 
				? atendimentoDTOsBusca 
						: new ArrayList<AtendimentoDTO>();
	}
	
	public List<AtendimentoDTO> buscarPorData(Date data, EstabelecimentoDTO estabelecimentoDTO)
	{
		List<AtendimentoDTO>  atendimentoDTOsBusca = null;
		
		if(data != null && estabelecimentoDTO != null)
		{
			atendimentoDTOsBusca = this.atendimentoSB.buscarPorData(data, data, 
					estabelecimentoDTO.getIdEstabelecimento());

		}
		return atendimentoDTOsBusca != null 
				? atendimentoDTOsBusca 
						: new ArrayList<AtendimentoDTO>();
	}
	
	public List<AtendimentoDTO> buscarPorPaciente()
	{
		List<AtendimentoDTO>  atendimentoDTOsBusca = null;
		
		if(getAtendimentoDTO().getAgendamento().getPaciente().getIdPaciente() != null)
		{
			atendimentoDTOsBusca = this.atendimentoSB.buscarPorPaciente(getAtendimentoDTO().getAgendamento().getPaciente().getIdPaciente(), 
					getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return atendimentoDTOsBusca != null 
				? atendimentoDTOsBusca 
						: new ArrayList<AtendimentoDTO>();
	}
	
	public List<AtendimentoDTO> buscarPorSituacao()
	{
		List<AtendimentoDTO>  atendimentoDTOsBusca = null;
		
		if(getAtendimentoDTO().getSituacao() != null)
		{
			atendimentoDTOsBusca = this.atendimentoSB.buscarPorSituacao(this.atendimentoDTO.getSituacao().name(),
					getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return atendimentoDTOsBusca != null 
				? atendimentoDTOsBusca 
						: new ArrayList<AtendimentoDTO>();
	}
	
	public List<AtendimentoDTO> buscarPorDentista()
	{
		List<AtendimentoDTO>  atendimentoDTOsBusca = null;
		
		if(getAtendimentoDTO().getAgendamento().getDentistaAgenda().getDentista().getIdDentista() != null)
		{
			atendimentoDTOsBusca = this.atendimentoSB.buscarPorDentista(this.atendimentoDTO.getAgendamento().getDentistaAgenda().getDentista().getIdDentista(),
					getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return atendimentoDTOsBusca != null 
				? atendimentoDTOsBusca 
						: new ArrayList<AtendimentoDTO>();
	}
	
	public AtendimentoDTO buscarPeloId()
	{
		AtendimentoDTO atendimentoDTOBusca = null;
		
		if(this.atendimentoDTO.getIdAtendimento() != null)
		{
			atendimentoDTOBusca = this.atendimentoSB.buscarPorId(this.atendimentoDTO.getIdAtendimento());
			if(atendimentoDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return atendimentoDTOBusca;
	}
	
	public AtendimentoDTO getAtendimentoDTO() {
		if(atendimentoDTO == null)
		{
			atendimentoDTO = DTOFactory.getAtendimentoDTO();
		}
		
		return atendimentoDTO;
	}

	public void setAtendimentoDTO(AtendimentoDTO atendimentoDTO) {
		this.atendimentoDTO = atendimentoDTO;
	}

	public ObsPacienteDTO getObsPacienteDTO() {
		if(obsPacienteDTO == null)
		{
			obsPacienteDTO = DTOFactory.getObsPacienteDTO();
		}
		
		return obsPacienteDTO;
	}

	public void setObsPacienteDTO(ObsPacienteDTO obsPacienteDTO) {
		this.obsPacienteDTO = obsPacienteDTO;
	}

	public Date getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(Date dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public String getMotivoCancelamento() {
		if(this.motivoCancelamento == null)
		{
			this.motivoCancelamento = "";
		}
		
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
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


	public PacienteDTO getPacienteDTO() {
		if(this.pacienteDTO == null)
		{
			this.pacienteDTO = DTOFactory.getPacienteDTO();
		}
		
		return pacienteDTO;
	}


	public void setPacienteDTO(PacienteDTO pacienteDTO) {
		this.pacienteDTO = pacienteDTO;
	}
}
