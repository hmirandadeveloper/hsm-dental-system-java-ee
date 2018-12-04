package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CpfInvalidoException;
import negocio.exception.EmailInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.PacienteDTO;

public interface IPacienteFachada {
	public abstract void salvar(PacienteDTO pacienteDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException,
			CpfInvalidoException, EmailInvalidoException;
	public abstract PacienteDTO alterar(PacienteDTO pacienteDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException,
			CpfInvalidoException, EmailInvalidoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idPaciente, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract PacienteDTO buscarPorId(Long idPaciente);
	public abstract PacienteDTO buscarTitularPorId(Long idPaciente);
	public abstract List<PacienteDTO> buscarPorDentista(Long idDentista);
	public abstract List<PacienteDTO> buscarTitularPorCpf(String cpf, int limite);
	public abstract List<PacienteDTO> buscarTitularPorRg(String rg, int limite);
	public abstract List<PacienteDTO> buscarTitularPorNome(String nome, int limite);
	public abstract List<PacienteDTO> buscarPorCpf(String cpf, int limite);
	public abstract List<PacienteDTO> buscarPorNome(String nome, int limite);
	public abstract List<PacienteDTO> buscarPorRg(String rg, int limite);
	public abstract List<PacienteDTO> buscarPorPR(Long idPaciente);
	public abstract List<PacienteDTO> buscarTitularAtivo(int limite);
	public abstract List<PacienteDTO> buscarAtivos(int limite);
	public abstract List<PacienteDTO> buscarInativos(int limite);
	public abstract PacienteDTO getEntidadeFromList(List<PacienteDTO> lista);
}
