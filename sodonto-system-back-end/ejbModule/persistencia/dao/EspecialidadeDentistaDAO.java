package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.EspecialidadeDentista;

@Stateless
public class EspecialidadeDentistaDAO extends GenericDAO<EspecialidadeDentista>{

	public EspecialidadeDentistaDAO() {
		super(EspecialidadeDentista.class);
	}
	
	public void remover(EspecialidadeDentista entidade)
	{
		super.remover(entidade.getIdEspecialidadeDentista(), EspecialidadeDentista.class);
	}
	
	
	public List<EspecialidadeDentista> buscarPorEspecialidade(Long idEspecialidade)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idEspecialidade", idEspecialidade);
		
		return buscarResultadosFiltrados(EspecialidadeDentista.NQ_BUSCAR_POR_ESPECIALIDADE, parametros);
	}
	
	public List<EspecialidadeDentista> buscarPorDentista(Long idDentista)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentista", idDentista);
		
		return buscarResultadosFiltrados(EspecialidadeDentista.NQ_BUSCAR_POR_DENTISTA, parametros);
	}
	
	public List<EspecialidadeDentista> buscarPorEspecialidadeEDentista(Long idEspecialidade, Long idDentista)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idEspecialidade", idEspecialidade);
		parametros.put("idDentista", idDentista);
		
		return buscarResultadosFiltrados(EspecialidadeDentista.NQ_BUSCAR_POR_ESPECIALIDADE_E_DENTISTA, parametros);
	}
	
	public List<EspecialidadeDentista> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(EspecialidadeDentista.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
