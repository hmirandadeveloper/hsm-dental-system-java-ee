package dto;

import java.util.Date;

import negocio.util.DataUtil;

public class CaixaDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idCaixa;
	private EstabelecimentoDTO estabelecimentoDTO;
	private UsuarioDTO usuarioAbertura;
	private UsuarioDTO usuarioFechamento;
	private Date dataCaixa;
	private int numeroOrdem;
	private double valorTotal;
	private String obs;
	private Date dataMov;
	private boolean ativo;

	public CaixaDTO() {
	}

	public CaixaDTO(EstabelecimentoDTO estabelecimentoDTO, UsuarioDTO tbUsuarioByIdUsuarioAbertura,
			UsuarioDTO tbUsuarioByIdUsuarioFechamento, Date dataCaixa,
			int numeroOrdem, double valorTotal, Date dataMov, boolean ativo) {
		this.estabelecimentoDTO = estabelecimentoDTO;
		this.usuarioAbertura = tbUsuarioByIdUsuarioAbertura;
		this.usuarioFechamento = tbUsuarioByIdUsuarioFechamento;
		this.dataCaixa = dataCaixa;
		this.numeroOrdem = numeroOrdem;
		this.valorTotal = valorTotal;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdCaixa() {
		return this.idCaixa;
	}

	public void setIdCaixa(Long idCaixa) {
		this.idCaixa = idCaixa;
	}
	
	public EstabelecimentoDTO getEstabelecimentoDTO() {
		return estabelecimentoDTO;
	}

	public void setEstabelecimentoDTO(EstabelecimentoDTO estabelecimentoDTO) {
		this.estabelecimentoDTO = estabelecimentoDTO;
	}

	public UsuarioDTO getUsuarioAbertura() {
		return this.usuarioAbertura;
	}

	public void setUsuarioAbertura(
			UsuarioDTO usuarioAbertura) {
		this.usuarioAbertura = usuarioAbertura;
	}

	public UsuarioDTO getUsuarioFechamento() {
		return this.usuarioFechamento;
	}

	public void setUsuarioFechamento(
			UsuarioDTO usuarioFechamento) {
		this.usuarioFechamento = usuarioFechamento;
	}

	public Date getDataCaixa() {
		return this.dataCaixa;
	}

	public void setDataCaixa(Date dataCaixa) {
		this.dataCaixa = dataCaixa;
	}

	public int getNumeroOrdem() {
		return this.numeroOrdem;
	}

	public void setNumeroOrdem(int numeroOrdem) {
		this.numeroOrdem = numeroOrdem;
	}

	public double getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public String getCaixaDados()
	{
		return  "Data: " + DataUtil.getDataFormatada(this.dataCaixa)+ " - Caixa Nº.: " + this.numeroOrdem + " - Valor Total: R$ " + this.valorTotal;
	}

	public String getObs() {
		if(this.obs == null)
		{
			this.obs = "";
		}
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
		if(!(obj instanceof CaixaDTO))
		{
			return false;
		}
		CaixaDTO outro = (CaixaDTO) obj;
		if(idCaixa == null)
		{
			if(outro.getIdCaixa() != null)
			{
				return false;
			}
		}
		else if(!idCaixa.equals(outro.getIdCaixa()))
		{
			return false;
		}
		
		return true;
	}
}
