package dto;

import java.util.Date;

import negocio.constante.enums.EUf;

public class DentistaDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idDentista;
	private EstabelecimentoDTO estabelecimentoDTO;
	private UsuarioDTO usuarioDTO;	
	private String nome;
	private String sobrenome;
	private String cpf;
	private EUf croUf;
	private String cro;
	private String pisNit;
	private Date dataMov;
	private boolean ativo;

	public DentistaDTO() {
	}

	public DentistaDTO(EstabelecimentoDTO estabelecimentoDTO, UsuarioDTO usuarioDTO, String nome, 
			String sobrenome, String cpf, EUf croUf, String cro, String pisNit, Date dataMov, boolean ativo) {
		this.estabelecimentoDTO = estabelecimentoDTO;
		this.usuarioDTO = usuarioDTO;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.cpf = cpf;
		this.croUf = croUf;
		this.cro = cro;
		this.pisNit = pisNit;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdDentista() {
		return this.idDentista;
	}

	public void setIdDentista(Long idDentista) {
		this.idDentista = idDentista;
	}
	
	public EstabelecimentoDTO getEstabelecimentoDTO() {
		return estabelecimentoDTO;
	}

	public void setEstabelecimentoDTO(EstabelecimentoDTO estabelecimentoDTO) {
		this.estabelecimentoDTO = estabelecimentoDTO;
	}

	public UsuarioDTO getUsuario() {
		return this.usuarioDTO;
	}

	public void setUsuario(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getNomeComCROFormatado() {
		return this.nome + " " + this.sobrenome + " (CRO: " + this.croUf + "-" + this.cro + ")";
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

	public EUf getCroUf() {
		return croUf;
	}

	public void setCroUf(EUf croUf) {
		this.croUf = croUf;
	}

	public String getCro() {
		return this.cro;
	}

	public void setCro(String cro) {
		if(cro == null)
		{
			cro = "";
		}
		
		this.cro = cro.trim();
	}
	
	public String getCroFormatado() {
		return this.croUf + " - " + this.cro;
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
		if(!(obj instanceof DentistaDTO))
		{
			return false;
		}
		DentistaDTO outro = (DentistaDTO) obj;
		if(idDentista == null)
		{
			if(outro.getIdDentista() != null)
			{
				return false;
			}
		}
		else if(!idDentista.equals(outro.getIdDentista()))
		{
			return false;
		}
		
		return true;
	}
}
