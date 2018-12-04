package persistencia.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.DentistaAgenda;

@Stateless
public class DentistaAgendaDAO extends GenericDAO<DentistaAgenda>{

	public DentistaAgendaDAO() {
		super(DentistaAgenda.class);
	}
	
	public void remover(DentistaAgenda entidade)
	{
		super.remover(entidade.getIdDentistaAgenda(), DentistaAgenda.class);
	}
	
	
	public List<DentistaAgenda> buscarPorData(Date dataI, Date dataF, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("dataI", dataI);
		parametros.put("dataF", dataF);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(DentistaAgenda.NQ_BUSCAR_POR_DATA, parametros);
	}
	
	public List<DentistaAgenda> buscarPorDentista(Long idDentista, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentista", idDentista);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(DentistaAgenda.NQ_BUSCAR_POR_DENTISTA, parametros);
	}
	
	public List<DentistaAgenda> buscarPorDentistaEData(Long idDentista, Date data, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentista", idDentista);
		parametros.put("data", data);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(DentistaAgenda.NQ_BUSCAR_POR_DENTISTA_E_DATA, parametros);
	}
	
	public List<DentistaAgenda> buscarPorDentistaDataEHorario(Long idDentista, Date data, Date horarioI, Date horarioF, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentista", idDentista);
		parametros.put("data", data);
		parametros.put("horarioI", horarioI);
		parametros.put("horarioF", horarioF);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(DentistaAgenda.NQ_BUSCAR_POR_DENTISTA_DATA_E_HORARIO, parametros);
	}
	
	public List<DentistaAgenda> buscarPorCondicao(boolean condicao, Long idEstabelecimento)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(DentistaAgenda.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
