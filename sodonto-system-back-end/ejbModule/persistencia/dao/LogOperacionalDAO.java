package persistencia.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import negocio.util.DataUtil;
import dao.util.Filtro;
import persistencia.generics.GenericDAO;
import entidade.LogOperacional;

@Stateless
public class LogOperacionalDAO extends GenericDAO<LogOperacional>{

	public LogOperacionalDAO() {
		super(LogOperacional.class);
	}	
	
	public List<LogOperacional> buscarPorFuncionarioNome(String nome, Filtro filtro)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nome", nome);
		
		return buscarResultadosFiltrados(
				LogOperacional.NQ_BUSCAR_POR_NOME_FUNCIONARIO, 
				parametros, 
				filtro.getQuantidadeRegistros(), 
				filtro.getPrimeiroRegistro());
	}
	
	public int buscarTotalPorFuncionarioNome(String nome)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nome", nome);
		
		return buscarContagemResultado(
				LogOperacional.NQ_BUSCAR_TOTAL_POR_NOME_FUNCIONARIO, 
				parametros);
	}
	
	public List<LogOperacional> buscarPorData(Date data, Filtro filtro)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("dataI", DataUtil.setDataInicioDia(data));
		parametros.put("dataF", DataUtil.setDataFinalDia(data));
		
		return buscarResultadosFiltrados(
				LogOperacional.NQ_BUSCAR_POR_DATA, 
				parametros, 
				filtro.getQuantidadeRegistros(), 
				filtro.getPrimeiroRegistro());
	}
	
	public int buscarTotalPorData(Date data)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("dataI", DataUtil.setDataInicioDia(data));
		parametros.put("dataF", DataUtil.setDataFinalDia(data));
		
		return buscarContagemResultado(
				LogOperacional.NQ_BUSCAR_TOTAL_POR_DATA, 
				parametros);
	}
}
