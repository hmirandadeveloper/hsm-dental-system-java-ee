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
@Table(name = "tb_obs_paciente", catalog = "dbsodontosystem")

@NamedQueries({		
	@NamedQuery(name="ObsPaciente.buscarPorPacienteTipoEObs",
			query="SELECT o" +
				  " FROM ObsPaciente o" +
				  " WHERE o.paciente.idPaciente = :idPaciente" +
				  " AND o.tipo = :tipo" +
				  " AND o.obs LIKE :obs" +
				  " AND o.ativo is true" +
				  " ORDER BY o.data DESC, o.dataMov DESC"),
				  
	@NamedQuery(name="ObsPaciente.buscarPorPacienteETipo",
			query="SELECT o" +
				  " FROM ObsPaciente o" +
				  " WHERE o.paciente.idPaciente = :idPaciente" +
				  " AND o.tipo = :tipo" +
				  " AND o.ativo is true" +
				  " ORDER BY o.data DESC, o.dataMov DESC"),
				  
	@NamedQuery(name="ObsPaciente.buscarPorPaciente",
				query="SELECT o" +
					  " FROM ObsPaciente o" +
					  " WHERE o.paciente.idPaciente = :idPaciente" +
					  " AND o.ativo is true" +
					  " ORDER BY o.data DESC, o.dataMov DESC"),
					  
	@NamedQuery(name="ObsPaciente.buscarPorCondicao",
				query="SELECT o" +
					  " FROM ObsPaciente o" +
					  " WHERE o.ativo = :condicao" +
					  " ORDER BY o.data DESC, o.dataMov DESC"),					  
})

public class ObsPaciente implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_PACIENTE = "ObsPaciente.buscarPorPaciente";
	public static final String NQ_BUSCAR_POR_PACIENTE_E_TIPO = "ObsPaciente.buscarPorPacienteETipo";
	public static final String NQ_BUSCAR_POR_PACIENTE_TIPO_E_OBS = "ObsPaciente.buscarPorPacienteTipoEObs";
	public static final String NQ_BUSCAR_POR_CONDICAO = "ObsPaciente.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idObsPaciente;
	private Usuario usuario;
	private Paciente paciente;
	private Date data;
	private String obs;
	private String tipo;
	private Date dataMov;
	private boolean ativo;

	public ObsPaciente() {
	}

	public ObsPaciente(Usuario usuario, Paciente paciente, Date data, String obs,
			String tipo, Date dataMov, boolean ativo) {
		this.usuario = usuario;
		this.paciente = paciente;
		this.data = data;
		this.obs = obs;
		this.tipo = tipo;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_obs_paciente", unique = true, nullable = false)
	public Long getIdObsPaciente() {
		return this.idObsPaciente;
	}

	public void setIdObsPaciente(Long idObsPaciente) {
		this.idObsPaciente = idObsPaciente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_paciente", nullable = false)
	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data", nullable = false, length = 10)
	public Date getData() {
		return this.data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	@Column(name = "obs", nullable = false, length = 500)
	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Column(name = "tipo", nullable = false, length = 1)
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
