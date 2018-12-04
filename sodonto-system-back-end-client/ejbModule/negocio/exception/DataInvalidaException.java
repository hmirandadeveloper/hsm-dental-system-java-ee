package negocio.exception;

public class DataInvalidaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DataInvalidaException()
	{
		super(DataInvalidaException.class.toString());
	}
	
	public DataInvalidaException(String msg)
	{
		super(msg);
	}

}
