package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Usuario;

@Stateless
public class UsuarioDAO extends GenericDAO<Usuario>{

	public UsuarioDAO() {
		super(Usuario.class);
	}
	
	
	public void remover(Usuario entidade)
	{
		super.remover(entidade.getIdUsuario(), Usuario.class);
	}
	
	public Usuario buscarPorUsuario(String usuario)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("usuario", usuario);
		
		return buscarUmResultado(Usuario.NQ_BUSCAR_POR_USUARIO, parametros);
	}
	
	public List<Usuario> buscarPorPerfil(String perfil)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("perfil", perfil);
		
		return buscarResultadosFiltrados(Usuario.NQ_BUSCAR_POR_PERFIL, parametros);
	}
	
	public List<Usuario> buscarTodosPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Usuario.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
	
}
