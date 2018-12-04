package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.OperadoraDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class OperadoraAtributoValidador implements IAtributoValidadorEntidade<OperadoraDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(OperadoraDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(entidadeDTO.getUsuario().getIdUsuario() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Usuário Cadastrante");
			}
			if(entidadeDTO.getNomeOperadora().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nome da Operadora");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<OperadoraDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(OperadoraDTO dto : entidadesDTO)
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
