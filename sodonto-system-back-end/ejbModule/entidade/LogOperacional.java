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
@Table(name = "tb_log_operacional", catalog = "dbsodontosystem")

@NamedQueries({	
	@NamedQuery(name="LogOperacional.buscarTotalPorNomeFuncionario",
			query="SELECT COUNT(l)" +
				  " FROM LogOperacional l, Usuario u, Funcionario f" +
				  " WHERE f.nome LIKE :nome" +
				  " AND f.usuarioPerfil.idUsuario = u.idUsuario" +
				  " AND u.idUsuario = l.usuarioLog.idUsuario"),		
	
	@NamedQuery(name="LogOperacional.buscarTotalPorData",
			query="SELECT COUNT(l)" +
				  " FROM LogOperacional l" +
				  " WHERE l.dataLog BETWEEN :dataI" +
				  " AND :dataF"),	
	
	@NamedQuery(name="LogOperacional.buscarPorNomeFuncionario",
			query="SELECT l" +
				  " FROM LogOperacional l, Usuario u, Funcionario f" +
				  " WHERE f.nome LIKE :nome" +
				  " AND f.usuarioPerfil.idUsuario = u.idUsuario" +
				  " AND u.idUsuario = l.usuarioLog.idUsuario" + 
				  " ORDER BY l.dataLog DESC"),
				  
	@NamedQuery(name="LogOperacional.buscarPorData",
				query="SELECT l" +
					  " FROM LogOperacional l" +
					  " WHERE l.dataLog BETWEEN :dataI" +
					  " AND :dataF" +
					  " ORDER BY l.dataLog DESC")			  
})

public class LogOperacional implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_TOTAL_POR_NOME_FUNCIONARIO = "LogOperacional.buscarTotalPorNomeFuncionario";
	public static final String NQ_BUSCAR_TOTAL_POR_DATA = "LogOperacional.buscarTotalPorData";
	public static final String NQ_BUSCAR_POR_NOME_FUNCIONARIO = "LogOperacional.buscarPorNomeFuncionario";
	public static final String NQ_BUSCAR_POR_DATA = "LogOperacional.buscarPorData";
	
	private static final long serialVersionUID = 1L;
	private Long idLogOperacional;
	private Usuario usuarioLog;
	private String tituloOperacao;
	private String detalhesOperacao;
	private Date dataLog;
	private Date dataMov;

	public LogOperacional() {
	}

	public LogOperacional(Usuario usuarioLog, String tituloOperacao, String detalhesOperacao,
			Date dataLog, Date dataMov) {
		this.usuarioLog = usuarioLog;
		this.tituloOperacao = tituloOperacao;
		this.detalhesOperacao = detalhesOperacao;
		this.dataLog = dataLog;
		this.dataMov = dataMov;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_log_operacional", unique = true, nullable = false)
	public Long getIdLogOperacional() {
		return this.idLogOperacional;
	}

	public void setIdLogOperacional(Long idLogOperacional) {
		this.idLogOperacional = idLogOperacional;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_log", nullable = false)
	public Usuario getUsuarioLog() {
		return this.usuarioLog;
	}

	public void setUsuarioLog(Usuario usuarioLog) {
		this.usuarioLog = usuarioLog;
	}

	@Column(name = "titulo_operacao", nullable = false, length = 250)
	public String getTituloOperacao() {
		return this.tituloOperacao;
	}

	public void setTituloOperacao(String tituloOperacao) {
		this.tituloOperacao = tituloOperacao;
	}
	
	@Column(name = "detalhes_operacao", nullable = false, length = 500)
	public String getDetalhesOperacao() {
		return this.detalhesOperacao;
	}

	public void setDetalhesOperacao(String detalhesOperacao) {
		this.detalhesOperacao = detalhesOperacao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_log", nullable = false, length = 19)
	public Date getDataLog() {
		return this.dataLog;
	}

	public void setDataLog(Date dataLog) {
		this.dataLog = dataLog;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_mov", nullable = false, length = 19)
	public Date getDataMov() {
		return this.dataMov;
	}

	public void setDataMov(Date dataMov) {
		this.dataMov = dataMov;
	}
}
