package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.AtributoOperacionalDTO;

public interface IAtributoOperacionalFachada {
	public abstract void salvar(AtributoOperacionalDTO atributoOperacionalDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract AtributoOperacionalDTO alterar(AtributoOperacionalDTO atributoOperacionalDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idAtributoOperacional, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract AtributoOperacionalDTO buscarPorId(Long idAtributoOperacional);
	public abstract AtributoOperacionalDTO buscarAtributoSelecionado();
	public abstract List<AtributoOperacionalDTO> buscarPorEstado(boolean ativo);
}
