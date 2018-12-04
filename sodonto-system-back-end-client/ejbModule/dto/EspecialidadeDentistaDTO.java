package dto;

import java.util.Date;

public class EspecialidadeDentistaDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idEspecialidadeDentista;
	private EspecialidadeDTO especialidadeDTO;
	private DentistaDTO dentistaDTO;
	private Date dataMov;
	private boolean ativo;

	public EspecialidadeDentistaDTO() {
	}

	public EspecialidadeDentistaDTO(EspecialidadeDTO especialidadeDTO,
			DentistaDTO dentistaDTO, Date dataMov, boolean ativo) {
		this.especialidadeDTO = especialidadeDTO;
		this.dentistaDTO = dentistaDTO;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdEspecialidadeDentista() {
		return this.idEspecialidadeDentista;
	}

	public void setIdEspecialidadeDentista(Long idEspecialidadeDentista) {
		this.idEspecialidadeDentista = idEspecialidadeDentista;
	}

	public EspecialidadeDTO getEspecialidade() {
		return this.especialidadeDTO;
	}

	public void setEspecialidade(EspecialidadeDTO especialidadeDTO) {
		this.especialidadeDTO = especialidadeDTO;
	}

	public DentistaDTO getDentista() {
		return this.dentistaDTO;
	}

	public void setDentista(DentistaDTO dentistaDTO) {
		this.dentistaDTO = dentistaDTO;
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
		
		System.out.println("[SODONTO SYSTEM][MB]Comparação EspecialidadeDentista...");
		
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(!(obj instanceof EspecialidadeDentistaDTO))
		{
			return false;
		}
		EspecialidadeDentistaDTO outro = (EspecialidadeDentistaDTO) obj;
		if(idEspecialidadeDentista == null)
		{
			if(outro.getIdEspecialidadeDentista() != null)
			{
				return false;
			}
		}
		else if(!idEspecialidadeDentista.equals(outro.getIdEspecialidadeDentista()))
		{
			return false;
		}
		
		return true;
	}
}
