package dto;

import java.util.Date;

public class MsgPreEmailDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idMsgPreEmail;
	private UsuarioDTO usuarioDTO;
	private String descicao;
	private String titulo;
	private String msg;
	private Date dataMov;
	private boolean ativo;

	public MsgPreEmailDTO() {
	}

	public MsgPreEmailDTO(UsuarioDTO usuarioDTO, String descicao, String titulo,
			String msg, Date dataMov, boolean ativo) {
		this.usuarioDTO = usuarioDTO;
		this.descicao = descicao;
		this.titulo = titulo;
		this.msg = msg;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdMsgPreEmail() {
		return this.idMsgPreEmail;
	}

	public void setIdMsgPreEmail(Long idMsgPreEmail) {
		this.idMsgPreEmail = idMsgPreEmail;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getDescicao() {
		return this.descicao;
	}

	public void setDescicao(String descicao) {
		if(descicao == null)
		{
			descicao = "";
		}
		
		this.descicao = descicao.toUpperCase().trim();
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		if(titulo == null)
		{
			titulo = "";
		}
		
		this.titulo = titulo.toUpperCase().trim();
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		if(msg == null)
		{
			msg = "";
		}

		this.msg = msg.toUpperCase().trim();
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
		if(!(obj instanceof MsgPreEmailDTO))
		{
			return false;
		}
		MsgPreEmailDTO outro = (MsgPreEmailDTO) obj;
		if(idMsgPreEmail == null)
		{
			if(outro.getIdMsgPreEmail() != null)
			{
				return false;
			}
		}
		else if(!idMsgPreEmail.equals(outro.getIdMsgPreEmail()))
		{
			return false;
		}
		
		return true;
	}
}
