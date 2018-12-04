package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.AgendamentoDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class AgendamentoAtributoValidador implements IAtributoValidadorEntidade<AgendamentoDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(AgendamentoDTO entidadeDTO) {
		
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
			if(entidadeDTO.getUsuarioCriacaoDTO() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Usuário criador do Agendamento");
			}
			if(entidadeDTO.getDataCriacao() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Data de Criação");
			}
			if(entidadeDTO.getDentistaAgenda() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Agenda do Dentista");
			}
			if(entidadeDTO.getPaciente() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Paciente");
			}
			if(entidadeDTO.getSituacao().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Situação do Agendamento");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<AgendamentoDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(AgendamentoDTO dto : entidadesDTO)
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
