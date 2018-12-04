package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.MensalidadePaciente;

@Stateless
public class MensalidadePacienteDAO extends GenericDAO<MensalidadePaciente>{

	public MensalidadePacienteDAO() {
		super(MensalidadePaciente.class);
	}
	
	public void remover(MensalidadePaciente entidade)
	{
		super.remover(entidade.getIdMensalidadePaciente(), MensalidadePaciente.class);
	}
	
	public int buscarTotalPorPlanoEPaciente(Long idPlano, Long idPaciente)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		parametros.put("idPaciente", idPaciente);
		
		return buscarContagemResultado(MensalidadePaciente.NQ_BUSCAR_TOTAL_POR_PLANO_E_PACIENTE, parametros);
	}
	
	public List<MensalidadePaciente> buscarPorPlanoEPaciente(Long idPlano, Long idPaciente)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		parametros.put("idPaciente", idPaciente);
		
		return buscarResultadosFiltrados(MensalidadePaciente.NQ_BUSCAR_POR_PLANO_E_PACIENTE, parametros);
	}
	
	public List<MensalidadePaciente> buscarPorPlanoPacienteEMes(Long idPlano, Long idPaciente, int mes)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPlano", idPlano);
		parametros.put("idPaciente", idPaciente);
		parametros.put("mes", mes);
		
		return buscarResultadosFiltrados(MensalidadePaciente.NQ_BUSCAR_POR_PLANO_PACIENTE_E_MES, parametros);
	}
	
	public List<MensalidadePaciente> buscarPorSituacaoPlanoEPaciente(String situacao, Long idPlano, Long idPaciente)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("situacao", situacao);
		parametros.put("idPlano", idPlano);
		parametros.put("idPaciente", idPaciente);
		
		return buscarResultadosFiltrados(MensalidadePaciente.NQ_BUSCAR_POR_SITUACAO_PLANO_E_PACIENTE, parametros);
	}
	
	public List<MensalidadePaciente> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(MensalidadePaciente.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
