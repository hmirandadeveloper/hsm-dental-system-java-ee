package negocio.exception;

public class EntidadeCadastradaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EntidadeCadastradaException()
	{
		super(EntidadeCadastradaException.class.toString());
	}
	
	public EntidadeCadastradaException(String msg)
	{
		super(msg);
	}

}
