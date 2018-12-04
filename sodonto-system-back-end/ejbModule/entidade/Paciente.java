package entidade;

// Generated 27/05/2014 11:53:57 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_paciente", catalog = "dbsodontosystem", uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))

@NamedQueries({	
	@NamedQuery(name="Paciente.buscarUltimoID",
			query="SELECT MAX(p.idPaciente)" +
				  " FROM Paciente p"),
				  
	@NamedQuery(name="Paciente.buscarPorDentista",
			query="SELECT p" +
				  " FROM Paciente p" +
				  " WHERE p.dentista.idDentista = :idDentista" +
				  " AND p.ativo is true" +
				  " ORDER BY p.nome, p.sobrenome"),
	
	@NamedQuery(name="Paciente.buscarPorNome",
				query="SELECT p" +
					  " FROM Paciente p" +
					  " WHERE CONCAT(TRIM(p.nome), ' ',TRIM(p.sobrenome)) LIKE :nome" +
					  " AND p.ativo is true" +
					  " ORDER BY p.nome, p.sobrenome"),
					  
	@NamedQuery(name="Paciente.buscarTitularPorNome",
				query="SELECT p" +
					  " FROM Paciente p" +
					  " WHERE CONCAT(TRIM(p.nome), ' ',TRIM(p.sobrenome)) LIKE :nome" +
					  " AND p.contratante is true" +
					  " AND p.ativo is true" +
					  " ORDER BY p.nome, p.sobrenome"),	
					  
	@NamedQuery(name="Paciente.buscarTitularPorId",
				query="SELECT p" +
					  " FROM Paciente p" +
					  " WHERE p.idPaciente = :idPaciente" +
					  " AND p.contratante = true" +
					  " AND p.ativo is true" +
					  " ORDER BY p.nome, p.sobrenome"),					  
					  
	@NamedQuery(name="Paciente.buscarPorCpfOuRg",
				query="SELECT p" +
					  " FROM Paciente p" +
					  " WHERE (p.cpf LIKE :cpf" +
					  " OR p.rg LIKE :rg)" +
					  " AND p.ativo is true" +
					  " ORDER BY p.nome, p.sobrenome"),
					  
	@NamedQuery(name="Paciente.buscarTitularPorCpfOuRg",
				query="SELECT p" +
					  " FROM Paciente p" +
					  " WHERE (p.cpf LIKE :cpf" +
					  " OR p.rg LIKE :rg)" +
					  " AND p.contratante = true" +
					  " AND p.ativo is true" +
					  " ORDER BY p.nome, p.sobrenome"),						  
					  
	@NamedQuery(name="Paciente.buscarPorPR",
				query="SELECT p" +
					  " FROM Paciente p" +
					  " WHERE p.paciente.idPaciente = :idPaciente" +
					  " AND p.ativo is true" +
					  " ORDER BY p.nome, p.sobrenome"),						  
					  
	@NamedQuery(name="Paciente.buscarPorCondicao",
				query="SELECT p" +
					  " FROM Paciente p" +
					  " WHERE p.ativo is :condicao" +
					  " ORDER BY p.idPaciente DESC"),
					  
	@NamedQuery(name="Paciente.buscarTitularPorCondicao",
				query="SELECT p" +
					  " FROM Paciente p" +
					  " WHERE p.ativo is :condicao" +
					  " AND p.contratante is true" +
					  " ORDER BY p.idPaciente DESC"),					  
})

