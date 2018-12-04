package dto;

import java.util.Date;

import negocio.util.DataUtil;

public class DentistaAgendaDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idDentistaAgenda;
	private EstabelecimentoDTO estabelecimentoDTO;
	private UsuarioDTO usuarioDTO;
	private DentistaDTO dentistaDTO;
	private Date dataAgenda;
	private Date horarioI;
	private Date horarioF;
	private Date dataMov;
	private boolean ativo;

	public DentistaAgendaDTO() {
	}

	public DentistaAgendaDTO(EstabelecimentoDTO estabelecimentoDTO, UsuarioDTO usuarioDTO, 
			DentistaDTO dentistaDTO, Date dataAgenda, Date horarioI, Date horarioF, Date dataMov,
			boolean ativo) {
		this.estabelecimentoDTO = estabelecimentoDTO;
		this.usuarioDTO = usuarioDTO;
		this.dentistaDTO = dentistaDTO;
		this.dataAgenda = dataAgenda;
		this.horarioI = horarioI;
		this.horarioF = horarioF;
		this.dataMov = dataMov;
		this.ativo = ativo;
	}

	public Long getIdDentistaAgenda() {
		return this.idDentistaAgenda;
	}

	public void setIdDentistaAgenda(Long idDentistaAgenda) {
		this.idDentistaAgenda = idDentistaAgenda;
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

	public DentistaDTO getDentista() {
		return this.dentistaDTO;
	}

	public void setDentista(DentistaDTO dentistaDTO) {
		this.dentistaDTO = dentistaDTO;
	}

	public Date getDataAgenda() {
		return this.dataAgenda;
	}

	public void setDataAgenda(Date dataAgenda) {
		this.dataAgenda = dataAgenda;
	}

	public Date getHorarioI() {
		return this.horarioI;
	}

	public void setHorarioI(Date horarioI) {
		this.horarioI = horarioI;
	}

	public Date getHorarioF() {
		return this.horarioF;
	}

	public void setHorarioF(Date horarioF) {
		this.horarioF = horarioF;
	}
	
	public String getIntervaloTempo()
	{
		return "DE " + DataUtil.getHoraPorData(this.horarioI) + "h ÀS " + 
				DataUtil.getHoraPorData(horarioF) + "h";
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
		
		System.out.println("[SODONTO SYSTEM][MB]Comparação DentistaAgenda...");
		
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(!(obj instanceof DentistaAgendaDTO))
		{
			return false;
		}
		DentistaAgendaDTO outro = (DentistaAgendaDTO) obj;
		if(idDentistaAgenda == null)
		{
			if(outro.getIdDentistaAgenda() != null)
			{
				return false;
			}
		}
		else if(!idDentistaAgenda.equals(outro.getIdDentistaAgenda()))
		{
			return false;
		}
		
		return true;
	}
}
