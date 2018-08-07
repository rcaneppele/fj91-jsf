package br.com.caelum.fj91.rh.domain.validacao.cargo.cadastro;

import java.math.BigDecimal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.exceptions.BusinessException;

@Named
@RequestScoped
public class ValidadorSalarioMinimoNacional implements ValidadorCadastroCargo {
	
	private static final BigDecimal SALARIO_MINIMO_NACIONAL = new BigDecimal("954.00");

	@Override
	public void valida(Cargo cargo) {
		if (cargo.getSalarioMinimo().compareTo(SALARIO_MINIMO_NACIONAL) < 0) {
			throw new BusinessException("Salário mínimo do cargo nao pode ser menor do que o salário mínimo nacional(R$954,00)");
		}
	}

}
