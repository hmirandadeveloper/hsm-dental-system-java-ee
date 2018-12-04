package negocio.fachada.local;

import java.util.Date;
import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.CaixaDTO;

public interface ICaixaFachada {
	public abstract void salvar(CaixaDTO caixaDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract CaixaDTO alterar(CaixaDTO caixaDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idCaixa, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract CaixaDTO buscarPorId(Long idCaixa);
	public abstract List<CaixaDTO> buscarPorData(Date dataI, Date dataF);
	public abstract List<CaixaDTO> buscarCaixasEmAbertoPorDataEUsuario(Date dataI, Date dataF, Long idUsuario);
	public abstract List<CaixaDTO> buscarCaixasEmAbertoPorUsuario(Long idUsuario);
	public abstract List<CaixaDTO> buscarPorDataEOrdem(Date data, int numeroOrdem);
	public abstract int buscarPorMaxOrdemData(Date data);
	public abstract List<CaixaDTO> buscarPorUsuarioA(Long idUsuarioA);
	public abstract List<CaixaDTO> buscarPorUsuarioAEmData(Long idUsuarioA, Date data);
	public abstract List<CaixaDTO> buscarPorUsuarioF(Long idUsuarioF);
	public abstract List<CaixaDTO> buscarAtivos();
	public abstract List<CaixaDTO> buscarInativos();
	public abstract CaixaDTO getEntidadeFromList(List<CaixaDTO> lista);
}
