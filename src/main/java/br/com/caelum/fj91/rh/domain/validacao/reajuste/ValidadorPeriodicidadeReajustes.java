package br.com.caelum.fj91.rh.domain.validacao.reajuste;

import java.time.Period;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.fj91.rh.domain.Reajuste;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.ReajusteRepository;

@Named
@RequestScoped
public class ValidadorPeriodicidadeReajustes implements ValidadorReajuste {
	
	private ReajusteRepository repository;
	
	@Inject
	public ValidadorPeriodicidadeReajustes(ReajusteRepository repository) {
		this.repository = repository;
	}

	//CDI
	protected ValidadorPeriodicidadeReajustes() {
	}

	@Override
	public void valida(Reajuste reajuste) {
		Reajuste ultimoRecebido = repository.findTopByFuncionarioOrderByIdDesc(reajuste.getFuncionario());
		
		//se o funcionario nunca teve reajustes 
		if (ultimoRecebido == null) {
			return;
		}
		
		Period intervaloEntreReajustes = ultimoRecebido.getData().until(reajuste.getData());
		if (intervaloEntreReajustes.getYears() < 1 && intervaloEntreReajustes.getMonths() < 6) {
			throw new BusinessException("Funcionário não pode receber reajuste pois recebeu outro há menos de 6 meses");
		}
	}
	
}
