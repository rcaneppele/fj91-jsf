package br.com.caelum.fj91.rh.domain.validacao.cargo.cadastro;

import java.math.BigDecimal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.exceptions.BusinessException;

@Named
@RequestScoped
public class ValidadorSalarioMaximoEmpresa implements ValidadorCadastroCargo {
	
	private static final BigDecimal SALARIO_MAXIMO_EMPRESA = new BigDecimal("100000.00");

	@Override
	public void valida(Cargo cargo) {
		if (cargo.getSalarioMaximo().compareTo(SALARIO_MAXIMO_EMPRESA) > 0) {
			throw new BusinessException("Salário máximo do cargo não pode ser maior do que R$100.000,00");
		}
	}

}
