package dto;

import java.util.Date;

public class MsgPreTorpedoDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idMsgPreTorpedo;
	private UsuarioDTO usuarioDTO;
	private String descricao;
	private String titulo;
	private String msg;
	private Date dataMov;
	private boolean ativo;

	public MsgPreTorpedoDTO() {
	}

	public MsgPreTorpedoDTO(UsuarioDTO usuarioDTO, String descricao,
			String titulo, String msg, Date dataMov, boolean ativo) {
		this.usuarioDTO = usuarioDTO;
		this.descricao = descricao;
		this.titulo = titulo;
		this.msg = msg;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdMsgPreTorpedo() {
		return this.idMsgPreTorpedo;
	}

	public void setIdMsgPreTorpedo(Long idMsgPreTorpedo) {
		this.idMsgPreTorpedo = idMsgPreTorpedo;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		if(descricao == null)
		{
			descricao = "";
		}
		
		this.descricao = descricao.toUpperCase().trim();
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
		
		this.msg = msg.toUpperCase();
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
		if(!(obj instanceof MsgPreTorpedoDTO))
		{
			return false;
		}
		MsgPreTorpedoDTO outro = (MsgPreTorpedoDTO) obj;
		if(idMsgPreTorpedo == null)
		{
			if(outro.getIdMsgPreTorpedo() != null)
			{
				return false;
			}
		}
		else if(!idMsgPreTorpedo.equals(outro.getIdMsgPreTorpedo()))
		{
			return false;
		}
		
		return true;
	}
}
