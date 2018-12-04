package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CpfInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.DentistaDTO;

public interface IDentistaFachada {
	public abstract void salvar(DentistaDTO dentistaDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException,
			CpfInvalidoException;
	public abstract DentistaDTO alterar(DentistaDTO dentistaDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException,
			CpfInvalidoException, EntidadeCadastradaException;
	public abstract void inativar(Long idDentista, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract DentistaDTO buscarPorId(Long idDentista);
	public abstract List<DentistaDTO> buscarPorCpf(String cpf);
	public abstract List<DentistaDTO> buscarPorNome(String nome);
	public abstract List<DentistaDTO> buscarPorCro(String cro);
	public abstract List<DentistaDTO> buscarAtivos();
	public abstract List<DentistaDTO> buscarInativos();
	public abstract DentistaDTO getEntidadeFromList(List<DentistaDTO> lista);
}
