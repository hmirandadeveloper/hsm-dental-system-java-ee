package negocio.util.email;

public class EmailSimples {
	
	private String destinatario;
	private String titulo;
	private MsgEmail msgEmail;

	
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario.toLowerCase();
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public MsgEmail getMsgEmail() {
		return msgEmail;
	}
	public void setMsgEmail(MsgEmail msgEmail) {
		this.msgEmail = msgEmail;
	}
}
