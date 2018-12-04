package negocio.util.email;

public class MsgEmail {
	private String cabecalho; 
	private String tituloMsg;
	private String msg;
	private String assinatura;
	
	public String getCabecalho() {
		return cabecalho;
	}
	public void setCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}
	public String getTituloMsg() {
		return tituloMsg;
	}
	public void setTituloMsg(String tituloMsg) {
		this.tituloMsg = tituloMsg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAssinatura() {
		return assinatura;
	}
	public void setAssinatura(String assinatura) {
		this.assinatura = assinatura;
	}
	
	public String getMsgMontada()
	{
		return this.cabecalho + this.msg + this.assinatura;
	}
}
