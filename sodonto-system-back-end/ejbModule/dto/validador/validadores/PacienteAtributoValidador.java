package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.PacienteDTO;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.template.IAtributoValidadorEntidade;

public class PacienteAtributoValidador implements IAtributoValidadorEntidade<PacienteDTO>{

	private EnderecoAtributoValidador enderecoAtributoValidador;
	
	public PacienteAtributoValidador()
	{
		this.enderecoAtributoValidador = AtributoValidadorFactory.getEnderecoAtributoValidador();
	}
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(PacienteDTO entidadeDTO) {

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
			if(!this.enderecoAtributoValidador.validarAtributosEmEntidade(entidadeDTO.getEndereco()).isAtributosValidados())
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Endereço");
			}
			if(entidadeDTO.getUsuario() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Usuário Cadastrante");
			}
			if(entidadeDTO.getDentistaDTO() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Dentista vinculado");
			}
			if(entidadeDTO.getNome().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nome");
			}
			if(entidadeDTO.getSobrenome().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Sobrenome");
			}
			if(entidadeDTO.isContratante())
			{
				if(entidadeDTO.getCpf().equals(""))
				{
					gerenciadorAtributos.getAtibutosNaoPreenchidos().add("CPF");
				}
			}
			else
			{
				if(entidadeDTO.getPaciente() == null)
				{
					gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Paciente Titular");
				}
			}
			if(entidadeDTO.getSexo().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Sexo");
			}
			if(entidadeDTO.getEstadoCivil() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Estado Civil");
			}
			if(entidadeDTO.getTelResidencial().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Telefone");
			}
			if(entidadeDTO.getCel01().equals("") || 
					entidadeDTO.getOperadoraCel01() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Celular (Mensagem)");
			}
			if(entidadeDTO.getDataNascimento() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Data de Nascimento");
			}
			if(entidadeDTO.getSituacaoPaciente() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Situação de Cadastro");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<PacienteDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(PacienteDTO dto : entidadesDTO)
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
