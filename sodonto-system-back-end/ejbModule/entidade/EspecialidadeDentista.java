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
@Table(name = "tb_especialidade_dentista", catalog = "dbsodontosystem")

@NamedQueries({					  					  
	@NamedQuery(name="EspecialidadeDentista.buscarPorEspecialidade",
				query="SELECT ed" +
					  " FROM EspecialidadeDentista ed" +
					  " WHERE ed.especialidade.idEspecialidade = :idEspecialidade" +
					  " AND ed.ativo = true"),
							  
	@NamedQuery(name="EspecialidadeDentista.buscarPorDentista",
				query="SELECT ed" +
					  " FROM EspecialidadeDentista ed" +
					  " WHERE ed.dentista.idDentista = :idDentista" +
					  " AND ed.ativo = true"),			
					  
	@NamedQuery(name="EspecialidadeDentista.buscarPorEspecialidadeEDentista",
				query="SELECT ed" +
					  " FROM EspecialidadeDentista ed" +
					  " WHERE ed.dentista.idDentista = :idDentista" +
					  " AND ed.especialidade.idEspecialidade = :idEspecialidade" +
					  " AND ed.ativo = true"),							  
					  
	@NamedQuery(name="EspecialidadeDentista.buscarPorCondicao",
				query="SELECT ed" +
					  " FROM EspecialidadeDentista ed" +
					  " WHERE ed.ativo = :condicao"),					  
})

public class EspecialidadeDentista implements java.io.Serializable {

	//Named Querys - Constantes
	public static final String NQ_BUSCAR_POR_ESPECIALIDADE = "EspecialidadeDentista.buscarPorEspecialidade";
	public static final String NQ_BUSCAR_POR_DENTISTA = "EspecialidadeDentista.buscarPorDentista";
	public static final String NQ_BUSCAR_POR_ESPECIALIDADE_E_DENTISTA = "EspecialidadeDentista.buscarPorEspecialidadeEDentista";
	public static final String NQ_BUSCAR_POR_CONDICAO = "EspecialidadeDentista.buscarPorCondicao";
	
	private static final long serialVersionUID = 1L;
	private Long idEspecialidadeDentista;
	private Especialidade especialidade;
	private Dentista dentista;
	private Date dataMov;
	private boolean ativo;

	public EspecialidadeDentista() {
	}

	public EspecialidadeDentista(Especialidade especialidade,
			Dentista dentista, Date dataMov, boolean ativo) {
		this.especialidade = especialidade;
		this.dentista = dentista;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_especialidade_dentista", unique = true, nullable = false)
	public Long getIdEspecialidadeDentista() {
		return this.idEspecialidadeDentista;
	}

	public void setIdEspecialidadeDentista(Long idEspecialidadeDentista) {
		this.idEspecialidadeDentista = idEspecialidadeDentista;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_especialidade", nullable = false)
	public Especialidade getEspecialidade() {
		return this.especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dentista", nullable = false)
	public Dentista getDentista() {
		return this.dentista;
	}

	public void setDentista(Dentista dentista) {
		this.dentista = dentista;
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
