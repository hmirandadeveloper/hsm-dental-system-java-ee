package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.ObsPacienteDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class ObsPacienteAtributoValidador implements IAtributoValidadorEntidade<ObsPacienteDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(ObsPacienteDTO entidadeDTO) {
		
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
			if(entidadeDTO.getPacienteDTO() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Paciente");
			}
			if(entidadeDTO.getData() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Data");
			}
			if(entidadeDTO.getObs().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Observação");
			}
			if(entidadeDTO.getTipo() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Tipo de Observação");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<ObsPacienteDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(ObsPacienteDTO dto : entidadesDTO)
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
