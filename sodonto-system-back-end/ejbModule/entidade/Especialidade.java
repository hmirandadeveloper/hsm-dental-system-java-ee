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
@Table(name = "tb_especialidade", catalog = "dbsodontosystem")

@NamedQueries({					  
	@NamedQuery(name="Especialidade.buscarPorNome",
				query="SELECT e" +
					  " FROM Especialidade e" +
					  " WHERE e.nomeEspecialidade LIKE :nomeEspecialidade" +
					  " AND e.ativo = true"),
					  
	@NamedQuery(name="Especialidade.buscarPorCondicao",
				query="SELECT e" +
					  " FROM Especialidade e" +
					  " WHERE e.ativo = :condicao"),					  
})

public class Especialidade implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_NOME = "Especialidade.buscarPorNome";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Especialidade.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idEspecialidade;
	private Usuario usuario;
	private String nomeEspecialidade;
	private Date dataMov;
	private boolean ativo;
	private Set<EspecialidadeDentista> especialidadesDentistas = new HashSet<EspecialidadeDentista>(
			0);

	public Especialidade() {
	}

	public Especialidade(Usuario usuario, String nomeEspecialidade,
			Date dataMov, boolean ativo) {
		this.usuario = usuario;
		this.nomeEspecialidade = nomeEspecialidade;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Especialidade(Usuario usuario, String nomeEspecialidade,
			Date dataMov, boolean ativo,
			Set<EspecialidadeDentista> especialidadesDentistas) {
		this.usuario = usuario;
		this.nomeEspecialidade = nomeEspecialidade;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.especialidadesDentistas = especialidadesDentistas;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_especialidade", unique = true, nullable = false)
	public Long getIdEspecialidade() {
		return this.idEspecialidade;
	}

	public void setIdEspecialidade(Long idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "nome_especialidade", nullable = false, length = 50)
	public String getNomeEspecialidade() {
		return this.nomeEspecialidade;
	}

	public void setNomeEspecialidade(String nomeEspecialidade) {
		this.nomeEspecialidade = nomeEspecialidade;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "especialidade")
	public Set<EspecialidadeDentista> getEspecialidadesDentistas() {
		return this.especialidadesDentistas;
	}

	public void setEspecialidadesDentistas(
			Set<EspecialidadeDentista> especialidadesDentistas) {
		this.especialidadesDentistas = especialidadesDentistas;
	}

}
