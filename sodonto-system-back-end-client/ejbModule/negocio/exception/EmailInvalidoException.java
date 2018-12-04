package negocio.exception;

public class EmailInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EmailInvalidoException()
	{
		super(EmailInvalidoException.class.toString());
	}
	
	public EmailInvalidoException(String msg)
	{
		super(msg);
	}

}
