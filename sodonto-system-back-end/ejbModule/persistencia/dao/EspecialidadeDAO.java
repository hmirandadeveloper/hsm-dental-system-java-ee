package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Especialidade;

@Stateless
public class EspecialidadeDAO extends GenericDAO<Especialidade>{

	public EspecialidadeDAO() {
		super(Especialidade.class);
	}
	
	public void remover(Especialidade entidade)
	{
		super.remover(entidade.getIdEspecialidade(), Especialidade.class);
	}
	
	
	public List<Especialidade> buscarPorNome(String nomeEspecialidade)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nomeEspecialidade", nomeEspecialidade);
		
		return buscarResultadosFiltrados(Especialidade.NQ_BUSCAR_POR_NOME, parametros);
	}
	
	public List<Especialidade> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Especialidade.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
