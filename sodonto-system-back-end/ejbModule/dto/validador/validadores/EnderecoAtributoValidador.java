package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.EnderecoDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class EnderecoAtributoValidador implements IAtributoValidadorEntidade<EnderecoDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(EnderecoDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(entidadeDTO.getLogradouro().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Logradouro");
			}
			if(entidadeDTO.getNumero() < 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Número (inferior a zero)");
			}
			if(entidadeDTO.getBairro().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Bairro");
			}
			if(entidadeDTO.getCidade().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Cidade");
			}
			if(entidadeDTO.getUf() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("UF");
			}
			if(entidadeDTO.getCep().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("CEP");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<EnderecoDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(EnderecoDTO dto : entidadesDTO)
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
