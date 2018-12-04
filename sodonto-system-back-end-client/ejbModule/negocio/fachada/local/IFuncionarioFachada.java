package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CpfInvalidoException;
import negocio.exception.EmailInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.FuncionarioDTO;

public interface IFuncionarioFachada {
	public abstract void salvar(FuncionarioDTO funcionarioDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException,
			CpfInvalidoException, EmailInvalidoException;
	public abstract FuncionarioDTO alterar(FuncionarioDTO funcionarioDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException,
			CpfInvalidoException, EmailInvalidoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idFuncionario, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract FuncionarioDTO buscarPorId(Long idFuncionario);
	public abstract List<FuncionarioDTO> buscarPorCpf(String cpf);
	public abstract List<FuncionarioDTO> buscarPorNome(String nome);
	public abstract List<FuncionarioDTO> buscarPorRg(String rg);
	public abstract List<FuncionarioDTO> buscarPorCargo(Long idCargo);
	public abstract List<FuncionarioDTO> buscarPorPerfil(String perfil);
	public abstract List<FuncionarioDTO> buscarPorIdUsuario(Long idUsuario);
	public abstract List<FuncionarioDTO> buscarAtivos();
	public abstract List<FuncionarioDTO> buscarInativos();
	public abstract FuncionarioDTO getEntidadeFromList(List<FuncionarioDTO> lista);
}
