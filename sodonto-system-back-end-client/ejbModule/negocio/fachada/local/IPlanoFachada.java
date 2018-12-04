package negocio.fachada.local;

import java.util.Date;
import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.DataInvalidaException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.PlanoDTO;

public interface IPlanoFachada {
	public abstract void salvar(PlanoDTO planoDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException, 
			DataInvalidaException;
	public abstract PlanoDTO alterar(PlanoDTO planoDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException,
			DataInvalidaException, EntidadeCadastradaException;
	public abstract void inativar(Long idPlano, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract PlanoDTO buscarPorId(Long idPlano);
	public abstract List<PlanoDTO> buscarVigentes(Long idEstabelecimento);
	public abstract List<PlanoDTO> buscarPorNome(String nomePlano);
	public abstract List<PlanoDTO> buscarPorValidade(Date dataI, Date dataF);
	public abstract List<PlanoDTO> buscarAtivos();
	public abstract List<PlanoDTO> buscarInativos();
	public abstract PlanoDTO getEntidadeFromList(List<PlanoDTO> lista);
}
