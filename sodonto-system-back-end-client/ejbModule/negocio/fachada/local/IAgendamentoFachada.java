package negocio.fachada.local;

import java.util.Date;
import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.enums.ESituacaoAgendamento;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.AgendamentoDTO;

public interface IAgendamentoFachada {
	public abstract void salvar(AgendamentoDTO agendamentoDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract AgendamentoDTO alterar(AgendamentoDTO agendamentoDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idAgendamento, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract AgendamentoDTO buscarPorId(Long idAgendamento);
	public abstract List<AgendamentoDTO> buscarPorSituacaoEData(String situacao, Date dataI, Date dataF, Long idEstabelecimento);
	public abstract List<AgendamentoDTO> buscarPorSituacaoEPaciente(String situacao, Long idPaciente, Long idEstabelecimento);
	public abstract List<AgendamentoDTO> buscarPorData(Date dataI, Date dataF, Long idEstabelecimento);
	public abstract List<AgendamentoDTO> buscarPorPaciente(Long idPaciente, Long idEstabelecimento);
	
	//Filtros de Busca de Agendamentos
	public abstract List<AgendamentoDTO> buscarPorDentistaAgenda(Long idDentistaAgenda, Long idEstabelecimento);
	
	//2.2.00
	public abstract List<AgendamentoDTO> buscarPorDentistaAgendaESituacao(Long idDentistaAgenda, Long idEstabelecimento, List<ESituacaoAgendamento> situacaoAgendamentoList);
	public abstract List<AgendamentoDTO> buscarPorDentistaAgendaEPacienteNome(Long idDentistaAgenda, Long idEstabelecimento, String pacienteNome);
	public abstract List<AgendamentoDTO> buscarPorDentistaAgendaSituacaoEPacienteNome(Long idDentistaAgenda, Long idEstabelecimento, ESituacaoAgendamento situacaoAgendamento, String pacienteNome);
	public abstract List<AgendamentoDTO> buscarPorSituacaoDataEPacienteNome(String situacao, Date dataI, Date dataF, Long idEstabelecimento, String pacienteNome);
	//
	
	public abstract List<AgendamentoDTO> buscarPorPacienteEDentistaAgenda(Long idPaciente, Long idDentistaAgenda, Long idEstabelecimento);
	public abstract List<AgendamentoDTO> buscarPorDentista(Long idDentista, Long idEstabelecimento);
	public abstract List<AgendamentoDTO> buscarAtivos(Long idEstabelecimento);
	public abstract List<AgendamentoDTO> buscarInativos(Long idEstabelecimento);
	public abstract AgendamentoDTO getEntidadeFromList(List<AgendamentoDTO> lista);
}
