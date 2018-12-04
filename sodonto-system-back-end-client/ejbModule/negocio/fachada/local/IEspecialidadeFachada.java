package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.EspecialidadeDTO;

public interface IEspecialidadeFachada {
	public abstract void salvar(EspecialidadeDTO especialidadeDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract EspecialidadeDTO alterar(EspecialidadeDTO especialidadeDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idEspecialidade, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract EspecialidadeDTO buscarPorId(Long idEspecialidade);
	public abstract List<EspecialidadeDTO> buscarPorNome(String nome);
	public abstract List<EspecialidadeDTO> buscarAtivos();
	public abstract List<EspecialidadeDTO> buscarInativos();
	public abstract EspecialidadeDTO getEntidadeFromList(List<EspecialidadeDTO> lista);
}
