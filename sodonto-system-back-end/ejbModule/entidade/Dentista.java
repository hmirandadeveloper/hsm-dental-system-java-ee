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
@Table(name = "tb_dentista", catalog = "dbsodontosystem")

@NamedQueries({
	@NamedQuery(name="Dentista.buscarPorCpfOuCro",
				query="SELECT d" +
					  " FROM Dentista d" +
					  " WHERE d.cpf = :cpf" +
					  " OR d.cro = :cro" +
					  " AND d.ativo = true"),
					  
	@NamedQuery(name="Dentista.buscarPorNome",
				query="SELECT d" +
					  " FROM Dentista d" +
					  " WHERE d.nome LIKE :nome" +
					  " OR d.sobrenome LIKE :nome" +
					  " AND d.ativo = true"),
					  
	@NamedQuery(name="Dentista.buscarPorCondicao",
				query="SELECT d" +
					  " FROM Dentista d" +
					  " WHERE d.ativo = :condicao"),					  
})

public class Dentista implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_CPF_OU_CRO = "Dentista.buscarPorCpfOuCro";
	public static final String NQ_BUSCAR_POR_NOME = "Dentista.buscarPorNome";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Dentista.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idDentista;
	private Estabelecimento estabelecimento;
	private Usuario usuario;
	private String nome;
	private String sobrenome;
	private String cpf;
	private String croUf;
	private String cro;
	private String pisNit;
	private Date dataMov;
	private boolean ativo;
	private Set<EspecialidadeDentista> especialidadesDentistas = new HashSet<EspecialidadeDentista>(
			0);
	private Set<DentistaAgenda> dentistasAgendas = new HashSet<DentistaAgenda>(
			0);

	public Dentista() {
	}

	public Dentista(Estabelecimento estabelecimento, Usuario usuario, String nome, String sobrenome,
			String cpf, String croUf, String cro, String pisNit, Date dataMov, boolean ativo) {
		this.estabelecimento = estabelecimento;
		this.usuario = usuario;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.cpf = cpf;
		this.croUf = croUf;
		this.cro = cro;
		this.pisNit = pisNit;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Dentista(Estabelecimento estabelecimento, Usuario usuario, String nome, String sobrenome,
			String cpf, String croUf, String cro, String pisNit, Date dataMov, boolean ativo,
			Set<EspecialidadeDentista> especialidadesDentistas,
			Set<DentistaAgenda> dentistasAgendas) {
		this.estabelecimento = estabelecimento;
		this.usuario = usuario;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.cpf = cpf;
		this.croUf = croUf;
		this.cro = cro;
		this.pisNit = pisNit;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.especialidadesDentistas = especialidadesDentistas;
		this.dentistasAgendas = dentistasAgendas;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_dentista", unique = true, nullable = false)
	public Long getIdDentista() {
		return this.idDentista;
	}

	public void setIdDentista(Long idDentista) {
		this.idDentista = idDentista;
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

	@Column(name = "nome", nullable = false, length = 50)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "sobrenome", nullable = false, length = 100)
	public String getSobrenome() {
		return this.sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	@Column(name = "cpf", nullable = false, length = 15)
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Column(name = "cro_uf", nullable = false, length = 2)
	public String getCroUf() {
		return this.croUf;
	}

	public void setCroUf(String croUf) {
		this.croUf = croUf;
	}

	@Column(name = "cro", nullable = false, length = 10)
	public String getCro() {
		return this.cro;
	}

	public void setCro(String cro) {
		this.cro = cro;
	}

	@Column(name = "pis_nit", nullable = false, length = 25)
	public String getPisNit() {
		return this.pisNit;
	}

	public void setPisNit(String pisNit) {
		this.pisNit = pisNit;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dentista")
	public Set<EspecialidadeDentista> getEspecialidadesDentistas() {
		return this.especialidadesDentistas;
	}

	public void setEspecialidadesDentistas(
			Set<EspecialidadeDentista> especialidadesDentistas) {
		this.especialidadesDentistas = especialidadesDentistas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dentista")
	public Set<DentistaAgenda> getDentistasAgendas() {
		return this.dentistasAgendas;
	}

	public void setDentistasAgendas(Set<DentistaAgenda> dentistasAgendas) {
		this.dentistasAgendas = dentistasAgendas;
	}

}
