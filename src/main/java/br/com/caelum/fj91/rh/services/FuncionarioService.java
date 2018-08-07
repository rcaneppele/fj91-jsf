package br.com.caelum.fj91.rh.services;

import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.domain.Funcionario;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.CargoRepository;
import br.com.caelum.fj91.rh.repositories.FuncionarioRepository;

@Named
@RequestScoped
public class FuncionarioService {
	
	private FuncionarioRepository funcionarioRepository;
	private CargoRepository cargoRepository;

	@Inject
	public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
	}
	
	//CDI
	protected FuncionarioService() {
	}
	
	public void salvar(Funcionario novo) {
		BigDecimal salario = novo.getSalario();
		Cargo cargo = cargoRepository.getOne(novo.getCargo().getId());
		
		if (salario.compareTo(cargo.getSalarioMinimo()) < 0) {
			throw new BusinessException("Salário do funcionário não pode ser menor do que o salário mínimo de seu cargo");
		}
		
		if (salario.compareTo(cargo.getSalarioMaximo()) > 0) {
			throw new BusinessException("Salário do funcionário não pode ser maior do que salário máximo de seu cargo");
		}
		
		funcionarioRepository.save(novo);
	}
	
	public Funcionario buscarPorId(Long id) {
		return funcionarioRepository.getOne(id);
	}
	
	public List<Funcionario> listarTodos() {
		return funcionarioRepository.findAll();
	}

	public void excluir(Long id) {
		funcionarioRepository.deleteById(id);
	}

}
