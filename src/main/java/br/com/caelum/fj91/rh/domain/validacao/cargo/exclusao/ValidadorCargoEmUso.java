package br.com.caelum.fj91.rh.domain.validacao.cargo.exclusao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.FuncionarioRepository;

@Named
@RequestScoped
public class ValidadorCargoEmUso implements ValidadorExclusaoCargo {
	
	private FuncionarioRepository repository;

	@Inject
	public ValidadorCargoEmUso(FuncionarioRepository repository) {
		this.repository = repository;
	}
	
	//CDI
	protected ValidadorCargoEmUso() {
	}

	@Override
	public void valida(Cargo cargo) {
		boolean cargoEmUso = repository.existsByCargo(cargo);
		if (cargoEmUso) {
			throw new BusinessException("Cargo não pode ser excluído pois está atributido a algum funcionário");
		}
	}
	
}
