package dto;

import java.util.Date;

public class OperadoraDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idOperadora;
	private UsuarioDTO usuarioDTO;
	private String nomeOperadora;
	private Date dataMov;
	private boolean ativo;

	public OperadoraDTO() {
	}

	public OperadoraDTO(UsuarioDTO usuarioDTO, String nomeOperadora, Date dataMov,
			boolean ativo) {
		this.usuarioDTO = usuarioDTO;
		this.nomeOperadora = nomeOperadora;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdOperadora() {
		return this.idOperadora;
	}

	public void setIdOperadora(Long idOperadora) {
		this.idOperadora = idOperadora;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getNomeOperadora() {
		return this.nomeOperadora;
	}

	public void setNomeOperadora(String nomeOperadora) {
		if(nomeOperadora == null)
		{
			nomeOperadora = "";
		}
		
		this.nomeOperadora = nomeOperadora.toUpperCase().trim();
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
		if(!(obj instanceof OperadoraDTO))
		{
			return false;
		}
		OperadoraDTO outro = (OperadoraDTO) obj;
		if(idOperadora == null)
		{
			if(outro.getIdOperadora() != null)
			{
				return false;
			}
		}
		else if(!idOperadora.equals(outro.getIdOperadora()))
		{
			return false;
		}
		
		return true;
	}
}
