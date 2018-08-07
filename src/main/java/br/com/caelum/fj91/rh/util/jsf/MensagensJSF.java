package br.com.caelum.fj91.rh.util.jsf;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class MensagensJSF {
	
	private FacesContext context;

	@Inject
	public MensagensJSF(FacesContext context) {
		this.context = context;
	}

	//CDI
	protected MensagensJSF() {
	}
	
	public void adicionaSucesso(String mensagem) {
		adicionaMensagem(mensagem, FacesMessage.SEVERITY_INFO);
	}
	
	public void adicionaErro(String mensagem) {
		adicionaMensagem(mensagem, FacesMessage.SEVERITY_ERROR);
	}
	
	private void adicionaMensagem(String mensagem, Severity tipo) {
		FacesMessage jsfMessage = new FacesMessage(tipo, mensagem, null);
		context.addMessage(null, jsfMessage);
		
		//Para nao perder as mensagens em caso de REDIRECT
		context.getExternalContext().getFlash().setKeepMessages(true);
	}

}
