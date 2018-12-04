package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.ObsPacienteDTO;

public interface IObsPacienteFachada {
	public abstract void salvar(ObsPacienteDTO obsPacienteDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract ObsPacienteDTO alterar(ObsPacienteDTO obsPacienteDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idObsPaciente, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract ObsPacienteDTO buscarPorId(Long idObsPaciente);
	public abstract List<ObsPacienteDTO> buscarPorPacienteETipo(Long idPaciente, String tipo);
	public abstract List<ObsPacienteDTO> buscarPorPaciente(Long idPaciente);
	public abstract List<ObsPacienteDTO> buscarAtivos();
	public abstract List<ObsPacienteDTO> buscarInativos();
	public abstract ObsPacienteDTO getEntidadeFromList(List<ObsPacienteDTO> lista);
}
