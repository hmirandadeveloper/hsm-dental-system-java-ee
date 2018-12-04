package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.PlanoDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class PlanoAtributoValidador implements IAtributoValidadorEntidade<PlanoDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(PlanoDTO entidadeDTO) {
		
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
			if(entidadeDTO.getUsuario() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Usuário Cadastrante");
			}
			if(entidadeDTO.getNomePlano().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nome");
			}
			if(entidadeDTO.getDescricaoPlano().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Descrição");
			}
			if(entidadeDTO.getTotalMeses() <= 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Total de Meses");
			}
			if(entidadeDTO.getValorTotal() < 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Valor Total");
			}
			if(entidadeDTO.getValidade() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Validade");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<PlanoDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(PlanoDTO dto : entidadesDTO)
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
