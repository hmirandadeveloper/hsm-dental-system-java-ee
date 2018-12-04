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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_usuario", catalog = "dbsodontosystem", uniqueConstraints = @UniqueConstraint(columnNames = "usuario"))

@NamedQueries({
	@NamedQuery(name="Usuario.buscarPorUsuario",
				query="SELECT u" +
					  " FROM Usuario u" +
					  " WHERE u.usuario LIKE :usuario" +
					  "	AND u.ativo = true"),
					  
	@NamedQuery(name="Usuario.buscarPorPerfil",
				query="SELECT u" +
					  " FROM Usuario u" +
					  " WHERE u.perfilAtivo = :perfilAtivo" +
					  " AND u.ativo = true"),					  
					  
	@NamedQuery(name="Usuario.buscarPorCondicao",
				query="SELECT u" +
					  " FROM Usuario u" +
					  " WHERE u.ativo = :condicao")
})

public class Usuario implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_USUARIO = "Usuario.buscarPorUsuario";
	public static final String NQ_BUSCAR_POR_PERFIL = "Usuario.buscarPorPerfil";
	public static final String NQ_BUSCAR_POR_CONDICAO = "Usuario.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idUsuario;
	private String usuario;
	private String senha;
	private String perfilAtivo;
	private String perfilCadastro;
	private Date dataMov;
	private boolean ativo;
	private Set<Caixa> caixasUsuarioAbertura = new HashSet<Caixa>(0);
	private Set<Caixa> caixasUsuarioFechamento = new HashSet<Caixa>(
			0);
	private Set<Cargo> cargos = new HashSet<Cargo>(0);
	private Set<Especialidade> especialidades = new HashSet<Especialidade>(
			0);
	private Set<Movimentacao> movimentacaos = new HashSet<Movimentacao>(0);
	private Set<PlanoPaciente> planoPacientes = new HashSet<PlanoPaciente>(
			0);
	private Set<Paciente> pacientes = new HashSet<Paciente>(0);
	private Set<Plano> planos = new HashSet<Plano>(0);
	private Set<MsgPreTorpedo> msgPreTorpedos = new HashSet<MsgPreTorpedo>(
			0);
	private Set<Agendamento> agendamentos = new HashSet<Agendamento>(0);
	private Set<MensalidadePaciente> mensalidadePacientes = new HashSet<MensalidadePaciente>(
			0);
	private Set<Atendimento> atendimentos = new HashSet<Atendimento>(0);
	private Set<MsgPreEmail> msgPreEmails = new HashSet<MsgPreEmail>(0);
	private Set<DentistaAgenda> dentistaAgendas = new HashSet<DentistaAgenda>(
			0);
	private Set<Dentista> dentistas = new HashSet<Dentista>(0);
	private Set<Funcionario> funcionariosPerfil = new HashSet<Funcionario>(
			0);
	private Set<Operadora> operadoras = new HashSet<Operadora>(0);
	private Set<Funcionario> funcionarios = new HashSet<Funcionario>(
			0);

	public Usuario() {
	}

	public Usuario(String usuario, String senha, String perfilAtivo,
			String perfilCadastro, Date dataMov, boolean ativo) {
		this.usuario = usuario;
		this.senha = senha;
		this.perfilAtivo = perfilAtivo;
		this.perfilCadastro = perfilCadastro;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Usuario(String usuario, String senha, String perfilAtivo,
			String perfilCadastro, Date dataMov, boolean ativo,
			Set<Caixa> tbCaixasForIdUsuarioAbertura,
			Set<Caixa> tbCaixasForIdUsuarioFechamento, Set<Cargo> cargos,
			Set<Especialidade> especialidades,
			Set<Movimentacao> movimentacaos,
			Set<PlanoPaciente> planoPacientes, Set<Paciente> pacientes,
			Set<Plano> planos, Set<MsgPreTorpedo> msgPreTorpedos,
			Set<Agendamento> agendamentos,
			Set<MensalidadePaciente> mensalidadePacientes,
			Set<Atendimento> atendimentos,
			Set<MsgPreEmail> msgPreEmails,
			Set<DentistaAgenda> dentistaAgendas,
			Set<Dentista> dentistas,
			Set<Funcionario> tbFuncionariosForIdUsuario,
			Set<Operadora> operadoras,
			Set<Funcionario> tbFuncionariosForIdUsuarioMov) {
		this.usuario = usuario;
		this.senha = senha;
		this.perfilAtivo = perfilAtivo;
		this.perfilCadastro = perfilCadastro;
		this.dataMov = dataMov;
		this.ativo = ativo;
		this.caixasUsuarioAbertura = tbCaixasForIdUsuarioAbertura;
		this.caixasUsuarioFechamento = tbCaixasForIdUsuarioFechamento;
		this.cargos = cargos;
		this.especialidades = especialidades;
		this.movimentacaos = movimentacaos;
		this.planoPacientes = planoPacientes;
		this.pacientes = pacientes;
		this.planos = planos;
		this.msgPreTorpedos = msgPreTorpedos;
		this.agendamentos = agendamentos;
		this.mensalidadePacientes = mensalidadePacientes;
		this.atendimentos = atendimentos;
		this.msgPreEmails = msgPreEmails;
		this.dentistaAgendas = dentistaAgendas;
		this.dentistas = dentistas;
		this.funcionariosPerfil = tbFuncionariosForIdUsuario;
		this.operadoras = operadoras;
		this.funcionarios = tbFuncionariosForIdUsuarioMov;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_usuario", unique = true, nullable = false)
	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "usuario", nullable = false, length = 25)
	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name = "senha", nullable = false, length = 50)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Column(name = "perfil_ativo", nullable = false, length = 1)
	public String getPerfilAtivo() {
		return this.perfilAtivo;
	}

	public void setPerfilAtivo(String perfilAtivo) {
		this.perfilAtivo = perfilAtivo;
	}

	@Column(name = "perfil_cadastro", nullable = false, length = 1)
	public String getPerfilCadastro() {
		return this.perfilCadastro;
	}

	public void setPerfilCadastro(String perfilCadastro) {
		this.perfilCadastro = perfilCadastro;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioAbertura")
	public Set<Caixa> getCaixasUsuarioAbertura() {
		return this.caixasUsuarioAbertura;
	}

	public void setCaixasUsuarioAbertura(
			Set<Caixa> caixasUsuarioAbertura) {
		this.caixasUsuarioAbertura = caixasUsuarioAbertura;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioFechamento")
	public Set<Caixa> getCaixasUsuarioFechamento() {
		return this.caixasUsuarioFechamento;
	}

	public void setCaixasUsuarioFechamento(
			Set<Caixa> caixasUsuarioFechamento) {
		this.caixasUsuarioFechamento = caixasUsuarioFechamento;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Cargo> getCargos() {
		return this.cargos;
	}

	public void setCargos(Set<Cargo> cargos) {
		this.cargos = cargos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Especialidade> getEspecialidades() {
		return this.especialidades;
	}

	public void setEspecialidades(Set<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Movimentacao> getMovimentacaos() {
		return this.movimentacaos;
	}

	public void setMovimentacaos(Set<Movimentacao> movimentacaos) {
		this.movimentacaos = movimentacaos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<PlanoPaciente> getPlanoPacientes() {
		return this.planoPacientes;
	}

	public void setPlanoPacientes(Set<PlanoPaciente> planoPacientes) {
		this.planoPacientes = planoPacientes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Paciente> getPacientes() {
		return this.pacientes;
	}

	public void setPacientes(Set<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Plano> getPlanos() {
		return this.planos;
	}

	public void setPlanos(Set<Plano> planos) {
		this.planos = planos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<MsgPreTorpedo> getMsgPreTorpedos() {
		return this.msgPreTorpedos;
	}

	public void setMsgPreTorpedos(Set<MsgPreTorpedo> msgPreTorpedos) {
		this.msgPreTorpedos = msgPreTorpedos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Agendamento> getAgendamentos() {
		return this.agendamentos;
	}

	public void setAgendamentos(Set<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<MensalidadePaciente> getMensalidadePacientes() {
		return this.mensalidadePacientes;
	}

	public void setMensalidadePacientes(
			Set<MensalidadePaciente> mensalidadePacientes) {
		this.mensalidadePacientes = mensalidadePacientes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Atendimento> getAtendimentos() {
		return this.atendimentos;
	}

	public void setAtendimentos(Set<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<MsgPreEmail> getMsgPreEmails() {
		return this.msgPreEmails;
	}

	public void setMsgPreEmails(Set<MsgPreEmail> msgPreEmails) {
		this.msgPreEmails = msgPreEmails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<DentistaAgenda> getDentistaAgendas() {
		return this.dentistaAgendas;
	}

	public void setDentistaAgendas(Set<DentistaAgenda> dentistaAgendas) {
		this.dentistaAgendas = dentistaAgendas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Dentista> getDentistas() {
		return this.dentistas;
	}

	public void setDentistas(Set<Dentista> dentistas) {
		this.dentistas = dentistas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioPerfil")
	public Set<Funcionario> getFuncionariosPerfil() {
		return this.funcionariosPerfil;
	}

	public void setFuncionariosPerfil(
			Set<Funcionario> funcionariosPerfil) {
		this.funcionariosPerfil = funcionariosPerfil;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Operadora> getOperadoras() {
		return this.operadoras;
	}

	public void setOperadoras(Set<Operadora> operadoras) {
		this.operadoras = operadoras;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Funcionario> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(
			Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

}
