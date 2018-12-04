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
@Table(name = "tb_operadora", catalog = "dbsodontosystem")

@NamedQueries({					  
	@NamedQuery(name="Operadora.buscarPorNome",
				query="SELECT o" +
					  " FROM Operadora o" +
					  " WHERE o.nomeOperadora LIKE :nomeOperadora" +
					  " AND o.ativo = true"),
					  
	@NamedQuery(name="Operadora.buscarPorCondicao",
				query="SELECT o" +
					  " FROM Operadora o" +
					  " WHERE o.ativo = :condicao"),					  
})

public class Operadora implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_NOME = "Operadora.buscarPorNome";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Operadora.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idOperadora;
	private Usuario usuario;
	private String nomeOperadora;
	private Date dataMov;
	private boolean ativo;
	private Set<Paciente> pacientesCel02 = new HashSet<Paciente>(
			0);
	private Set<Paciente> pacientesCel01 = new HashSet<Paciente>(
			0);
	private Set<Funcionario> funcionariosCel02 = new HashSet<Funcionario>(
			0);
	private Set<Funcionario> funcionariosCel01 = new HashSet<Funcionario>(
			0);
	private Set<Funcionario> funcionariosCel03 = new HashSet<Funcionario>(
			0);
	private Set<Paciente> pacientesCel03 = new HashSet<Paciente>(
			0);

	public Operadora() {
	}

	public Operadora(Usuario usuario, String nomeOperadora, Date dataMov,
			boolean ativo) {
		this.usuario = usuario;
		this.nomeOperadora = nomeOperadora;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Operadora(Usuario usuario, String nomeOperadora, Date dataMov,
			boolean ativo, Set<Paciente> pacientesCel02,
			Set<Paciente> pacientesCel01,
			Set<Funcionario> funcionariosCel02,
			Set<Funcionario> funcionariosCel01,
			Set<Funcionario> funcionariosCel03,
			Set<Paciente> pacientesCel03) {
		this.usuario = usuario;
		this.nomeOperadora = nomeOperadora;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.pacientesCel02 = pacientesCel02;
		this.pacientesCel01 = pacientesCel01;
		this.funcionariosCel02 = funcionariosCel02;
		this.funcionariosCel01 = funcionariosCel01;
		this.funcionariosCel03 = funcionariosCel03;
		this.pacientesCel03 = pacientesCel03;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_operadora", unique = true, nullable = false)
	public Long getIdOperadora() {
		return this.idOperadora;
	}

	public void setIdOperadora(Long idOperadora) {
		this.idOperadora = idOperadora;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "nome_operadora", nullable = false, length = 15)
	public String getNomeOperadora() {
		return this.nomeOperadora;
	}

	public void setNomeOperadora(String nomeOperadora) {
		this.nomeOperadora = nomeOperadora;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operadoraCel02")
	public Set<Paciente> getPacientesCel02() {
		return this.pacientesCel02;
	}

	public void setPacientesCel02(
			Set<Paciente> pacientesCel02) {
		this.pacientesCel02 = pacientesCel02;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operadoraCel01")
	public Set<Paciente> getPacientesCel01() {
		return this.pacientesCel01;
	}

	public void setPacientesCel01(
			Set<Paciente> pacientesCel01) {
		this.pacientesCel01 = pacientesCel01;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operadoraCel02")
	public Set<Funcionario> getFuncionariosCel02() {
		return this.funcionariosCel02;
	}

	public void setFuncionariosCel02(
			Set<Funcionario> funcionariosCel02) {
		this.funcionariosCel02 = funcionariosCel02;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operadoraCel01")
	public Set<Funcionario> getFuncionariosCel01() {
		return this.funcionariosCel01;
	}

	public void setFuncionariosCel01(
			Set<Funcionario> funcionariosCel01) {
		this.funcionariosCel01 = funcionariosCel01;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operadoraCel03")
	public Set<Funcionario> getFuncionariosCel03() {
		return this.funcionariosCel03;
	}

	public void setFuncionariosCel03(
			Set<Funcionario> funcionariosCel03) {
		this.funcionariosCel03 = funcionariosCel03;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operadoraCel03")
	public Set<Paciente> getPacientesCel03() {
		return this.pacientesCel03;
	}

	public void setPacientesCel03(
			Set<Paciente> pacientesCel03) {
		this.pacientesCel03 = pacientesCel03;
	}

}
