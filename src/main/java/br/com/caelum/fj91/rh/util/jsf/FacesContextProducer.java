package br.com.caelum.fj91.rh.util.jsf;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class FacesContextProducer {

	@Produces @RequestScoped
	public FacesContext create() {
		return FacesContext.getCurrentInstance();
	}
	
}
