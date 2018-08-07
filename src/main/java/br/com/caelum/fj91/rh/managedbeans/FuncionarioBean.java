package br.com.caelum.fj91.rh.managedbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.domain.Funcionario;
import br.com.caelum.fj91.rh.domain.validacao.reajuste.ValidadorReajuste;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.CargoRepository;
import br.com.caelum.fj91.rh.repositories.ReajusteRepository;
import br.com.caelum.fj91.rh.services.FuncionarioService;
import br.com.caelum.fj91.rh.util.jsf.MensagensJSF;

@Named
@RequestScoped
public class FuncionarioBean {

	private static final String REDIRECT_PAGINA_FUNCIONARIOS = "funcionarios?faces-redirect=true";

	private FuncionarioService funcionarioService;
	private MensagensJSF msg;
	
	private List<Funcionario> funcionarios;
	private Funcionario funcionario;
	
	@Inject
	public FuncionarioBean(CargoRepository cargoRepository, ReajusteRepository reajusteRepository, FuncionarioService funcionarioService, Instance<ValidadorReajuste> validadoresReajuste, MensagensJSF msg) {
		this.funcionarioService = funcionarioService;
		this.msg = msg;
	}
	
	//CDI
	protected FuncionarioBean() {
	}
	
	@PostConstruct
	public void init() {
		this.funcionario = new Funcionario();
		this.funcionario.setCargo(new Cargo());
	}
	
	public List<Funcionario> getFuncionarios() {
		if (this.funcionarios == null) {
			this.funcionarios = funcionarioService.listarTodos();
		}
		return this.funcionarios;
	}

	@Transactional
	public String salvar() {
		try {
			funcionarioService.salvar(funcionario);
			msg.adicionaSucesso("Funcionario cadastrado!");
			return REDIRECT_PAGINA_FUNCIONARIOS;
		} catch (BusinessException e) {
			msg.adicionaErro(e.getMessage());
			return "";
		}
	}
	
	@Transactional
	public String remover(Funcionario funcionario) {
		funcionarioService.excluir(funcionario.getId());
		msg.adicionaSucesso("Funcionario excluido!");
		return REDIRECT_PAGINA_FUNCIONARIOS;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}
