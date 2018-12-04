package dto;

import java.util.Date;

import negocio.constante.enums.EPerfilUsuario;

public class UsuarioDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idUsuario;
	private String usuario;
	private String senha;
	private EPerfilUsuario perfilAtivo;
	private EPerfilUsuario perfilCadastro;
	private Date dataMov;
	private boolean ativo;

	public UsuarioDTO() {
	}

	public UsuarioDTO(String usuario, String senha, 
			EPerfilUsuario perfilAtivo, EPerfilUsuario perfilCadastro, Date dataMov, 
			boolean ativo) {
		this.usuario = usuario;
		this.senha = senha;
		this.perfilAtivo = perfilAtivo;
		this.perfilCadastro = perfilCadastro;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario.trim();
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public EPerfilUsuario getPerfilAtivo() {
		return this.perfilAtivo;
	}

	public void setPerfilAtivo(EPerfilUsuario perfilAtivo) {
		this.perfilAtivo = perfilAtivo;
	}

	public EPerfilUsuario getPerfilCadastro() {
		return this.perfilCadastro;
	}

	public void setPerfilCadastro(EPerfilUsuario perfilCadastro) {
		this.perfilCadastro = perfilCadastro;
	}

	public Date getDataMov() {
		return this.dataMov;
	}

	public void setDataMov(Date dataMov) {
		this.dataMov = dataMov;
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(!(obj instanceof UsuarioDTO))
		{
			return false;
		}
		UsuarioDTO outro = (UsuarioDTO) obj;
		if(idUsuario == null)
		{
			if(outro.getIdUsuario() != null)
			{
				return false;
			}
		}
		else if(!idUsuario.equals(outro.getIdUsuario()))
		{
			return false;
		}
		
		return true;
	}
}
