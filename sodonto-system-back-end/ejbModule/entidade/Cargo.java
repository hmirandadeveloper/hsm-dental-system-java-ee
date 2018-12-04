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
@Table(name = "tb_cargo", catalog = "dbsodontosystem")

@NamedQueries({					  
	@NamedQuery(name="Cargo.buscarPorNome",
				query="SELECT c" +
					  " FROM Cargo c" +
					  " WHERE c.nomeCargo LIKE :nomeCargo" +
					  " AND c.ativo is true" +
					  " ORDER BY c.nomeCargo"),
					  
	@NamedQuery(name="Cargo.buscarPorCondicao",
				query="SELECT c" +
					  " FROM Cargo c" +
					  " WHERE c.ativo = :condicao" +
					  " ORDER BY c.nomeCargo"),					  
})

public class Cargo implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_NOME = "Cargo.buscarPorNome";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Cargo.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idCargo;
	private Usuario usuario;
	private String nomeCargo;
	private Date dataMov;
	private boolean ativo;
	private Set<Funcionario> funcionarios = new HashSet<Funcionario>(0);

	public Cargo() {
	}

	public Cargo(Usuario usuario, String nomeCargo, Date dataMov,
			boolean ativo) {
		this.usuario = usuario;
		this.nomeCargo = nomeCargo;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Cargo(Usuario usuario, String nomeCargo, Date dataMov,
			boolean ativo, Set<Funcionario> funcionarios) {
		this.usuario = usuario;
		this.nomeCargo = nomeCargo;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.funcionarios = funcionarios;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_cargo", unique = true, nullable = false)
	public Long getIdCargo() {
		return this.idCargo;
	}

	public void setIdCargo(Long idCargo) {
		this.idCargo = idCargo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "nome_cargo", nullable = false, length = 50)
	public String getNomeCargo() {
		return this.nomeCargo;
	}

	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cargo")
	public Set<Funcionario> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

}
