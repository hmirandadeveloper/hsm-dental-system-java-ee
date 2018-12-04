package gui.managedbeans;

import gui.managedbeans.atributo.ConstantesSodontoSystem;
import gui.managedbeans.generics.ManagedBeanGenericBasic;
import gui.util.email.EmailUtil;
import gui.util.torpedo.TorpedoUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.enums.ESituacaoAgendamento;
import negocio.constante.enums.ETipoObsPaciente;
import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IAgendamentoFachada;
import negocio.fachada.local.IObsPacienteFachada;
import negocio.util.DataUtil;

import org.apache.commons.mail.EmailException;

import dto.AgendamentoDTO;
import dto.DentistaAgendaDTO;
import dto.EstabelecimentoDTO;
import dto.MsgPreEmailDTO;
import dto.MsgPreTorpedoDTO;
import dto.ObsPacienteDTO;
import dto.PacienteDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class AgendamentoMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|AGENDAMENTO-MB|";
	
	@EJB
	private IAgendamentoFachada agendamentoSB;
	@EJB
	private IObsPacienteFachada obsPacienteSB;
	
	private AgendamentoDTO agendamentoDTO;
	private ObsPacienteDTO obsPacienteDTO;
	
	private DentistaAgendaDTO dentistaAgendaSelecionadoDTO;
	private DentistaAgendaDTO dentistaAgendaReagendamentoDTO;
	
	//2.2.00
	private ESituacaoAgendamento situacaoFiltro;
	private String pacienteNomeFiltro;
	//
	
	private Date dataFiltro;
	private Date dataSelecionada;
	private boolean reagendamento;
	public static boolean diaBloqueado;
	private boolean diaEncerrado;
	private String motivoCancelamento;
	
	private EstabelecimentoDTO estabelecimentoFiltroDTO;
	
	private MsgPreEmailDTO msgPreEmailSelecionadaDTO;
	private MsgPreTorpedoDTO msgPreTorpedoSelecionadoDTO;
		
	public AgendamentoMB()
	{
		System.out.println(ConstantesSodontoSystem.SISTEMA_LOG_HEADER
							+ MBName);
		if(getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.D)
		{
			setSituacaoFiltro(ESituacaoAgendamento.E);
		}
	}
	
	public void limparMB()
	{
		this.agendamentoDTO = null;
	}
	
	public ESituacaoAgendamento[] getSituacoesAgendamento()
	{
		ESituacaoAgendamento[] situacoes = null;
		
		
		if(getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.A
				&& isPermissaoHelioMiranda())
		{
			situacoes = new ESituacaoAgendamento[11];
			situacoes[0] = ESituacaoAgendamento.E;
			situacoes[1] = ESituacaoAgendamento.R;
			situacoes[2] = ESituacaoAgendamento.P;
			situacoes[3] = ESituacaoAgendamento.N;
			situacoes[4] = ESituacaoAgendamento.T;
			situacoes[5] = ESituacaoAgendamento.Z;
			situacoes[6] = ESituacaoAgendamento.F;
			situacoes[7] = ESituacaoAgendamento.G;
			situacoes[8] = ESituacaoAgendamento.S;
			situacoes[9] = ESituacaoAgendamento.M;
			situacoes[10] = ESituacaoAgendamento.C;
		}
		else if(getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.A ||
				getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.G ||
				getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.O ||
				getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.D)
		{
			situacoes = new ESituacaoAgendamento[11];
			situacoes[0] = ESituacaoAgendamento.E;
			situacoes[1] = ESituacaoAgendamento.R;
			situacoes[2] = ESituacaoAgendamento.P;
			situacoes[3] = ESituacaoAgendamento.N;
			situacoes[4] = ESituacaoAgendamento.T;
			situacoes[5] = ESituacaoAgendamento.Z;
			situacoes[6] = ESituacaoAgendamento.F;
			situacoes[7] = ESituacaoAgendamento.G;
			situacoes[8] = ESituacaoAgendamento.S;
			situacoes[9] = ESituacaoAgendamento.M;
			situacoes[10] = ESituacaoAgendamento.C;
		}
		else if (getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.C)
		{
			situacoes = new ESituacaoAgendamento[4];
			situacoes[0] = ESituacaoAgendamento.P;
			situacoes[1] = ESituacaoAgendamento.T;
			situacoes[2] = ESituacaoAgendamento.N;
			situacoes[3] = ESituacaoAgendamento.Z;
		}		
		
		return situacoes;
	}
	
	public List<ESituacaoAgendamento> getSituacaoListUsuario()
	{
		List<ESituacaoAgendamento> situacaoList = new ArrayList<ESituacaoAgendamento>();
		
		if(getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.C)
		{
			situacaoList.add(ESituacaoAgendamento.P);
			situacaoList.add(ESituacaoAgendamento.N);
			situacaoList.add(ESituacaoAgendamento.T);
			situacaoList.add(ESituacaoAgendamento.Z);
		}
		else if(getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.D)
		{
			situacaoList.add(ESituacaoAgendamento.E);
			situacaoList.add(ESituacaoAgendamento.C);
			situacaoList.add(ESituacaoAgendamento.F);
			situacaoList.add(ESituacaoAgendamento.G);
			situacaoList.add(ESituacaoAgendamento.S);
			situacaoList.add(ESituacaoAgendamento.M);
		}
		else
		{
			situacaoList.add(ESituacaoAgendamento.E);
			situacaoList.add(ESituacaoAgendamento.C);
			situacaoList.add(ESituacaoAgendamento.F);
			situacaoList.add(ESituacaoAgendamento.G);
			situacaoList.add(ESituacaoAgendamento.S);
			situacaoList.add(ESituacaoAgendamento.M);
			situacaoList.add(ESituacaoAgendamento.P);
			situacaoList.add(ESituacaoAgendamento.N);
			situacaoList.add(ESituacaoAgendamento.T);
			situacaoList.add(ESituacaoAgendamento.Z);			
		}
		
		
		return situacaoList;
	}
	
	public void selecionarData(Date data)
	{
		this.dataSelecionada = data;
	}
	
	public void encerrarDia()
	{
		getAgendamentoDTO().getDentistaAgenda().setDataAgenda(new Date());
		List<AgendamentoDTO> todosAgendamentosDoDia = buscarPorData();
		
		if(todosAgendamentosDoDia.size() > 0)
		{
			enviarMenssagemAlerta("Favor AGUARDE a conclusão da rotina de ENCERRAMENTO DO DIA, pois, dependendo da massa de dados, poderá demorar alguns minutos. Ao ser finalizada, você será notificado.");
			
			for(AgendamentoDTO ag : todosAgendamentosDoDia)
			{
				if(ag.getSituacao() == ESituacaoAgendamento.E || ag.getSituacao() == ESituacaoAgendamento.R)
				{
					String msgObs = "[O PACIENTE FALTOU AO AGENDAMENTO NO DIA: "+ DataUtil.getDataFormatada(ag.getDentistaAgenda().getDataAgenda()) + " / HORÁRIO: " + ag.getDentistaAgenda().getIntervaloTempo() + " / COM O DENTISTA: " + ag.getDentistaAgenda().getDentista().getNomeComCROFormatado() + "]";
					ag.setSituacao(ESituacaoAgendamento.F);
					alterar(ag, null);
					salvarObservacaoAutomatica(ag.getPaciente(), ETipoObsPaciente.G, msgObs, false);
				}
				else if(ag.getSituacao() == ESituacaoAgendamento.P || ag.getSituacao() == ESituacaoAgendamento.N)
				{
					String msgObs = "[O PACIENTE PAGOU E NÃO COMPARECEU AO AGENDAMENTO NO DIA: "+ DataUtil.getDataFormatada(ag.getDentistaAgenda().getDataAgenda()) + " / HORÁRIO: " + ag.getDentistaAgenda().getIntervaloTempo() + " / COM O DENTISTA: " + ag.getDentistaAgenda().getDentista().getNomeComCROFormatado() + "]";
					ag.setSituacao(ESituacaoAgendamento.G);
					alterar(ag, null);
					salvarObservacaoAutomatica(ag.getPaciente(), ETipoObsPaciente.G, msgObs, false);
				}
				else if(ag.getSituacao() == ESituacaoAgendamento.T)
				{
					ag.setSituacao(ESituacaoAgendamento.Z);
					alterar(ag, null);
				}
			}
/*
 * INÍCIO DO REGISTRO DE LOG
*/
			incluirLogOperacional(
					"FECHAMENTO DE DIA", 
					"FECHOU O DIA: "
					+ DataUtil.getDataFormatada(DataUtil.getDataAtual()));
/*
 * FIM DO REGISTRO DE LOG
 */
			
			enviarMenssagemInformativa("Dia: " + DataUtil.getDataFormatada(new Date()) + ", ENCERRADO com sucesso!");
		}
		else
		{
			enviarMenssagemAlerta("Não há AGENDAMENTOS no dia: " + DataUtil.getDataFormatada(getAgendamentoDTO().getDentistaAgenda().getDataAgenda()) + ", sendo assim, não há o que ser fechado!");
		}
	}
	
	public void reagendarPaciente()
	{	
		getAgendamentoDTO().setRemarcacao(this.reagendamento);
		
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"REAGENDAMENTO DE PACIENTE", 
				"REAGENDOU O PACIENTE: "
				+ getAgendamentoDTO().getPaciente().getNomeCompleto()
				+ " PARA O DIA: "
				+ DataUtil.getDataFormatada(getDentistaAgendaReagendamentoDTO().getDataAgenda())
				+ ", NO HORÁRIO: "
				+ getDentistaAgendaReagendamentoDTO().getIntervaloTempo()
				+ DataUtil.getDataFormatada(DataUtil.getDataAtual())
				+ ", COM O DENTISTA: "
				+ getDentistaAgendaReagendamentoDTO().getDentista().getNomeComCROFormatado()
				);
