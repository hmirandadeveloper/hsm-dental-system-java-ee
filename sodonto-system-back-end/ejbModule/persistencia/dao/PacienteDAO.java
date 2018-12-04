package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Paciente;

@Stateless
public class PacienteDAO extends GenericDAO<Paciente>{

	public PacienteDAO() {
		super(Paciente.class);
	}
	
	public void remover(Paciente entidade)
	{
		System.out.println("[SODONTO SYSTEM][2.0.00][DAO] Instanciando paciente DAO...");
		super.remover(entidade.getIdPaciente(), Paciente.class);
		System.out.println("[SODONTO SYSTEM][2.0.00][DAO] Instancia Concluída!");
	}
	
	public List<Paciente> buscarPorDentista(Long idDentista)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idDentista", idDentista);
		
		return buscarResultadosFiltrados(Paciente.NQ_BUSCAR_POR_DENTISTA, parametros);
	}
	
	public long buscarUltimoID()
	{	
		return (long)buscarContagemResultado(Paciente.NQ_BUSCAR_ULTIMO_ID, null);
	}
	
	public List<Paciente> buscarPorNome(String nome, int limite)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nome", nome);
		
		return buscarResultadosFiltrados(Paciente.NQ_BUSCAR_POR_NOME, parametros, limite);
	}
	
	public List<Paciente> buscarTitularPorNome(String nome, int limite)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nome", nome);
		
		return buscarResultadosFiltrados(Paciente.NQ_BUSCAR_TITULAR_POR_NOME, parametros, limite);
	}
	
	public Paciente buscarTitularPorId(Long idPaciente)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPaciente", idPaciente);
		
		return buscarUmResultado(Paciente.NQ_BUSCAR_TITULAR_POR_ID, parametros);
	}
	
	public List<Paciente> buscarPorCpfOuRg(String cpf, String rg, int limite)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("cpf", cpf);
		parametros.put("rg", rg);
		
		return buscarResultadosFiltrados(Paciente.NQ_BUSCAR_POR_CPF_OU_RG, parametros, limite);
	}
	
	public List<Paciente> buscarTitularPorCpfOuRg(String cpf, String rg, int limite)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("cpf", cpf);
		parametros.put("rg", rg);
		
		return buscarResultadosFiltrados(Paciente.NQ_BUSCAR_TITULAR_POR_CPF_OU_RG, parametros, limite);
	}
	
	public List<Paciente> buscarPorPR(Long idPaciente)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idPaciente", idPaciente);
		
		return buscarResultadosFiltrados(Paciente.NQ_BUSCAR_POR_PR, parametros);
	}
	
	
	public List<Paciente> buscarPorCondicao(boolean condicao, int limite)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Paciente.NQ_BUSCAR_POR_CONDICAO, parametros, limite);
	}
	
	public List<Paciente> buscarTitularPorCondicao(boolean condicao, int limite)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Paciente.NQ_BUSCAR_TITULAR_POR_CONDICAO, parametros, limite);
	}
}
