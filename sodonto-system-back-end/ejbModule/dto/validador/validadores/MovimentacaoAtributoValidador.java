package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.MovimentacaoDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class MovimentacaoAtributoValidador implements IAtributoValidadorEntidade<MovimentacaoDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(MovimentacaoDTO entidadeDTO) {
		
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
			if(entidadeDTO.getCaixa() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Caixa");
			}
			if(entidadeDTO.getTipo().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Tipo de Movimentação");
			}
			if(entidadeDTO.getRefMovimentacao().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Referência da Movimentação");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<MovimentacaoDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(MovimentacaoDTO dto : entidadesDTO)
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
