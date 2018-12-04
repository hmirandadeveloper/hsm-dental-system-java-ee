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
@Table(name = "tb_atendimento", catalog = "dbsodontosystem")

@NamedQueries({					  
	@NamedQuery(name="Atendimento.buscarPorAgendamento",
			query="SELECT at" +
				  " FROM Atendimento at" +
				  " WHERE at.agendamento.idAgendamento = :idAgendamento" +
				  " AND at.ativo is true" +
				  " ORDER BY at.agendamento.dentistaAgenda.dataAgenda DESC, " +
				  " at.agendamento.dentistaAgenda.dentista.nome, " +
				  " at.agendamento.dentistaAgenda.dentista.sobrenome," +
				  " at.dataMov DESC, " +
				  " at.agendamento.paciente.nome, " +
				  " at.agendamento.paciente.sobrenome"),
	
	@NamedQuery(name="Atendimento.buscarPorData",
				query="SELECT at" +
					  " FROM Atendimento at" +
					  " WHERE at.agendamento.dentistaAgenda.dataAgenda BETWEEN :dataI AND :dataF" +
					  " AND at.agendamento.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " AND at.ativo is true" +
					  " ORDER BY at.agendamento.dentistaAgenda.dataAgenda DESC, " +
					  " at.agendamento.dentistaAgenda.dentista.nome, " +
					  " at.agendamento.dentistaAgenda.dentista.sobrenome," +
					  " at.dataMov DESC, " +
					  " at.agendamento.paciente.nome, " +
					  " at.agendamento.paciente.sobrenome"),
					  
	@NamedQuery(name="Atendimento.buscarPorPaciente",
				query="SELECT at" +
					  " FROM Atendimento at" +
					  " WHERE at.agendamento.paciente.idPaciente = :idPaciente" +
					  " AND at.agendamento.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " AND at.ativo is true" +
					  " ORDER BY at.agendamento.dentistaAgenda.dataAgenda DESC"),			  
					  
	@NamedQuery(name="Atendimento.buscarPorSituacao",
				query="SELECT at" +
					  " FROM Atendimento at" +
					  " WHERE at.situacao = :situacao" +
					  " AND at.agendamento.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " AND at.ativo is true" +
					  " ORDER BY at.agendamento.dentistaAgenda.dataAgenda DESC, " +
					  " at.agendamento.dentistaAgenda.dentista.nome, " +
					  " at.agendamento.dentistaAgenda.dentista.sobrenome," +
					  " at.dataMov DESC, " +
					  " at.agendamento.paciente.nome, " +
					  " at.agendamento.paciente.sobrenome"),					  
							  
	@NamedQuery(name="Atendimento.buscarPorDentista",
				query="SELECT at" +
					  " FROM Atendimento at" +
					  " WHERE at.agendamento.dentistaAgenda.dentista.idDentista = :idDentista" +
					  " AND at.agendamento.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " AND at.ativo is true" +
					  " ORDER BY at.agendamento.dentistaAgenda.dataAgenda DESC, " +
					  " at.agendamento.dentistaAgenda.dentista.nome, " +
					  " at.agendamento.dentistaAgenda.dentista.sobrenome," +
					  " at.dataMov DESC, " +
					  " at.agendamento.paciente.nome, " +
					  " at.agendamento.paciente.sobrenome"),							  
					  
	@NamedQuery(name="Atendimento.buscarPorCondicao",
				query="SELECT at" +
					  " FROM Atendimento at" +
					  " WHERE at.ativo = :condicao" +
					  "	AND at.agendamento.estabelecimento.idEstabelecimento = :idEstabelecimento" +
					  " ORDER BY at.agendamento.dentistaAgenda.dataAgenda DESC, " +
					  " at.agendamento.dentistaAgenda.dentista.nome, " +
					  " at.agendamento.dentistaAgenda.dentista.sobrenome," +
					  " at.dataMov DESC, " +
					  " at.agendamento.paciente.nome, " +
					  " at.agendamento.paciente.sobrenome")					  
})

public class Atendimento implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_AGENDAMENTO = "Atendimento.buscarPorAgendamento";
	public static final String NQ_BUSCAR_POR_DATA = "Atendimento.buscarPorData";
	public static final String NQ_BUSCAR_POR_PACIENTE = "Atendimento.buscarPorPaciente";
	public static final String NQ_BUSCAR_POR_SITUACAO = "Atendimento.buscarPorSituacao";
	public static final String NQ_BUSCAR_POR_DENTISTA = "Atendimento.buscarPorDentista";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Atendimento.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idAtendimento;
	private Agendamento agendamento;
	private Usuario usuario;
	private String situacao;
	private Date dataMov;
	private boolean ativo;

	public Atendimento() {
	}

	public Atendimento(Agendamento agendamento, Usuario usuario,
			String situacao, Date dataMov, boolean ativo) {
		this.agendamento = agendamento;
		this.usuario = usuario;
		this.situacao = situacao;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_atendimento", unique = true, nullable = false)
	public Long getIdAtendimento() {
		return this.idAtendimento;
	}

	public void setIdAtendimento(Long idAtendimento) {
		this.idAtendimento = idAtendimento;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
	@JoinColumn(name = "id_agendamento", nullable = false)
	public Agendamento getAgendamento() {
		return this.agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "situacao", nullable = false, length = 1)
	public String getSituacao() {
		return this.situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
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
