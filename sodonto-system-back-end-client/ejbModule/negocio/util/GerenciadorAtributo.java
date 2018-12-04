package negocio.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorAtributo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<String> atibutosNaoPreenchidos;
	
	public GerenciadorAtributo()
	{
		this.setAtibutosNaoPreenchidos(new ArrayList<String>());
	}

	public boolean isAtributosValidados()
	{
		return this.atibutosNaoPreenchidos.size() == 0;
	}
	
	public List<String> getAtibutosNaoPreenchidos() {
		return atibutosNaoPreenchidos;
	}

	public void setAtibutosNaoPreenchidos(List<String> atibutosNaoPreenchidos) {
		this.atibutosNaoPreenchidos = atibutosNaoPreenchidos;
	}
}
