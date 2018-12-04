package dto.factory;

import dto.AgendamentoDTO;
import dto.AtendimentoDTO;
import dto.AtributoOperacionalDTO;
import dto.CaixaDTO;
import dto.CargoDTO;
import dto.DentistaAgendaDTO;
import dto.DentistaDTO;
import dto.EnderecoDTO;
import dto.EspecialidadeDTO;
import dto.EspecialidadeDentistaDTO;
import dto.EstabelecimentoDTO;
import dto.FuncionarioDTO;
import dto.LogOperacionalDTO;
import dto.MensalidadePacienteDTO;
import dto.MovimentacaoDTO;
import dto.MsgPreEmailDTO;
import dto.MsgPreTorpedoDTO;
import dto.ObsPacienteDTO;
import dto.OperadoraDTO;
import dto.PacienteDTO;
import dto.PlanoDTO;
import dto.PlanoMensalidadeDTO;
import dto.PlanoPacienteDTO;
import dto.UsuarioDTO;

public abstract class DTOFactory {
	
	public static AgendamentoDTO getAgendamentoDTO()
	{
		AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
		agendamentoDTO.setDentistaAgenda(getDentistaAgendaDTO());
		agendamentoDTO.setPaciente(getPacienteDTO());
		agendamentoDTO.setUsuario(getUsuarioDTO());
		agendamentoDTO.setUsuarioCriacaoDTO(getUsuarioDTO());
		agendamentoDTO.setEstabelecimentoDTO(getEstabelecimentoDTO());
		
		return agendamentoDTO;
	}
	
	public static AtendimentoDTO getAtendimentoDTO()
	{
		AtendimentoDTO atendimentoDTO = new AtendimentoDTO();
		atendimentoDTO.setAgendamento(getAgendamentoDTO());
		atendimentoDTO.setUsuario(getUsuarioDTO());
		
		return atendimentoDTO;
	}
	
	public static AtributoOperacionalDTO getAtributoOperacionalDTO()
	{
		AtributoOperacionalDTO atributoOperacionalDTO = new AtributoOperacionalDTO();
		atributoOperacionalDTO.setUsuarioDTO(getUsuarioDTO());
		
		return atributoOperacionalDTO;
	}
	
	public static CaixaDTO getCaixaDTO()
	{
		CaixaDTO caixaDTO = new CaixaDTO();
		caixaDTO.setUsuarioAbertura(getUsuarioDTO());
		caixaDTO.setUsuarioFechamento(getUsuarioDTO());
		caixaDTO.setEstabelecimentoDTO(getEstabelecimentoDTO());
		
		return caixaDTO;
	}
	
	public static CargoDTO getCargoDTO()
	{
		CargoDTO cargoDTO = new CargoDTO();
		cargoDTO.setUsuario(getUsuarioDTO());
		
		return cargoDTO;
	}
	
	public static DentistaAgendaDTO getDentistaAgendaDTO()
	{
		DentistaAgendaDTO dentistaAgendaDTO = new DentistaAgendaDTO();
		dentistaAgendaDTO.setDentista(getDentistaDTO());
		dentistaAgendaDTO.setUsuario(getUsuarioDTO());
		dentistaAgendaDTO.setEstabelecimentoDTO(getEstabelecimentoDTO());
		
		return dentistaAgendaDTO;
	}
	
	public static DentistaDTO getDentistaDTO()
	{
		DentistaDTO dentistaDTO = new DentistaDTO();
		dentistaDTO.setUsuario(getUsuarioDTO());
		dentistaDTO.setEstabelecimentoDTO(getEstabelecimentoDTO());
		
		return dentistaDTO;
	}
	
	public static EnderecoDTO getEnderecoDTO()
	{
		EnderecoDTO enderecoDTO = new EnderecoDTO();
		
		return enderecoDTO;
	}
	
	public static EspecialidadeDTO getEspecialidadeDTO()
	{
		EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();
		especialidadeDTO.setUsuario(getUsuarioDTO());
		
		return especialidadeDTO;
	}
	
	public static EspecialidadeDentistaDTO getEspecialidadeDentistaDTO()
	{
		EspecialidadeDentistaDTO especialidadeDentistaDTO = new EspecialidadeDentistaDTO();
		especialidadeDentistaDTO.setDentista(getDentistaDTO());
		especialidadeDentistaDTO.setEspecialidade(getEspecialidadeDTO());
		
		return especialidadeDentistaDTO;
	}
	
	public static EstabelecimentoDTO getEstabelecimentoDTO()
	{
		EstabelecimentoDTO estabelecimentoDTO = new EstabelecimentoDTO();
		estabelecimentoDTO.setEnderecoDTO(getEnderecoDTO());
		estabelecimentoDTO.setUsuarioDTO(getUsuarioDTO());
		
		return estabelecimentoDTO;
	}
	
