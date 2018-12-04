package dto;

import java.util.Date;

public class EspecialidadeDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idEspecialidade;
	private UsuarioDTO usuarioDTO;
	private String nomeEspecialidade;
	private Date dataMov;
	private boolean ativo;

	public EspecialidadeDTO() {
	}

	public EspecialidadeDTO(UsuarioDTO usuarioDTO, String nomeEspecialidade,
			Date dataMov, boolean ativo) {
		this.usuarioDTO = usuarioDTO;
		this.nomeEspecialidade = nomeEspecialidade;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdEspecialidade() {
		return this.idEspecialidade;
	}

	public void setIdEspecialidade(Long idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getNomeEspecialidade() {
		return this.nomeEspecialidade;
	}

	public void setNomeEspecialidade(String nomeEspecialidade) {
		if(nomeEspecialidade == null)
		{
			nomeEspecialidade = "";
		}
		
		this.nomeEspecialidade = nomeEspecialidade.toUpperCase().trim();
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
		if(!(obj instanceof EspecialidadeDTO))
		{
			return false;
		}
		EspecialidadeDTO outro = (EspecialidadeDTO) obj;
		if(idEspecialidade == null)
		{
			if(outro.getIdEspecialidade() != null)
			{
				return false;
			}
		}
		else if(!idEspecialidade.equals(outro.getIdEspecialidade()))
		{
			return false;
		}
		
		return true;
	}
}
