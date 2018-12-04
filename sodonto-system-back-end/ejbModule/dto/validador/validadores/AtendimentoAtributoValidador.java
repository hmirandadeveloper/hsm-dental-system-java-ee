package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.AtendimentoDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class AtendimentoAtributoValidador implements IAtributoValidadorEntidade<AtendimentoDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(AtendimentoDTO entidadeDTO) {
		
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
			if(entidadeDTO.getAgendamento() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Agendamento");
			}
			if(entidadeDTO.getSituacao().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Situação do Atendimento");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<AtendimentoDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(AtendimentoDTO dto : entidadesDTO)
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
