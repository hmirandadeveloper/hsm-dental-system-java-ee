package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.OperadoraDTO;

public interface IOperadoraFachada {
	public abstract void salvar(OperadoraDTO operadoraDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract OperadoraDTO alterar(OperadoraDTO operadoraDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idOperadora, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract OperadoraDTO buscarPorId(Long idOperadora);
	public abstract List<OperadoraDTO> buscarPorNome(String nome);
	public abstract List<OperadoraDTO> buscarAtivos();
	public abstract List<OperadoraDTO> buscarInativos();
	public abstract OperadoraDTO getEntidadeFromList(List<OperadoraDTO> lista);
}
