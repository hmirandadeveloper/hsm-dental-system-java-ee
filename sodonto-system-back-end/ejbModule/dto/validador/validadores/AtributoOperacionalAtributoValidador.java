package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.AtributoOperacionalDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class AtributoOperacionalAtributoValidador implements IAtributoValidadorEntidade<AtributoOperacionalDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(AtributoOperacionalDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(entidadeDTO.getUsuarioDTO() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Usuário Cadastrante");
			}
			if(entidadeDTO.getTitulo().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Título");
			}
			if(entidadeDTO.getTempoValidacaoTelefone() < 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Tempo para Atualizar o Telefone");
			}
			if(entidadeDTO.getDuracaoAtendimento() < 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Duração do Atendimento");
			}
			if(entidadeDTO.getNumeroLinhaTabela() < 1)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Total de Linas das Tabelas");
			}
			if(entidadeDTO.getLimitQuery() < 1)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Limite da Query");
			}
			if(entidadeDTO.getProvedorSMS() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Provedor SMS");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<AtributoOperacionalDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(AtributoOperacionalDTO dto : entidadesDTO)
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
