package entidade;

// Generated 27/05/2014 11:53:57 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

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

@Entity
@Table(name = "tb_msg_pre_torpedo", catalog = "dbsodontosystem")

@NamedQueries({					  
	@NamedQuery(name="MsgPreTorpedo.buscarPorDescricao",
				query="SELECT mt" +
					  " FROM MsgPreTorpedo mt" +
					  " WHERE mt.descricao LIKE :descricao" +
					  " AND mt.ativo = true"),
					  
	@NamedQuery(name="MsgPreTorpedo.buscarPorCondicao",
				query="SELECT mt" +
					  " FROM MsgPreTorpedo mt" +
					  " WHERE mt.ativo = :condicao"),					  
})

public class MsgPreTorpedo implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_DESCRICAO = "MsgPreTorpedo.buscarPorDescricao";
	public static final String NQ_BUSCAR_POR_CONDICAO = "MsgPreTorpedo.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idMsgPreTorpedo;
	private Usuario usuario;
	private String descricao;
	private String titulo;
	private String msg;
	private Date dataMov;
	private boolean ativo;

	public MsgPreTorpedo() {
	}

	public MsgPreTorpedo(Usuario usuario, String descricao,
			String titulo, String msg, Date dataMov, boolean ativo) {
		this.usuario = usuario;
		this.descricao = descricao;
		this.titulo = titulo;
		this.msg = msg;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_msg_pre_torpedo", unique = true, nullable = false)
	public Long getIdMsgPreTorpedo() {
		return this.idMsgPreTorpedo;
	}

	public void setIdMsgPreTorpedo(Long idMsgPreTorpedo) {
		this.idMsgPreTorpedo = idMsgPreTorpedo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_mov", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "descricao", nullable = false, length = 50)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "titulo", nullable = false, length = 20)
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name = "msg", nullable = false, length = 150)
	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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
