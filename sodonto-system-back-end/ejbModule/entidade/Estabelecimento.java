package entidade;

// Generated 27/05/2014 11:53:57 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_estabelecimento", catalog = "dbsodontosystem")

@NamedQueries({					  
	@NamedQuery(name="Estabelecimento.buscarPorNome",
				query="SELECT e" +
					  " FROM Estabelecimento e" +
					  " WHERE e.razaoSocial LIKE :nome" +
					  " OR e.nomeFantasia LIKE :nome" +
					  " AND e.ativo = true"),
					  
	@NamedQuery(name="Estabelecimento.buscarPorCnpj",
				query="SELECT e" +
					  " FROM Estabelecimento e" +
					  " WHERE e.cnpj = :cnpj" +
					  " AND e.ativo = true"),							  
					  
	@NamedQuery(name="Estabelecimento.buscarPorCondicao",
				query="SELECT e" +
					  " FROM Estabelecimento e" +
					  " WHERE e.ativo = :condicao"),					  
})

public class Estabelecimento implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_NOME = "Estabelecimento.buscarPorNome";
	public static final String NQ_BUSCAR_POR_CNPJ = "Estabelecimento.buscarPorCnpj";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Estabelecimento.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idEstabelecimento;
	private Endereco endereco;
	private Usuario usuario;
	private String cnpj;
	private String razaoSocial;
	private String nomeFantasia;
	private String telefone;
	private String fax;
	private String email;
	private Date dataMov;
	private boolean ativo;
	private Set<Agendamento> agendamentos = new HashSet<Agendamento>(0);
	private Set<Caixa> caixas = new HashSet<Caixa>(0);
	private Set<Dentista> dentistas = new HashSet<Dentista>(0);
	private Set<DentistaAgenda> dentistasAgenda = new HashSet<DentistaAgenda>(0);
	private Set<Funcionario> funcionarios = new HashSet<Funcionario>(0);
	private Set<Paciente> pacientes = new HashSet<Paciente>(0);
	private Set<Plano> planos = new HashSet<Plano>(0);

	public Estabelecimento() {
	}

	public Estabelecimento(Long idEstabelecimento, Endereco endereco,
			Usuario usuario, String cnpj, String razaoSocial, String nomeFantasia,
			String telefone, String fax, String email, Date dataMov,
			boolean ativo) {
		this.idEstabelecimento = idEstabelecimento;
		this.endereco = endereco;
		this.usuario = usuario;
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.telefone = telefone;
		this.fax = fax;
		this.email = email;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Estabelecimento(Long idEstabelecimento, Endereco endereco,
			Usuario usuario, String cnpj, String razaoSocial, String nomeFantasia, 
			String telefone, String fax, String email, Date dataMov, boolean ativo, 
			Set<Agendamento> agendamentos, Set<Caixa> caixas, 
			Set<Dentista> dentistas, Set<DentistaAgenda> dentistasAgenda,
			Set<Funcionario> funcionarios, Set<Paciente> pacientes, Set<Plano> planos) {
		this.idEstabelecimento = idEstabelecimento;
		this.endereco = endereco;
		this.usuario = usuario;
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.telefone = telefone;
		this.fax = fax;
		this.email = email;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.agendamentos = agendamentos;
		this.caixas = caixas;
		this.dentistas = dentistas;
		this.dentistasAgenda = dentistasAgenda;
		this.funcionarios = funcionarios;
		this.pacientes = pacientes;
		this.planos = planos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_estabelecimento", unique = true, nullable = false)
	public Long getIdEstabelecimento() {
		return this.idEstabelecimento;
	}

	public void setIdEstabelecimento(Long idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_endereco", nullable = false)
	public Endereco getEndereco() {
		return this.endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "cnpj", nullable = false, length = 20)
	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Column(name = "razao_social", nullable = false, length = 100)
	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Column(name = "nome_fantasia", nullable = false, length = 100)
	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	@Column(name = "telefone", nullable = false, length = 15)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "fax", nullable = false, length = 15)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
	public Set<Agendamento> getAgendamentos() {
		return this.agendamentos;
	}

	public void setAgendamentos(Set<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
	public Set<Caixa> getCaixas() {
		return this.caixas;
	}

	public void setCaixas(Set<Caixa> caixas) {
		this.caixas = caixas;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
	public Set<Dentista> getDentistas() {
		return this.dentistas;
	}

	public void setDentistas(Set<Dentista> dentistas) {
		this.dentistas = dentistas;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
	public Set<DentistaAgenda> getDentistasAgenda() {
		return this.dentistasAgenda;
	}

	public void setDentistasAgenda(Set<DentistaAgenda> dentistasAgenda) {
		this.dentistasAgenda = dentistasAgenda;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
	public Set<Funcionario> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
	public Set<Paciente> getPacientes() {
		return this.pacientes;
	}

	public void setPacientes(Set<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento")
	public Set<Plano> getPlanos() {
		return this.planos;
	}

	public void setPlanos(Set<Plano> planos) {
		this.planos = planos;
	}
}
