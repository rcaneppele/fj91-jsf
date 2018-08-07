package br.com.caelum.fj91.rh.domain.validacao.cargo.cadastro;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.CargoRepository;

@Named
@RequestScoped
public class ValidadorNomeUnico implements ValidadorCadastroCargo {
	
	private CargoRepository repository;
	
	@Inject
	public ValidadorNomeUnico(CargoRepository repository) {
		this.repository = repository;
	}
	
	//CDI
	protected ValidadorNomeUnico() {
	}

	@Override
	public void valida(Cargo cargo) {
		boolean existeOutroComMesmoNome = repository.existsByNome(cargo.getNome());
		if (existeOutroComMesmoNome) {
			throw new BusinessException("Já existe outro cargo cadastrado com esse nome");
		}
	}

}
