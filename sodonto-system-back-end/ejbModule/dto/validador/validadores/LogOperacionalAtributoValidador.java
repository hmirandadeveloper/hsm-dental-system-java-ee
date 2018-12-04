package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.LogOperacionalDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class LogOperacionalAtributoValidador implements IAtributoValidadorEntidade<LogOperacionalDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(LogOperacionalDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(entidadeDTO.getUsuarioLogDTO() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Usuário do Log");
			}
			if(entidadeDTO.getTituloOperacao().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Título do Log Operacional");
			}
			if(entidadeDTO.getDetalhesOperacao().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Detalhes do Log Operacional");
			}
			if(entidadeDTO.getDataLog() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Data do Log Operacional");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<LogOperacionalDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(LogOperacionalDTO dto : entidadesDTO)
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
