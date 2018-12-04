package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.EspecialidadeDentistaDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class EspecialidadeDentistaAtributoValidador implements IAtributoValidadorEntidade<EspecialidadeDentistaDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(EspecialidadeDentistaDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(entidadeDTO.getEspecialidade() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Especialidade");
			}
			if(entidadeDTO.getDentista() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Dentista");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<EspecialidadeDentistaDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(EspecialidadeDentistaDTO dto : entidadesDTO)
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
