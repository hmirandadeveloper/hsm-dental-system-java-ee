package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.MsgPreTorpedoDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.MsgPreTorpedo;


public class MsgPreTorpedoConversorDTO implements IConversorDTO<MsgPreTorpedoDTO, MsgPreTorpedo>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	
	public MsgPreTorpedoConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
	}
	
	@Override
	public MsgPreTorpedo converterDTOEmEntidade(MsgPreTorpedoDTO entidadeDTO) {

		MsgPreTorpedo entidade = new MsgPreTorpedo();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdMsgPreTorpedo() != null)
			{
				entidade.setIdMsgPreTorpedo(entidadeDTO.getIdMsgPreTorpedo());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setDescricao(entidadeDTO.getDescricao());
			entidade.setTitulo(entidadeDTO.getTitulo());
			entidade.setMsg(entidadeDTO.getMsg());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public MsgPreTorpedoDTO converterEntidadeEmDTO(MsgPreTorpedo entidade) {
		MsgPreTorpedoDTO entidadeDTO = new MsgPreTorpedoDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdMsgPreTorpedo() != null)
			{
				entidadeDTO.setIdMsgPreTorpedo(entidade.getIdMsgPreTorpedo());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setDescricao(entidade.getDescricao());
			entidadeDTO.setTitulo(entidade.getTitulo());
			entidadeDTO.setMsg(entidade.getMsg());
			entidadeDTO.setAtivo(entidade.isAtivo());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<MsgPreTorpedoDTO> converterSetEntidadeEmListDTO(Set<MsgPreTorpedo> entidades) {

		List<MsgPreTorpedoDTO> entidadesDTO = new ArrayList<MsgPreTorpedoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(MsgPreTorpedo entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<MsgPreTorpedoDTO> converterListEntidadeEmListDTO(
			List<MsgPreTorpedo> entidades) {
		List<MsgPreTorpedoDTO> entidadesDTO = new ArrayList<MsgPreTorpedoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(MsgPreTorpedo entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
