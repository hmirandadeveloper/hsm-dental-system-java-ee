package dto;

import java.util.Date;

public class MovimentacaoDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idMovimentacao;
	private CaixaDTO caixaDTO;
	private UsuarioDTO usuarioDTO;
	private String tipo;
	private Double valor;
	private String refMovimentacao;
	private String obs;
	private Date dataMov;
	private Boolean ativo;

	public MovimentacaoDTO() {
	}

	public MovimentacaoDTO(CaixaDTO caixaDTO, UsuarioDTO usuarioDTO) {
		this.caixaDTO = caixaDTO;
		this.usuarioDTO = usuarioDTO;
	}

	public MovimentacaoDTO(CaixaDTO caixaDTO, UsuarioDTO usuarioDTO, String tipo,
			Double valor, String refMovimentacao, String obs, Date dataMov,
			Boolean ativo) {
		this.caixaDTO = caixaDTO;
		this.usuarioDTO = usuarioDTO;
		this.tipo = tipo;
		this.valor = valor;
		this.refMovimentacao = refMovimentacao;
		this.obs = obs;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdMovimentacao() {
		return this.idMovimentacao;
	}

	public void setIdMovimentacao(Long idMovimentacao) {
		this.idMovimentacao = idMovimentacao;
	}

	public CaixaDTO getCaixa() {
		return this.caixaDTO;
	}

	public void setCaixa(CaixaDTO caixaDTO) {
		this.caixaDTO = caixaDTO;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		if(tipo == null)
		{
			tipo = "";
		}
		
		this.tipo = tipo.toUpperCase().trim();
	}

	public Double getValor() {
		return this.valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getRefMovimentacao() {
		return this.refMovimentacao;
	}

	public void setRefMovimentacao(String refMovimentacao) {
		if(refMovimentacao == null)
		{
			refMovimentacao = "";
		}
		
		this.refMovimentacao = refMovimentacao.toUpperCase().trim();
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

	public Date getDataMov() {
		return this.dataMov;
	}

	public void setDataMov(Date dataMov) {
		this.dataMov = dataMov;
	}

	public Boolean getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Boolean ativo) {
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
		if(!(obj instanceof MovimentacaoDTO))
		{
			return false;
		}
		MovimentacaoDTO outro = (MovimentacaoDTO) obj;
		if(idMovimentacao == null)
		{
			if(outro.getIdMovimentacao() != null)
			{
				return false;
			}
		}
		else if(!idMovimentacao.equals(outro.getIdMovimentacao()))
		{
			return false;
		}
		
		return true;
	}
}
