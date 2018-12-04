package dto;

import java.util.Date;

public class PlanoMensalidadeDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idPlanoMensalidade;
	private PlanoDTO planoDTO;
	private int mes;
	private double valorMes;
	private double valorReajustado;
	private String obs;
	private Date dataMov;
	private boolean ativo;

	public PlanoMensalidadeDTO() {
	}

	public PlanoMensalidadeDTO(PlanoDTO planoDTO, int mes, double valorMes,
			double valorReajustado, String obs, Date dataMov, boolean ativo) {
		this.planoDTO = planoDTO;
		this.mes = mes;
		this.valorMes = valorMes;
		this.valorReajustado = valorReajustado;
		this.obs = obs;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdPlanoMensalidade() {
		return this.idPlanoMensalidade;
	}

	public void setIdPlanoMensalidade(Long idPlanoMensalidade) {
		this.idPlanoMensalidade = idPlanoMensalidade;
	}

	public PlanoDTO getPlano() {
		return this.planoDTO;
	}

	public void setPlano(PlanoDTO planoDTO) {
		this.planoDTO = planoDTO;
	}

	public int getMes() {
		return this.mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public double getValorMes() {
		return this.valorMes;
	}

	public void setValorMes(double valorMes) {
		this.valorMes = valorMes;
	}

	public double getValorReajustado() {
		return this.valorReajustado;
	}

	public void setValorReajustado(double valorReajustado) {
		this.valorReajustado = valorReajustado;
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
		if(!(obj instanceof PlanoMensalidadeDTO))
		{
			return false;
		}
		PlanoMensalidadeDTO outro = (PlanoMensalidadeDTO) obj;
		if(idPlanoMensalidade == null)
		{
			if(outro.getIdPlanoMensalidade() != null)
			{
				return false;
			}
		}
		else if(!idPlanoMensalidade.equals(outro.getIdPlanoMensalidade()))
		{
			return false;
		}
		
		return true;
	}
}
