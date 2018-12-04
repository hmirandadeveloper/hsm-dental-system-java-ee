package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.CargoDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class CargoAtributoValidador implements IAtributoValidadorEntidade<CargoDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(CargoDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(entidadeDTO.getUsuario() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Usuário Cadastrante");
			}
			if(entidadeDTO.getNomeCargo().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nome do Cargo");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<CargoDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(CargoDTO dto : entidadesDTO)
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
