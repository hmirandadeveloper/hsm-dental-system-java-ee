package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeInexistenteException;
import negocio.exception.SenhaInvalidaException;
import dto.UsuarioDTO;

public interface IUsuarioFachada {
	public abstract UsuarioDTO alterar(UsuarioDTO usuarioDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException;
	public abstract void inativar(Long idUsuario, EPerfilUsuario perfil) 
			throws EntidadeInexistenteException;
	public abstract void ativarUsuario(UsuarioDTO usuarioDTO)
			throws SenhaInvalidaException;
	public abstract UsuarioDTO buscarPorId(Long idUsuario);
	public abstract UsuarioDTO buscarPorUsuario(String login);
	public abstract List<UsuarioDTO> buscarPorPerfil(EPerfilUsuario perfilUsuario);
	public abstract List<UsuarioDTO> buscarAtivos();
	public abstract List<UsuarioDTO> buscarInativos();
	public abstract UsuarioDTO getEntidadeFromList(List<UsuarioDTO> lista);
}
