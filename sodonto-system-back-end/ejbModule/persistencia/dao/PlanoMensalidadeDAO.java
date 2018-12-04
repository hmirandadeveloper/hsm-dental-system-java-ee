package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.PlanoMensalidade;

@Stateless
public class PlanoMensalidadeDAO extends GenericDAO<PlanoMensalidade>{

	public PlanoMensalidadeDAO() {
		super(PlanoMensalidade.class);
	}
	
	public void remover(PlanoMensalidade entidade)
	{
		super.remover(entidade.getIdPlanoMensalidade(), PlanoMensalidade.class);
	}
	
	
	public List<PlanoMensalidade> buscarPorPlano(Long idPlano)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		
		return buscarResultadosFiltrados(PlanoMensalidade.NQ_BUSCAR_POR_PLANO, parametros);
	}
	
	public List<PlanoMensalidade> buscarPorPlanoEMes(Long idPlano, int mes)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		parametros.put("mes", mes);
		
		return buscarResultadosFiltrados(PlanoMensalidade.NQ_BUSCAR_POR_PLANO_E_MES, parametros);
	}
	
	public List<PlanoMensalidade> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(PlanoMensalidade.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
