package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.PlanoPacienteDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class PlanoPacienteAtributoValidador implements IAtributoValidadorEntidade<PlanoPacienteDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(PlanoPacienteDTO entidadeDTO) {
		
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
			if(entidadeDTO.getPaciente() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Paciente");
			}
			if(entidadeDTO.getPlano() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Plano");
			}
			if(entidadeDTO.getDataAssinatura() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Data de Assinatura");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<PlanoPacienteDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(PlanoPacienteDTO dto : entidadesDTO)
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
