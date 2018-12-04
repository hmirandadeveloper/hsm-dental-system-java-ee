package dto;

import java.util.Date;

import negocio.constante.enums.ETipoObsPaciente;


public class ObsPacienteDTO implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long idObsPaciente;
	private UsuarioDTO usuarioDTO;
	private PacienteDTO pacienteDTO;
	private Date data;
	private String obs;
	private ETipoObsPaciente tipo;
	private Date dataMov;
	private boolean ativo;

	public ObsPacienteDTO() {
	}

	public ObsPacienteDTO(UsuarioDTO usuarioDTO, PacienteDTO pacienteDTO, Date data, String obs,
			ETipoObsPaciente tipo, Date dataMov, boolean ativo) {
		this.usuarioDTO = usuarioDTO;
		this.pacienteDTO = pacienteDTO;
		this.setData(data);
		this.obs = obs;
		this.tipo = tipo;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdObsPaciente() {
		return this.idObsPaciente;
	}

	public void setIdObsPaciente(Long idObsPaciente) {
		this.idObsPaciente = idObsPaciente;
	}

	public UsuarioDTO getUsuarioDTO() {
		return this.usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public PacienteDTO getPacienteDTO() {
		return this.pacienteDTO;
	}

	public void setPacienteDTO(PacienteDTO pacienteDTO) {
		this.pacienteDTO = pacienteDTO;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		if(obs == null)
		{
			obs = "";
		}
		
		this.obs = obs.toUpperCase().trim();
	}

	public ETipoObsPaciente getTipo() {
		return tipo;
	}

	public void setTipo(ETipoObsPaciente tipo) {
		this.tipo = tipo;
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
		if(!(obj instanceof ObsPacienteDTO))
		{
			return false;
		}
		ObsPacienteDTO outro = (ObsPacienteDTO) obj;
		if(idObsPaciente == null)
		{
			if(outro.getIdObsPaciente() != null)
			{
				return false;
			}
		}
		else if(!idObsPaciente.equals(outro.getIdObsPaciente()))
		{
			return false;
		}
		
		return true;
	}
}
