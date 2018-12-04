package dto.conversor.factory;

import dto.conversor.conversores.AgendamentoConversorDTO;
import dto.conversor.conversores.AtendimentoConversorDTO;
import dto.conversor.conversores.AtributoOperacionalConversorDTO;
import dto.conversor.conversores.CaixaConversorDTO;
import dto.conversor.conversores.CargoConversorDTO;
import dto.conversor.conversores.DentistaAgendaConversorDTO;
import dto.conversor.conversores.DentistaConversorDTO;
import dto.conversor.conversores.EnderecoConversorDTO;
import dto.conversor.conversores.EspecialidadeConversorDTO;
import dto.conversor.conversores.EspecialidadeDentistaConversorDTO;
import dto.conversor.conversores.EstabelecimentoConversorDTO;
import dto.conversor.conversores.FuncionarioConversorDTO;
import dto.conversor.conversores.LogOperacionalConversorDTO;
import dto.conversor.conversores.MensalidadePacienteConversorDTO;
import dto.conversor.conversores.MovimentacaoConversorDTO;
import dto.conversor.conversores.MsgPreEmailConversorDTO;
import dto.conversor.conversores.MsgPreTorpedoConversorDTO;
import dto.conversor.conversores.ObsPacienteConversorDTO;
import dto.conversor.conversores.OperadoraConversorDTO;
import dto.conversor.conversores.PacienteConversorDTO;
import dto.conversor.conversores.PlanoConversorDTO;
import dto.conversor.conversores.PlanoMensalidadeConversorDTO;
import dto.conversor.conversores.PlanoPacienteConversorDTO;
import dto.conversor.conversores.UsuarioConversorDTO;

public abstract class ConversorDTOFactory {
	
	public static AgendamentoConversorDTO getAgendamentoConversorDTO()
	{
		return new AgendamentoConversorDTO();
	}
	
	public static AtendimentoConversorDTO getAtendimentoConversorDTO()
	{
		return new AtendimentoConversorDTO();
	}
	
	public static AtributoOperacionalConversorDTO getAtributoOperacionalConversorDTO()
	{
		return new AtributoOperacionalConversorDTO();
	}
	
	public static CaixaConversorDTO getCaixaConversorDTO()
	{
		return new CaixaConversorDTO();
	}
	
	public static CargoConversorDTO getCargoConversorDTO()
	{
		return new CargoConversorDTO();
	}
	
	public static DentistaAgendaConversorDTO getDentistaAgendaConversorDTO()
	{
		return new DentistaAgendaConversorDTO();
	}
	
	public static DentistaConversorDTO getDentistaConversorDTO()
	{
		return new DentistaConversorDTO();
	}
	
	public static EnderecoConversorDTO getEnderecoConversorDTO()
	{
		return new EnderecoConversorDTO();
	}
	
	public static EspecialidadeConversorDTO getEspecialidadeConversorDTO()
	{
		return new EspecialidadeConversorDTO();
	}
	
	public static EspecialidadeDentistaConversorDTO getEspecialidadeDentistaConversorDTO()
	{
		return new EspecialidadeDentistaConversorDTO();
	}
	
	public static EstabelecimentoConversorDTO getEstabelecimentoConversorDTO()
	{
		return new EstabelecimentoConversorDTO();
	}
	
	public static FuncionarioConversorDTO getFuncionarioConversorDTO()
	{
		return new FuncionarioConversorDTO();
	}
	
	public static LogOperacionalConversorDTO getLogOperacionalConversorDTO()
	{
		return new LogOperacionalConversorDTO();
	}
	
	public static MensalidadePacienteConversorDTO getMensalidadePacienteConversorDTO()
	{
		return new MensalidadePacienteConversorDTO();
	}
	
	public static MovimentacaoConversorDTO getMovimentacaoConversorDTO()
	{
		return new MovimentacaoConversorDTO();
	}
	
	public static MsgPreEmailConversorDTO getMsgPreEmailConversorDTO()
	{
		return new MsgPreEmailConversorDTO();
	}
	
	public static MsgPreTorpedoConversorDTO getMsgPreTorpedoConversorDTO()
	{
		return new MsgPreTorpedoConversorDTO();
	}
	
	public static ObsPacienteConversorDTO getObsPacienteConversorDTO()
	{
		return new ObsPacienteConversorDTO();
	}
	
	public static OperadoraConversorDTO getOperadoraConversorDTO()
	{
		return new OperadoraConversorDTO();
	}
	
	public static PacienteConversorDTO getPacienteConversorDTO()
	{
		return new PacienteConversorDTO();
	}
	
	public static PlanoConversorDTO getPlanoConversorDTO()
	{
		return new PlanoConversorDTO();
	}
	
	public static PlanoMensalidadeConversorDTO getPlanoMensalidadeConversorDTO()
	{
		return new PlanoMensalidadeConversorDTO();
	}
	
	public static PlanoPacienteConversorDTO getPlanoPacienteConversorDTO()
	{
		return new PlanoPacienteConversorDTO();
	}
	
	public static UsuarioConversorDTO getUsuarioConversorDTO()
	{
		return new UsuarioConversorDTO();
	}
}
