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
@Table(name = "tb_plano_mensalidade", catalog = "dbsodontosystem")

@NamedQueries({					  							  
	@NamedQuery(name="PlanoMensalidade.buscarPorPlano",
				query="SELECT pm" +
					  " FROM PlanoMensalidade pm" +
					  " WHERE pm.plano.idPlano = :idPlano" +
					  " AND pm.ativo = true"),	
					  
	@NamedQuery(name="PlanoMensalidade.buscarPorPlanoEMes",
				query="SELECT pm" +
					  " FROM PlanoMensalidade pm" +
					  " WHERE pm.plano.idPlano = :idPlano" +
					  " AND pm.mes = :mes"),						  
				  
	@NamedQuery(name="PlanoMensalidade.buscarPorCondicao",
				query="SELECT pm" +
					  " FROM PlanoMensalidade pm" +
					  " WHERE pm.ativo = :condicao"),					  
})

public class PlanoMensalidade implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_PLANO = "PlanoMensalidade.buscarPorPlano";
	public static final String NQ_BUSCAR_POR_PLANO_E_MES = "PlanoMensalidade.buscarPorPlanoEMes";
	public static final String NQ_BUSCAR_POR_CONDICAO = "PlanoMensalidade.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idPlanoMensalidade;
	private Plano plano;
	private int mes;
	private double valorMes;
	private double valorReajustado;
	private String obs;
	private Date dataMov;
	private boolean ativo;

	public PlanoMensalidade() {
	}

	public PlanoMensalidade(Plano plano, int mes, double valorMes,
			double valorReajustado, String obs, Date dataMov, boolean ativo) {
		this.plano = plano;
		this.mes = mes;
		this.valorMes = valorMes;
		this.valorReajustado = valorReajustado;
		this.obs = obs;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_plano_mensalidade", unique = true, nullable = false)
	public Long getIdPlanoMensalidade() {
		return this.idPlanoMensalidade;
	}

	public void setIdPlanoMensalidade(Long idPlanoMensalidade) {
		this.idPlanoMensalidade = idPlanoMensalidade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_plano", nullable = false)
	public Plano getPlano() {
		return this.plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	@Column(name = "mes", nullable = false)
	public int getMes() {
		return this.mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	@Column(name = "valor_mes", nullable = false, precision = 22, scale = 0)
	public double getValorMes() {
		return this.valorMes;
	}

	public void setValorMes(double valorMes) {
		this.valorMes = valorMes;
	}

	@Column(name = "valor_reajustado", nullable = false, precision = 22, scale = 0)
	public double getValorReajustado() {
		return this.valorReajustado;
	}

	public void setValorReajustado(double valorReajustado) {
		this.valorReajustado = valorReajustado;
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
}
