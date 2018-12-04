package dto;

import java.util.Date;

import negocio.constante.enums.ESituacaoAtendimento;

public class AtendimentoDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idAtendimento;
	private AgendamentoDTO agendamentoDTO;
	private UsuarioDTO usuarioDTO;
	private ESituacaoAtendimento situacao;
	private Date dataMov;
	private boolean ativo;

	public AtendimentoDTO() {
	}

	public AtendimentoDTO(AgendamentoDTO agendamentoDTO, UsuarioDTO usuarioDTO,
			ESituacaoAtendimento situacao, Date dataMov, boolean ativo) {
		this.agendamentoDTO = agendamentoDTO;
		this.usuarioDTO = usuarioDTO;
		this.situacao = situacao;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdAtendimento() {
		return this.idAtendimento;
	}

	public void setIdAtendimento(Long idAtendimento) {
		this.idAtendimento = idAtendimento;
	}

	public AgendamentoDTO getAgendamento() {
		return this.agendamentoDTO;
	}

	public void setAgendamento(AgendamentoDTO agendamentoDTO) {
		this.agendamentoDTO = agendamentoDTO;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public ESituacaoAtendimento getSituacao() {
		return this.situacao;
	}

	public void setSituacao(ESituacaoAtendimento situacao) {
		this.situacao = situacao;
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
		
		System.out.println("[SODONTO SYSTEM][MB]Comparação Atendimento...");
		
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(!(obj instanceof AtendimentoDTO))
		{
			return false;
		}
		AtendimentoDTO outro = (AtendimentoDTO) obj;
		if(idAtendimento == null)
		{
			if(outro.getIdAtendimento() != null)
			{
				return false;
			}
		}
		else if(!idAtendimento.equals(outro.getIdAtendimento()))
		{
			return false;
		}
		
		return true;
	}
}
