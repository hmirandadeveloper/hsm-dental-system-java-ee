package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Operadora;

@Stateless
public class OperadoraDAO extends GenericDAO<Operadora>{

	public OperadoraDAO() {
		super(Operadora.class);
	}
	
	public void remover(Operadora entidade)
	{
		super.remover(entidade.getIdOperadora(), Operadora.class);
	}
	
	
	public List<Operadora> buscarPorNome(String nomeOperadora)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nomeOperadora", nomeOperadora);
		
		return buscarResultadosFiltrados(Operadora.NQ_BUSCAR_POR_NOME, parametros);
	}
	
	public List<Operadora> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Operadora.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
