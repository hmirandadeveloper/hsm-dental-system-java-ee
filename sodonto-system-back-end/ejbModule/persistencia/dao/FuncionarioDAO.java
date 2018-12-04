package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Funcionario;

@Stateless
public class FuncionarioDAO extends GenericDAO<Funcionario>{

	public FuncionarioDAO() {
		super(Funcionario.class);
	}
	
	public void remover(Funcionario entidade)
	{
		super.remover(entidade.getIdFuncionario(), Funcionario.class);
	}
	
	public List<Funcionario> buscarPorCpfOuRg(String cpf, String rg)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("cpf", cpf);
		parametros.put("rg", rg);
		
		return buscarResultadosFiltrados(Funcionario.NQ_BUSCAR_POR_CPF_OU_RG, parametros);
	}
	
	public List<Funcionario> buscarPorNome(String nome)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("nome", nome);
		
		return buscarResultadosFiltrados(Funcionario.NQ_BUSCAR_POR_NOME, parametros);
	}
	
	public List<Funcionario> buscarPorCargo(Long idCargo)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idCargo", idCargo);
		
		return buscarResultadosFiltrados(Funcionario.NQ_BUSCAR_POR_CARGO, parametros);
	}
	
	public List<Funcionario> buscarPorPerfil(String perfil)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("perfil", perfil);
		
		return buscarResultadosFiltrados(Funcionario.NQ_BUSCAR_POR_PERFIL, parametros);
	}
	
	public List<Funcionario> buscarPorIdUsuario(Long idUsuario)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idUsuario", idUsuario);
		
		return buscarResultadosFiltrados(Funcionario.NQ_BUSCAR_POR_ID_USUARIO, parametros);
	}
	
	public List<Funcionario> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Funcionario.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
