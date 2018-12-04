package persistencia.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.MsgPreTorpedo;

@Stateless
public class MsgPreTorpedoDAO extends GenericDAO<MsgPreTorpedo>{

	public MsgPreTorpedoDAO() {
		super(MsgPreTorpedo.class);
	}
	
	public void remover(MsgPreTorpedo entidade)
	{
		super.remover(entidade.getIdMsgPreTorpedo(), MsgPreTorpedo.class);
	}
	
	
	public List<MsgPreTorpedo> buscarPorDescricao(String descricao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("descricao", descricao);
		
		return buscarResultadosFiltrados(MsgPreTorpedo.NQ_BUSCAR_POR_DESCRICAO, parametros);
	}
	
	public List<MsgPreTorpedo> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(MsgPreTorpedo.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
