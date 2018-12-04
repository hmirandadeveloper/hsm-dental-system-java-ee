package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;

import persistencia.generics.GenericDAO;
import entidade.operacional.AtributoOperacional;

@Singleton
public class AtributoOperacionalDAO extends GenericDAO<AtributoOperacional>{

	public AtributoOperacionalDAO() {
		super(AtributoOperacional.class);
	}
	
	public void remover(AtributoOperacional entidade)
	{
		super.remover(entidade.getIdAtributoOperacional(), AtributoOperacional.class);
	}
	
	public List<AtributoOperacional> buscarPorEstado(boolean ativo)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", ativo);

		
		return buscarResultadosFiltrados(AtributoOperacional.NQ_BUSCAR_ATRIBUTOS, parametros);
	}
	
	public AtributoOperacional buscarAtributoSelecionado()
	{		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		return buscarResultadosFiltrados(AtributoOperacional.NQ_CARREGAR_ATRIBUTO_SELECIONADO, parametros).get(0);
	}
}
