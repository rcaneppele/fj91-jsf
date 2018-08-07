package br.com.caelum.fj91.rh.managedbeans;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.domain.validacao.cargo.cadastro.ValidadorCadastroCargo;
import br.com.caelum.fj91.rh.domain.validacao.cargo.exclusao.ValidadorExclusaoCargo;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.CargoRepository;
import br.com.caelum.fj91.rh.util.jsf.MensagensJSF;

@Named
@RequestScoped
public class CargoBean {
	
	private static final String REDIRECT_PAGINA_CARGOS = "cargos?faces-redirect=true";
	
	private CargoRepository repository;
	private Instance<ValidadorCadastroCargo> validadoresCadastro;
	private Instance<ValidadorExclusaoCargo> validadoresExclusao;
	private MensagensJSF msg;
	
	private List<Cargo> cargos;
	private Cargo cargo = new Cargo();

	//CDI
	protected CargoBean() {
	}
	
	@Inject
	public CargoBean(CargoRepository repository, Instance<ValidadorCadastroCargo> validadoresCadastro,
			Instance<ValidadorExclusaoCargo> validadoresExclusao, MensagensJSF msg) {
		this.repository = repository;
		this.validadoresCadastro = validadoresCadastro;
		this.validadoresExclusao = validadoresExclusao;
		this.msg = msg;
	}

	public List<Cargo> getCargos() {
		if (this.cargos == null) {
			this.cargos = repository.findAll();
		}
		return this.cargos;
	}
	
	@Transactional
	public String salvar() {
		try {
			validadoresCadastro.forEach(v -> v.valida(cargo));
			repository.save(cargo);
			msg.adicionaSucesso("Cargo cadastrado!");
			return REDIRECT_PAGINA_CARGOS;
		} catch (BusinessException e) {
			msg.adicionaErro(e.getMessage());
			return "";
		}
	}
	
	@Transactional
	public String remover(Cargo cargo) {
		try {
			validadoresExclusao.forEach(v -> v.valida(cargo));
			repository.delete(cargo);
			msg.adicionaSucesso("Cargo excluido!");
		} catch (BusinessException e) {
			msg.adicionaErro(e.getMessage());
		}
		
		return REDIRECT_PAGINA_CARGOS;
	}
	
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

}
