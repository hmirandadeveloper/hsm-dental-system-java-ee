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
@Table(name = "tb_agendamento", catalog = "dbsodontosystem")

@NamedQueries({		
//2.2.00
	
	@NamedQuery(name="Agendamento.buscarPorSituacaoDataEPacienteNome",
			query="SELECT ag" +
				  " FROM Agendamento ag" +
				  " WHERE ag.situacao = :situacao" +
				  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
				  " AND ag.dentistaAgenda.dataAgenda BETWEEN :dataI AND :dataF" +
				  " AND CONCAT(TRIM(ag.paciente.nome), ' ',TRIM(ag.paciente.sobrenome)) LIKE :pacienteNome" +
				  " AND ag.ativo is true" +
				  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),		
	
	@NamedQuery(name="Agendamento.buscarPorDentistaAgendaSituacaoEPacienteNome",
			query="SELECT ag" +
				  " FROM Agendamento ag" +
				  " WHERE ag.dentistaAgenda.idDentistaAgenda = :idDentistaAgenda" +
				  " AND ag.ativo is true" +
				  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
				  " AND ag.situacao = :situacao" +
				  " AND CONCAT(TRIM(ag.paciente.nome), ' ',TRIM(ag.paciente.sobrenome)) LIKE :pacienteNome" +
				  " AND ag.paciente.ativo is true" +				  
				  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),		
	
	@NamedQuery(name="Agendamento.buscarPorDentistaAgendaEPacienteNome",
			query="SELECT ag" +
				  " FROM Agendamento ag" +
				  " WHERE ag.dentistaAgenda.idDentistaAgenda = :idDentistaAgenda" +
				  " AND ag.ativo is true" +
				  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
				  " AND CONCAT(TRIM(ag.paciente.nome), ' ',TRIM(ag.paciente.sobrenome)) LIKE :pacienteNome" +
				  " AND ag.paciente.ativo is true" +				  
				  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),		
	
	@NamedQuery(name="Agendamento.buscarPorDentistaAgendaESituacao",
			query="SELECT ag" +
				  " FROM Agendamento ag" +
				  " WHERE ag.dentistaAgenda.idDentistaAgenda = :idDentistaAgenda" +
				  " AND ag.ativo is true" +
				  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
				  " AND ag.situacao IN :situacaoList" +
				  " AND ag.paciente.ativo is true" +
				  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),		
//	
	@NamedQuery(name="Agendamento.buscarPorSituacaoEData",
			query="SELECT ag" +
				  " FROM Agendamento ag" +
				  " WHERE ag.situacao = :situacao" +
				  " AND ag.dentistaAgenda.dataAgenda BETWEEN :dataI AND :dataF" +
				  " AND ag.ativo is true" +
				  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
				  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),	
	
	@NamedQuery(name="Agendamento.buscarPorSituacaoEPaciente",
			query="SELECT ag" +
				  " FROM Agendamento ag" +
				  " WHERE ag.situacao = :situacao" +
				  " AND ag.paciente.idPaciente = :idPaciente" +
				  " AND ag.ativo is true" +
				  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
				  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),
	
	@NamedQuery(name="Agendamento.buscarPorData",
				query="SELECT ag" +
					  " FROM Agendamento ag" +
					  " WHERE ag.dentistaAgenda.dataAgenda BETWEEN :dataI AND :dataF" +
					  " AND ag.ativo is true" +
					  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),
					  
	@NamedQuery(name="Agendamento.buscarPorPaciente",
				query="SELECT ag" +
					  " FROM Agendamento ag" +
					  " WHERE ag.paciente.idPaciente = :idPaciente" +
					  " AND ag.ativo is true" +
					  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),
					  
	@NamedQuery(name="Agendamento.buscarPorDentistaAgenda",
				query="SELECT ag" +
					  " FROM Agendamento ag" +
					  " WHERE ag.dentistaAgenda.idDentistaAgenda = :idDentistaAgenda" +
					  " AND ag.ativo is true" +
					  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " AND ag.paciente.ativo is true" +
					  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),						  
					  
	@NamedQuery(name="Agendamento.buscarPorPacienteEDentistaAgenda",
				query="SELECT ag" +
					  " FROM Agendamento ag" +
					  " WHERE ag.paciente.idPaciente = :idPaciente" +
					  " AND ag.dentistaAgenda.idDentistaAgenda = :idDentistaAgenda" +
					  " AND ag.ativo is true" +
					  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),					  
							  
	@NamedQuery(name="Agendamento.buscarPorDentista",
				query="SELECT ag" +
					  " FROM Agendamento ag" +
					  " WHERE ag.dentistaAgenda.dentista.idDentista = :idDentista" +
					  " AND ag.ativo is true" +
					  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),							  
					  
	@NamedQuery(name="Agendamento.buscarPorCondicao",
				query="SELECT ag" +
					  " FROM Agendamento ag" +
					  " WHERE ag.ativo = :condicao" +
					  " AND ag.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " ORDER BY ag.dentistaAgenda.dataAgenda DESC"),					  
})

public class Agendamento implements java.io.Serializable {

	//Named Querys - Constantes
	
	//2.2.00
	public static final String NQ_BUSCAR_POR_DENTISTA_AGENDA_E_SITUACAO = "Agendamento.buscarPorDentistaAgendaESituacao";
	public static final String NQ_BUSCAR_POR_DENTISTA_AGENDA_E_PACIENTE_NOME = "Agendamento.buscarPorDentistaAgendaEPacienteNome";
	public static final String NQ_BUSCAR_POR_DENTISTA_AGENDA_SITUACAO_E_PACIENTE_NOME = "Agendamento.buscarPorDentistaAgendaSituacaoEPacienteNome";
	public static final String NQ_BUSCAR_POR_SITUACAO_DATA_E_PACIENTE_NOME = "Agendamento.buscarPorSituacaoDataEPacienteNome";
	//
	public static final String NQ_BUSCAR_POR_SITUACAO_E_DATA = "Agendamento.buscarPorSituacaoEData";
	public static final String NQ_BUSCAR_POR_SITUACAO_E_PACIENTE = "Agendamento.buscarPorSituacaoEPaciente";
	public static final String NQ_BUSCAR_POR_DATA = "Agendamento.buscarPorData";
	public static final String NQ_BUSCAR_POR_PACIENTE = "Agendamento.buscarPorPaciente";
	public static final String NQ_BUSCAR_POR_DENTISTA_AGENDA = "Agendamento.buscarPorDentistaAgenda";
	public static final String NQ_BUSCAR_POR_PACIENTE_E_DENTISTA_AGENDA = "Agendamento.buscarPorPacienteEDentistaAgenda";
	public static final String NQ_BUSCAR_POR_DENTISTA = "Agendamento.buscarPorDentista";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Agendamento.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idAgendamento;
	private Estabelecimento estabelecimento;
	private Paciente paciente;
	private DentistaAgenda dentistaAgenda;
	private Usuario usuario;
	private Usuario usuarioCriacao;
	private Date dataCriacao;
	private String situacao;
	private boolean remarcacao;
	private Date dataMov;
	private boolean ativo;
	private Set<Atendimento> atendimentos = new HashSet<Atendimento>(0);

	public Agendamento() {
	}

	public Agendamento(Estabelecimento estabelecimento, Paciente paciente,
			DentistaAgenda dentistaAgenda, Usuario usuario, Usuario usuarioCriacao,
			Date dataCriacao, String situacao, boolean remarcacao,
			Date dataMov, boolean ativo) {
		this.estabelecimento = estabelecimento;
		this.paciente = paciente;
		this.dentistaAgenda = dentistaAgenda;
		this.usuario = usuario;
		this.usuarioCriacao = usuarioCriacao;
		this.dataCriacao = dataCriacao;
		this.situacao = situacao;
		this.remarcacao = remarcacao;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Agendamento(Estabelecimento estabelecimento, Paciente paciente,
			DentistaAgenda dentistaAgenda, Usuario usuario, Usuario usuarioCriacao,
			Date dataCriacao, String situacao, boolean remarcacao,
			Date dataMov, boolean ativo, Set<Atendimento> atendimentos) {
		this.estabelecimento = estabelecimento;
		this.paciente = paciente;
		this.dentistaAgenda = dentistaAgenda;
		this.usuario = usuario;
		this.usuarioCriacao = usuarioCriacao;
		this.dataCriacao = dataCriacao;		
		this.situacao = situacao;
		this.remarcacao = remarcacao;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.atendimentos = atendimentos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_agendamento", unique = true, nullable = false)
	public Long getIdAgendamento() {
		return this.idAgendamento;
	}

	public void setIdAgendamento(Long idAgendamento) {
		this.idAgendamento = idAgendamento;
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
	@JoinColumn(name = "id_paciente", nullable = false)
	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dentista_agenda", nullable = false)
	public DentistaAgenda getDentistaAgenda() {
		return this.dentistaAgenda;
	}

	public void setDentistaAgenda(DentistaAgenda dentistaAgenda) {
		this.dentistaAgenda = dentistaAgenda;
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
	@JoinColumn(name = "id_usuario_criacao", nullable = false)
	public Usuario getUsuarioCriacao() {
		return this.usuarioCriacao;
	}

	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_criacao", nullable = false, length = 10)
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Column(name = "situacao", nullable = false, length = 1)
	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	@Column(name = "remarcacao", nullable = false)
	public boolean isRemarcacao() {
		return remarcacao;
	}

	public void setRemarcacao(boolean remarcacao) {
		this.remarcacao = remarcacao;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agendamento")
	public Set<Atendimento> getAtendimentos() {
		return this.atendimentos;
	}

	public void setAtendimentos(Set<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}

}
