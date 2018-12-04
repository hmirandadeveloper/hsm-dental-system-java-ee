package negocio.exception;

public class CpfInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CpfInvalidoException()
	{
		super(CpfInvalidoException.class.toString());
	}
	
	public CpfInvalidoException(String msg)
	{
		super(msg);
	}

}
