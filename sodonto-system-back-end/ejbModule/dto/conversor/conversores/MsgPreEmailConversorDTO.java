package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.MsgPreEmailDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.MsgPreEmail;


public class MsgPreEmailConversorDTO implements IConversorDTO<MsgPreEmailDTO, MsgPreEmail>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	
	public MsgPreEmailConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
	}
	
	@Override
	public MsgPreEmail converterDTOEmEntidade(MsgPreEmailDTO entidadeDTO) {

		MsgPreEmail entidade = new MsgPreEmail();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdMsgPreEmail() != null)
			{
				entidade.setIdMsgPreEmail(entidadeDTO.getIdMsgPreEmail());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setDescicao(entidadeDTO.getDescicao());
			entidade.setTitulo(entidadeDTO.getTitulo());
			entidade.setMsg(entidadeDTO.getMsg());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public MsgPreEmailDTO converterEntidadeEmDTO(MsgPreEmail entidade) {
		MsgPreEmailDTO entidadeDTO = new MsgPreEmailDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdMsgPreEmail() != null)
			{
				entidadeDTO.setIdMsgPreEmail(entidade.getIdMsgPreEmail());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setDescicao(entidade.getDescicao());
			entidadeDTO.setTitulo(entidade.getTitulo());
			entidadeDTO.setMsg(entidade.getMsg());
			entidadeDTO.setAtivo(entidade.isAtivo());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<MsgPreEmailDTO> converterSetEntidadeEmListDTO(Set<MsgPreEmail> entidades) {

		List<MsgPreEmailDTO> entidadesDTO = new ArrayList<MsgPreEmailDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(MsgPreEmail entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<MsgPreEmailDTO> converterListEntidadeEmListDTO(
			List<MsgPreEmail> entidades) {
		List<MsgPreEmailDTO> entidadesDTO = new ArrayList<MsgPreEmailDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(MsgPreEmail entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
