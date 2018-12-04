package dto;

import negocio.constante.enums.EUf;


public class EnderecoDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idEndereco;
	private String logradouro;
	private int numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private EUf uf;
	private String cep;

	public EnderecoDTO() {
	}

	public EnderecoDTO(String logradouro, int numero, String bairro,
			String cidade, EUf uf, String cep) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.cep = cep;
	}

	public Long getIdEndereco() {
		return this.idEndereco;
	}

	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		if(logradouro == null)
		{
			logradouro = "";
		}
		
		this.logradouro = logradouro.toUpperCase().trim();
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		if(complemento == null)
		{
			complemento = "";
		}
		
		this.complemento = complemento.toUpperCase().trim();
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		if(bairro == null)
		{
			bairro = "";
		}
		
		this.bairro = bairro.toUpperCase().trim();
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		if(cidade == null)
		{
			cidade = "";
		}
		
		this.cidade = cidade.toUpperCase().trim();
	}

	public EUf getUf() {
		return this.uf;
	}

	public void setUf(EUf uf) {
		this.uf = uf;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		if(cep == null)
		{
			cep = "";
		}
		
		this.cep = cep.trim();
	}
}
