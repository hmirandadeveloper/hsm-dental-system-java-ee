package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import dao.util.Filtro;
import entidade.PlanoPaciente;

@Stateless
public class PlanoPacienteDAO extends GenericDAO<PlanoPaciente>{

	public PlanoPacienteDAO() {
		super(PlanoPaciente.class);
	}
	
	public void remover(PlanoPaciente entidade)
	{
		super.remover(entidade.getIdPlanoPaciente(), PlanoPaciente.class);
	}
	
	//2.2.00
	public List<PlanoPaciente> buscarPorPlanoEPacienteNomeComFiltro(Long idPlano, String pacienteNome)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		parametros.put("pacienteNome", pacienteNome);
		
		return buscarResultadosFiltrados(PlanoPaciente.NQ_BUSCAR_POR_PLANO_E_PACIENTE_NOME, parametros);
	}	
	
	public List<PlanoPaciente> buscarPorPlanoEPacienteNomeComFiltro(Long idPlano, String pacienteNome, Filtro filtro)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		parametros.put("pacienteNome", pacienteNome);
		
		return buscarResultadosFiltrados(PlanoPaciente.NQ_BUSCAR_POR_PLANO_E_PACIENTE_NOME, parametros, filtro.getQuantidadeRegistros(), filtro.getPrimeiroRegistro());
	}
	
	public int buscarTotalPorPlanoEPacienteNome(Long idPlano, String pacienteNome)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		parametros.put("pacienteNome", pacienteNome);
		
		return buscarContagemResultado(PlanoPaciente.NQ_BUSCAR_TOTAL_POR_PLANO_E_PACIENTE_NOME, parametros);
	}	
	//
	
	public List<PlanoPaciente> buscarPorPlano(Long idPlano)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		
		return buscarResultadosFiltrados(PlanoPaciente.NQ_BUSCAR_POR_PLANO, parametros);
	}
	
	public List<PlanoPaciente> buscarPorPlanoComFiltro(Long idPlano, Filtro filtro)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		
		return buscarResultadosFiltrados(PlanoPaciente.NQ_BUSCAR_POR_PLANO, parametros, filtro.getQuantidadeRegistros(), filtro.getPrimeiroRegistro());
	}
	
	public int buscarTotalPorPlano(Long idPlano)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		
		return buscarContagemResultado(PlanoPaciente.NQ_BUSCAR_TOTAL_POR_PLANO, parametros);
	}
	
	public List<PlanoPaciente> buscarPorPaciente(Long idPaciente)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPaciente", idPaciente);
		
		return buscarResultadosFiltrados(PlanoPaciente.NQ_BUSCAR_POR_PACIENTE, parametros);
	}
	
	public List<PlanoPaciente> buscarPorPlanoEPaciente(Long idPlano, Long idPaciente)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		parametros.put("idPaciente", idPaciente);
		
		return buscarResultadosFiltrados(PlanoPaciente.NQ_BUSCAR_POR_PLANO_E_PACIENTE, parametros);
	}
	
	public List<PlanoPaciente> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(PlanoPaciente.NQ_BUSCAR_POR_CONDICAO, parametros, 25000, 0);
	}
}
