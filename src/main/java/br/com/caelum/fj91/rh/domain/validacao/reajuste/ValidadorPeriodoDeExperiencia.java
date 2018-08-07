package br.com.caelum.fj91.rh.domain.validacao.reajuste;

import java.time.LocalDate;
import java.time.Period;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.caelum.fj91.rh.domain.Reajuste;
import br.com.caelum.fj91.rh.exceptions.BusinessException;

@Named
@RequestScoped
public class ValidadorPeriodoDeExperiencia implements ValidadorReajuste {
	
	@Override
	public void valida(Reajuste reajuste) {
		LocalDate dataAdmissao = reajuste.getFuncionario().getDataDeAdmissao();
		LocalDate dataReajuste = reajuste.getData();
		
		Period intervaloEntreAdmissaoEReajuste = dataAdmissao.until(dataReajuste);
		if (intervaloEntreAdmissaoEReajuste.getYears() < 1 && intervaloEntreAdmissaoEReajuste.getMonths() < 3) {
			throw new BusinessException("Funcionário em período de experiência não pode receber reajuste");
		}
	}

}
