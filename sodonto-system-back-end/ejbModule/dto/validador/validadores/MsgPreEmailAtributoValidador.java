package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.MsgPreEmailDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class MsgPreEmailAtributoValidador implements IAtributoValidadorEntidade<MsgPreEmailDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(MsgPreEmailDTO entidadeDTO) {
		
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
			if(entidadeDTO.getDescicao().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Descrição");
			}
			if(entidadeDTO.getTitulo().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Título");
			}
			if(entidadeDTO.getMsg().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Mensagem");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<MsgPreEmailDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(MsgPreEmailDTO dto : entidadesDTO)
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
