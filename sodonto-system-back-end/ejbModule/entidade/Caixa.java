package entidade;

// Generated 27/05/2014 11:53:57 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_caixa", catalog = "dbsodontosystem")

@NamedQueries({					  
	@NamedQuery(name="Caixa.buscarPorData",
				query="SELECT cx" +
					  " FROM Caixa cx" +
					  " WHERE cx.dataCaixa BETWEEN :dataI AND :dataF" +
					  " AND cx.ativo = true"),
					  
	@NamedQuery(name="Caixa.buscarEmAbertoPorDataEUsuario",
				query="SELECT cx" +
					  " FROM Caixa cx" +
					  " WHERE cx.dataCaixa BETWEEN :dataI AND :dataF" +
					  " AND cx.usuarioFechamento is null" +
					  " AND cx.usuarioAbertura.idUsuario = :idUsuario" +
					  " AND cx.ativo = true"),	
					  
	@NamedQuery(name="Caixa.buscarEmAbertoPorUsuario",
				query="SELECT cx" +
					  " FROM Caixa cx" +
					  " WHERE cx.usuarioAbertura.idUsuario = :idUsuario" +
					  " AND cx.usuarioFechamento is null" +
					  " AND cx.ativo = true"),						  
					  
	@NamedQuery(name="Caixa.buscarPorDataEOrdem",
				query="SELECT cx" +
					  " FROM Caixa cx" +
					  " WHERE cx.dataCaixa = :data" +
					  " AND cx.numeroOrdem = :numeroOrdem" +
					  " AND cx.ativo = true"),
							  
	@NamedQuery(name="Caixa.buscarMaxOrdemData",
				query="SELECT MAX(cx.numeroOrdem)" +
					  " FROM Caixa cx" +
					  " WHERE cx.dataCaixa = :data" +
					  " AND cx.ativo = true"),
					  
	@NamedQuery(name="Caixa.buscarPorUsuarioA",
				query="SELECT cx" +
					  " FROM Caixa cx" +
					  " WHERE cx.usuarioAbertura.idUsuario = :idUsuarioA" +
					  " AND cx.ativo = true"),
					  
	@NamedQuery(name="Caixa.buscarPorUsuarioAEmData",
				query="SELECT cx" +
					  " FROM Caixa cx" +
					  " WHERE cx.usuarioAbertura.idUsuario = :idUsuarioA" +
					  " AND cx.dataCaixa = :data" +
					  " AND cx.ativo = true"),					  
							  
	@NamedQuery(name="Caixa.buscarPorUsuarioF",
				query="SELECT cx" +
					  " FROM Caixa cx" +
					  " WHERE cx.usuarioFechamento.idUsuario = :idUsuarioF" +
					  " AND cx.ativo = true"),							  
					  
	@NamedQuery(name="Caixa.buscarPorCondicao",
				query="SELECT cx" +
					  " FROM Caixa cx" +
					  " WHERE cx.ativo = :condicao"),					  
})

public class Caixa implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_DATA = "Caixa.buscarPorData";
	public static final String NQ_BUSCAR_EM_ABERTO_POR_DATA_E_USUARIO = "Caixa.buscarEmAbertoPorDataEUsuario";
	public static final String NQ_BUSCAR_EM_ABERTO_POR_USUARIO = "Caixa.buscarEmAbertoPorUsuario";
	public static final String NQ_BUSCAR_POR_DATA_E_ORDEM = "Caixa.buscarPorDataEOrdem";
	public static final String NQ_BUSCAR_POR_MAX_ORDEM_DATA = "Caixa.buscarMaxOrdemData";
	public static final String NQ_BUSCAR_POR_USUARIO_A = "Caixa.buscarPorUsuarioA";
	public static final String NQ_BUSCAR_POR_USUARIO_A_EM_DATA = "Caixa.buscarPorUsuarioAEmData";
	public static final String NQ_BUSCAR_POR_USUARIO_F = "Caixa.buscarPorUsuarioF";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Caixa.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idCaixa;
	private Estabelecimento estabelecimento;
	private Usuario usuarioAbertura;
	private Usuario usuarioFechamento;
	private Date dataCaixa;
	private int numeroOrdem;
	private double valorTotal;
	private String obs;
	private Date dataMov;
	private boolean ativo;
	private Set<Movimentacao> movimentacoes = new HashSet<Movimentacao>(0);

	public Caixa() {
	}

	public Caixa(Estabelecimento estabelecimento, Usuario usuarioAbertura,
			Usuario usuarioFechamento, Date dataCaixa,
			int numeroOrdem, double valorTotal, Date dataMov, boolean ativo) {
		this.estabelecimento = estabelecimento;
		this.usuarioAbertura = usuarioAbertura;
		this.usuarioFechamento = usuarioFechamento;
		this.dataCaixa = dataCaixa;
		this.numeroOrdem = numeroOrdem;
		this.valorTotal = valorTotal;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Caixa(Estabelecimento estabelecimento, Usuario usuarioAbertura,
			Usuario usuarioFechamento, Date dataCaixa,
			int numeroOrdem, double valorTotal, String obs, Date dataMov,
			boolean ativo, Set<Movimentacao> movimentacoes) {
		this.estabelecimento = estabelecimento;
		this.usuarioAbertura = usuarioAbertura;
		this.usuarioFechamento = usuarioFechamento;
		this.dataCaixa = dataCaixa;
		this.numeroOrdem = numeroOrdem;
		this.valorTotal = valorTotal;
		this.obs = obs;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.movimentacoes = movimentacoes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_caixa", unique = true, nullable = false)
	public Long getIdCaixa() {
		return this.idCaixa;
	}

	public void setIdCaixa(Long idCaixa) {
		this.idCaixa = idCaixa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estabelecimento", nullable = false)
	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_abertura", nullable = false)
	public Usuario getUsuarioAbertura() {
		return this.usuarioAbertura;
	}

	public void setUsuarioAbertura(
			Usuario usuarioAbertura) {
		this.usuarioAbertura = usuarioAbertura;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_fechamento")
	public Usuario getUsuarioFechamento() {
		return this.usuarioFechamento;
	}

	public void setUsuarioFechamento(
			Usuario usuarioFechamento) {
		this.usuarioFechamento = usuarioFechamento;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_caixa", nullable = false, length = 10)
	public Date getDataCaixa() {
		return this.dataCaixa;
	}

	public void setDataCaixa(Date dataCaixa) {
		this.dataCaixa = dataCaixa;
	}

	@Column(name = "numero_ordem", nullable = false)
	public int getNumeroOrdem() {
		return this.numeroOrdem;
	}

	public void setNumeroOrdem(int numeroOrdem) {
		this.numeroOrdem = numeroOrdem;
	}

	@Column(name = "valor_total", nullable = false, precision = 22, scale = 0)
	public double getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Column(name = "obs", length = 250)
	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_mov", nullable = false, length = 19)
	public Date getDataMov() {
		return this.dataMov;
	}

	public void setDataMov(Date dataMov) {
		this.dataMov = dataMov;
	}

	@Column(name = "ativo", nullable = false)
	public boolean isAtivo() {
		return this.ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caixa")
	public Set<Movimentacao> getMovimentacoes() {
		return this.movimentacoes;
	}

	public void setMovimentacoes(Set<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

}
