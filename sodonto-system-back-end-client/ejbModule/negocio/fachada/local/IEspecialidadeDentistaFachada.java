package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.EspecialidadeDentistaDTO;

public interface IEspecialidadeDentistaFachada {
	public abstract void salvar(EspecialidadeDentistaDTO especialidadeDentistaDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract EspecialidadeDentistaDTO alterar(EspecialidadeDentistaDTO especialidadeDentistaDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idEspecialidadeDentista, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract EspecialidadeDentistaDTO buscarPorId(Long idEspecialidadeDentista);
	public abstract List<EspecialidadeDentistaDTO> buscarPorEspecialidade(Long idEspecialidade);
	public abstract List<EspecialidadeDentistaDTO> buscarPorDentista(Long idDentista);
	public abstract List<EspecialidadeDentistaDTO> buscarPorEspecialidadeEDentista(Long idEspecialidade, Long idDentista);
	public abstract List<EspecialidadeDentistaDTO> buscarAtivos();
	public abstract List<EspecialidadeDentistaDTO> buscarInativos();
	public abstract EspecialidadeDentistaDTO getEntidadeFromList(List<EspecialidadeDentistaDTO> lista);
}
