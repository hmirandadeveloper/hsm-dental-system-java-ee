package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import negocio.constante.enums.EPerfilUsuario;
import dto.UsuarioDTO;
import dto.conversor.template.IConversorDTO;
import entidade.Usuario;


public class UsuarioConversorDTO implements IConversorDTO<UsuarioDTO, Usuario>{

	@Override
	public Usuario converterDTOEmEntidade(UsuarioDTO entidadeDTO) {

		Usuario entidade = new Usuario();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdUsuario() != null)
			{
				entidade.setIdUsuario(entidadeDTO.getIdUsuario());
			}
			entidade.setUsuario(entidadeDTO.getUsuario());
			entidade.setSenha(entidadeDTO.getSenha());
			entidade.setPerfilAtivo(entidadeDTO.getPerfilAtivo().name());
			entidade.setPerfilCadastro(entidadeDTO.getPerfilCadastro().name());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public UsuarioDTO converterEntidadeEmDTO(Usuario entidade) {
		UsuarioDTO entidadeDTO = new UsuarioDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			entidadeDTO.setIdUsuario(entidade.getIdUsuario());
			entidadeDTO.setUsuario(entidade.getUsuario());
			entidadeDTO.setSenha(entidade.getSenha());
			entidadeDTO.setPerfilAtivo(EPerfilUsuario.valueOf(entidade.getPerfilAtivo()));
			entidadeDTO.setPerfilCadastro(EPerfilUsuario.valueOf(entidade.getPerfilCadastro()));
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<UsuarioDTO> converterSetEntidadeEmListDTO(Set<Usuario> entidades) {

		List<UsuarioDTO> entidadesDTO = new ArrayList<UsuarioDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Usuario entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<UsuarioDTO> converterListEntidadeEmListDTO(
			List<Usuario> entidades) {
		List<UsuarioDTO> entidadesDTO = new ArrayList<UsuarioDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Usuario entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
