package gui.managedbeans;

import gui.managedbeans.atributo.ConstantesSodontoSystem;
import gui.managedbeans.generics.ManagedBeanGenericBasic;
import gui.util.security.KeyManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.util.DataUtil;

@ManagedBean
@ViewScoped
public class SecurityMB extends ManagedBeanGenericBasic {
	
	private int mesesGeracao;
	private String passKey;
	private boolean generateKey;

	public void ativarGeracaoKey()
	{
		this.generateKey = true;
	}
	
	public void gerarChaves()
	{
		if(mesesGeracao > 0)
		{
			if(this.passKey != null)
			{
				KeyManager
				.getInstance()
				.insertKeysInFile(KeyManager.getInstance().loadKeysToMonths(this.mesesGeracao + 1));
				System.out.println(ConstantesSodontoSystem.SISTEMA_LOG_HEADER + "(SECURITY) - Chave(s) gerada(s) com sucesso!");
				enviarMenssagemInformativa((this.mesesGeracao > 1 ? "Keys geradas para " : "Key gerada para ") + this.mesesGeracao + (this.mesesGeracao > 1 ? " meses" : " mês") + " com sucesso!");
			}		
		}
		else
		{
			System.out.println(ConstantesSodontoSystem.SISTEMA_LOG_HEADER + "(SECURITY)(ERROR) - Meses inferior a 01!");
		}
		
		this.mesesGeracao = 0;
		this.passKey = null;
		this.generateKey = false;
	}

	public int getMesesGeracao() {
		return mesesGeracao;
	}

	public void setMesesGeracao(int mesesGeracao) {
		this.mesesGeracao = mesesGeracao;
	}

	public String getPassKey() {
		return passKey;
	}

	public void setPassKey(String passKey) {
		this.passKey = passKey;
	}
	
	public boolean validarPasskey()
	{
		boolean comparacao = false;
		
		
		if(this.passKey != null)
		{
			if(passKey.equals(ConstantesSodontoSystem.SISTEMA_ANALISTA + "-" + 
					ConstantesSodontoSystem.SISTEMA_ANALISTA_CPF + "-" +
					ConstantesSodontoSystem.SISTEMA_NOME + "-" + 
					ConstantesSodontoSystem.SISTEMA_VERSAO + "-" +
					DataUtil.getAnoDaData(DataUtil.getDataAtual()) +
					DataUtil.getMesDaData(DataUtil.getDataAtual()) +
					DataUtil.getDiaDaData(DataUtil.getDataAtual())))
			{
				comparacao = true;
			}
		}
		
		return comparacao;
	}

	public boolean isGenerateKey() {
		return generateKey;
	}

	public void setGenerateKey(boolean generateKey) {
		this.generateKey = generateKey;
	}

}
