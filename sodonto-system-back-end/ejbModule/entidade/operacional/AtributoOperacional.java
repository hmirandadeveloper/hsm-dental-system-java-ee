package entidade.operacional;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

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

import entidade.Usuario;

@Entity
@Table(name = "tb_atributo_operacional", catalog = "dbsodontosystem")

@NamedQueries({					  							  
	@NamedQuery(name="AtributoOperacional.buscarAtributoSelecionado",
				query="SELECT atb" +
					  " FROM AtributoOperacional atb" +
					  " WHERE atb.selecionado = true"),	
					  
	@NamedQuery(name="AtributoOperacional.buscarAtributos",
				query="SELECT atb" +
					  " FROM AtributoOperacional atb" +
					  " WHERE atb.ativo = :condicao")				  
})

public class AtributoOperacional implements Serializable{

	public static final String NQ_CARREGAR_ATRIBUTO_SELECIONADO = "AtributoOperacional.buscarAtributoSelecionado"; 
	public static final String NQ_BUSCAR_ATRIBUTOS = "AtributoOperacional.buscarAtributos";
	
	private static final long serialVersionUID = 1L;
	private Long idAtributoOperacional;
	private Usuario usuario;
	private String titulo;
	private boolean selecionado;
	
	private int duracaoAtendimento;
	private int tempoValidacaoTelefone;
	private int numeroLinhaTabela;
	private int limitQuery;
	
	private int provedorSMS;
	
	private Date dataMov;
	private boolean ativo;
		
	
	//Getters and Setters
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_atributo_operacional", unique = true, nullable = false)
	public Long getIdAtributoOperacional() {
		return idAtributoOperacional;
	}
	
	public void setIdAtributoOperacional(Long idAtributoOperacional) {
		this.idAtributoOperacional = idAtributoOperacional;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "titulo", nullable = false, length = 100)
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Column(name = "selecionado", nullable = false)
	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	@Column(name = "duracao_atendimento", nullable = false)
	public int getDuracaoAtendimento() {
		return duracaoAtendimento;
	}
	
	public void setDuracaoAtendimento(int duracaoAtendimento) {
		this.duracaoAtendimento = duracaoAtendimento;
	}
	
	@Column(name = "tempo_validacao_telefone", nullable = false)
	public int getTempoValidacaoTelefone() {
		return tempoValidacaoTelefone;
	}
	
	public void setTempoValidacaoTelefone(int tempoValidacaoTelefone) {
		this.tempoValidacaoTelefone = tempoValidacaoTelefone;
	}
	
	@Column(name = "numero_linha_tabela", nullable = false)
	public int getNumeroLinhaTabela() {
		return numeroLinhaTabela;
	}
	
	public void setNumeroLinhaTabela(int numeroLinhaTabela) {
		this.numeroLinhaTabela = numeroLinhaTabela;
	}
	
	@Column(name = "limit_query", nullable = false)
	public int getLimitQuery() {
		return limitQuery;
	}

	public void setLimitQuery(int limitQuery) {
		this.limitQuery = limitQuery;
	}

	@Column(name = "provedor_sms", nullable = false)
	public int getProvedorSMS() {
		return provedorSMS;
	}
	
	public void setProvedorSMS(int provedorSMS) {
		this.provedorSMS = provedorSMS;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_mov", nullable = false, length = 19)
	public Date getDataMov() {
		return dataMov;
	}

	public void setDataMov(Date dataMov) {
		this.dataMov = dataMov;
	}

	@Column(name = "ativo", nullable = false)
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
