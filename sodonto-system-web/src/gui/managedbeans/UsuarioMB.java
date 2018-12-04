package gui.managedbeans;

import gui.managedbeans.atributo.ConstantesSodontoSystem;
import gui.managedbeans.generics.ManagedBeanGenericBasic;
import gui.util.UsuarioUtil;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeInexistenteException;
import negocio.exception.SenhaInvalidaException;
import negocio.fachada.local.IUsuarioFachada;
import dto.UsuarioDTO;
import dto.factory.DTOFactory;
@ViewScoped
@ManagedBean
public class UsuarioMB extends ManagedBeanGenericBasic {
	
	@EJB
	private IUsuarioFachada usuarioSB;

	private UsuarioDTO usuarioDTO;
	private String senhaAtual;
	private boolean permissaoAlterarSenha;
	
	public void validacaoSenhaAtual()
	{
		if(!getSenhaAtual().equals(""))
		{
			if(getFuncionarioLogado().getUsuarioPerfil().getSenha().equals(UsuarioUtil.criptografarSenha(getSenhaAtual())))
			{
				this.permissaoAlterarSenha = true;
			}
			else
			{
				enviarMenssagemErro(MensagemErro.MSG_ERRO_SENHA_ATUAL_INCORRETA);
			}
		}
		else
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_SENHA_ATUAL_VAZIA);
		}
	}
	
	public String ativarUsuario()
	{
		if(this.usuarioDTO != null)
		{
			try {
				this.usuarioDTO.setSenha(UsuarioUtil.criptografarSenha(this.usuarioDTO.getSenha()));
				this.usuarioSB.ativarUsuario(this.usuarioDTO);
				redirecionar();
				enviarMenssagemInformativa(MensagemInformativa.MSG_ATIVAR_USUARIO);
				
			} catch (SenhaInvalidaException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
			
		}
		return "";
	}
	
	public String alterar()
	{
		try {
			this.usuarioDTO.setSenha(UsuarioUtil.criptografarSenha(this.usuarioDTO.getSenha()));
			this.usuarioSB.alterar(this.usuarioDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.usuarioDTO = null;
			this.permissaoAlterarSenha = false;
		} catch (EntidadeInexistenteException e) {
			enviarMenssagemErro(e.getMessage());
			e.printStackTrace();
		} catch (AtributoIncompletoException e) {
			enviarMenssagemErro(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	public List<UsuarioDTO> buscarTodos()
	{
		List<UsuarioDTO>  usuarioDTOsBusca = null;
		
		if(this.usuarioDTO.getIdUsuario() != null)
		{
			usuarioDTOsBusca = this.usuarioSB.buscarAtivos();
			if(usuarioDTOsBusca != null)
			{
				if(usuarioDTOsBusca.size() <= 0)
				{
					enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
				}
			}
		}
		return usuarioDTOsBusca;
	}
	
	public UsuarioDTO buscarPeloLogin()
	{
		UsuarioDTO usuarioDTOBusca = null;
		
		if(this.usuarioDTO.getIdUsuario() != null)
		{
			usuarioDTOBusca = this.usuarioSB.buscarPorUsuario(this.usuarioDTO.getUsuario());
			if(usuarioDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return usuarioDTOBusca;
	}
	
	public UsuarioDTO buscarPeloId()
	{
		UsuarioDTO usuarioDTOBusca = null;
		
		if(this.usuarioDTO.getIdUsuario() != null)
		{
			usuarioDTOBusca = this.usuarioSB.buscarPorId(this.usuarioDTO.getIdUsuario());
			if(usuarioDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return usuarioDTOBusca;
	}
	
	public void redirecionar()
	{
		super.redirecionar(ConstantesSodontoSystem.SISTEMA_NOME_FILE + "/pages/protected/temporario/ativacao/ativacao-concluida.xhtml");
	}
	
	public void redirecionarLogout()
	{
		logout();
		super.redirecionar(ConstantesSodontoSystem.SISTEMA_NOME_FILE + "/pages/protected/index.xhtml");
	}
	
	public EPerfilUsuario[] getPerfisUsuario()
	{
		EPerfilUsuario[] perfisCadastro = null;
		
		
		if(getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.A
				&& isPermissaoHelioMiranda())
		{
			perfisCadastro = new EPerfilUsuario[5];
			perfisCadastro[0] = EPerfilUsuario.A;
			perfisCadastro[1] = EPerfilUsuario.G;
			perfisCadastro[2] = EPerfilUsuario.D;
			perfisCadastro[3] = EPerfilUsuario.O;
			perfisCadastro[4] = EPerfilUsuario.C;
		}
		else if(getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.A)
		{
			perfisCadastro = new EPerfilUsuario[4];
			perfisCadastro[0] = EPerfilUsuario.G;
			perfisCadastro[1] = EPerfilUsuario.D;
			perfisCadastro[2] = EPerfilUsuario.O;
			perfisCadastro[3] = EPerfilUsuario.C;
		}
		else if (getFuncionarioLogado().getUsuario().getPerfilAtivo() == EPerfilUsuario.G)
		{
			perfisCadastro = new EPerfilUsuario[3];
			perfisCadastro[0] = EPerfilUsuario.D;
			perfisCadastro[1] = EPerfilUsuario.O;
			perfisCadastro[2] = EPerfilUsuario.C;
		}
		
		return perfisCadastro;
	}

	public UsuarioDTO getUsuarioDTO() {
		if(getFuncionarioLogado() != null)
		{
			this.usuarioDTO = getFuncionarioLogado().getUsuarioPerfil();
		}
		else if(this.usuarioDTO == null)
		{
			this.usuarioDTO = DTOFactory.getUsuarioDTO();
		}
		return this.usuarioDTO;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public boolean isPermissaoAlterarSenha() {
		return permissaoAlterarSenha;
	}

	public void setPermissaoAlterarSenha(boolean permissaoAlterarSenha) {
		this.permissaoAlterarSenha = permissaoAlterarSenha;
	}
}
