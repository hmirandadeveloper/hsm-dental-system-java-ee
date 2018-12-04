package dto;

import java.util.Date;

import negocio.constante.enums.ESituacaoMensalidadePaciente;

public class MensalidadePacienteDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idMensalidadePaciente;
	private PlanoPacienteDTO planoPacienteDTO;
	private PlanoMensalidadeDTO planoMensalidadeDTO;
	private UsuarioDTO usuarioDTO;
	private double valorPago;
	private ESituacaoMensalidadePaciente situacao;
	private Date dataMensalidade;
	private Date dataMov;
	private boolean ativo;

	public MensalidadePacienteDTO() {
	}

	public MensalidadePacienteDTO(PlanoPacienteDTO planoPacienteDTO,
			PlanoMensalidadeDTO planoMensalidadeDTO, UsuarioDTO usuarioDTO,
			double valorPago, ESituacaoMensalidadePaciente situacao, Date dataMensalidade, 
			Date dataMov, boolean ativo) {
		this.planoPacienteDTO = planoPacienteDTO;
		this.planoMensalidadeDTO = planoMensalidadeDTO;
		this.usuarioDTO = usuarioDTO;
		this.valorPago = valorPago;
		this.situacao = situacao;
		this.dataMensalidade = dataMensalidade;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdMensalidadePaciente() {
		return this.idMensalidadePaciente;
	}

	public void setIdMensalidadePaciente(Long idMensalidadePaciente) {
		this.idMensalidadePaciente = idMensalidadePaciente;
	}

	public PlanoPacienteDTO getPlanoPaciente() {
		return this.planoPacienteDTO;
	}

	public void setPlanoPaciente(PlanoPacienteDTO planoPacienteDTO) {
		this.planoPacienteDTO = planoPacienteDTO;
	}

	public PlanoMensalidadeDTO getPlanoMensalidade() {
		return this.planoMensalidadeDTO;
	}

	public void setPlanoMensalidade(PlanoMensalidadeDTO planoMensalidadeDTO) {
		this.planoMensalidadeDTO = planoMensalidadeDTO;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public double getValorPago() {
		return this.valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public ESituacaoMensalidadePaciente getSituacao() {
		return this.situacao;
	}

	public void setSituacao(ESituacaoMensalidadePaciente situacao) {
		this.situacao = situacao;
	}

	public Date getDataMensalidade() {
		return dataMensalidade;
	}

	public void setDataMensalidade(Date dataMensalidade) {
		this.dataMensalidade = dataMensalidade;
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
		
		System.out.println("[SODONTO SYSTEM][MB]Comparação MensalidadePaciente...");
		
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(!(obj instanceof MensalidadePacienteDTO))
		{
			return false;
		}
		MensalidadePacienteDTO outro = (MensalidadePacienteDTO) obj;
		if(idMensalidadePaciente == null)
		{
			if(outro.getIdMensalidadePaciente() != null)
			{
				return false;
			}
		}
		else if(!idMensalidadePaciente.equals(outro.getIdMensalidadePaciente()))
		{
			return false;
		}
		
		return true;
	}
}
