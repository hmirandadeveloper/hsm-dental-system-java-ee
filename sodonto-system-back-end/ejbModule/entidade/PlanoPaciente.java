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
@Table(name = "tb_plano_paciente", catalog = "dbsodontosystem")

@NamedQueries({  
	@NamedQuery(name="PlanoPaciente.buscarTotalPorPlanoEPacienteNome",
			query="SELECT COUNT(pp)" +
				  " FROM PlanoPaciente pp" +
				  " WHERE pp.plano.idPlano = :idPlano" +
				  " AND CONCAT(TRIM(pp.paciente.nome), ' ',TRIM(pp.paciente.sobrenome)) LIKE :pacienteNome" +
				  " AND pp.paciente.ativo is true" +	
				  " AND pp.ativo = true"),		
	
	@NamedQuery(name="PlanoPaciente.buscarPorPlanoEPacienteNome",
			query="SELECT pp" +
				  " FROM PlanoPaciente pp" +
				  " WHERE pp.plano.idPlano = :idPlano" +
				  " AND CONCAT(TRIM(pp.paciente.nome), ' ',TRIM(pp.paciente.sobrenome)) LIKE :pacienteNome" +				  
				  " AND pp.ativo is true" +
				  " AND pp.paciente.ativo is true" +				  
				  " ORDER BY pp.dataMov DESC"),		
	
	@NamedQuery(name="PlanoPaciente.buscarTotalPorPlano",
			query="SELECT COUNT(pp)" +
				  " FROM PlanoPaciente pp" +
				  " WHERE pp.plano.idPlano = :idPlano" +
				  " AND pp.ativo = true"),	
				  
	@NamedQuery(name="PlanoPaciente.buscarPorPaciente",
				query="SELECT pp" +
					  " FROM PlanoPaciente pp" +
					  " WHERE pp.paciente.idPaciente = :idPaciente" +
					  " AND pp.ativo is true" +
					  " ORDER BY pp.dataMov DESC"),
							  
	@NamedQuery(name="PlanoPaciente.buscarPorPlano",
				query="SELECT pp" +
					  " FROM PlanoPaciente pp" +
					  " WHERE pp.plano.idPlano = :idPlano" +
					  " AND pp.ativo is true" +
					  " ORDER BY pp.dataMov DESC"),	
					  
	@NamedQuery(name="PlanoPaciente.buscarPorPlanoEPaciente",
				query="SELECT pp" +
					  " FROM PlanoPaciente pp" +
					  " WHERE pp.plano.idPlano = :idPlano" +
					  " AND pp.paciente.idPaciente = :idPaciente" +
					  " AND pp.ativo is true" +
					  " ORDER BY pp.dataMov DESC"),				  
					  
	@NamedQuery(name="PlanoPaciente.buscarPorCondicao",
				query="SELECT pp" +
					  " FROM PlanoPaciente pp" +
					  " WHERE pp.ativo = :condicao" +
					  " ORDER BY pp.dataMov DESC"),					  
})

public class PlanoPaciente implements java.io.Serializable {

	//Named Querys - Constantes
	//2.2.00
	public static final String NQ_BUSCAR_TOTAL_POR_PLANO_E_PACIENTE_NOME = "PlanoPaciente.buscarTotalPorPlanoEPacienteNome";
	public static final String NQ_BUSCAR_POR_PLANO_E_PACIENTE_NOME = "PlanoPaciente.buscarPorPlanoEPacienteNome";
	//
	public static final String NQ_BUSCAR_POR_PACIENTE = "PlanoPaciente.buscarPorPaciente";
	public static final String NQ_BUSCAR_POR_PLANO = "PlanoPaciente.buscarPorPlano";
	public static final String NQ_BUSCAR_TOTAL_POR_PLANO = "PlanoPaciente.buscarTotalPorPlano";
	public static final String NQ_BUSCAR_POR_PLANO_E_PACIENTE = "PlanoPaciente.buscarPorPlanoEPaciente";
	public static final String NQ_BUSCAR_POR_CONDICAO = "PlanoPaciente.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idPlanoPaciente;
	private Plano plano;
	private Paciente paciente;
	private Usuario usuario;
	private Date dataAssinatura;
	private Date dataMov;
	private boolean ativo;
	private Set<MensalidadePaciente> mensalidadePacientes = new HashSet<MensalidadePaciente>(
			0);

	public PlanoPaciente() {
	}

	public PlanoPaciente(Plano plano, Paciente paciente,
			Usuario usuario, Date dataAssinatura, Date dataMov, boolean ativo) {
		this.plano = plano;
		this.paciente = paciente;
		this.usuario = usuario;
		this.dataAssinatura = dataAssinatura;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public PlanoPaciente(Plano plano, Paciente paciente,
			Usuario usuario, Date dataMov, boolean ativo,
			Set<MensalidadePaciente> mensalidadePacientes) {
		this.plano = plano;
		this.paciente = paciente;
		this.usuario = usuario;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.mensalidadePacientes = mensalidadePacientes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_plano_paciente", unique = true, nullable = false)
	public Long getIdPlanoPaciente() {
		return this.idPlanoPaciente;
	}

	public void setIdPlanoPaciente(Long idPlanoPaciente) {
		this.idPlanoPaciente = idPlanoPaciente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_plano", nullable = false)
	public Plano getPlano() {
		return this.plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_paciente", nullable = false)
	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_assinatura", nullable = false, length = 19)
	public Date getDataAssinatura() {
		return this.dataAssinatura;
	}

	public void setDataAssinatura(Date dataAssinatura) {
		this.dataAssinatura = dataAssinatura;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "planoPaciente")
	public Set<MensalidadePaciente> getMensalidadePacientes() {
		return this.mensalidadePacientes;
	}

	public void setMensalidadePacientes(
			Set<MensalidadePaciente> mensalidadePacientes) {
		this.mensalidadePacientes = mensalidadePacientes;
	}

}
