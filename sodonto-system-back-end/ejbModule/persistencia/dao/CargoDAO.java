package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Cargo;

@Stateless
public class CargoDAO extends GenericDAO<Cargo>{

	public CargoDAO() {
		super(Cargo.class);
	}
	
	public void remover(Cargo entidade)
	{
		super.remover(entidade.getIdCargo(), Cargo.class);
	}
	
	
	public List<Cargo> buscarPorNome(String nomeCargo)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nomeCargo", nomeCargo);
		
		return buscarResultadosFiltrados(Cargo.NQ_BUSCAR_POR_NOME, parametros);
	}
	
	public List<Cargo> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Cargo.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
