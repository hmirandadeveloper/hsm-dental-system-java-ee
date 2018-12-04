package negocio.exception;

public class SenhaInvalidaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public SenhaInvalidaException()
	{
		super(SenhaInvalidaException.class.toString());
	}
	
	public SenhaInvalidaException(String msg)
	{
		super(msg);
	}

}
