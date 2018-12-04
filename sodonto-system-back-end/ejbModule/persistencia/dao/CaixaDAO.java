package persistencia.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Caixa;

@Stateless
public class CaixaDAO extends GenericDAO<Caixa>{

	public CaixaDAO() {
		super(Caixa.class);
	}
	
	public void remover(Caixa entidade)
	{
		super.remover(entidade.getIdCaixa(), Caixa.class);
	}
	
	
	public List<Caixa> buscarPorData(Date dataI, Date dataF)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("dataI", dataI);
		parametros.put("dataF", dataF);
		
		return buscarResultadosFiltrados(Caixa.NQ_BUSCAR_POR_DATA, parametros);
	}
	
	public List<Caixa> buscarCaixasEmAbertoPorDataEUsuario(Date dataI, Date dataF, Long idUsuario)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("dataI", dataI);
		parametros.put("dataF", dataF);
		parametros.put("idUsuario", idUsuario);
		
		return buscarResultadosFiltrados(Caixa.NQ_BUSCAR_EM_ABERTO_POR_DATA_E_USUARIO, parametros);
	}
	
	public List<Caixa> buscarCaixasEmAbertoPorUsuario(Long idUsuario)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idUsuario", idUsuario);
		
		return buscarResultadosFiltrados(Caixa.NQ_BUSCAR_EM_ABERTO_POR_USUARIO, parametros);
	}
	
	public List<Caixa> buscarPorDataEOrdem(Date data, int numeroOrdem)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("data", data);
		parametros.put("numeroOrdem", numeroOrdem);
		
		return buscarResultadosFiltrados(Caixa.NQ_BUSCAR_POR_DATA_E_ORDEM, parametros);
	}
	
	public int buscarPorMaxOrdemData(Date data)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("data", data);
		return buscarContagemResultado(Caixa.NQ_BUSCAR_POR_MAX_ORDEM_DATA, parametros);
	}
	
	public List<Caixa> buscarPorUsuarioA(Long idUsuarioA)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idUsuarioA", idUsuarioA);
		
		return buscarResultadosFiltrados(Caixa.NQ_BUSCAR_POR_USUARIO_A, parametros);
	}
	
	public List<Caixa> buscarPorUsuarioAEmData(Long idUsuarioA, Date data)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idUsuarioA", idUsuarioA);
		parametros.put("data", data);
		
		return buscarResultadosFiltrados(Caixa.NQ_BUSCAR_POR_USUARIO_A_EM_DATA, parametros);
	}
	
	public List<Caixa> buscarPorUsuarioF(Long idUsuarioF)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idUsuarioF", idUsuarioF);
		
		return buscarResultadosFiltrados(Caixa.NQ_BUSCAR_POR_USUARIO_F, parametros);
	}
	
	public List<Caixa> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Caixa.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
