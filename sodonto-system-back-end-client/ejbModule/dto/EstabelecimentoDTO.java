package dto;

import java.util.Date;


public class EstabelecimentoDTO implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long idEstabelecimento;
	private EnderecoDTO endereco;
	private UsuarioDTO usuario;
	private String cnpj;
	private String razaoSocial;
	private String nomeFantasia;
	private String telefone;
	private String fax;
	private String email;
	private Date dataMov;
	private boolean ativo;

	public EstabelecimentoDTO() {
	}

	public EstabelecimentoDTO(Long idEstabelecimento, EnderecoDTO endereco,
			UsuarioDTO usuario, String cnpj, String razaoSocial, String nomeFantasia,
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

	public Long getIdEstabelecimento() {
		return this.idEstabelecimento;
	}

	public void setIdEstabelecimento(Long idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	public EnderecoDTO getEnderecoDTO() {
		return this.endereco;
	}

	public void setEnderecoDTO(EnderecoDTO endereco) {
		this.endereco = endereco;
	}
	
	public UsuarioDTO getUsuarioDTO() {
		return this.usuario;
	}

	public void setUsuarioDTO(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		if(cnpj == null)
		{
			cnpj = "";
		}
		
		this.cnpj = cnpj.trim();
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		if(razaoSocial == null)
		{
			razaoSocial = "";
		}
		
		this.razaoSocial = razaoSocial.toUpperCase().trim();
	}

	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		if(nomeFantasia == null)
		{
			nomeFantasia = "";
		}
		
		this.nomeFantasia = nomeFantasia.toUpperCase().trim();
	}
	
	public String getNomeEstabelecimentoFormatado() {
		return this.cnpj + " - " + this.nomeFantasia;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		if(telefone == null)
		{
			telefone = "";
		}
		
		this.telefone = telefone.trim();
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		if(fax == null)
		{
			fax = "";
		}
		
		this.fax = fax.trim();
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
		
		System.out.println("[SGPM][MB]Comparação Estabelecimento...");
		
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(!(obj instanceof EstabelecimentoDTO))
		{
			return false;
		}
		EstabelecimentoDTO outro = (EstabelecimentoDTO) obj;
		if(idEstabelecimento == null)
		{
			if(outro.getIdEstabelecimento() != null)
			{
				return false;
			}
		}
		else if(!idEstabelecimento.equals(outro.getIdEstabelecimento()))
		{
			return false;
		}
		
		return true;
	}
}
