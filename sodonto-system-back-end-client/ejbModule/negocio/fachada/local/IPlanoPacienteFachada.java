package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dao.util.Filtro;
import dto.PlanoPacienteDTO;

public interface IPlanoPacienteFachada {
	public abstract void salvar(PlanoPacienteDTO planoPacienteDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract PlanoPacienteDTO alterar(PlanoPacienteDTO planoPacienteDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idPlanoPaciente, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract PlanoPacienteDTO buscarPorId(Long idPlanoPaciente);
	public abstract List<PlanoPacienteDTO> buscarPorPaciente(Long idPaciente);
	public abstract List<PlanoPacienteDTO> buscarPorPlano(Long idPlano);
	public abstract int buscarTotalPorPlano(Long idPlano);
	public abstract List<PlanoPacienteDTO> buscarPorPlano(Long idPlano, Filtro filtro);
	//2.2.00
	public abstract List<PlanoPacienteDTO> buscarPorPlanoEPacienteNome(Long idPlano, String pacienteNome);
	public abstract List<PlanoPacienteDTO> buscarPorPlanoEPacienteNome(Long idPlano, String pacienteNome, Filtro filtro);
	public abstract int buscarTotalPorPlanoEPacienteNome(Long idPlano, String pacienteNome);
	//
	public abstract List<PlanoPacienteDTO> buscarPorPlanoEPaciente(Long idPlano, Long idPaciente);
	public abstract List<PlanoPacienteDTO> buscarAtivos();
	public abstract List<PlanoPacienteDTO> buscarInativos();
	public abstract PlanoPacienteDTO getEntidadeFromList(List<PlanoPacienteDTO> lista);
}
