package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.MsgPreEmail;

@Stateless
public class MsgPreEmailDAO extends GenericDAO<MsgPreEmail>{

	public MsgPreEmailDAO() {
		super(MsgPreEmail.class);
	}
	
	public void remover(MsgPreEmail entidade)
	{
		super.remover(entidade.getIdMsgPreEmail(), MsgPreEmail.class);
	}
	
	
	public List<MsgPreEmail> buscarPorDescricao(String descricao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("descricao", descricao);
		
		return buscarResultadosFiltrados(MsgPreEmail.NQ_BUSCAR_POR_DESCRICAO, parametros);
	}
	
	public List<MsgPreEmail> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(MsgPreEmail.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