/*
 * FIM DO REGISTRO DE LOG
*/
		
		salvar();
		enviarMenssagemInformativa("Reagendamento efetuado com sucesso!");
	}
	
	public void cancelarReagendamento(boolean discordadoPeloPaciente)
	{
/*
 * INÍCIO DO REGISTRO DE LOG
*/
				
		incluirLogOperacional(
				"NÃO REAGENDOU O PACIENTE", 
				"NÃO REAGENDOU O PACIENTE: "
				+ getAgendamentoDTO().getPaciente().getNomeCompleto()
				);
/*
 * FIM DO REGISTRO DE LOG
*/		
		if(!discordadoPeloPaciente)
		{
			salvarObservacaoAutomatica(getAgendamentoDTO().getPaciente(), ETipoObsPaciente.G, "Reagendamento Cancelado pelo FUNCIONÁRIO: " + getFuncionarioLogado().getNomeCompleto(), true);
			enviarMenssagemAlerta("Reagendamento Cancelado pelo FUNCIONÁRIO: " + getFuncionarioLogado().getNomeCompleto());
		}
		else
		{
			salvarObservacaoAutomatica(getAgendamentoDTO().getPaciente(), ETipoObsPaciente.G, "Reagendamento Cancelado pelo PACIENTE!", true);
			enviarMenssagemAlerta("Reagendamento Cancelado pelo PACIENTE: " + getAgendamentoDTO().getPaciente().getNomeCompleto());
		}
		
		this.dentistaAgendaReagendamentoDTO = null;
		this.agendamentoDTO = null;
	}
	
	public void incluirAgendamentoPaciente(PacienteDTO pacienteSelecionadoDTO)
	{
		this.reagendamento = false;
		getAgendamentoDTO().setPaciente(pacienteSelecionadoDTO);
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"AGENDAMENTO DE PACIENTE", 
				"AGENDOU O PACIENTE: "
				+ getAgendamentoDTO().getPaciente().getNomeCompleto()
				+ " PARA O DIA: "
				+ DataUtil.getDataFormatada(getAgendamentoDTO().getDentistaAgenda().getDataAgenda())
				+ ", NO HORÁRIO: "
				+ getAgendamentoDTO().getDentistaAgenda().getIntervaloTempo()
				+ DataUtil.getDataFormatada(DataUtil.getDataAtual())
				+ ", COM O DENTISTA: "
				+ getAgendamentoDTO().getDentistaAgenda().getDentista().getNomeComCROFormatado()
				);
