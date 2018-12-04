package negocio.exception;

public class EntidadeInexistenteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EntidadeInexistenteException()
	{
		super(EntidadeInexistenteException.class.toString());
	}
	
	public EntidadeInexistenteException(String msg)
	{
		super(msg);
	}

}
