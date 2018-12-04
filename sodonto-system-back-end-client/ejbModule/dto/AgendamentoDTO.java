package dto;

import java.util.Date;

import negocio.constante.enums.ESituacaoAgendamento;

public class AgendamentoDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idAgendamento;
	private EstabelecimentoDTO estabelecimentoDTO;
	private PacienteDTO pacienteDTO;
	private DentistaAgendaDTO dentistaAgendaDTO;
	private UsuarioDTO usuarioDTO;
	private UsuarioDTO usuarioCriacaoDTO;
	private Date dataCriacao;
	private ESituacaoAgendamento situacao;
	private boolean remarcacao;
	private Date dataMov;
	private boolean ativo;

	public AgendamentoDTO() {
	}

	public AgendamentoDTO(EstabelecimentoDTO estabelecimentoDTO, PacienteDTO pacienteDTO,
			DentistaAgendaDTO dentistaAgendaDTO, UsuarioDTO usuarioDTO, 
			UsuarioDTO usuarioCriacaoDTO, ESituacaoAgendamento situacao, Date dataCriacao,
			boolean remarcacao, Date dataMov, boolean ativo) {
		this.estabelecimentoDTO = estabelecimentoDTO;
		this.pacienteDTO = pacienteDTO;
		this.dentistaAgendaDTO = dentistaAgendaDTO;
		this.usuarioDTO = usuarioDTO;
		this.usuarioCriacaoDTO = usuarioCriacaoDTO;
		this.dataCriacao = dataCriacao;
		this.situacao = situacao;
		this.remarcacao = remarcacao;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdAgendamento() {
		return this.idAgendamento;
	}

	public void setIdAgendamento(Long idAgendamento) {
		this.idAgendamento = idAgendamento;
	}
	
	public EstabelecimentoDTO getEstabelecimentoDTO() {
		return estabelecimentoDTO;
	}

	public void setEstabelecimentoDTO(EstabelecimentoDTO estabelecimentoDTO) {
		this.estabelecimentoDTO = estabelecimentoDTO;
	}

	public PacienteDTO getPaciente() {
		return this.pacienteDTO;
	}

	public void setPaciente(PacienteDTO pacienteDTO) {
		this.pacienteDTO = pacienteDTO;
	}

	public DentistaAgendaDTO getDentistaAgenda() {
		return this.dentistaAgendaDTO;
	}

	public void setDentistaAgenda(DentistaAgendaDTO dentistaAgendaDTO) {
		this.dentistaAgendaDTO = dentistaAgendaDTO;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public UsuarioDTO getUsuarioCriacaoDTO() {
		return usuarioCriacaoDTO;
	}

	public void setUsuarioCriacaoDTO(UsuarioDTO usuarioCriacaoDTO) {
		this.usuarioCriacaoDTO = usuarioCriacaoDTO;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public ESituacaoAgendamento getSituacao() {
		return situacao;
	}

	public void setSituacao(ESituacaoAgendamento situacao) {
		this.situacao = situacao;
	}

	public boolean isRemarcacao() {
		return remarcacao;
	}

	public void setRemarcacao(boolean remarcacao) {
		this.remarcacao = remarcacao;
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
		
		System.out.println("[SODONTO SYSTEM][MB]Comparação Agendamento...");
		
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(!(obj instanceof AgendamentoDTO))
		{
			return false;
		}
		AgendamentoDTO outro = (AgendamentoDTO) obj;
		if(idAgendamento == null)
		{
			if(outro.getIdAgendamento() != null)
			{
				return false;
			}
		}
		else if(!idAgendamento.equals(outro.getIdAgendamento()))
		{
			return false;
		}
		
		return true;
	}
}