	public static FuncionarioDTO getFuncionarioDTO()
	{
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setUsuarioPerfil(getUsuarioDTO());
		funcionarioDTO.setCargo(getCargoDTO());
		funcionarioDTO.setEndereco(getEnderecoDTO());
		funcionarioDTO.setOperadoraCel01(getOperadoraDTO());
		funcionarioDTO.setOperadoraCel02(getOperadoraDTO());
		funcionarioDTO.setOperadoraCel03(getOperadoraDTO());
		funcionarioDTO.setUsuario(getUsuarioDTO());
		funcionarioDTO.setEstabelecimentoDTO(getEstabelecimentoDTO());
		
		return funcionarioDTO;
	}
	
	public static LogOperacionalDTO getLogOperacionalDTO()
	{
		LogOperacionalDTO logOperacionalDTO = new LogOperacionalDTO();
		logOperacionalDTO.setUsuarioLogDTO(getUsuarioDTO());
		
		return logOperacionalDTO;
	}
	
	public static MensalidadePacienteDTO getMensalidadePacienteDTO()
	{
		MensalidadePacienteDTO mensalidadePacienteDTO = new MensalidadePacienteDTO();
		mensalidadePacienteDTO.setPlanoMensalidade(getPlanoMensalidadeDTO());
		mensalidadePacienteDTO.setPlanoPaciente(getPlanoPacienteDTO());
		mensalidadePacienteDTO.setUsuario(getUsuarioDTO());
		
		return mensalidadePacienteDTO;
	}
	
	public static MovimentacaoDTO getMovimentacaoDTO()
	{
		MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO();
		movimentacaoDTO.setCaixa(getCaixaDTO());
		movimentacaoDTO.setUsuario(getUsuarioDTO());
		
		return movimentacaoDTO;
	}
	
	public static MsgPreEmailDTO getMsgPreEmailDTO()
	{
		MsgPreEmailDTO msgPreEmailDTO = new MsgPreEmailDTO();
		msgPreEmailDTO.setUsuario(getUsuarioDTO());
		
		return msgPreEmailDTO;
	}
	
	public static MsgPreTorpedoDTO getMsgPreTorpedoDTO()
	{
		MsgPreTorpedoDTO msgPreTorpedoDTO = new MsgPreTorpedoDTO();
		msgPreTorpedoDTO.setUsuario(getUsuarioDTO());
		
		return msgPreTorpedoDTO;
	}
	
	public static MsgPreTorpedoDTO getMsgPreTorpedoDTO(String header)
	{
		MsgPreTorpedoDTO msgPreTorpedoDTO = new MsgPreTorpedoDTO();
		msgPreTorpedoDTO.setUsuario(getUsuarioDTO());
		
		msgPreTorpedoDTO.setMsg(header);
		
		return msgPreTorpedoDTO;
	}
	
	public static ObsPacienteDTO getObsPacienteDTO()
	{
		ObsPacienteDTO obsPacienteDTO = new ObsPacienteDTO();
		obsPacienteDTO.setUsuarioDTO(getUsuarioDTO());
		obsPacienteDTO.setPacienteDTO(getPacienteDTO());
		
		return obsPacienteDTO;
	}
	
	public static OperadoraDTO getOperadoraDTO()
	{
		OperadoraDTO operadoraDTO = new OperadoraDTO();
		operadoraDTO.setUsuario(getUsuarioDTO());
		
		return operadoraDTO;
	}
	
	public static PacienteDTO getPacienteDTO()
	{
		PacienteDTO pacienteDTO =  new PacienteDTO();
		pacienteDTO.setPaciente(new PacienteDTO());
		pacienteDTO.setEndereco(getEnderecoDTO());
		pacienteDTO.setOperadoraCel01(getOperadoraDTO());
		pacienteDTO.setOperadoraCel02(getOperadoraDTO());
		pacienteDTO.setOperadoraCel03(getOperadoraDTO());
		pacienteDTO.setUsuario(getUsuarioDTO());
		pacienteDTO.setEstabelecimentoDTO(getEstabelecimentoDTO());
		
		return pacienteDTO;
	}
	
	public static PlanoDTO getPlanoDTO()
	{
		PlanoDTO planoDTO = new PlanoDTO();
		planoDTO.setUsuario(getUsuarioDTO());
		planoDTO.setEstabelecimentoDTO(getEstabelecimentoDTO());
		
		return planoDTO;
	}
	
	public static PlanoMensalidadeDTO getPlanoMensalidadeDTO()
	{
		PlanoMensalidadeDTO planoMensalidadeDTO = new PlanoMensalidadeDTO();
		planoMensalidadeDTO.setPlano(getPlanoDTO());
		
		return planoMensalidadeDTO;
	}
	
	public static PlanoPacienteDTO getPlanoPacienteDTO()
	{
		PlanoPacienteDTO planoPacienteDTO = new PlanoPacienteDTO();
		planoPacienteDTO.setPaciente(getPacienteDTO());
		planoPacienteDTO.setPlano(getPlanoDTO());
		planoPacienteDTO.setUsuario(getUsuarioDTO());
		
		return planoPacienteDTO;
	}
	
	public static UsuarioDTO getUsuarioDTO()
	{
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		
		return usuarioDTO;
	}
}
