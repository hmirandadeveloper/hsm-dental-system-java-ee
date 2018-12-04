package persistencia.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Plano;

@Stateless
public class PlanoDAO extends GenericDAO<Plano>{

	public PlanoDAO() {
		super(Plano.class);
	}
	
	public void remover(Plano entidade)
	{
		super.remover(entidade.getIdPlano(), Plano.class);
	}
	
	public List<Plano> buscarVigentes(Long idEstabelecimento)
	{	
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idEstabelecimento", idEstabelecimento);
		
		return buscarResultadosFiltrados(Plano.NQ_BUSCAR_VIGENTES, parametros);
	}
	
	public List<Plano> buscarPorNome(String nomePlano)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nomePlano", nomePlano);
		
		return buscarResultadosFiltrados(Plano.NQ_BUSCAR_POR_NOME, parametros);
	}
	
	public List<Plano> buscarPorValidade(Date dataI, Date dataF)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("dataI", dataI);
		parametros.put("dataF", dataF);
		
		return buscarResultadosFiltrados(Plano.NQ_BUSCAR_POR_VALIDADE, parametros);
	}
	
	public List<Plano> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Plano.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