public class Paciente implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_ULTIMO_ID = "Paciente.buscarUltimoID";
	public static final String NQ_BUSCAR_POR_DENTISTA = "Paciente.buscarPorDentista";
	public static final String NQ_BUSCAR_POR_NOME = "Paciente.buscarPorNome";
	public static final String NQ_BUSCAR_TITULAR_POR_NOME = "Paciente.buscarTitularPorNome";
	public static final String NQ_BUSCAR_TITULAR_POR_ID = "Paciente.buscarTitularPorId";
	public static final String NQ_BUSCAR_POR_CPF_OU_RG = "Paciente.buscarPorCpfOuRg";
	public static final String NQ_BUSCAR_TITULAR_POR_CPF_OU_RG = "Paciente.buscarTitularPorCpfOuRg";
	public static final String NQ_BUSCAR_POR_PR = "Paciente.buscarPorPR";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Paciente.buscarPorCondicao";
	public static final String NQ_BUSCAR_TITULAR_POR_CONDICAO = "Paciente.buscarTitularPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idPaciente;
	private Estabelecimento estabelecimento;
	private Dentista dentista;
	private Paciente paciente;
	private Operadora operadoraCel01;
	private Endereco endereco;
	private Operadora operadoraCel02;
	private Usuario usuario;
	private Operadora operadoraCel03;
	private String nome;
	private String sobrenome;
	private String cpf;
	private String rg;
	private String rgOrgao;
	private String rgUf;
	private String sexo;
	private int estadoCivil;
	private boolean contratante;
	private String telResidencial;
	private String cel01;
	private String cel02;
	private String cel03;
	private String email;
	private Date dataNascimento;
	private Date dataCadastro;
	private String situacao;
	private Date dataMov;
	private boolean ativo;
	private Set<Agendamento> agendamentos = new HashSet<Agendamento>(0);
	private Set<PlanoPaciente> planoPacientes = new HashSet<PlanoPaciente>(
			0);
	private Set<Paciente> pacientes = new HashSet<Paciente>(0);

	public Paciente() {
	}

	public Paciente(Dentista dentista, Estabelecimento estabelecimento, Long idPaciente, Paciente paciente,
			Operadora tbOperadoraByIdOperadoraCel01, Endereco endereco,
			Usuario usuario, String nome, String sobrenome, String sexo,
			int estadoCivil, boolean contratante, String telResidencial,
			String cel01, Date dataNascimento, Date dataCadastro, Date dataMov,
			boolean ativo) {
		this.dentista = dentista;
		this.estabelecimento = estabelecimento;
		this.idPaciente = idPaciente;
		this.paciente = paciente;
		this.operadoraCel01 = tbOperadoraByIdOperadoraCel01;
		this.endereco = endereco;
		this.usuario = usuario;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.sexo = sexo;
		this.estadoCivil = estadoCivil;
		this.contratante = contratante;
		this.telResidencial = telResidencial;
		this.cel01 = cel01;
		this.dataNascimento = dataNascimento;
		this.dataCadastro = dataCadastro;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Paciente(Estabelecimento estabelecimento, Long idPaciente, Paciente paciente,
			Operadora tbOperadoraByIdOperadoraCel01, Endereco endereco,
			Operadora tbOperadoraByIdOperadoraCel02, Usuario usuario,
			Operadora tbOperadoraByIdOperadoraCel03, String nome,
			String sobrenome, String cpf, String rg, String rgOrgao,
			String rgUf, String sexo, int estadoCivil, boolean contratante,
			String telResidencial, String cel01, String cel02, String cel03,
			String email, Date dataNascimento, Date dataCadastro, Date dataMov,
			boolean ativo, Set<Agendamento> agendamentos,
			Set<PlanoPaciente> planoPacientes, Set<Paciente> pacientes) {
		this.estabelecimento = estabelecimento;
		this.idPaciente = idPaciente;
		this.paciente = paciente;
		this.operadoraCel01 = tbOperadoraByIdOperadoraCel01;
		this.endereco = endereco;
		this.operadoraCel02 = tbOperadoraByIdOperadoraCel02;
		this.usuario = usuario;
		this.operadoraCel03 = tbOperadoraByIdOperadoraCel03;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.cpf = cpf;
		this.rg = rg;
		this.rgOrgao = rgOrgao;
		this.rgUf = rgUf;
		this.sexo = sexo;
		this.estadoCivil = estadoCivil;
		this.contratante = contratante;
		this.telResidencial = telResidencial;
		this.cel01 = cel01;
		this.cel02 = cel02;
		this.cel03 = cel03;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.dataCadastro = dataCadastro;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.agendamentos = agendamentos;
		this.planoPacientes = planoPacientes;
		this.pacientes = pacientes;
	}

	@Id
	@Column(name = "id_paciente", unique = true, nullable = false)
	public Long getIdPaciente() {
		return this.idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dentista", nullable = false)
	public Dentista getDentista() {
		return this.dentista;
	}

	public void setDentista(Dentista dentista) {
		this.dentista = dentista;
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
	@JoinColumn(name = "id_paciente_responsavel")
	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_operadora_cel_01", nullable = false)
	public Operadora getOperadoraCel01() {
		return this.operadoraCel01;
	}

	public void setOperadoraCel01(
			Operadora operadoraCel01) {
		this.operadoraCel01 = operadoraCel01;
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
	@JoinColumn(name = "id_operadora_cel_02")
	public Operadora getOperadoraCel02() {
		return this.operadoraCel02;
	}

	public void setOperadoraCel02(
			Operadora operadoraCel02) {
		this.operadoraCel02 = operadoraCel02;
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
	@JoinColumn(name = "id_operadora_cel_03")
	public Operadora getOperadoraCel03() {
		return this.operadoraCel03;
	}

	public void setOperadoraCel03(
			Operadora operadoraCel03) {
		this.operadoraCel03 = operadoraCel03;
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

	@Column(name = "cpf", length = 15)
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "rg", length = 10)
	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Column(name = "rg_orgao", length = 5)
	public String getRgOrgao() {
		return this.rgOrgao;
	}

	public void setRgOrgao(String rgOrgao) {
		this.rgOrgao = rgOrgao;
	}

	@Column(name = "rg_uf", length = 2)
	public String getRgUf() {
		return this.rgUf;
	}

	public void setRgUf(String rgUf) {
		this.rgUf = rgUf;
	}

	@Column(name = "sexo", nullable = false, length = 1)
	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Column(name = "estado_civil", nullable = false)
	public int getEstadoCivil() {
		return this.estadoCivil;
	}

	public void setEstadoCivil(int estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@Column(name = "contratante", nullable = false)
	public boolean isContratante() {
		return this.contratante;
	}

	public void setContratante(boolean contratante) {
		this.contratante = contratante;
	}

	@Column(name = "tel_residencial", nullable = false, length = 15)
	public String getTelResidencial() {
		return this.telResidencial;
	}

	public void setTelResidencial(String telResidencial) {
		this.telResidencial = telResidencial;
	}

	@Column(name = "cel_01", nullable = false, length = 15)
	public String getCel01() {
		return this.cel01;
	}

	public void setCel01(String cel01) {
		this.cel01 = cel01;
	}

	@Column(name = "cel_02", length = 15)
	public String getCel02() {
		return this.cel02;
	}

	public void setCel02(String cel02) {
		this.cel02 = cel02;
	}

	@Column(name = "cel_03", length = 15)
	public String getCel03() {
		return this.cel03;
	}

	public void setCel03(String cel03) {
		this.cel03 = cel03;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = false, length = 10)
	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro", nullable = false, length = 10)
	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Column(name = "situacao", nullable = false, length = 1)
	public String getSituacao() {
		return situacao;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente")
	public Set<Agendamento> getAgendamentos() {
		return this.agendamentos;
	}

	public void setAgendamentos(Set<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente")
	public Set<PlanoPaciente> getPlanoPacientes() {
		return this.planoPacientes;
	}

	public void setPlanoPacientes(Set<PlanoPaciente> planoPacientes) {
		this.planoPacientes = planoPacientes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente")
	public Set<Paciente> getPacientes() {
		return this.pacientes;
	}

	public void setPacientes(Set<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

}
