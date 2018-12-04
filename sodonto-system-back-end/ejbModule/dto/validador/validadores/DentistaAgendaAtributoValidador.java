package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.DentistaAgendaDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class DentistaAgendaAtributoValidador implements IAtributoValidadorEntidade<DentistaAgendaDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(DentistaAgendaDTO entidadeDTO) {
		
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
			if(entidadeDTO.getDentista() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Dentista");
			}
			if(entidadeDTO.getDataAgenda() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Data da Agenda");
			}
			if(entidadeDTO.getHorarioI() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Horário Inicial");
			}
			if(entidadeDTO.getHorarioF() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Horário Final");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<DentistaAgendaDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(DentistaAgendaDTO dto : entidadesDTO)
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
