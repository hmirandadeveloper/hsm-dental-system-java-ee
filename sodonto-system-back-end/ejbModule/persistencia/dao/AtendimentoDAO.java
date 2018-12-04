package persistencia.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Atendimento;

@Stateless
public class AtendimentoDAO extends GenericDAO<Atendimento>{

	public AtendimentoDAO() {
		super(Atendimento.class);
	}
	
	public void remover(Atendimento entidade)
	{
		super.remover(entidade.getIdAtendimento(), Atendimento.class);
	}
	
	
	public List<Atendimento> buscarPorAgendamento(Long idAgendamento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idAgendamento", idAgendamento);
		
		return buscarResultadosFiltrados(Atendimento.NQ_BUSCAR_POR_AGENDAMENTO, parametros);
	}
	
	public List<Atendimento> buscarPorData(Date dataI, Date dataF, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("dataI", dataI);
		parametros.put("dataF", dataF);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Atendimento.NQ_BUSCAR_POR_DATA, parametros);
	}
	
	public List<Atendimento> buscarPorPaciente(Long idPaciente, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPaciente", idPaciente);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Atendimento.NQ_BUSCAR_POR_PACIENTE, parametros);
	}
	
	public List<Atendimento> buscarPorSituacao(String situacao, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("situacao", situacao);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Atendimento.NQ_BUSCAR_POR_SITUACAO, parametros);
	}
	
	public List<Atendimento> buscarPorDentista(Long idDentista, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentista", idDentista);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Atendimento.NQ_BUSCAR_POR_DENTISTA, parametros);
	}
	
	public List<Atendimento> buscarPorCondicao(boolean condicao, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Atendimento.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
