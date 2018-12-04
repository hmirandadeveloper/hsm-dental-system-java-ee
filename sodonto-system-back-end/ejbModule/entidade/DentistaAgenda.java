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
@Table(name = "tb_dentista_agenda", catalog = "dbsodontosystem")

@NamedQueries({					  
	@NamedQuery(name="DentistaAgenda.buscarPorData",
				query="SELECT da" +
					  " FROM DentistaAgenda da" +
					  " WHERE da.dataAgenda BETWEEN :dataI AND :dataF" +
					  " AND da.ativo is true" +
					  " AND da.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " ORDER BY da.dataAgenda DESC, da.horarioI DESC"),
							  
	@NamedQuery(name="DentistaAgenda.buscarPorDentista",
				query="SELECT da" +
					  " FROM DentistaAgenda da" +
					  " WHERE da.dentista.idDentista = :idDentista" +
					  " AND da.ativo is true" +
					  " AND da.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " ORDER BY da.dataAgenda DESC, da.horarioI DESC"),	
					  
	@NamedQuery(name="DentistaAgenda.buscarPorDentistaEData",
				query="SELECT da" +
					  " FROM DentistaAgenda da" +
					  " WHERE da.dentista.idDentista = :idDentista" +
					  " AND da.dataAgenda = :data" +
					  " AND da.ativo is true" +
					  " AND da.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " ORDER BY da.dataAgenda DESC, da.horarioI DESC"),						  
					  
	@NamedQuery(name="DentistaAgenda.buscarPorDentistaDataEHorario",
				query="SELECT da" +
					  " FROM DentistaAgenda da" +
					  " WHERE da.dentista.idDentista = :idDentista" +
					  " AND da.dataAgenda = :data" +
					  " AND da.ativo is true" +
					  " AND da.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " AND ((:horarioI BETWEEN da.horarioI AND da.horarioF)" +
					  " OR (:horarioF BETWEEN da.horarioI AND da.horarioF))" +
					  " ORDER BY da.dataAgenda DESC, da.horarioI DESC"),						  
					  
	@NamedQuery(name="DentistaAgenda.buscarPorCondicao",
				query="SELECT da" +
					  " FROM DentistaAgenda da" +
					  " WHERE da.ativo = :condicao" +
					  " AND da.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " ORDER BY da.dataAgenda DESC, " +
					  " da.horarioI DESC"),					  
})

public class DentistaAgenda implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_DATA = "DentistaAgenda.buscarPorData";
	public static final String NQ_BUSCAR_POR_DENTISTA = "DentistaAgenda.buscarPorDentista";
	public static final String NQ_BUSCAR_POR_DENTISTA_E_DATA = "DentistaAgenda.buscarPorDentistaEData";
	public static final String NQ_BUSCAR_POR_DENTISTA_DATA_E_HORARIO = "DentistaAgenda.buscarPorDentistaDataEHorario";
	public static final String NQ_BUSCAR_POR_CONDICAO = "DentistaAgenda.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idDentistaAgenda;
	private Estabelecimento estabelecimento;
	private Usuario usuario;
	private Dentista dentista;
	private Date dataAgenda;
	private Date horarioI;
	private Date horarioF;
	private Date dataMov;
	private boolean ativo;
	private Set<Agendamento> agendamentos = new HashSet<Agendamento>(0);

	public DentistaAgenda() {
	}

	public DentistaAgenda(Estabelecimento estabelecimento, Usuario usuario, Dentista dentista,
			Date dataAgenda, Date horarioI, Date horarioF, Date dataMov,
			boolean ativo) {
		this.estabelecimento = estabelecimento;
		this.usuario = usuario;
		this.dentista = dentista;
		this.dataAgenda = dataAgenda;
		this.horarioI = horarioI;
		this.horarioF = horarioF;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public DentistaAgenda(Estabelecimento estabelecimento, Usuario usuario, Dentista dentista,
			Date dataAgenda, Date horarioI, Date horarioF, Date dataMov,
			boolean ativo, Set<Agendamento> agendamentos) {
		this.estabelecimento = estabelecimento;
		this.usuario = usuario;
		this.dentista = dentista;
		this.dataAgenda = dataAgenda;
		this.horarioI = horarioI;
		this.horarioF = horarioF;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.agendamentos = agendamentos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_dentista_agenda", unique = true, nullable = false)
	public Long getIdDentistaAgenda() {
		return this.idDentistaAgenda;
	}

	public void setIdDentistaAgenda(Long idDentistaAgenda) {
		this.idDentistaAgenda = idDentistaAgenda;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dentista", nullable = false)
	public Dentista getDentista() {
		return this.dentista;
	}

	public void setDentista(Dentista dentista) {
		this.dentista = dentista;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_agenda", nullable = false, length = 10)
	public Date getDataAgenda() {
		return this.dataAgenda;
	}

	public void setDataAgenda(Date dataAgenda) {
		this.dataAgenda = dataAgenda;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "horario_i", nullable = false, length = 8)
	public Date getHorarioI() {
		return this.horarioI;
	}

	public void setHorarioI(Date horarioI) {
		this.horarioI = horarioI;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "horario_f", nullable = false, length = 8)
	public Date getHorarioF() {
		return this.horarioF;
	}

	public void setHorarioF(Date horarioF) {
		this.horarioF = horarioF;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dentistaAgenda")
	public Set<Agendamento> getAgendamentos() {
		return this.agendamentos;
	}

	public void setAgendamentos(Set<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

}
