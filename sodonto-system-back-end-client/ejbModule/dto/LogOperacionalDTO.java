package dto;


import java.util.Date;


public class LogOperacionalDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idLogOperacional;
	private UsuarioDTO usuarioLogDTO;
	private String tituloOperacao;
	private String detalhesOperacao;
	private Date dataLog;
	private Date dataMov;

	public LogOperacionalDTO() {
	}

	public LogOperacionalDTO(UsuarioDTO usuarioLogDTO, String tituloOperacao, String detalhesOperacao,
			Date dataLog, Date dataMov) {
		this.usuarioLogDTO = usuarioLogDTO;
		this.tituloOperacao = tituloOperacao;
		this.detalhesOperacao = detalhesOperacao;
		this.dataLog = dataLog;
		this.dataMov = dataMov;
	}

	public Long getIdLogOperacional() {
		return this.idLogOperacional;
	}

	public void setIdLogOperacional(Long idLogOperacional) {
		this.idLogOperacional = idLogOperacional;
	}

	public UsuarioDTO getUsuarioLogDTO() {
		return this.usuarioLogDTO;
	}

	public void setUsuarioLogDTO(UsuarioDTO usuarioLogDTO) {
		this.usuarioLogDTO = usuarioLogDTO;
	}

	public String getTituloOperacao() {
		return this.tituloOperacao;
	}

	public void setTituloOperacao(String tituloOperacao) {
		this.tituloOperacao = tituloOperacao;
	}
	
	public String getDetalhesOperacao() {
		return this.detalhesOperacao;
	}

	public void setDetalhesOperacao(String detalhesOperacao) {
		this.detalhesOperacao = detalhesOperacao;
	}

	public Date getDataLog() {
		return this.dataLog;
	}

	public void setDataLog(Date dataLog) {
		this.dataLog = dataLog;
	}

	public Date getDataMov() {
		return this.dataMov;
	}

	public void setDataMov(Date dataMov) {
		this.dataMov = dataMov;
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
		if(!(obj instanceof LogOperacionalDTO))
		{
			return false;
		}
		LogOperacionalDTO outro = (LogOperacionalDTO) obj;
		if(idLogOperacional == null)
		{
			if(outro.getIdLogOperacional() != null)
			{
				return false;
			}
		}
		else if(!idLogOperacional.equals(outro.getIdLogOperacional()))
		{
			return false;
		}
		
		return true;
	}
}
