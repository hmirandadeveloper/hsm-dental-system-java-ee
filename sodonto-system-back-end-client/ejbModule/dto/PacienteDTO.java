package dto;

import java.util.Date;

import negocio.constante.enums.EEstadoCivil;
import negocio.constante.enums.ESituacaoPaciente;
import negocio.constante.enums.EUf;

public class PacienteDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idPaciente;
	private DentistaDTO dentistaDTO;
	private EstabelecimentoDTO estabelecimentoDTO;
	private PacienteDTO pacienteDTO;
	private OperadoraDTO operadoraCel01;
	private EnderecoDTO enderecoDTO;
	private OperadoraDTO operadoraCel02;
	private UsuarioDTO usuarioDTO;
	private OperadoraDTO operadoraCel03;
	private String nome;
	private String sobrenome;
	private String cpf;
	private String rg;
	private String rgOrgao;
	private EUf rgUf;
	private String sexo;
	private EEstadoCivil estadoCivil;
	private boolean contratante;
	private String telResidencial;
	private String cel01;
	private String cel02;
	private String cel03;
	private String email;
	private Date dataNascimento;
	private Date dataCadastro;
	private ESituacaoPaciente situacaoPaciente;
	private Date dataMov;
	private boolean ativo;

	public PacienteDTO() {
	}

	public PacienteDTO(DentistaDTO dentistaDTO, EstabelecimentoDTO estabelecimentoDTO, Long idPaciente, PacienteDTO pacienteDTO,
			OperadoraDTO tbOperadoraByIdOperadoraCel01, EnderecoDTO enderecoDTO,
			UsuarioDTO usuarioDTO, String nome, String sobrenome, String sexo,
			EEstadoCivil estadoCivil, boolean contratante, String telResidencial,
			String cel01, Date dataNascimento, Date dataCadastro, Date dataMov,
			boolean ativo) {
		this.dentistaDTO = dentistaDTO;
		this.estabelecimentoDTO = estabelecimentoDTO;
		this.idPaciente = idPaciente;
		this.pacienteDTO = pacienteDTO;
		this.operadoraCel01 = tbOperadoraByIdOperadoraCel01;
		this.enderecoDTO = enderecoDTO;
		this.usuarioDTO = usuarioDTO;
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

	public Long getIdPaciente() {
		return this.idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}
	
	public DentistaDTO getDentistaDTO() {
		return dentistaDTO;
	}

	public void setDentistaDTO(DentistaDTO dentistaDTO) {
		this.dentistaDTO = dentistaDTO;
	}

	public EstabelecimentoDTO getEstabelecimentoDTO() {
		return estabelecimentoDTO;
	}

	public void setEstabelecimentoDTO(EstabelecimentoDTO estabelecimentoDTO) {
		this.estabelecimentoDTO = estabelecimentoDTO;
	}

	public PacienteDTO getPaciente() {
		return this.pacienteDTO;
	}

	public void setPaciente(PacienteDTO pacienteDTO) {
		this.pacienteDTO = pacienteDTO;
	}

	public OperadoraDTO getOperadoraCel01() {
		return this.operadoraCel01;
	}

	public void setOperadoraCel01(
			OperadoraDTO operadoraCel01) {
		this.operadoraCel01 = operadoraCel01;
	}

	public EnderecoDTO getEndereco() {
		return this.enderecoDTO;
	}

	public void setEndereco(EnderecoDTO enderecoDTO) {
		this.enderecoDTO = enderecoDTO;
	}

	public OperadoraDTO getOperadoraCel02() {
		return this.operadoraCel02;
	}

	public void setOperadoraCel02(
			OperadoraDTO operadoraCel02) {
		this.operadoraCel02 = operadoraCel02;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public OperadoraDTO getOperadoraCel03() {
		return this.operadoraCel03;
	}

	public void setOperadoraCel03(
			OperadoraDTO operadoraCel03) {
		this.operadoraCel03 = operadoraCel03;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		if(nome == null)
		{
			nome = "";
		}
		
		this.nome = nome.toUpperCase().trim();
	}

	public String getSobrenome() {
		return this.sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		if(sobrenome == null)
		{
			sobrenome = "";
		}
		
		this.sobrenome = sobrenome.toUpperCase().trim();
	}

	public String getNomeCompleto()
	{
		return this.nome + " " + this.sobrenome;
	}
	
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		if(cpf == null)
		{
			cpf = "000.000.000-00";
		}
		
		this.cpf = cpf.trim();
	}

	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		if(rg == null)
		{
			rg = "";
		}
		
		this.rg = rg.trim();
	}

	public String getRgOrgao() {
		return this.rgOrgao;
	}

	public void setRgOrgao(String rgOrgao) {
		if(rgOrgao == null)
		{
			rgOrgao = "";
		}
		
		this.rgOrgao = rgOrgao.toUpperCase().trim();
	}

	public EUf getRgUf() {
		return this.rgUf;
	}

	public void setRgUf(EUf rgUf) {
		this.rgUf = rgUf;
	}

	public String getSexo() {
		return this.sexo;
	}
	
	public String getSexoFormatado() {
		return (this.sexo.equals('M') ? "MASCULINO" : "FEMININO");
	}

	public void setSexo(String sexo) {
		this.sexo = sexo.toUpperCase().trim();
	}

	public EEstadoCivil getEstadoCivil() {
		return this.estadoCivil;
	}

	public void setEstadoCivil(EEstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public boolean isContratante() {
		return this.contratante;
	}

	public void setContratante(boolean contratante) {
		this.contratante = contratante;
	}

	public String getTelResidencial() {
		return this.telResidencial;
	}

	public void setTelResidencial(String telResidencial) {
		if(telResidencial == null)
		{
			telResidencial = "";
		}
		
		this.telResidencial = telResidencial.trim();
	}

	public String getCel01() {
		return this.cel01;
	}

	public void setCel01(String cel01) {
		if(cel01 == null)
		{
			cel01 = "";
		}
		
		this.cel01 = cel01.trim();
	}

	public String getCel02() {
		return this.cel02;
	}

	public void setCel02(String cel02) {
		if(cel02 == null)
		{
			cel02 = "";
		}
		
		this.cel02 = cel02.trim();
	}

	public String getCel03() {
		return this.cel03;
	}

	public void setCel03(String cel03) {
		if(cel03 == null)
		{
			cel03 = "";
		}
		
		this.cel03 = cel03.trim();
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		if(email == null)
		{
			email = "";
		}
		
		this.email = email.trim();
	}

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public ESituacaoPaciente getSituacaoPaciente() {
		return situacaoPaciente;
	}

	public void setSituacaoPaciente(ESituacaoPaciente situacaoPaciente) {
		this.situacaoPaciente = situacaoPaciente;
	}

	public Date getDataMov() {
		return this.dataMov;
	}

	public void setDataMov(Date dataMov) {
		this.dataMov = dataMov;
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(!(obj instanceof PacienteDTO))
		{
			return false;
		}
		PacienteDTO outro = (PacienteDTO) obj;
		if(idPaciente == null)
		{
			if(outro.getIdPaciente() != null)
			{
				return false;
			}
		}
		else if(!idPaciente.equals(outro.getIdPaciente()))
		{
			return false;
		}
		
		return true;
	}
}
