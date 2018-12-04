package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Estabelecimento;

@Stateless
public class EstabelecimentoDAO extends GenericDAO<Estabelecimento>{

	public EstabelecimentoDAO() {
		super(Estabelecimento.class);
	}
	
	public void remover(Estabelecimento entidade)
	{
		super.remover(entidade.getIdEstabelecimento(), Estabelecimento.class);
	}
	
	
	public List<Estabelecimento> buscarPorNome(String nome)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nome", nome);
		
		return buscarResultadosFiltrados(Estabelecimento.NQ_BUSCAR_POR_NOME, parametros);
	}
	
	public List<Estabelecimento> buscarPorCnpj(String cnpj)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("cnpj", cnpj);
		
		return buscarResultadosFiltrados(Estabelecimento.NQ_BUSCAR_POR_CNPJ, parametros);
	}

	public List<Estabelecimento> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Estabelecimento.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
