package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.MensalidadePacienteDTO;
import dto.validador.template.IAtributoValidadorEntidade;

public class MensalidadePacienteAtributoValidador implements IAtributoValidadorEntidade<MensalidadePacienteDTO>{
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(MensalidadePacienteDTO entidadeDTO) {
		
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
			if(entidadeDTO.getPlanoPaciente() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Plano do Paciente");
			}
			if(entidadeDTO.getPlanoMensalidade() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Mensalidade do Plano");
			}
			if(entidadeDTO.getValorPago() < 0)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Valor pago (inferior a zero)");
			}
			if(entidadeDTO.getSituacao() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Situação da Mensalidade");
			}
			if(entidadeDTO.getDataMensalidade() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Data de Vencimento");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<MensalidadePacienteDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(MensalidadePacienteDTO dto : entidadesDTO)
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
