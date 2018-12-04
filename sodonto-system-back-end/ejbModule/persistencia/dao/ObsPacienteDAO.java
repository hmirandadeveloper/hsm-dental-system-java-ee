package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.ObsPaciente;

@Stateless
public class ObsPacienteDAO extends GenericDAO<ObsPaciente>{

	public ObsPacienteDAO() {
		super(ObsPaciente.class);
	}
	
	public void remover(ObsPaciente entidade)
	{
		super.remover(entidade.getIdObsPaciente(), ObsPaciente.class);
	}
	
	public List<ObsPaciente> buscarPorPacienteTipoEObs(Long idPaciente, String tipo, String obs)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPaciente", idPaciente);
		parametros.put("tipo", tipo);
		parametros.put("obs", obs);
		
		return buscarResultadosFiltrados (ObsPaciente.NQ_BUSCAR_POR_PACIENTE_TIPO_E_OBS, parametros);
	}
	
	public List<ObsPaciente> buscarPorPacienteETipo(Long idPaciente, String tipo)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPaciente", idPaciente);
		parametros.put("tipo", tipo);
		
		return buscarResultadosFiltrados(ObsPaciente.NQ_BUSCAR_POR_PACIENTE_E_TIPO, parametros);
	}
	
	public List<ObsPaciente> buscarPorPaciente(Long idPaciente)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPaciente", idPaciente);
		
		return buscarResultadosFiltrados(ObsPaciente.NQ_BUSCAR_POR_PACIENTE, parametros);
	}
	
	public List<ObsPaciente> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(ObsPaciente.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
