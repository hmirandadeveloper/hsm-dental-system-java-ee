package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.MsgPreTorpedoDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class MsgPreTorpedoAtributoValidador implements IAtributoValidadorEntidade<MsgPreTorpedoDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(MsgPreTorpedoDTO entidadeDTO) {
		
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
			if(entidadeDTO.getDescricao().equals(""))
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
			List<MsgPreTorpedoDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(MsgPreTorpedoDTO dto : entidadesDTO)
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
