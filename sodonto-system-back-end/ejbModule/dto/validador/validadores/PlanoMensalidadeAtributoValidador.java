package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.PlanoMensalidadeDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class PlanoMensalidadeAtributoValidador implements IAtributoValidadorEntidade<PlanoMensalidadeDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(PlanoMensalidadeDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(entidadeDTO.getPlano() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Plano");
			}
			if(entidadeDTO.getMes() <= 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Mês");
			}
			if(entidadeDTO.getValorMes() < 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Valor da Mensalidade");
			}
			if(entidadeDTO.getValorReajustado() < 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Valor Reajustado");
			}
		}
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<PlanoMensalidadeDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(PlanoMensalidadeDTO dto : entidadesDTO)
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
