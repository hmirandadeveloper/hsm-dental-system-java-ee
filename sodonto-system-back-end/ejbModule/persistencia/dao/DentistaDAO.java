package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Dentista;

@Stateless
public class DentistaDAO extends GenericDAO<Dentista>{

	public DentistaDAO() {
		super(Dentista.class);
	}
	
	public void remover(Dentista entidade)
	{
		super.remover(entidade.getIdDentista(), Dentista.class);
	}
	
	
	public List<Dentista> buscarPorCpfOuCro(String cpf, String cro)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("cpf", cpf);
		parametros.put("cro", cro);
		
		return buscarResultadosFiltrados(Dentista.NQ_BUSCAR_POR_CPF_OU_CRO, parametros);
	}
	
	public List<Dentista> buscarPorNome(String nome)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nome", nome);
		
		return buscarResultadosFiltrados(Dentista.NQ_BUSCAR_POR_NOME, parametros);
	}
	
	public List<Dentista> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Dentista.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
