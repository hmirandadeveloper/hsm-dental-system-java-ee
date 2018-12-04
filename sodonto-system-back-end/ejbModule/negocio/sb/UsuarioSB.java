package negocio.sb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeInexistenteException;
import negocio.exception.SenhaInvalidaException;
import negocio.fachada.local.IUsuarioFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.UsuarioDAO;
import dto.UsuarioDTO;
import dto.conversor.conversores.UsuarioConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.UsuarioAtributoValidador;
import entidade.Usuario;

@Stateless
@Remote(IUsuarioFachada.class)
public class UsuarioSB implements IUsuarioFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioDAO usuarioDAO;
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private UsuarioAtributoValidador usuarioAtributoValidador;
	
	public UsuarioSB()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.usuarioAtributoValidador = AtributoValidadorFactory.getUsuarioAtributoValidador();
	}
	
	@Override
	public UsuarioDTO alterar(UsuarioDTO usuarioDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException {
		UsuarioDTO usuarioRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.usuarioAtributoValidador.validarAtributosEmEntidade(usuarioDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			Usuario usuarioBusca = this.usuarioDAO.buscar(usuarioDTO.getIdUsuario());
			if(usuarioBusca != null)
			{
				usuarioBusca = this.usuarioConversorDTO.converterDTOEmEntidade(usuarioDTO);
				this.usuarioDAO.atualizar(usuarioBusca);
				
				usuarioRetornoDTO = usuarioDTO;
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}
		
		return usuarioRetornoDTO;
	}

	@Override
	public void inativar(Long idUsuario, EPerfilUsuario perfil)
			throws EntidadeInexistenteException {	
		if(perfil == EPerfilUsuario.A || perfil == EPerfilUsuario.G)
		{
			Usuario usuarioBusca = this.usuarioDAO.buscar(idUsuario);
			
			if(usuarioBusca != null)
			{
				usuarioBusca.setAtivo(false);
				this.usuarioDAO.atualizar(usuarioBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
	}

	@Override
	public void ativarUsuario(UsuarioDTO usuarioDTO)
			throws SenhaInvalidaException {
		System.out.println("[SODONTO SYSTEM][SB] ATIVAÇÃO DE USUÁRIO:");
		System.out.println("[SODONTO SYSTEM][SB]Iniciando busca de Usuário com Usuario: " + usuarioDTO.getUsuario());
		Usuario usuarioBusca = this.usuarioDAO.buscarPorUsuario(usuarioDTO.getUsuario());
		
		if(usuarioBusca != null)
		{
			System.out.println("[SODONTO SYSTEM][SB]Usuário encontrado na base de dados...");
			System.out.println("[SODONTO SYSTEM][SB]Iniciando comparação de senhas...");
			if(!usuarioBusca.getSenha().equals(usuarioDTO.getSenha())
					&& usuarioBusca.getPerfilAtivo().equals(EPerfilUsuario.T.name()))
			{
				System.out.println("[SODONTO SYSTEM][SB]Comparação concluída com sucesso...");
				System.out.println("[SODONTO SYSTEM][SB]Iniciando Ativação...");
				System.out.println("[SODONTO SYSTEM][SB]Novo perfil do usuário: " + usuarioBusca.getPerfilCadastro());
				usuarioBusca.setPerfilAtivo(usuarioBusca.getPerfilCadastro());
				usuarioBusca.setSenha(usuarioDTO.getSenha());
				this.usuarioDAO.atualizar(
						usuarioBusca
						);
				System.out.println("[SODONTO SYSTEM][SB]Ativação concluída!!!");
			}
			else
			{
				throw new SenhaInvalidaException(MensagemErro.MSG_ERRO_SENHA_INVALIDA);
			}
		}
		else
		{
			System.out.println("[SODONTO SYSTEM][SB]Usuário NÃO encontrado na base de dados...");
		}
	}

	@Override
	public UsuarioDTO buscarPorId(Long idUsuario) {
		return this.usuarioConversorDTO.converterEntidadeEmDTO(
				this.usuarioDAO.buscar(idUsuario)
				);
	}

	@Override
	public UsuarioDTO buscarPorUsuario(String usuario) {
		return this.usuarioConversorDTO.converterEntidadeEmDTO(
				this.usuarioDAO.buscarPorUsuario(usuario)
				);
	}

	@Override
	public List<UsuarioDTO> buscarPorPerfil(EPerfilUsuario perfilUsuario) {
		return this.usuarioConversorDTO.converterListEntidadeEmListDTO(
				this.usuarioDAO.buscarPorPerfil(perfilUsuario.name())
				);
	}

	@Override
	public List<UsuarioDTO> buscarAtivos() {
		return this.usuarioConversorDTO.converterListEntidadeEmListDTO(
				this.usuarioDAO.buscarTodosPorCondicao(true)
				);
	}

	@Override
	public List<UsuarioDTO> buscarInativos() {
		return this.usuarioConversorDTO.converterListEntidadeEmListDTO(
				this.usuarioDAO.buscarTodosPorCondicao(false)
				);
	}

	@Override
	public UsuarioDTO getEntidadeFromList(List<UsuarioDTO> lista) {		
		return lista.size() > 0 ? lista.get(0) : null;
	}

}
