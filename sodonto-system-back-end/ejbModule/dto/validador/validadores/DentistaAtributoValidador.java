package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.DentistaDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class DentistaAtributoValidador implements IAtributoValidadorEntidade<DentistaDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(DentistaDTO entidadeDTO) {
		
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
			if(entidadeDTO.getNome().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nome");
			}
			if(entidadeDTO.getSobrenome().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Sobrenome");
			}
			if(entidadeDTO.getCpf().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("CPF");
			}
			if(entidadeDTO.getCroUf() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("UF do CRO");
			}
			if(entidadeDTO.getCro().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("CRO");
			}
			if(entidadeDTO.getPisNit().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("PIS/NIT");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<DentistaDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(DentistaDTO dto : entidadesDTO)
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