/*
 * FIM DO REGISTRO DE LOG
*/		
		salvar();
	}
	
	public void cancelarAgendamento()
	{
		if(getAgendamentoDTO().getIdAgendamento() != null && (getAgendamentoDTO().getSituacao() == ESituacaoAgendamento.E || getAgendamentoDTO().getSituacao() == ESituacaoAgendamento.R))
		{
			String infFuncCancelamento = "[AGENDAMENTO (DIA: " + DataUtil.getDataFormatada(this.agendamentoDTO.getDentistaAgenda().getDataAgenda()) +" / HORÁRIO: " + this.agendamentoDTO.getDentistaAgenda().getIntervaloTempo() + " / DENTISTA: " + this.agendamentoDTO.getDentistaAgenda().getDentista().getNomeComCROFormatado() + ") CANCELADO PELO FUNCIONÁRIO: " + getFuncionarioLogado().getNomeCompleto() + "] Motivo: " + (getMotivoCancelamento().equals("") ? "O Funcionário não informou!" : getMotivoCancelamento());
			
			salvarObservacaoAutomatica(getAgendamentoDTO().getPaciente(), ETipoObsPaciente.G, infFuncCancelamento, true);
			
			this.agendamentoDTO.setSituacao(ESituacaoAgendamento.C);
			
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
			incluirLogOperacional(
					"CANCELOU O AGENDAMENTO DE PACIENTE", 
					"CANCELOU O AGENDAMENTO DO PACIENTE: "
					+ getAgendamentoDTO().getPaciente().getNomeCompleto()
					+ " PARA O DIA: "
					+ DataUtil.getDataFormatada(getAgendamentoDTO().getDentistaAgenda().getDataAgenda())
					+ ", NO HORÁRIO: "
					+ getAgendamentoDTO().getDentistaAgenda().getIntervaloTempo()
					+ DataUtil.getDataFormatada(DataUtil.getDataAtual())
					+ ", COM O DENTISTA: "
					+ getAgendamentoDTO().getDentistaAgenda().getDentista().getNomeComCROFormatado()
					);
/*
 * FIM DO REGISTRO DE LOG
*/			
			
			alterar();
			enviarMenssagemAlerta(MensagemInformativa.MSG_AGENDAMENTO_CANCELADO);
			enviarMenssagemAlerta(infFuncCancelamento);
			this.motivoCancelamento = null;
		}
	}
	
	public void reativarAgendamento(AgendamentoDTO agendamentoSelecionadoDTO)
	{
		if(agendamentoSelecionadoDTO != null && agendamentoSelecionadoDTO.getSituacao() == ESituacaoAgendamento.C)
		{
			
			this.agendamentoDTO = agendamentoSelecionadoDTO;
			this.agendamentoDTO.setSituacao(ESituacaoAgendamento.E);
			
			String infFuncReativacao = "[AGENDAMENTO (DIA: " + DataUtil.getDataFormatada(this.agendamentoDTO.getDentistaAgenda().getDataAgenda()) +" / HORÁRIO: " + this.agendamentoDTO.getDentistaAgenda().getIntervaloTempo() + " / DENTISTA: " + this.agendamentoDTO.getDentistaAgenda().getDentista().getNomeComCROFormatado() + ") REATIVADO PELO FUNCIONÁRIO: " + getFuncionarioLogado().getNomeCompleto() + "]";
			
			salvarObservacaoAutomatica(getAgendamentoDTO().getPaciente(), ETipoObsPaciente.G, infFuncReativacao, true);
			
			
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
			incluirLogOperacional(
					"REATIVOU O AGENDAMENTO DE PACIENTE", 
					"REATIVOU O AGENDAMENTO DO PACIENTE: "
					+ getAgendamentoDTO().getPaciente().getNomeCompleto()
					+ " PARA O DIA: "
					+ DataUtil.getDataFormatada(getAgendamentoDTO().getDentistaAgenda().getDataAgenda())
					+ ", NO HORÁRIO: "
					+ getAgendamentoDTO().getDentistaAgenda().getIntervaloTempo()
					+ DataUtil.getDataFormatada(DataUtil.getDataAtual())
					+ ", COM O DENTISTA: "
					+ getAgendamentoDTO().getDentistaAgenda().getDentista().getNomeComCROFormatado()
					);
/*
 * FIM DO REGISTRO DE LOG
*/				
			
			alterar();
			enviarMenssagemInformativa(MensagemInformativa.MSG_AGENDAMENTO_REATIVADO);
			enviarMenssagemInformativa(infFuncReativacao);
			infFuncReativacao = null;
		}
	}
	
	public void enviarParaPagamento(AgendamentoDTO agendamentoSelecionadoDTO)
	{
		String nomePaciente = agendamentoSelecionadoDTO.getPaciente().getNomeCompleto();
		agendamentoSelecionadoDTO.setSituacao(ESituacaoAgendamento.R);
		
		
/*
* INÍCIO DO REGISTRO DE LOG
*/
	
		incluirLogOperacional(
				"ENVIO DE PACIENTE PARA CAIXA", 
				"ENVIOU O PACIENTE: "
				+ agendamentoSelecionadoDTO.getPaciente().getNomeCompleto()
				+ " PARA O CAIXA: "
				);
/*
* FIM DO REGISTRO DE LOG
*/			
		
		alterar(agendamentoSelecionadoDTO, "O Paciente " + nomePaciente + " foi enviado para o Caixa!");
	}
	
	public void enviarParaAtendimento(boolean abono)
	{
		if(getAgendamentoDTO().getIdAgendamento() != null)
		{
			String nomePaciente = getAgendamentoDTO().getPaciente().getNomeCompleto();
			String msg = null;
			
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
			incluirLogOperacional(
					"ENVIO DE PACIENTE PARA ATENDIMENTO", 
					"ENVIOU, PARA ATENDIMENTO, O PACIENTE: "
					+ getAgendamentoDTO().getPaciente().getNomeCompleto()
					+ ", COM O DENTISTA: "
					+ getAgendamentoDTO().getDentistaAgenda().getDentista().getNomeComCROFormatado()
					);
/*
 * FIM DO REGISTRO DE LOG
*/	
						
			if(abono && getAgendamentoDTO().getSituacao() == ESituacaoAgendamento.R)
			{
				getAgendamentoDTO().setSituacao(ESituacaoAgendamento.P);
				msg = "A mensalidade do Paciente " + nomePaciente + " para o Agendamento de HOJE, foi ABONADA! O mesmo, poderá prosseguir para ATENDIMENTO!";
				alterar(getAgendamentoDTO(), msg);
			}
			else if(abono && getAgendamentoDTO().getSituacao() == ESituacaoAgendamento.P)
			{
				enviarMenssagemInformativa("O Paciente poderá prosseguir para ATENDIMENTO, pois sua mensalidade referente a esse mês foi ABONADA!");
			}
			else
			{
				
				if(getAgendamentoDTO().getSituacao() == ESituacaoAgendamento.R)
				{
					getAgendamentoDTO().setSituacao(ESituacaoAgendamento.P);
					msg = "A mensalidade do Paciente " + nomePaciente + " para o Agendamento de HOJE, foi PAGA sem passar pelo CAIXA, sendo assim, não haverá ENTRADA no Caixa SELECIONADO! O mesmo, poderá prosseguir para ATENDIMENTO!";
					alterar(getAgendamentoDTO(), msg);
				}
				enviarMenssagemInformativa("O Paciente poderá prosseguir para ATENDIMENTO!");
			}
		}
	}
	
	public void realizarPagamento()
	{
		if(getAgendamentoDTO().getIdAgendamento() != null)
		{
			String nomePaciente = getAgendamentoDTO().getPaciente().getNomeCompleto();
			String msg = null;
			
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"PAGAMENTO DE PACIENTE", 
				"REALIZOU, NO CAIXA, O PAGAMENTO DO PACIENTE: "
				+ getAgendamentoDTO().getPaciente().getNomeCompleto()
				);
/*
 * FIM DO REGISTRO DE LOG
*/				

			if(getAgendamentoDTO().getSituacao() != ESituacaoAgendamento.P)
			{
				msg = "O Pagamento do Paciente " + nomePaciente + " para o Agendamento de HOJE, foi realizado com sucesso! O Paciente, poderá prosseguir para ATENDIMENTO!";
			}
			else
			{
				msg = "O Pagamento do Paciente " + nomePaciente + " para um FUTURO Agendamento, foi realizado com sucesso!";
				salvarObservacaoAutomatica(getAgendamentoDTO().getPaciente(), ETipoObsPaciente.G, "[O PACIENTE ANTECIPOU UMA MENSALIDADE]", true);
			}
			
			getAgendamentoDTO().setSituacao(ESituacaoAgendamento.P);
			alterar(getAgendamentoDTO(), msg);
		}
		else
		{
			enviarMenssagemErro("Nenhum Agendamento foi Selecionado!");
		}
	}
	
	public void confirmarPagamento(AgendamentoDTO agendamentoSelecionadoDTO)
	{
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"CONFIRMAÇÃO DE PAGAMENTO DE PACIENTE", 
				"CONFIRMOU O PAGAMENTO DO PACIENTE: "
				+ getAgendamentoDTO().getPaciente().getNomeCompleto()
				);
