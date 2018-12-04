package gui.util;

import java.util.Random;

import org.jboss.crypto.CryptoUtil;

public class UsuarioUtil {
	
	private static final String[] caracteres = {"a","b","c","d","e","f","g","h","i","j",
		"k","k","m","n","o","p","q","r","s","t","u","v","w","y","x","z","A","B",
		"C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S",
		"T","U","V","W","Y","X","Z","1","2","3","4","5","6","7","8","9","0",
		",","_","@","#","!","&","$"};
	
	public static String gerarSenha()
	{
		String senha = "";
		Random random = new Random();
		
		for(int i = 0; i < 8; i++)
		{
			senha = senha + caracteres[random.nextInt(caracteres.length - 1)];
		}
		
		return senha;
	}
	
	public static String criptografarSenha(String pass)
	{
		String senhaCripto = "";
		
		senhaCripto = CryptoUtil.createPasswordHash("MD5", CryptoUtil.BASE64_ENCODING, null, null, pass);
		
		return senhaCripto;
	}
}
