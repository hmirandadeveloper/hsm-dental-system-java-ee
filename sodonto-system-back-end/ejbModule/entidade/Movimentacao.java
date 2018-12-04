package entidade;

// Generated 27/05/2014 11:53:57 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_movimentacao", catalog = "dbsodontosystem")

@NamedQueries({					  
	@NamedQuery(name="Movimentacao.buscarPorRefMovimentacao",
				query="SELECT mv" +
					  " FROM Movimentacao mv" +
					  " WHERE mv.refMovimentacao LIKE :refMovimentacao" +
					  " AND mv.ativo is true" + 
					  " ORDER BY mv.dataMov DESC"),
					  
	@NamedQuery(name="Movimentacao.buscarPorRefMovimentacaoCaixaEData",
				query="SELECT mv" +
					  " FROM Movimentacao mv" +
					  " WHERE mv.refMovimentacao LIKE :refMovimentacao" +
					  " AND mv.caixa.idCaixa = :idCaixa" +
					  " AND mv.caixa.dataCaixa = :data" +
					  " AND mv.ativo is true" + 
					  " ORDER BY mv.dataMov DESC"),					  
					  
	@NamedQuery(name="Movimentacao.buscarPorTipo",
				query="SELECT mv" +
					  " FROM Movimentacao mv" +
					  " WHERE mv.tipo LIKE :tipo" +
					  " AND mv.ativo is true" + 
					  " ORDER BY mv.dataMov DESC"),
							  
	@NamedQuery(name="Movimentacao.buscarPorCaixa",
				query="SELECT mv" +
					  " FROM Movimentacao mv" +
					  " WHERE mv.caixa.idCaixa = :idCaixa" +
					  " AND mv.ativo is true" + 
					  " ORDER BY mv.dataMov DESC"),							  
					  
	@NamedQuery(name="Movimentacao.buscarPorCondicao",
				query="SELECT mv" +
					  " FROM Movimentacao mv" +
					  " WHERE mv.ativo = :condicao" +
					  " ORDER BY mv.dataMov DESC"),					  
})

public class Movimentacao implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_REF_MOVIMENTACAO = "Movimentacao.buscarPorRefMovimentacao";
	public static final String NQ_BUSCAR_POR_REF_MOVIMENTACAO_CAIXA_E_DATA = "Movimentacao.buscarPorRefMovimentacaoCaixaEData";
	public static final String NQ_BUSCAR_POR_TIPO = "Movimentacao.buscarPorTipo";
	public static final String NQ_BUSCAR_POR_CAIXA = "Movimentacao.buscarPorCaixa";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Movimentacao.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idMovimentacao;
	private Caixa caixa;
	private Usuario usuario;
	private String tipo;
	private Double valor;
	private String refMovimentacao;
	private String obs;
	private Date dataMov;
	private Boolean ativo;

	public Movimentacao() {
	}

	public Movimentacao(Caixa caixa, Usuario usuario) {
		this.caixa = caixa;
		this.usuario = usuario;
	}

	public Movimentacao(Caixa caixa, Usuario usuario, String tipo,
			Double valor, String refMovimentacao, String obs, Date dataMov,
			Boolean ativo) {
		this.caixa = caixa;
		this.usuario = usuario;
		this.tipo = tipo;
		this.valor = valor;
		this.refMovimentacao = refMovimentacao;
		this.obs = obs;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_movimentacao", unique = true, nullable = false)
	public Long getIdMovimentacao() {
		return this.idMovimentacao;
	}

	public void setIdMovimentacao(Long idMovimentacao) {
		this.idMovimentacao = idMovimentacao;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade= {CascadeType.MERGE})
	@JoinColumn(name = "id_caixa", nullable = false)
	public Caixa getCaixa() {
		return this.caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "tipo", length = 1, nullable = false)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "valor", precision = 22, scale = 0, nullable = false)
	public Double getValor() {
		return this.valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Column(name = "ref_movimentacao", length = 150, nullable = false)
	public String getRefMovimentacao() {
		return this.refMovimentacao;
	}

	public void setRefMovimentacao(String refMovimentacao) {
		this.refMovimentacao = refMovimentacao;
	}

	@Column(name = "obs", length = 300)
	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_mov", length = 19, nullable = false)
	public Date getDataMov() {
		return this.dataMov;
	}

	public void setDataMov(Date dataMov) {
		this.dataMov = dataMov;
	}

	@Column(name = "ativo")
	public Boolean getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
