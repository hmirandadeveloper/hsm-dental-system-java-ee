package dto;

import java.util.Date;

public class CargoDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idCargo;
	private UsuarioDTO usuarioDTO;
	private String nomeCargo;
	private Date dataMov;
	private boolean ativo;

	public CargoDTO() {
	}

	public CargoDTO(UsuarioDTO usuarioDTO, String nomeCargo, Date dataMov,
			boolean ativo) {
		this.usuarioDTO = usuarioDTO;
		this.nomeCargo = nomeCargo;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdCargo() {
		return this.idCargo;
	}

	public void setIdCargo(Long idCargo) {
		this.idCargo = idCargo;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getNomeCargo() {
		return this.nomeCargo;
	}

	public void setNomeCargo(String nomeCargo) {
		if(nomeCargo == null)
		{
			nomeCargo = "";
		}
		
		this.nomeCargo = nomeCargo.toUpperCase().trim();
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
		if(!(obj instanceof CargoDTO))
		{
			return false;
		}
		CargoDTO outro = (CargoDTO) obj;
		if(idCargo == null)
		{
			if(outro.getIdCargo() != null)
			{
				return false;
			}
		}
		else if(!idCargo.equals(outro.getIdCargo()))
		{
			return false;
		}
		
		return true;
	}
}
