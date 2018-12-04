package dto;

import java.util.Date;

import negocio.constante.enums.EUf;

public class FuncionarioDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idFuncionario;
	private EstabelecimentoDTO estabelecimentoDTO;
	private CargoDTO cargoDTO;
	private UsuarioDTO usuarioPerfil;
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
	private String pisNit;
	private String telResidencial;
	private String cel01;
	private String cel02;
	private String cel03;
	private String email;
	private Date dataContratacao;
	private Date dataMov;
	private boolean ativo;

	public FuncionarioDTO() {
	}

	public FuncionarioDTO(EstabelecimentoDTO estabelecimentoDTO, CargoDTO cargoDTO, UsuarioDTO usuarioPerfil,
			OperadoraDTO operadoraCel01, EnderecoDTO enderecoDTO,
			OperadoraDTO operadoraCel02,
			UsuarioDTO usuarioDTO,
			OperadoraDTO operadoraCel03, String nome,
			String sobrenome, String cpf, String rg, String rgOrgao,
			EUf rgUf, String pisNit, String telResidencial, String cel01,
			String cel02, String cel03, String email, Date dataContratacao,
			Date dataMov, boolean ativo) {
		this.estabelecimentoDTO = estabelecimentoDTO;
		this.cargoDTO = cargoDTO;
		this.usuarioPerfil = usuarioPerfil;
		this.operadoraCel01 = operadoraCel01;
		this.enderecoDTO = enderecoDTO;
		this.operadoraCel02 = operadoraCel02;
		this.usuarioDTO = usuarioDTO;
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

	public Long getIdFuncionario() {
		return this.idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public EstabelecimentoDTO getEstabelecimentoDTO() {
		return estabelecimentoDTO;
	}

	public void setEstabelecimentoDTO(EstabelecimentoDTO estabelecimentoDTO) {
		this.estabelecimentoDTO = estabelecimentoDTO;
	}

	public CargoDTO getCargo() {
		return this.cargoDTO;
	}

	public void setCargo(CargoDTO cargoDTO) {
		this.cargoDTO = cargoDTO;
	}

	public UsuarioDTO getUsuarioPerfil() {
		return this.usuarioPerfil;
	}

	public void setUsuarioPerfil(UsuarioDTO usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
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
	
	public String getNomeCompleto() {
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

	public String getPisNit() {
		return this.pisNit;
	}

	public void setPisNit(String pisNit) {
		if(pisNit == null)
		{
			pisNit = "";
		}
		
		this.pisNit = pisNit.trim();
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

	public Date getDataContratacao() {
		return this.dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
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
		if(!(obj instanceof FuncionarioDTO))
		{
			return false;
		}
		FuncionarioDTO outro = (FuncionarioDTO) obj;
		if(idFuncionario == null)
		{
			if(outro.getIdFuncionario() != null)
			{
				return false;
			}
		}
		else if(!idFuncionario.equals(outro.getIdFuncionario()))
		{
			return false;
		}
		
		return true;
	}
}
