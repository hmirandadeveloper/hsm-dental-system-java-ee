package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.CaixaDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class CaixaAtributoValidador implements IAtributoValidadorEntidade<CaixaDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(CaixaDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(entidadeDTO.getEstabelecimentoDTO() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Estabelecimento");
			}
			if(entidadeDTO.getUsuarioAbertura() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Usuário de Abertura");
			}
			if(entidadeDTO.getDataCaixa() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Data do Caixa");
			}
			if(entidadeDTO.getNumeroOrdem() <= 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Número de Ordem (inferior a zero)");
			}
			if(entidadeDTO.getValorTotal() < 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Valor Total (inferior a zero)");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<CaixaDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(CaixaDTO dto : entidadesDTO)
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
