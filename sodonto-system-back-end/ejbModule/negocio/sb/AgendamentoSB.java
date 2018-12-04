package negocio.sb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.enums.ESituacaoAgendamento;
import negocio.constante.mensagens.MensagemErro;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IAgendamentoFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.AgendamentoDAO;
import dto.AgendamentoDTO;
import dto.conversor.conversores.AgendamentoConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.AgendamentoAtributoValidador;
import entidade.Agendamento;

@Stateless
@Remote(IAgendamentoFachada.class)
public class AgendamentoSB implements IAgendamentoFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private AgendamentoDAO agendamentoDAO;
	
	private AgendamentoConversorDTO agendamentoConversorDTO;
	private AgendamentoAtributoValidador agendamentoAtributoValidador;
	
	public AgendamentoSB()
	{
		this.agendamentoConversorDTO = ConversorDTOFactory.getAgendamentoConversorDTO();
		this.agendamentoAtributoValidador = AtributoValidadorFactory.getAgendamentoAtributoValidador();
	}
	
	@Override
	public void salvar(AgendamentoDTO agendamentoDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.agendamentoAtributoValidador.validarAtributosEmEntidade(agendamentoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Agendamento> agendamentosBusca = this.agendamentoDAO.buscarPorPacienteEDentistaAgenda(
					agendamentoDTO.getPaciente().getIdPaciente(), agendamentoDTO.getDentistaAgenda().getIdDentistaAgenda(), 
					agendamentoDTO.getEstabelecimentoDTO().getIdEstabelecimento());
			Agendamento agendamentoBusca = agendamentosBusca.size() > 0 && agendamentosBusca.size() < 2 ? 
					agendamentosBusca.get(0) : null;

			if(agendamentoBusca != null)
			{
				if(!agendamentoBusca.isAtivo())
				{
					agendamentoDTO.setIdAgendamento(agendamentoBusca.getIdAgendamento());
					agendamentoDTO.setAtivo(true);
					this.agendamentoDAO.atualizar(this.agendamentoConversorDTO.converterDTOEmEntidade(agendamentoDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				agendamentoDTO.setAtivo(true);
				this.agendamentoDAO.salvar(this.agendamentoConversorDTO.converterDTOEmEntidade(agendamentoDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public AgendamentoDTO alterar(AgendamentoDTO agendamentoDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		AgendamentoDTO agendamentoRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.agendamentoAtributoValidador.validarAtributosEmEntidade(agendamentoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Agendamento> agendamentosBusca = this.agendamentoDAO.buscarPorPacienteEDentistaAgenda(
					agendamentoDTO.getPaciente().getIdPaciente(), agendamentoDTO.getDentistaAgenda().getIdDentistaAgenda(), 
					agendamentoDTO.getEstabelecimentoDTO().getIdEstabelecimento());
			Agendamento agendamentoBusca = agendamentosBusca.size() > 0 && agendamentosBusca.size() < 2 ? 
					agendamentosBusca.get(0) : null;
			
			if(agendamentoBusca != null)
			{
				agendamentoBusca = this.agendamentoConversorDTO.converterDTOEmEntidade(agendamentoDTO);
				this.agendamentoDAO.atualizar(agendamentoBusca);
				
				agendamentoRetornoDTO = agendamentoDTO;
			}
			else if(agendamentosBusca.size() > 1)
			{
				throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}
		
		return agendamentoRetornoDTO;
	}

	@Override
	public void inativar(Long idAgendamento, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Agendamento agendamentoBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			agendamentoBusca = this.agendamentoDAO.buscar(idAgendamento);
			if(agendamentoBusca != null)
			{
				try
				{
					this.agendamentoDAO.remover(agendamentoBusca);
				}
				catch(Exception e)
				{
					agendamentoBusca.setAtivo(false);
					this.agendamentoDAO.atualizar(agendamentoBusca);
					e.printStackTrace();
					System.out.println("[SB][SODONTO SYSTEM][ERRO]Item com vículos, impossível remover, " +
							"o mesmo foi, apenas, inativado! (Esse item só pode ser removido " +
							"diretamente no banco de dados)");
				}
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		else if (perfilUsuario == EPerfilUsuario.G)
		{
			agendamentoBusca = this.agendamentoDAO.buscar(idAgendamento);
			if(agendamentoBusca != null)
			{
				agendamentoBusca.setAtivo(false);
				this.agendamentoDAO.atualizar(agendamentoBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public AgendamentoDTO buscarPorId(Long idAgendamento) {
		return this.agendamentoConversorDTO.converterEntidadeEmDTO(
				this.agendamentoDAO.buscar(idAgendamento)
				);
	}
	
	//2.2.00
	@Override
	public List<AgendamentoDTO> buscarPorDentistaAgendaSituacaoEPacienteNome(Long idDentistaAgenda, Long idEstabelecimento, ESituacaoAgendamento situacao, String pacienteNome) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorDentistaAgendaSituacaoEPacienteNome(idDentistaAgenda, idEstabelecimento, situacao.name(), pacienteNome)
				);
	}		
	
	@Override
	public List<AgendamentoDTO> buscarPorDentistaAgendaEPacienteNome(Long idDentistaAgenda, Long idEstabelecimento, String pacienteNome) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorDentistaAgendaEPacienteNome(idDentistaAgenda, idEstabelecimento, pacienteNome)
				);
	}		
	
	@Override
	public List<AgendamentoDTO> buscarPorDentistaAgendaESituacao(Long idDentistaAgenda, Long idEstabelecimento, List<ESituacaoAgendamento> situacaoList) {
		
		List<String> situacaoListString = new ArrayList<String>();
		
		for (ESituacaoAgendamento sa : situacaoList) {
			situacaoListString.add(sa.name());
		}
		
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorDentistaAgendaESituacao(idDentistaAgenda, idEstabelecimento, situacaoListString)
				);
	}	
	
	@Override
	public List<AgendamentoDTO> buscarPorSituacaoDataEPacienteNome(String situacao, Date dataI, Date dataF, Long idEstabelecimento, String pacienteNome) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorSituacaoDataEPacienteNome(situacao, dataI, dataF, idEstabelecimento, pacienteNome)
				);
	}	
	//
	
	@Override
	public List<AgendamentoDTO> buscarPorSituacaoEData(String situacao, Date dataI, Date dataF, Long idEstabelecimento) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorSituacaoEData(situacao, dataI, dataF, idEstabelecimento)
				);
	}
	
	@Override
	public List<AgendamentoDTO> buscarPorSituacaoEPaciente(String situacao, Long idPaciente, Long idEstabelecimento) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorSituacaoEPaciente(situacao, idPaciente, idEstabelecimento)
				);
	}
	
	@Override
	public List<AgendamentoDTO> buscarPorData(Date dataI, Date dataF, Long idEstabelecimento) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorData(dataI, dataF, idEstabelecimento)
				);
	}
	
	@Override
	public List<AgendamentoDTO> buscarPorPaciente(Long idPaciente, Long idEstabelecimento) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorPaciente(idPaciente, idEstabelecimento)
				);
	}
	
	@Override
	public List<AgendamentoDTO> buscarPorDentistaAgenda(Long idDentistaAgenda, Long idEstabelecimento) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorDentistaAgenda(idDentistaAgenda, idEstabelecimento)
				);
	}
	
	@Override
	public List<AgendamentoDTO> buscarPorPacienteEDentistaAgenda(Long idPaciente, Long idDentistaAgenda, Long idEstabelecimento) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorPacienteEDentistaAgenda(idPaciente, idDentistaAgenda, idEstabelecimento)
				);
	}
	
	@Override
	public List<AgendamentoDTO> buscarPorDentista(Long idDentista, Long idEstabelecimento) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorDentista(idDentista, idEstabelecimento)
				);
	}

	@Override
	public List<AgendamentoDTO> buscarAtivos(Long idEstabelecimento) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorCondicao(true, idEstabelecimento)
				);
	}

	@Override
	public List<AgendamentoDTO> buscarInativos(Long idEstabelecimento) {
		return this.agendamentoConversorDTO.converterListEntidadeEmListDTO(
				this.agendamentoDAO.buscarPorCondicao(false, idEstabelecimento)
				);
	}

	@Override
	public AgendamentoDTO getEntidadeFromList(List<AgendamentoDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