/*
 * FIM DO REGISTRO DE LOG
*/			
		
		String nomePaciente = agendamentoSelecionadoDTO.getPaciente().getNomeCompleto();
		agendamentoSelecionadoDTO.setSituacao(ESituacaoAgendamento.N);
		alterar(agendamentoSelecionadoDTO, "O Pagamento do Paciente " + nomePaciente + " foi confirmado com sucesso!");
	}
	
	public void selecionarPaciente(PacienteDTO pacienteDTOSelecionado)
	{
		getAgendamentoDTO().setPaciente(pacienteDTOSelecionado);
	}
	
	public void removerSelecaoPaciente()
	{
		getAgendamentoDTO().setPaciente(null);
	}
	
	public void selecionarMensagemEmail(MsgPreEmailDTO msgPreEmailDTO)
	{
		this.msgPreEmailSelecionadaDTO = msgPreEmailDTO;
	}
	
	public void removerSelecaoMensagemEmail()
	{
		this.msgPreEmailSelecionadaDTO = null;
	}
	
	public void selecionarMensagemTorpedo(MsgPreTorpedoDTO msgPreTorpedoDTO)
	{
		this.msgPreTorpedoSelecionadoDTO = msgPreTorpedoDTO;
	}
	
	public void removerSelecaoMensagemTorpedo()
	{
		this.msgPreTorpedoSelecionadoDTO = null;
	}
	
	public void selecionarDentistaAgenda(DentistaAgendaDTO dentistaAgendaSelecionadoDTO)
	{
		this.dentistaAgendaSelecionadoDTO = dentistaAgendaSelecionadoDTO;
	}
	
	public void removerSelecaoDentistaAgenda()
	{
		this.dentistaAgendaSelecionadoDTO = null;
	}
	
	public void selecionarDentistaAgendaReagendamento(DentistaAgendaDTO dentistaAgendaSelecionadoDTO)
	{
		this.dentistaAgendaReagendamentoDTO = dentistaAgendaSelecionadoDTO;
	}
	
	public void removerSelecaoDentistaAgendaReagendamento()
	{
		this.dentistaAgendaReagendamentoDTO = null;
	}
	
	public void enviarEmailAgendamento()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return;
		}
		
		List<String> emails = new ArrayList<String>();
		if(getMsgPreEmailSelecionadaDTO().getIdMsgPreEmail() != null)
		{
			for(AgendamentoDTO ag : getAgendamentosFiltrados())
			{
				emails.add(ag.getPaciente().getEmail());
			}
			
			try {
				EmailUtil.enviarEmailVariosDestinatarios(emails, 
						getMsgPreEmailSelecionadaDTO().getTitulo(), 
						getMsgPreEmailSelecionadaDTO().getMsg());			
				
				enviarMenssagemInformativa(MensagemInformativa.MSG_ENVIO_EMAILS);
			} catch (EmailException e) {
				enviarMenssagemErro(MensagemErro.MSG_ERRO_ENVIO_EMAIL);
				e.printStackTrace();
			}
		}
	}
	
	public Date getMinDataParaReagendamento()
	{
		int mesAtual = Integer.valueOf(DataUtil.getMesDaData(new Date()));
		int anoAtual = Integer.valueOf(DataUtil.getAnoDaData(new Date()));
		
		if(mesAtual == 12)
		{
			mesAtual = 1;
			anoAtual++;
		}
		else
		{
			mesAtual++;
		}
		String dataReagendamento = "01/" + (mesAtual < 10 ? "0" : "") + mesAtual + "/" + anoAtual;
		
		return DataUtil.getDataPorString(dataReagendamento);
	}
	
	public List<AgendamentoDTO> FiltrarAgendamentos(List<AgendamentoDTO> agendamentosDia, ESituacaoAgendamento situacaoDesejada)
	{
		if(situacaoDesejada == null || agendamentosDia == null)
		{
			return null;
		}
	
		List<AgendamentoDTO> tempAgendamentos = new ArrayList<AgendamentoDTO>(agendamentosDia);
		
		if(tempAgendamentos.size() > 0)
		{
			for (AgendamentoDTO ag : agendamentosDia) {
				
				if(ag.getSituacao() != situacaoDesejada)
				{
					tempAgendamentos.remove(ag);
				}
			}
		}
			
		return tempAgendamentos;
	}
	
	public void enviarTorpedoAgendamento()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return;
		}
		
		List<AgendamentoDTO> tempList = buscarPorDentistaAgenda();
		
		if(getMsgPreTorpedoSelecionadoDTO().getIdMsgPreTorpedo() 
				!= null && tempList.size() > 0)
		{
			System.out.println("[SODONTO SYSTEM][2.2.00][MB] Total de AGENDAMENTOS carregados: " + tempList.size());
			
			List<AgendamentoDTO> agendamentosDia = FiltrarAgendamentos(tempList, ESituacaoAgendamento.E);
			
			System.out.println("[SODONTO SYSTEM][2.2.00][MB] Total de AGENDAMENTOS Filtrados para envio: " + agendamentosDia.size());
			
			int totalEnvios = 0;
			String pacientesErro = "";
			String retornoEnvio = "";					
			
			for(AgendamentoDTO ag :  agendamentosDia)
			{		
				retornoEnvio = TorpedoUtil
						.enviarTorpedo(
								getAtributoOperacionalSelecionado(),
								ag.getPaciente().getCel01(), 
								getMsgPreTorpedoSelecionadoDTO().getMsg(), 
								ag.getPaciente().getNome(),
								DataUtil.getDataFormatada(
										ag.getDentistaAgenda().getDataAgenda()),
								ag.getDentistaAgenda().getIntervaloTempo());				
			
				
				if(retornoEnvio.contains(
						TorpedoUtil
						.getRetornoSucessoOperadora(
								getAtributoOperacionalSelecionado()
								.getProvedorSMS())))
				{
					totalEnvios++;
				}
				else
				{
					pacientesErro = (pacientesErro.length() == 0 
							? ag.getPaciente().getIdPaciente().toString() 
							: pacientesErro + ", " + 
							ag.getPaciente()
							.getIdPaciente().toString());
				}
				
			}
			
			
			if(totalEnvios == agendamentosDia.size())
			{
				
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
			incluirLogOperacional(
					"ENVIOU TORPEDOS PARA PACIENTES", 
					"ENVIOU TORPEDOS PARA TODOS OS PACIENTES AGENDADOS PARA A DATA: "
					+ DataUtil.getDataFormatada(getDentistaAgendaSelecionadoDTO().getDataAgenda())
					);
/*
 * FIM DO REGISTRO DE LOG
*/				
				
				System.out.println("[SODONTO SYSTEM][2.2.00][MB] Total de TORPEDOS enviados: " + totalEnvios);
				
				if(totalEnvios > 1)
				{
					enviarMenssagemInformativa(MensagemInformativa.MSG_ENVIO_TORPEDOS);
				}
				else if(totalEnvios == 1)
				{
					enviarMenssagemInformativa(MensagemInformativa.MSG_ENVIO_TORPEDO);
				}
			}
			else
			{
				enviarMenssagemErro(MensagemErro.MSG_ERRO_ENVIO_TORPEDO);
				enviarMenssagemAlerta((agendamentosDia.size() - totalEnvios < 10 ? "0" : "")
						+(agendamentosDia.size() - totalEnvios)
						+ " Paciente"
						+(agendamentosDia.size() - totalEnvios > 1 ? "s" : "")
						+ " NÃO receberam Torpedo. (O"
						+(agendamentosDia.size() - totalEnvios > 1 ? "s" : "")
						+ " seu"
						+(agendamentosDia.size() - totalEnvios > 1 ? "s" : "")
						+ " Código "
						+(agendamentosDia.size() - totalEnvios > 1 ? "s são:" : " é:")
						+ pacientesErro
						+ ") - Favor verificar se há algo ERRADO no Cadastro do CELULAR 01 do Paciente"
						);
			}
		}
	}
	
	public int getVagasReagendamento()
	{
		int limiteAtendimentos = 0;

		int totalAgendamentos = buscarPorDentistaAgenda(this.dentistaAgendaReagendamentoDTO).size();
		
		limiteAtendimentos = (DataUtil.getDiferencaHorarioEmMinutos(dentistaAgendaSelecionadoDTO.getHorarioI(), 
				dentistaAgendaSelecionadoDTO.getHorarioF()) / getAtributoOperacionalSelecionado().getDuracaoAtendimento()) - totalAgendamentos;
		
		return limiteAtendimentos;
	}
	
	public int getLimiteAtendimentos()
	{
		int limiteAtendimentos = 0;
		
		limiteAtendimentos = (DataUtil.getDiferencaHorarioEmMinutos(dentistaAgendaSelecionadoDTO.getHorarioI(), 
				dentistaAgendaSelecionadoDTO.getHorarioF()) / getAtributoOperacionalSelecionado().getDuracaoAtendimento());
		
		return limiteAtendimentos;
	}
	
	public int getTotalAtendimentos(DentistaAgendaDTO dentistaAgendaSelecionadoDTO)
	{
		int limiteAtendimentos = 0;
		setDentistaAgendaSelecionadoDTO(dentistaAgendaSelecionadoDTO);
		int totalAgendamentos = buscarPorDentistaAgenda().size();
		
		limiteAtendimentos = (DataUtil.getDiferencaHorarioEmMinutos(dentistaAgendaSelecionadoDTO.getHorarioI(), 
				dentistaAgendaSelecionadoDTO.getHorarioF()) / getAtributoOperacionalSelecionado().getDuracaoAtendimento()) - totalAgendamentos;
		
		return limiteAtendimentos;
	}
	
	public int getTotalAtendimentos()
	{
		int limiteAtendimentos = 0;
		int totalAgendamentos = buscarPorDentistaAgenda().size();
		
		limiteAtendimentos = (DataUtil.getDiferencaHorarioEmMinutos(dentistaAgendaSelecionadoDTO.getHorarioI(), 
				dentistaAgendaSelecionadoDTO.getHorarioF()) / getAtributoOperacionalSelecionado().getDuracaoAtendimento()) - totalAgendamentos;
		
		return limiteAtendimentos;
	}
	
	public void salvarObservacaoAutomatica(PacienteDTO pacienteDTO, ETipoObsPaciente tipoObsPaciente, String obs, boolean enviarMensagem)
	{
		try {
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
	
	public DentistaAgendaDTO verificarDuplicacaoAgendaMes(PacienteDTO pacienteDTO, 
			DentistaAgendaDTO dentistaAgendaDTOAtual, boolean reagendamento)
	{
		List<AgendamentoDTO> agendamentosDTO = null;
				
		if(pacienteDTO == null)
		{
			return null;
		}
		
		agendamentosDTO = this.agendamentoSB.buscarPorPaciente(pacienteDTO.getIdPaciente(), 
				getEstabelecimentoFiltroDTO().getIdEstabelecimento());
		
		
		if(agendamentosDTO.size() > 0)
		{
			for(AgendamentoDTO ag : agendamentosDTO)
			{
				if(
					(DataUtil.compararData(
						ag.getDentistaAgenda().getDataAgenda(), 
						DataUtil.getDataAtual()) <= 0)
						&& 
					(!ag.getDentistaAgenda().getIdDentistaAgenda().equals(
					 dentistaAgendaDTOAtual.getIdDentistaAgenda()))
						&&
					(!reagendamento)
						&&
					(ag.getSituacao().equals(ESituacaoAgendamento.E) || 
					ag.getSituacao().equals(ESituacaoAgendamento.R))
					)
				{
					String msg = "";
					
					if((DataUtil.compararData(ag.getDentistaAgenda().getDataAgenda(), dentistaAgendaDTOAtual.getDataAgenda()) == 0)
							&& !ag.getDentistaAgenda().getHorarioI().equals(dentistaAgendaDTOAtual.getHorarioI()))
					{
							
						msg = "O Agendamento do Paciente: " + ag.getPaciente().getNomeCompleto() + 
								", para hoje, dia " + DataUtil.getDataFormatada(ag.getDentistaAgenda().getDataAgenda()) +
								" (" + ag.getDentistaAgenda().getIntervaloTempo() +")" +
								", com o Dentista: " + ag.getDentistaAgenda().getDentista().getNomeComCROFormatado() + 
								" foi CANCELADO. Pois houve, apenas, uma troca de horário.";
						
						ag.setSituacao(ESituacaoAgendamento.C);
						alterar(ag, msg);
					}
					else
					{
						msg = "O Agendamento do Paciente: " + ag.getPaciente().getNomeCompleto() + 
								", para o dia " + DataUtil.getDataFormatada(ag.getDentistaAgenda().getDataAgenda()) +
								" (" + ag.getDentistaAgenda().getIntervaloTempo() +")" +
								", com o Dentista: " + ag.getDentistaAgenda().getDentista().getNomeComCROFormatado() + 
								" foi CANCELADO.";
						
						ag.setSituacao(ESituacaoAgendamento.C);
						salvarObservacaoAutomatica(ag.getPaciente(), ETipoObsPaciente.A, msg, false);
						alterar(ag, msg);
					}
					
					return ag.getDentistaAgenda();
				}
			}
		}
		System.out.println("[SODONTO SYSTEM][2.0.00][MB] Não foi encontrado nenhum Agendamento para o memso mes...");
		return null;
	}
	
	public boolean getAtendimentoCancelado()
	{	
		AgendamentoDTO ag = this.agendamentoSB.getEntidadeFromList(this.agendamentoSB.buscarPorSituacaoEPaciente(ESituacaoAgendamento.G.name(), this.agendamentoDTO.getPaciente().getIdPaciente(), this.agendamentoDTO.getEstabelecimentoDTO().getIdEstabelecimento()));
		
		if(ag != null)
		{
			String msg = "O Paciente possuía um Agendamento PAGO para o dia: " + DataUtil.getDataFormatada(ag.getDentistaAgenda().getDataAgenda()) + ", porém NÃO COMPARECEU. Sendo assim, ao criar um novo Agendamento (Para o dia: "+ DataUtil.getDataFormatada(this.agendamentoDTO.getDentistaAgenda().getDataAgenda()) +") o valor pago anteriormente foi transferido para o mesmo.";
			ag.setSituacao(ESituacaoAgendamento.S);
			salvarObservacaoAutomatica(ag.getPaciente(), ETipoObsPaciente.G, msg, true);
			alterar(ag, msg);
			
			return true;
		}
		
		return false;
	}
	
	public void validarFaltoso(AgendamentoDTO agendamentoDTO)
	{
		if(agendamentoDTO == null)
		{
			return;
		}
		
		List<AgendamentoDTO> agendamentosDTO = this.agendamentoSB.buscarPorPaciente(agendamentoDTO.getPaciente().getIdPaciente(), getAgendamentoDTO().getEstabelecimentoDTO().getIdEstabelecimento());
		
		if(agendamentosDTO.size() > 0)
		{
			for(AgendamentoDTO ag : agendamentosDTO)
			{
				if(ag.getSituacao() == ESituacaoAgendamento.F)
				{
					String msg = "O Paciente foi FALTOSO no agendamento do dia: " + DataUtil.getDataFormatada(
							ag.getDentistaAgenda().getDataAgenda()) + ", no horário: " + ag.getDentistaAgenda().getIntervaloTempo() +
							", com o Dentista: " + ag.getDentistaAgenda().getDentista().getNomeComCROFormatado() + ". No entando DEIXOU de ser FALTOSO ao remarcar um novo agendamento para o dia: " +
							DataUtil.getDataFormatada(agendamentoDTO.getDentistaAgenda().getDataAgenda()) + ", no horário: " + 
							agendamentoDTO.getDentistaAgenda().getIntervaloTempo() + ", com o Dentista: " + agendamentoDTO.getDentistaAgenda().getDentista().getNomeComCROFormatado();
					
					ag.setSituacao(ESituacaoAgendamento.M);
					
					alterar(ag, msg);
					salvarObservacaoAutomatica(ag.getPaciente(), ETipoObsPaciente.G, msg, true);
				}
			}
		}
	}
	
	public String salvar()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return "";
		}
		
		try {
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Iniciando Camada de Visualização...");
			
			this.agendamentoDTO.setEstabelecimentoDTO(getEstabelecimentoFiltroDTO());
			
			if(this.reagendamento)
			{
				this.agendamentoDTO.setDentistaAgenda(getDentistaAgendaReagendamentoDTO());
			}
			else
			{
				this.agendamentoDTO.setDentistaAgenda(getDentistaAgendaSelecionadoDTO());
			}
			
			this.agendamentoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.agendamentoDTO.setUsuarioCriacaoDTO(getFuncionarioLogado().getUsuarioPerfil());
			this.agendamentoDTO.setDataCriacao(new Date());
			
			if(getAtendimentoCancelado())
			{
				this.agendamentoDTO.setSituacao(ESituacaoAgendamento.P);
			}
			else
			{
				this.agendamentoDTO.setSituacao(ESituacaoAgendamento.E);
			}
			
			this.agendamentoSB.salvar(this.agendamentoDTO);
			validarFaltoso(this.agendamentoDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Iniciando verificação de duplicidade de Agendamentos...");
			DentistaAgendaDTO dentistaAgendaDuplicada = null;
			
			if(!this.reagendamento && DataUtil.igualDataAtual(this.agendamentoDTO.getDentistaAgenda().getDataAgenda()))
			{
				dentistaAgendaDuplicada = verificarDuplicacaoAgendaMes(this.agendamentoDTO.getPaciente(), 
						this.agendamentoDTO.getDentistaAgenda(), false);
			}
			else
			{
				enviarMenssagemAlerta("A Rotina para GERENCIAMENTO "
									+ "AUTOMÁTICO de Agendamentos, "
									+ "NÃO irá funcionar para esse "
									+ "agendamento, pois trata-se de "
									+ "um agendamento para uma Data "
									+ "SUPERIOR a atual, sendo assim, "
									+ "o Usuário deverá CANCELAR "
									+ "agendamentos FUTUROS incorretos, "
									+ "MANUALMENTE!");
			}
				
			
			if(dentistaAgendaDuplicada != null)
			{
				System.out.println("[SODONTO SYSTEM][2.0.00][MB] Encontrada duplicidade de Agendamentos para o Mes...");
				
				System.out.println("[SODONTO SYSTEM][2.0.00][MB] Dia já Agendado: " + DataUtil.getDiaDaData(dentistaAgendaDuplicada.getDataAgenda()));
				
				String msg = "O Paciente: " + this.agendamentoDTO.getPaciente().getNomeCompleto() 
						+ ((Integer.parseInt(DataUtil.getDiaDaData(dentistaAgendaDuplicada.getDataAgenda())) <= 
								Integer.parseInt(DataUtil.getDiaDaData(this.agendamentoDTO.getDentistaAgenda().getDataAgenda()))
								) || (Integer.parseInt(DataUtil.getHoraDaData(dentistaAgendaDuplicada.getHorarioI())) < 
										Integer.parseInt(DataUtil.getHoraDaData(this.agendamentoDTO.getDentistaAgenda().getHorarioI())))

						? " teve uma Consulta marcada para a Data: " + 
								DataUtil.getDataFormatada(dentistaAgendaDuplicada.getDataAgenda()) + " com o Dentista: " +
								dentistaAgendaDuplicada.getDentista().getNomeComCROFormatado() + " no Horário: " +
								dentistaAgendaDuplicada.getIntervaloTempo() + " e não compareceu. No entanto, foi reagendada um para a Data: " +
								DataUtil.getDataFormatada(this.agendamentoDTO.getDentistaAgenda().getDataAgenda()) + " com o Dentista: " +
								this.agendamentoDTO.getDentistaAgenda().getDentista().getNomeComCROFormatado() + " no Horário: " +
								this.agendamentoDTO.getDentistaAgenda().getIntervaloTempo() + "."
								: " tinha uma Consulta marcada para a Data: " +
									DataUtil.getDataFormatada(dentistaAgendaDuplicada.getDataAgenda()) + " com o Dentista: " +
									dentistaAgendaDuplicada.getDentista().getNomeComCROFormatado() + " no Horário: " +
									dentistaAgendaDuplicada.getIntervaloTempo() + ". No entanto, foi antecipada para a Data: " +
									DataUtil.getDataFormatada(this.agendamentoDTO.getDentistaAgenda().getDataAgenda()) + " com o Dentista: " +
									this.agendamentoDTO.getDentistaAgenda().getDentista().getNomeComCROFormatado() + " no Horário: " +
									this.agendamentoDTO.getDentistaAgenda().getIntervaloTempo() + "."									
									);
				
				salvarObservacaoAutomatica(this.agendamentoDTO.getPaciente(), ETipoObsPaciente.G, msg, true);
				enviarMenssagemAlerta(msg);
			}
			else
			{
				System.out.println("[SODONTO SYSTEM][2.0.00][MB] Não foi Encontrada duplicidade de Agendamentos...");
			}
			
			this.agendamentoDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR AGENDAMENTO", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarAgendamento(AgendamentoDTO agendamentoDTOAlteracao)
	{
		this.agendamentoDTO = agendamentoDTOAlteracao;
	}
	
	public String alterar()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return "";
		}
		
		try {
			this.agendamentoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.agendamentoSB.alterar(this.agendamentoDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.agendamentoDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR AGENDAMENTO", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public String alterar(AgendamentoDTO ag, String msg)
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return "";
		}
		
		try {
			ag.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.agendamentoSB.alterar(ag);
			if(msg != null)
			{
				enviarMenssagemInformativa(msg);
			}

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
			reportarErroAoAnalista(MBName + " ALTERAR AGENDAMENTO", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerAgendamento(AgendamentoDTO agendamentoDTOAlteracao)
	{
		this.agendamentoDTO = agendamentoDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.agendamentoDTO.getIdAgendamento() != null)
		{
			try {
				this.agendamentoSB.inativar(this.agendamentoDTO.getIdAgendamento(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemAlerta(MensagemInformativa.MSG_REMOVER_ITEM);
				this.agendamentoDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<AgendamentoDTO> getAgendamentosFiltrados()
	{
		List<AgendamentoDTO> retornoBuscaFiltro;
		
		if(getDataFiltro() != null)
		{
			getAgendamentoDTO().getDentistaAgenda().setDataAgenda(this.dataFiltro);
			retornoBuscaFiltro = buscarPorData();
		}
		else if(getDentistaAgendaSelecionadoDTO().getIdDentistaAgenda() != null)
		{
			//Entra aqui quando tem Agenda de Dentista Selecionada...
			if(!this.pacienteNomeFiltro.equals("") || this.situacaoFiltro != null)
			{
				if(!this.pacienteNomeFiltro.equals("") && this.situacaoFiltro != null)
				{
					setPacienteNomeFiltro(!getPacienteNomeFiltro().equals("") ? "%" + this.pacienteNomeFiltro + "%" : "");
					retornoBuscaFiltro = buscarPorDentistaAgendaSituacaoEPacienteNome(this.situacaoFiltro, this.pacienteNomeFiltro);
				}
				else if (this.situacaoFiltro != null)
				{
					retornoBuscaFiltro = buscarPorDentistaAgendaESituacao(this.situacaoFiltro);
				}
				else
				{
					setPacienteNomeFiltro(!getPacienteNomeFiltro().equals("") ? "%" + this.pacienteNomeFiltro + "%" : "");
					retornoBuscaFiltro = buscarPorDentistaAgendaEPacienteNome(this.pacienteNomeFiltro);
				}
			}
			else
			{
				retornoBuscaFiltro = buscarPorDentistaAgenda();
			}
		}
		else if(getAgendamentoDTO().getPaciente().getIdPaciente() != null)
		{
			retornoBuscaFiltro = buscarPorPaciente();
		}
		else if(getAgendamentoDTO().getDentistaAgenda().getDentista().getIdDentista() != null)
		{
			retornoBuscaFiltro = buscarPorDentista();
		}
		else
		{
			retornoBuscaFiltro = buscarPorDentistaAgendaESituacaoAgrupada();
		}
	
		return retornoBuscaFiltro;
	}
	
	public List<AgendamentoDTO> buscarAgendamentos()
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
			agendamentoDTOsBusca = this.agendamentoSB.buscarAtivos(getEstabelecimentoFiltroDTO().getIdEstabelecimento());
			
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}
	
	//2.2.00
	public List<AgendamentoDTO> buscarPorDentistaAgendaSituacaoEPacienteNome(ESituacaoAgendamento situacao, String pacienteNome)
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		if(getDentistaAgendaSelecionadoDTO().getIdDentistaAgenda() != null)
		{
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorDentistaAgendaSituacaoEPacienteNome(this.dentistaAgendaSelecionadoDTO.getIdDentistaAgenda(),
					getEstabelecimentoFiltroDTO().getIdEstabelecimento(), situacao, pacienteNome);

		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}		
	
	public List<AgendamentoDTO> buscarPorDentistaAgendaEPacienteNome(String pacienteNome)
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		if(getDentistaAgendaSelecionadoDTO().getIdDentistaAgenda() != null)
		{
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorDentistaAgendaEPacienteNome(this.dentistaAgendaSelecionadoDTO.getIdDentistaAgenda(),
					getEstabelecimentoFiltroDTO().getIdEstabelecimento(), pacienteNome);

		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}		
	
	public List<AgendamentoDTO> buscarPorDentistaAgendaESituacao(ESituacaoAgendamento situacao)
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		if(getDentistaAgendaSelecionadoDTO().getIdDentistaAgenda() != null)
		{
			List<ESituacaoAgendamento> situacaoList = new ArrayList<ESituacaoAgendamento>();
			situacaoList.add(situacao);
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorDentistaAgendaESituacao(this.dentistaAgendaSelecionadoDTO.getIdDentistaAgenda(),
					getEstabelecimentoFiltroDTO().getIdEstabelecimento(), situacaoList);

		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}	
	
	public List<AgendamentoDTO> buscarPorDentistaAgendaESituacaoAgrupada()
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		if(getDentistaAgendaSelecionadoDTO().getIdDentistaAgenda() != null)
		{
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorDentistaAgendaESituacao(this.dentistaAgendaSelecionadoDTO.getIdDentistaAgenda(),
					getEstabelecimentoFiltroDTO().getIdEstabelecimento(), getSituacaoListUsuario());

		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}	
	
	public List<AgendamentoDTO> buscarPorSituacaoEData(String situacao, Date data, Long idEstabelecimento)
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		
		if(!situacao.equals("") && data != null && idEstabelecimento != null && !this.pacienteNomeFiltro.equals(""))
		{
			setPacienteNomeFiltro(!getPacienteNomeFiltro().equals("") ? "%" + this.pacienteNomeFiltro + "%" : "");
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorSituacaoDataEPacienteNome(situacao, data, data, idEstabelecimento, this.pacienteNomeFiltro);

		}
		else if (!situacao.equals("") && data != null && idEstabelecimento != null)
		{
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorSituacaoEData(situacao, data, data, idEstabelecimento);
		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}	
	//
	
	public List<AgendamentoDTO> buscarPorData()
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		
		if(getAgendamentoDTO().getDentistaAgenda().getDataAgenda() != null)
		{
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorData(getAgendamentoDTO().getDentistaAgenda().getDataAgenda(),
					getAgendamentoDTO().getDentistaAgenda().getDataAgenda(), getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}
	
	public List<AgendamentoDTO> buscarPorPaciente()
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		
		if(getAgendamentoDTO().getPaciente().getIdPaciente() != null)
		{
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorPaciente(getAgendamentoDTO().getPaciente().getIdPaciente(), 
					getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}
	
	public List<AgendamentoDTO> buscarPorDentistaAgenda(DentistaAgendaDTO dentistaAgendaDTO)
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		if(dentistaAgendaDTO.getIdDentistaAgenda() != null)
		{
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorDentistaAgenda(dentistaAgendaDTO.getIdDentistaAgenda(),
					getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}
	
	public List<AgendamentoDTO> buscarPorDentistaAgenda()
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		if(getDentistaAgendaSelecionadoDTO().getIdDentistaAgenda() != null)
		{
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorDentistaAgenda(this.dentistaAgendaSelecionadoDTO.getIdDentistaAgenda(),
					getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}
	
	public List<AgendamentoDTO> buscarPorPacienteEDentistaAgendaAgenda()
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		
		if(getDentistaAgendaSelecionadoDTO().getIdDentistaAgenda() != null
				&& getAgendamentoDTO().getPaciente().getIdPaciente() != null)
		{
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorPacienteEDentistaAgenda(this.dentistaAgendaSelecionadoDTO.getIdDentistaAgenda(),
					getAgendamentoDTO().getPaciente().getIdPaciente(), getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}
	
	public List<AgendamentoDTO> buscarPorDentista()
	{
		List<AgendamentoDTO>  agendamentoDTOsBusca = null;
		
		if(getDentistaAgendaSelecionadoDTO().getIdDentistaAgenda() != null)
		{
			agendamentoDTOsBusca = this.agendamentoSB.buscarPorDentista(this.dentistaAgendaSelecionadoDTO.getDentista().getIdDentista(),
					getEstabelecimentoFiltroDTO().getIdEstabelecimento());

		}
		return agendamentoDTOsBusca != null 
				? agendamentoDTOsBusca 
						: new ArrayList<AgendamentoDTO>();
	}
	
	public AgendamentoDTO buscarPeloId()
	{
		AgendamentoDTO agendamentoDTOBusca = null;
		
		if(this.agendamentoDTO.getIdAgendamento() != null)
		{
			agendamentoDTOBusca = this.agendamentoSB.buscarPorId(this.agendamentoDTO.getIdAgendamento());
			if(agendamentoDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return agendamentoDTOBusca;
	}
	
	public AgendamentoDTO getAgendamentoDTO() {
		if(agendamentoDTO == null)
		{
			agendamentoDTO = DTOFactory.getAgendamentoDTO();
		}
		
		return this.agendamentoDTO;
	}

	public void setAgendamentoDTO(AgendamentoDTO agendamentoDTO) {
		this.agendamentoDTO = agendamentoDTO;
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

	public DentistaAgendaDTO getDentistaAgendaSelecionadoDTO() {
		if(dentistaAgendaSelecionadoDTO == null)
		{
			this.dentistaAgendaSelecionadoDTO = DTOFactory.getDentistaAgendaDTO();
		}
		return this.dentistaAgendaSelecionadoDTO;
	}

	public void setDentistaAgendaSelecionadoDTO(DentistaAgendaDTO dentistaAgendaSelecionadoDTO) {
		this.dentistaAgendaSelecionadoDTO = dentistaAgendaSelecionadoDTO;
	}

	public DentistaAgendaDTO getDentistaAgendaReagendamentoDTO() {
		if(dentistaAgendaReagendamentoDTO == null)
		{
			this.dentistaAgendaReagendamentoDTO = DTOFactory.getDentistaAgendaDTO();
		}
		
		return this.dentistaAgendaReagendamentoDTO;
	}

	public void setDentistaAgendaReagendamentoDTO(
			DentistaAgendaDTO dentistaAgendaReagendamentoDTO) {
		this.dentistaAgendaReagendamentoDTO = dentistaAgendaReagendamentoDTO;
	}

	public Date getDataFiltro() {
		return this.dataFiltro;
	}

	public void setDataFiltro(Date dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public Date getDataSelecionada() {
		return dataSelecionada;
	}

	public void setDataSelecionada(Date dataSelecionada) {
		this.dataSelecionada = dataSelecionada;
	}

	public boolean isReagendamento() {
		return this.reagendamento;
	}

	public void setReagendamento(boolean reagendamento) {
		this.reagendamento = reagendamento;
	}

	public boolean isDiaBloqueado() {
		if(!DataUtil.getDataFormatada(this.dataSelecionada).equals(DataUtil.getDataFormatada(new Date())))
		{
			return false;
		}
		
		return diaBloqueado;
	}

	public void setDiaBloqueado(boolean diaB) {
		diaBloqueado = diaB;
	}
	
	public boolean isDiaEncerrado() {
		return diaEncerrado;
	}

	public void setDiaEncerrado(boolean diaEncerrado) {
		this.diaEncerrado = diaEncerrado;
	}

	public String getMotivoCancelamento() {
		if(this.motivoCancelamento == null)
		{
			this.motivoCancelamento = "";
		}
		
		return this.motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public EstabelecimentoDTO getEstabelecimentoFiltroDTO() {
		if(this.estabelecimentoFiltroDTO == null)
		{
			this.estabelecimentoFiltroDTO = getFuncionarioLogado().getEstabelecimentoDTO();
		}
		
		return this.estabelecimentoFiltroDTO;
	}

	public void setEstabelecimentoFiltroDTO(EstabelecimentoDTO estabelecimentoFiltroDTO) {
		this.estabelecimentoFiltroDTO = estabelecimentoFiltroDTO;
	}

	public MsgPreEmailDTO getMsgPreEmailSelecionadaDTO() {
		if(this.msgPreEmailSelecionadaDTO == null)
		{
			this.msgPreEmailSelecionadaDTO = DTOFactory.getMsgPreEmailDTO();
		}
		
		return this.msgPreEmailSelecionadaDTO;
	}

	public void setMsgPreEmailSelecionadaDTO(MsgPreEmailDTO msgPreEmailSelecionadaDTO) {
		this.msgPreEmailSelecionadaDTO = msgPreEmailSelecionadaDTO;
	}

	public MsgPreTorpedoDTO getMsgPreTorpedoSelecionadoDTO() {
		if(this.msgPreTorpedoSelecionadoDTO == null)
		{
			this.msgPreTorpedoSelecionadoDTO = DTOFactory.getMsgPreTorpedoDTO();
		}
		
		return this.msgPreTorpedoSelecionadoDTO;
	}

	public void setMsgPreTorpedoSelecionadoDTO(MsgPreTorpedoDTO msgPreTorpedoSelecionadoDTO) {
		this.msgPreTorpedoSelecionadoDTO = msgPreTorpedoSelecionadoDTO;
	}

	public ESituacaoAgendamento getSituacaoFiltro() {
		return situacaoFiltro;
	}

	public void setSituacaoFiltro(ESituacaoAgendamento situacaoFiltro) {
		
		if(situacaoFiltro == null)
		{
			this.situacaoFiltro = situacaoFiltro;
		}
		
		this.situacaoFiltro = situacaoFiltro;
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
