package persistencia.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import persistencia.generics.GenericDAO;
import entidade.Movimentacao;

@Stateless
public class MovimentacaoDAO extends GenericDAO<Movimentacao>{

	public MovimentacaoDAO() {
		super(Movimentacao.class);
	}
	
	public void remover(Movimentacao entidade)
	{
		super.remover(entidade.getIdMovimentacao(), Movimentacao.class);
	}
	
	
	public List<Movimentacao> buscarPorRefMovimentacao(String refMovimentacao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("refMovimentacao", refMovimentacao);
		
		return buscarResultadosFiltrados(Movimentacao.NQ_BUSCAR_POR_REF_MOVIMENTACAO, parametros);
	}
	
	public List<Movimentacao> buscarPorRefMovimentacaoCaixaEData(String refMovimentacao, Long idCaixa, Date data)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("refMovimentacao", refMovimentacao);
		parametros.put("idCaixa", idCaixa);
		parametros.put("data", data);
		
		return buscarResultadosFiltrados(Movimentacao.NQ_BUSCAR_POR_REF_MOVIMENTACAO_CAIXA_E_DATA, parametros);
	}
	
	public List<Movimentacao> buscarPorTipo(String tipo)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("tipo", tipo);
		
		return buscarResultadosFiltrados(Movimentacao.NQ_BUSCAR_POR_TIPO, parametros);
	}
	
	public List<Movimentacao> buscarPorCaixa(Long idCaixa)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("idCaixa", idCaixa);
		
		return buscarResultadosFiltrados(Movimentacao.NQ_BUSCAR_POR_CAIXA, parametros);
	}
	
	public List<Movimentacao> buscarPorCondicao(boolean condicao)
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("condicao", condicao);
		
		return buscarResultadosFiltrados(Movimentacao.NQ_BUSCAR_POR_CONDICAO, parametros);
	}
}
