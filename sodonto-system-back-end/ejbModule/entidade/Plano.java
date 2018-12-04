package entidade;

// Generated 27/05/2014 11:53:57 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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
@Table(name = "tb_plano", catalog = "dbsodontosystem")

@NamedQueries({					  
	@NamedQuery(name="Plano.buscarVigentes",
			query="SELECT pl" +
				  " FROM Plano pl" +
				  " WHERE pl.validade >= CURRENT_DATE" +
				  " AND pl.estabelecimento.idEstabelecimento = :idEstabelecimento" +
				  " AND pl.ativo = true"),
					  
	@NamedQuery(name="Plano.buscarPorNome",
				query="SELECT pl" +
					  " FROM Plano pl" +
					  " WHERE pl.nomePlano like :nomePlano" +
					  " AND pl.ativo = true"),	
					  
	@NamedQuery(name="Plano.buscarPorValidade",
				query="SELECT pl" +
					  " FROM Plano pl" +
					  " WHERE pl.validade BETWEEN :dataI AND :dataF" +
					  " AND pl.ativo = true"),
					  
	@NamedQuery(name="Plano.buscarPorCondicao",
				query="SELECT pl" +
					  " FROM Plano pl" +
					  " WHERE pl.ativo = :condicao"),					  
})

public class Plano implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_VIGENTES = "Plano.buscarVigentes";
	public static final String NQ_BUSCAR_POR_NOME = "Plano.buscarPorNome";
	public static final String NQ_BUSCAR_POR_VALIDADE = "Plano.buscarPorValidade";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Plano.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idPlano;
	private Estabelecimento estabelecimento;
	private Usuario usuario;
	private String nomePlano;
	private String descricaoPlano;
	private int totalMeses;
	private double valorTotal;
	private Date validade;
	private Date dataMov;
	private boolean ativo;

	public Plano() {
	}

	public Plano(Usuario usuario, String nomePlano,
			String descricaoPlano, int totalMeses, double valorTotal,
			Date validade, Date dataMov, boolean ativo) {
		this.usuario = usuario;
		this.nomePlano = nomePlano;
		this.descricaoPlano = descricaoPlano;
		this.totalMeses = totalMeses;
		this.valorTotal = valorTotal;
		this.validade = validade;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_plano", unique = true, nullable = false)
	public Long getIdPlano() {
		return this.idPlano;
	}

	public void setIdPlano(Long idPlano) {
		this.idPlano = idPlano;
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
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "nome_plano", nullable = false, length = 50)
	public String getNomePlano() {
		return this.nomePlano;
	}

	public void setNomePlano(String nomePlano) {
		this.nomePlano = nomePlano;
	}

	@Column(name = "descricao_plano", nullable = false, length = 500)
	public String getDescricaoPlano() {
		return this.descricaoPlano;
	}

	public void setDescricaoPlano(String descricaoPlano) {
		this.descricaoPlano = descricaoPlano;
	}

	@Column(name = "total_meses", nullable = false)
	public int getTotalMeses() {
		return this.totalMeses;
	}

	public void setTotalMeses(int totalMeses) {
		this.totalMeses = totalMeses;
	}

	@Column(name = "valor_total", nullable = false, precision = 22, scale = 0)
	public double getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "validade", nullable = false, length = 10)
	public Date getValidade() {
		return this.validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
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
}
