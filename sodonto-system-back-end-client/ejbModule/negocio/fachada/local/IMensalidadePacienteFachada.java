package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.MensalidadePacienteDTO;

public interface IMensalidadePacienteFachada {
	public abstract void salvar(MensalidadePacienteDTO mensalidadePacienteDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract MensalidadePacienteDTO alterar(MensalidadePacienteDTO mensalidadePacienteDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idMensalidadePaciente, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract MensalidadePacienteDTO buscarPorId(Long idMensalidadePaciente);
	public abstract int buscarTotalPorPlanoEPaciente(Long idPlano, Long idPaciente);
	public abstract List<MensalidadePacienteDTO> buscarPorPlanoEPaciente(Long idPlano, Long idPaciente);
	public abstract List<MensalidadePacienteDTO> buscarPorPlanoPacienteEMes(Long idPlano, Long idPaciente, int mes);
	public abstract List<MensalidadePacienteDTO> buscarPorSituacaoPlanoEPaciente(String situacao, Long idPlano, Long idPaciente);
	public abstract List<MensalidadePacienteDTO> buscarAtivos();
	public abstract List<MensalidadePacienteDTO> buscarInativos();
	public abstract MensalidadePacienteDTO getEntidadeFromList(List<MensalidadePacienteDTO> lista);
}
