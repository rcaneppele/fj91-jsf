package br.com.caelum.fj91.rh.domain.validacao.cargo.cadastro;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.exceptions.BusinessException;

@Named
@RequestScoped
public class ValidadorSalarioMinimoMenorQueMaximo implements ValidadorCadastroCargo {
	
	@Override
	public void valida(Cargo cargo) {
		if (cargo.getSalarioMinimo().compareTo(cargo.getSalarioMaximo()) > 0) {
			throw new BusinessException("Salário mínimo deve ser menor do que o salário máximo");
		}
	}

}
