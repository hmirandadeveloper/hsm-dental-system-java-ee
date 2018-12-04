package dto;

import java.io.Serializable;
import java.util.Date;

import negocio.constante.enums.EProvedorSMS;

public class AtributoOperacionalDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long idAtributoOperacional;
	private UsuarioDTO usuarioDTO;
	private String titulo;
	private boolean selecionado;
	
	private int duracaoAtendimento;
	private int tempoValidacaoTelefone;
	private int numeroLinhaTabela;
	private int limitQuery;
	
	private EProvedorSMS provedorSMS;
	
	private Date dataMov;
	private boolean ativo;
		
	
	//Getters and Setters

	public Long getIdAtributoOperacional() {
		return idAtributoOperacional;
	}
	
	public void setIdAtributoOperacional(Long idAtributoOperacional) {
		this.idAtributoOperacional = idAtributoOperacional;
	}

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo.toUpperCase();
	}
	
	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public int getDuracaoAtendimento() {
		return duracaoAtendimento;
	}
	
	public void setDuracaoAtendimento(int duracaoAtendimento) {
		this.duracaoAtendimento = duracaoAtendimento;
	}
	
	public int getTempoValidacaoTelefone() {
		return tempoValidacaoTelefone;
	}
	
	public void setTempoValidacaoTelefone(int tempoValidacaoTelefone) {
		this.tempoValidacaoTelefone = tempoValidacaoTelefone;
	}
	
	public int getNumeroLinhaTabela() {
		return numeroLinhaTabela;
	}
	
	public void setNumeroLinhaTabela(int numeroLinhaTabela) {
		this.numeroLinhaTabela = numeroLinhaTabela;
	}
	
	public int getLimitQuery() {
		return limitQuery;
	}

	public void setLimitQuery(int limitQuery) {
		this.limitQuery = limitQuery;
	}

	public EProvedorSMS getProvedorSMS() {
		return provedorSMS;
	}
	
	public void setProvedorSMS(EProvedorSMS provedorSMS) {
		this.provedorSMS = provedorSMS;
	}

	public Date getDataMov() {
		return dataMov;
	}

	public void setDataMov(Date dataMov) {
		this.dataMov = dataMov;
	}

	public boolean isAtivo() {
		return ativo;
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
		if(!(obj instanceof AtributoOperacionalDTO))
		{
			return false;
		}
		AtributoOperacionalDTO outro = (AtributoOperacionalDTO) obj;
		if(idAtributoOperacional == null)
		{
			if(outro.getIdAtributoOperacional() != null)
			{
				return false;
			}
		}
		else if(!idAtributoOperacional.equals(outro.getIdAtributoOperacional()))
		{
			return false;
		}
		
		return true;
	}
}
