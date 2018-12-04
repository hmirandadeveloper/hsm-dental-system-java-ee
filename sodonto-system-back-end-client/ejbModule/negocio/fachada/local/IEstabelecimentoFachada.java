package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CnpjInvalidoException;
import negocio.exception.EmailInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.EstabelecimentoDTO;

public interface IEstabelecimentoFachada {
	public abstract void salvar(EstabelecimentoDTO pacienteDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException,
			CnpjInvalidoException, EmailInvalidoException;
	public abstract EstabelecimentoDTO alterar(EstabelecimentoDTO pacienteDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException,
			CnpjInvalidoException, EmailInvalidoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idEstabelecimento, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract EstabelecimentoDTO buscarPorId(Long idEstabelecimento);
	public abstract List<EstabelecimentoDTO> buscarPorCnpj(String cnpj);
	public abstract List<EstabelecimentoDTO> buscarPorNome(String nome);
	public abstract List<EstabelecimentoDTO> buscarAtivos();
	public abstract List<EstabelecimentoDTO> buscarInativos();
	public abstract EstabelecimentoDTO getEntidadeFromList(List<EstabelecimentoDTO> lista);
}
