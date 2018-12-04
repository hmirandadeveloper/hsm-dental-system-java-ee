package negocio.exception;

public class PacientePendenciaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PacientePendenciaException()
	{
		super(PacientePendenciaException.class.toString());
	}
	
	public PacientePendenciaException(String msg)
	{
		super(msg);
	}

}
