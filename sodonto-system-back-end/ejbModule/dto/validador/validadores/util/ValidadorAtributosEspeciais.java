package dto.validador.validadores.util;

import java.util.Date;

public abstract class ValidadorAtributosEspeciais {
	
	public static boolean validarCpf(String cpf)
	{
		return true;
	}
	
	public static boolean validarCnpj(String cnpj)
	{
		return true;
	}
	
	public static boolean validarEmail(String email)
	{
		return true;
	}
	
	public static boolean validarDataComAtual(Date data)
	{
		if(data.getTime() <= (new Date().getTime() + 1000000))
		{
			return true;
		}
		
		return false;
	}
}
