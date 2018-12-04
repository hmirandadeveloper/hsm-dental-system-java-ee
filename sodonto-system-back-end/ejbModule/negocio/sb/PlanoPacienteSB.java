package negocio.sb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IPlanoPacienteFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.PlanoPacienteDAO;
import dao.util.Filtro;
import dto.PlanoPacienteDTO;
import dto.conversor.conversores.PlanoPacienteConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.PlanoPacienteAtributoValidador;
import entidade.PlanoPaciente;

@Stateless
@Remote(IPlanoPacienteFachada.class)
public class PlanoPacienteSB implements IPlanoPacienteFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private PlanoPacienteDAO planoPacienteDAO;
	
	private PlanoPacienteConversorDTO planoPacienteConversorDTO;
	private PlanoPacienteAtributoValidador planoPacienteAtributoValidador;
	
	public PlanoPacienteSB()
	{
		this.planoPacienteConversorDTO = ConversorDTOFactory.getPlanoPacienteConversorDTO();
		this.planoPacienteAtributoValidador = AtributoValidadorFactory.getPlanoPacienteAtributoValidador();
	}
	
	@Override
	public void salvar(PlanoPacienteDTO planoPacienteDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.planoPacienteAtributoValidador.validarAtributosEmEntidade(planoPacienteDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<PlanoPaciente> planoPacientesBusca = this.planoPacienteDAO.buscarPorPlanoEPaciente(
					planoPacienteDTO.getPlano().getIdPlano(), planoPacienteDTO.getPaciente().getIdPaciente());
			PlanoPaciente planoPacienteBusca = planoPacientesBusca.size() > 0 && planoPacientesBusca.size() < 2 ? 
					planoPacientesBusca.get(0) : null;

			if(planoPacienteBusca != null)
			{
				if(!planoPacienteBusca.isAtivo())
				{
					planoPacienteDTO.setIdPlanoPaciente(planoPacienteBusca.getIdPlanoPaciente());
					planoPacienteDTO.setAtivo(true);
					this.planoPacienteDAO.atualizar(this.planoPacienteConversorDTO.converterDTOEmEntidade(planoPacienteDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				planoPacienteDTO.setAtivo(true);
				this.planoPacienteDAO.salvar(this.planoPacienteConversorDTO.converterDTOEmEntidade(planoPacienteDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public PlanoPacienteDTO alterar(PlanoPacienteDTO planoPacienteDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		PlanoPacienteDTO planoPacienteRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.planoPacienteAtributoValidador.validarAtributosEmEntidade(planoPacienteDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<PlanoPaciente> planoPacientesBusca = this.planoPacienteDAO.buscarPorPlanoEPaciente(
					planoPacienteDTO.getPlano().getIdPlano(), planoPacienteDTO.getPaciente().getIdPaciente());
			PlanoPaciente planoPacienteBusca = planoPacientesBusca.size() > 0 && planoPacientesBusca.size() < 2 ? 
					planoPacientesBusca.get(0) : null;
			
			if(planoPacienteBusca != null)
			{
				planoPacienteBusca = this.planoPacienteConversorDTO.converterDTOEmEntidade(planoPacienteDTO);
				this.planoPacienteDAO.atualizar(planoPacienteBusca);
				
				planoPacienteRetornoDTO = planoPacienteDTO;
			}
			else if(planoPacientesBusca.size() > 1)
			{
				throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}
		
		return planoPacienteRetornoDTO;
	}

	@Override
	public void inativar(Long idPlanoPaciente, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		PlanoPaciente planoPacienteBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			planoPacienteBusca = this.planoPacienteDAO.buscar(idPlanoPaciente);
			if(planoPacienteBusca != null)
			{
				planoPacienteBusca.setAtivo(false);
				this.planoPacienteDAO.atualizar(planoPacienteBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public PlanoPacienteDTO buscarPorId(Long idPlanoPaciente) {
		return this.planoPacienteConversorDTO.converterEntidadeEmDTO(
				this.planoPacienteDAO.buscar(idPlanoPaciente)
				);
	}
	
	@Override
	public List<PlanoPacienteDTO> buscarPorPaciente(Long idPaciente) {
		return this.planoPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.planoPacienteDAO.buscarPorPaciente(idPaciente)
				);
	}
	
	//2.2.00
	@Override
	public List<PlanoPacienteDTO> buscarPorPlanoEPacienteNome(Long idPlano, String pacienteNome,Filtro filtro) {
		return this.planoPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.planoPacienteDAO.buscarPorPlanoEPacienteNomeComFiltro(idPlano, pacienteNome, filtro)
				);
	}
	
	@Override
	public List<PlanoPacienteDTO> buscarPorPlanoEPacienteNome(Long idPlano, String pacienteNome) {
		return this.planoPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.planoPacienteDAO.buscarPorPlanoEPacienteNomeComFiltro(idPlano, pacienteNome)
				);
	}	
	
	@Override
	public int buscarTotalPorPlanoEPacienteNome(Long idPlano, String pacienteNome) {
		return this.planoPacienteDAO.buscarTotalPorPlanoEPacienteNome(idPlano, pacienteNome);
	}	
	//
	@Override
	public List<PlanoPacienteDTO> buscarPorPlano(Long idPlano, Filtro filtro) {
		return this.planoPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.planoPacienteDAO.buscarPorPlanoComFiltro(idPlano, filtro)
				);
	}
	
	@Override
	public int buscarTotalPorPlano(Long idPlano) {
		return this.planoPacienteDAO.buscarTotalPorPlano(idPlano);
	}
	
	@Override
	public List<PlanoPacienteDTO> buscarPorPlano(Long idPlano) {
		return this.planoPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.planoPacienteDAO.buscarPorPlano(idPlano)
				);
	}
	
	@Override
	public List<PlanoPacienteDTO> buscarPorPlanoEPaciente(Long idPlano, Long idPaciente) {
		return this.planoPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.planoPacienteDAO.buscarPorPlanoEPaciente(idPlano, idPaciente)
				);
	}

	@Override
	public List<PlanoPacienteDTO> buscarAtivos() {
		return this.planoPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.planoPacienteDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<PlanoPacienteDTO> buscarInativos() {
		return this.planoPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.planoPacienteDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public PlanoPacienteDTO getEntidadeFromList(List<PlanoPacienteDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
