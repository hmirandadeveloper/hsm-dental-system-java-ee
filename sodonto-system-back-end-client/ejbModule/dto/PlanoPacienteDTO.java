package dto;

import java.util.Date;

public class PlanoPacienteDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idPlanoPaciente;
	private PlanoDTO planoDTO;
	private PacienteDTO pacienteDTO;
	private UsuarioDTO usuarioDTO;
	private Date dataAssinatura;
	private Date dataMov;
	private boolean ativo;

	public PlanoPacienteDTO() {
	}

	public PlanoPacienteDTO(PlanoDTO planoDTO, PacienteDTO pacienteDTO,
			UsuarioDTO usuarioDTO, Date dataAssinatura, Date dataMov, boolean ativo) {
		this.planoDTO = planoDTO;
		this.pacienteDTO = pacienteDTO;
		this.usuarioDTO = usuarioDTO;
		this.setDataAssinatura(dataAssinatura);
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdPlanoPaciente() {
		return this.idPlanoPaciente;
	}

	public void setIdPlanoPaciente(Long idPlanoPaciente) {
		this.idPlanoPaciente = idPlanoPaciente;
	}

	public PlanoDTO getPlano() {
		return this.planoDTO;
	}

	public void setPlano(PlanoDTO planoDTO) {
		this.planoDTO = planoDTO;
	}

	public PacienteDTO getPaciente() {
		return this.pacienteDTO;
	}

	public void setPaciente(PacienteDTO pacienteDTO) {
		this.pacienteDTO = pacienteDTO;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public Date getDataAssinatura() {
		return dataAssinatura;
	}

	public void setDataAssinatura(Date dataAssinatura) {
		this.dataAssinatura = dataAssinatura;
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
		if(!(obj instanceof PlanoPacienteDTO))
		{
			return false;
		}
		PlanoPacienteDTO outro = (PlanoPacienteDTO) obj;
		if(idPlanoPaciente == null)
		{
			if(outro.getIdPlanoPaciente() != null)
			{
				return false;
			}
		}
		else if(!idPlanoPaciente.equals(outro.getIdPlanoPaciente()))
		{
			return false;
		}
		
		return true;
	}
}
