package persistencia.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Agendamento;

@Stateless
public class AgendamentoDAO extends GenericDAO<Agendamento>{

	public AgendamentoDAO() {
		super(Agendamento.class);
	}
	
	public void remover(Agendamento entidade)
	{
		super.remover(entidade.getIdAgendamento(), Agendamento.class);
	}
	
	//2.2.00
	
	public List<Agendamento> buscarPorDentistaAgendaESituacao(Long idDentistaAgenda, Long idEstabelecimento, List<String> situacaoList)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentistaAgenda", idDentistaAgenda);
		parametros.put("idEstabelecimento", idEstabelecimento);
		parametros.put("situacaoList", situacaoList);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_DENTISTA_AGENDA_E_SITUACAO, parametros);
	}
	
	public List<Agendamento> buscarPorDentistaAgendaEPacienteNome(Long idDentistaAgenda, Long idEstabelecimento, String pacienteNome)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentistaAgenda", idDentistaAgenda);
		parametros.put("idEstabelecimento", idEstabelecimento);
		parametros.put("pacienteNome", pacienteNome);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_DENTISTA_AGENDA_E_PACIENTE_NOME, parametros);
	}	
	
	public List<Agendamento> buscarPorDentistaAgendaSituacaoEPacienteNome(Long idDentistaAgenda, Long idEstabelecimento, String situacao, String pacienteNome)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentistaAgenda", idDentistaAgenda);
		parametros.put("idEstabelecimento", idEstabelecimento);
		parametros.put("situacao", situacao);
		parametros.put("pacienteNome", pacienteNome);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_DENTISTA_AGENDA_SITUACAO_E_PACIENTE_NOME, parametros);
	}	
	
	public List<Agendamento> buscarPorSituacaoDataEPacienteNome(String situacao, Date dataI, Date dataF, Long idEstabelecimento, String pacienteNome)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("situacao", situacao);
		parametros.put("dataI", dataI);
		parametros.put("dataF", dataF);
		parametros.put("idEstabelecimento", idEstabelecimento);
		parametros.put("pacienteNome", pacienteNome);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_SITUACAO_DATA_E_PACIENTE_NOME, parametros);
	}
	
	//
	public List<Agendamento> buscarPorSituacaoEData(String situacao, Date dataI, Date dataF, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("situacao", situacao);
		parametros.put("dataI", dataI);
		parametros.put("dataF", dataF);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_SITUACAO_E_DATA, parametros);
	}
	
	public List<Agendamento> buscarPorData(Date dataI, Date dataF, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("dataI", dataI);
		parametros.put("dataF", dataF);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_DATA, parametros);
	}
	
	public List<Agendamento> buscarPorSituacaoEPaciente(String situacao, Long idPaciente, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("situacao", situacao);
		parametros.put("idPaciente", idPaciente);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_SITUACAO_E_PACIENTE, parametros);
	}
	
	public List<Agendamento> buscarPorPaciente(Long idPaciente, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPaciente", idPaciente);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_PACIENTE, parametros);
	}
	
	public List<Agendamento> buscarPorDentistaAgenda(Long idDentistaAgenda, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentistaAgenda", idDentistaAgenda);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_DENTISTA_AGENDA, parametros);
	}
	
	public List<Agendamento> buscarPorPacienteEDentistaAgenda(Long idPaciente, Long idDentistaAgenda, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPaciente", idPaciente);
		parametros.put("idDentistaAgenda", idDentistaAgenda);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_PACIENTE_E_DENTISTA_AGENDA, parametros);
	}
	
	public List<Agendamento> buscarPorDentista(Long idDentista, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentista", idDentista);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_DENTISTA, parametros);
	}
	
	public List<Agendamento> buscarPorCondicao(boolean condicao, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Agendamento.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
