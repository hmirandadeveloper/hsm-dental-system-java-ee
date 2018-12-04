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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_funcionario", catalog = "dbsodontosystem", uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))

@NamedQueries({
	@NamedQuery(name="Funcionario.buscarPorCpfOuRg",
				query="SELECT f" +
					  " FROM Funcionario f" +
					  " WHERE f.cpf = :cpf" +
					  " OR f.rg = :rg" +
					  " AND f.ativo = true"),
					  
	@NamedQuery(name="Funcionario.buscarPorNome",
				query="SELECT f" +
					  " FROM Funcionario f" +
					  " WHERE f.nome LIKE :nome" +
					  " OR f.sobrenome LIKE :nome" +
					  " AND f.ativo = true"),
					  
	@NamedQuery(name="Funcionario.buscarPorCargo",
				query="SELECT f" +
					  " FROM Funcionario f" +
					  " WHERE f.cargo.idCargo = :idCargo" +
					  " AND f.ativo = true"),
					  
	@NamedQuery(name="Funcionario.buscarPorIdUsuario",
				query="SELECT f" +
					  " FROM Funcionario f" +
					  " WHERE f.usuarioPerfil.idUsuario = :idUsuario" +
					  " AND f.ativo = true"),
					  
	@NamedQuery(name="Funcionario.buscarPorPerfil",
				query="SELECT f" +
					  " FROM Funcionario f" +
					  " WHERE f.usuarioPerfil.perfilAtivo = :perfil" +
					  " AND f.ativo = true"),
					  
	@NamedQuery(name="Funcionario.buscarPorCondicao",
				query="SELECT f" +
					  " FROM Funcionario f" +
					  " WHERE f.ativo = :condicao"),					  
})

public class Funcionario implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_CPF_OU_RG = "Funcionario.buscarPorCpfOuRg";
	public static final String NQ_BUSCAR_POR_NOME = "Funcionario.buscarPorNome";
	public static final String NQ_BUSCAR_POR_CARGO = "Funcionario.buscarPorCargo";
	public static final String NQ_BUSCAR_POR_ID_USUARIO = "Funcionario.buscarPorIdUsuario";
	public static final String NQ_BUSCAR_POR_PERFIL = "Funcionario.buscarPorPerfil";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Funcionario.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idFuncionario;
	private Estabelecimento estabelecimento;
	private Cargo cargo;
	private Usuario usuarioPerfil;
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
	private String pisNit;
	private String telResidencial;
	private String cel01;
	private String cel02;
	private String cel03;
	private String email;
	private Date dataContratacao;
	private Date dataMov;
	private boolean ativo;

	public Funcionario() {
	}

	public Funcionario(Estabelecimento estabelecimento, Cargo cargo, Usuario usuarioPerfil,
			Operadora operadoraCel01, Endereco endereco,
			Usuario usuario, String nome, String sobrenome,
			String cpf, String rg, String rgOrgao, String rgUf, String pisNit,
			String telResidencial, String cel01, String email,
			Date dataContratacao, Date dataMov, boolean ativo) {
		this.estabelecimento = estabelecimento;
		this.cargo = cargo;
		this.usuarioPerfil = usuarioPerfil;
		this.operadoraCel01 = operadoraCel01;
		this.endereco = endereco;
		this.usuario = usuario;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.cpf = cpf;
		this.rg = rg;
		this.rgOrgao = rgOrgao;
		this.rgUf = rgUf;
		this.pisNit = pisNit;
		this.telResidencial = telResidencial;
		this.cel01 = cel01;
		this.email = email;
		this.dataContratacao = dataContratacao;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Funcionario(Estabelecimento estabelecimento, Cargo cargo, Usuario usuarioPerfil,
			Operadora operadoraCel01, Endereco endereco,
			Operadora operadoraCel02,
			Usuario usuario,
			Operadora operadoraCel03, String nome,
			String sobrenome, String cpf, String rg, String rgOrgao,
			String rgUf, String pisNit, String telResidencial, String cel01,
			String cel02, String cel03, String email, Date dataContratacao,
			Date dataMov, boolean ativo) {
		this.estabelecimento = estabelecimento;
		this.cargo = cargo;
		this.usuarioPerfil = usuarioPerfil;
		this.operadoraCel01 = operadoraCel01;
		this.endereco = endereco;
		this.operadoraCel02 = operadoraCel02;
		this.usuario = usuario;
		this.operadoraCel03 = operadoraCel03;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.cpf = cpf;
		this.rg = rg;
		this.rgOrgao = rgOrgao;
		this.rgUf = rgUf;
		this.pisNit = pisNit;
		this.telResidencial = telResidencial;
		this.cel01 = cel01;
		this.cel02 = cel02;
		this.cel03 = cel03;
		this.email = email;
		this.dataContratacao = dataContratacao;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_funcionario", unique = true, nullable = false)
	public Long getIdFuncionario() {
		return this.idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
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
	@JoinColumn(name = "id_cargo", nullable = false)
	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_usuario", nullable = false)
	public Usuario getUsuarioPerfil() {
		return this.usuarioPerfil;
	}

	public void setUsuarioPerfil(Usuario usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
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

	@Column(name = "cpf", nullable = false, length = 15)
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "rg", nullable = false, length = 10)
	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Column(name = "rg_orgao", nullable = false, length = 5)
	public String getRgOrgao() {
		return this.rgOrgao;
	}

	public void setRgOrgao(String rgOrgao) {
		this.rgOrgao = rgOrgao;
	}

	@Column(name = "rg_uf", nullable = false, length = 2)
	public String getRgUf() {
		return this.rgUf;
	}

	public void setRgUf(String rgUf) {
		this.rgUf = rgUf;
	}

	@Column(name = "pis_nit", nullable = false, length = 25)
	public String getPisNit() {
		return this.pisNit;
	}

	public void setPisNit(String pisNit) {
		this.pisNit = pisNit;
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

	@Column(name = "email", nullable = false, length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_contratacao", nullable = false, length = 10)
	public Date getDataContratacao() {
		return this.dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
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
