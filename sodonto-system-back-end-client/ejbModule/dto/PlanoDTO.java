package dto;

import java.util.Date;

public class PlanoDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idPlano;
	private EstabelecimentoDTO estabelecimentoDTO;
	private UsuarioDTO usuarioDTO;
	private String nomePlano;
	private String descricaoPlano;
	private int totalMeses;
	private double valorTotal;
	private Date validade;
	private Date dataMov;
	private boolean ativo;

	public PlanoDTO() {
	}

	public PlanoDTO(EstabelecimentoDTO estabelecimentoDTO, UsuarioDTO usuarioDTO, String nomePlano,
			String descricaoPlano, int totalMeses, double valorTotal,
			Date validade, Date dataMov, boolean ativo) {
		this.estabelecimentoDTO = estabelecimentoDTO;
		this.usuarioDTO = usuarioDTO;
		this.nomePlano = nomePlano;
		this.descricaoPlano = descricaoPlano;
		this.totalMeses = totalMeses;
		this.valorTotal = valorTotal;
		this.validade = validade;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdPlano() {
		return this.idPlano;
	}

	public void setIdPlano(Long idPlano) {
		this.idPlano = idPlano;
	}

	public EstabelecimentoDTO getEstabelecimentoDTO() {
		return estabelecimentoDTO;
	}

	public void setEstabelecimentoDTO(EstabelecimentoDTO estabelecimentoDTO) {
		this.estabelecimentoDTO = estabelecimentoDTO;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getNomePlano() {
		return this.nomePlano;
	}

	public void setNomePlano(String nomePlano) {
		if(nomePlano == null)
		{
			nomePlano = "";
		}
		
		this.nomePlano = nomePlano.toUpperCase().trim();
	}

	public String getDescricaoPlano() {
		return this.descricaoPlano;
	}

	public void setDescricaoPlano(String descricaoPlano) {
		if(descricaoPlano == null)
		{
			descricaoPlano = "";
		}
		
		this.descricaoPlano = descricaoPlano.toUpperCase().trim();
	}

	public int getTotalMeses() {
		return this.totalMeses;
	}

	public void setTotalMeses(int totalMeses) {
		this.totalMeses = totalMeses;
	}

	public double getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Date getValidade() {
		return this.validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
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
		if(!(obj instanceof PlanoDTO))
		{
			return false;
		}
		PlanoDTO outro = (PlanoDTO) obj;
		if(idPlano == null)
		{
			if(outro.getIdPlano() != null)
			{
				return false;
			}
		}
		else if(!idPlano.equals(outro.getIdPlano()))
		{
			return false;
		}
		
		return true;
	}
}
