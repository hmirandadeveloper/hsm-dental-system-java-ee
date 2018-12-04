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
@Table(name = "tb_mensalidade_paciente", catalog = "dbsodontosystem")

@NamedQueries({	
	@NamedQuery(name="MensalidadePaciente.buscarTotalPorPlanoEPaciente",
			query="SELECT COUNT(mp)" +
				  " FROM MensalidadePaciente mp" +
				  " WHERE mp.planoPaciente.plano.idPlano = :idPlano" +
				  " AND mp.planoPaciente.paciente.idPaciente = :idPaciente" +
				  " AND mp.ativo = true"),	
	
	@NamedQuery(name="MensalidadePaciente.buscarPorPlanoEPaciente",
			query="SELECT mp" +
				  " FROM MensalidadePaciente mp" +
				  " WHERE mp.planoPaciente.plano.idPlano = :idPlano" +
				  " AND mp.planoPaciente.paciente.idPaciente = :idPaciente" +
				  " AND mp.ativo is true" +
				  " ORDER BY mp.dataMensalidade"),
	
	@NamedQuery(name="MensalidadePaciente.buscarPorPlanoPacienteEMes",
				query="SELECT mp" +
					  " FROM MensalidadePaciente mp" +
					  " WHERE mp.planoPaciente.plano.idPlano = :idPlano" +
					  " AND mp.planoPaciente.paciente.idPaciente = :idPaciente" +
					  " AND mp.planoMensalidade.mes = :mes" +
					  " AND mp.ativo is true" +
			  		  " ORDER BY mp.dataMensalidade"),
					  
	@NamedQuery(name="MensalidadePaciente.buscarPorSitacaoPlanoEPaciente",
				query="SELECT mp" +
					  " FROM MensalidadePaciente mp" +
					  " WHERE mp.planoPaciente.plano.idPlano = :idPlano" +
					  " AND mp.planoPaciente.paciente.idPaciente = :idPaciente" +
					  " AND mp.situacao LIKE :situacao" +
					  " AND mp.ativo is true" +
			  		  " ORDER BY mp.dataMensalidade"),
					  
	@NamedQuery(name="MensalidadePaciente.buscarPorCondicao",
				query="SELECT mp" +
					  " FROM MensalidadePaciente mp" +
					  " WHERE mp.ativo = :condicao" + 
					  " ORDER BY mp.dataMensalidade"),					  
})

public class MensalidadePaciente implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_TOTAL_POR_PLANO_E_PACIENTE = "MensalidadePaciente.buscarTotalPorPlanoEPaciente";
	public static final String NQ_BUSCAR_POR_PLANO_E_PACIENTE = "MensalidadePaciente.buscarPorPlanoEPaciente";
	public static final String NQ_BUSCAR_POR_PLANO_PACIENTE_E_MES = "MensalidadePaciente.buscarPorPlanoPacienteEMes";
	public static final String NQ_BUSCAR_POR_SITUACAO_PLANO_E_PACIENTE = "MensalidadePaciente.buscarPorSitacaoPlanoEPaciente";
	public static final String NQ_BUSCAR_POR_CONDICAO = "MensalidadePaciente.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idMensalidadePaciente;
	private PlanoPaciente planoPaciente;
	private PlanoMensalidade planoMensalidade;
	private Usuario usuario;
	private double valorPago;
	private String situacao;
	private Date dataMensalidade;
	private Date dataMov;
	private boolean ativo;

	public MensalidadePaciente() {
	}

	public MensalidadePaciente(PlanoPaciente planoPaciente,
			PlanoMensalidade planoMensalidade, Usuario usuario,
			double valorPago, String situacao, Date dataMensalidade, Date dataMov, boolean ativo) {
		this.planoPaciente = planoPaciente;
		this.planoMensalidade = planoMensalidade;
		this.usuario = usuario;
		this.valorPago = valorPago;
		this.situacao = situacao;
		this.dataMensalidade = dataMensalidade;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_mensalidade_paciente", unique = true, nullable = false)
	public Long getIdMensalidadePaciente() {
		return this.idMensalidadePaciente;
	}

	public void setIdMensalidadePaciente(Long idMensalidadePaciente) {
		this.idMensalidadePaciente = idMensalidadePaciente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_plano_paciente", nullable = false)
	public PlanoPaciente getPlanoPaciente() {
		return this.planoPaciente;
	}

	public void setPlanoPaciente(PlanoPaciente planoPaciente) {
		this.planoPaciente = planoPaciente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_plano_mensalidade", nullable = false)
	public PlanoMensalidade getPlanoMensalidade() {
		return this.planoMensalidade;
	}

	public void setPlanoMensalidade(PlanoMensalidade planoMensalidade) {
		this.planoMensalidade = planoMensalidade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "valor_pago", nullable = false, precision = 22, scale = 0)
	public double getValorPago() {
		return this.valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	@Column(name = "situacao", nullable = false, length = 1)
	public String getSituacao() {
		return this.situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_mensalidade", nullable = false, length = 10)
	public Date getDataMensalidade() {
		return this.dataMensalidade;
	}

	public void setDataMensalidade(Date dataMensalidade) {
		this.dataMensalidade = dataMensalidade;
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
