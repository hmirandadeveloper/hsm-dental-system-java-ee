package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.EspecialidadeDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class EspecialidadeAtributoValidador implements IAtributoValidadorEntidade<EspecialidadeDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(EspecialidadeDTO entidadeDTO) {
		
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
			if(entidadeDTO.getNomeEspecialidade().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nome da Especialidade");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<EspecialidadeDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(EspecialidadeDTO dto : entidadesDTO)
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
