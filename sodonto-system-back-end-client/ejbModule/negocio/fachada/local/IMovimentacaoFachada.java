package negocio.fachada.local;

import java.util.Date;
import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.MovimentacaoDTO;

public interface IMovimentacaoFachada {
	public abstract void salvar(MovimentacaoDTO movimentacaoDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract MovimentacaoDTO alterar(MovimentacaoDTO movimentacaoDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(MovimentacaoDTO movimentacaoDTO, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract MovimentacaoDTO buscarPorId(Long idMovimentacao);
	public abstract List<MovimentacaoDTO> buscarPorRefMovimentacao(String refMovimentacao);
	public abstract List<MovimentacaoDTO> buscarPorRefMovimentacaoCaixaEData(String refMovimentacao, Long idCaixa, Date data);
	public abstract List<MovimentacaoDTO> buscarPorTipo(String tipo);
	public abstract List<MovimentacaoDTO> buscarPorCaixa(Long idCaixa);
	public abstract List<MovimentacaoDTO> buscarAtivos();
	public abstract List<MovimentacaoDTO> buscarInativos();
	public abstract MovimentacaoDTO getEntidadeFromList(List<MovimentacaoDTO> lista);
}
