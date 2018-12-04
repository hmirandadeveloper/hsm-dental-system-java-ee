package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.UsuarioDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class UsuarioAtributoValidador implements IAtributoValidadorEntidade<UsuarioDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(UsuarioDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(entidadeDTO.getUsuario().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Login");
			}
			if(entidadeDTO.getSenha().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Senha");
			}
			if(entidadeDTO.getPerfilCadastro() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Perfil de Cadastro");
			}
			if(entidadeDTO.getPerfilAtivo() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Perfil Ativo");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<UsuarioDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(UsuarioDTO dto : entidadesDTO)
			{
				if(!validarAtributosEmEntidade(dto).isAtributosValidados())
				{
					validacaoAtributo = false;
					break;
				}
			}
		}
		
		return validacaoAtributo;
	}

}
