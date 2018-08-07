package br.com.caelum.fj91.rh.managedbeans;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.caelum.fj91.rh.domain.Funcionario;
import br.com.caelum.fj91.rh.domain.Reajuste;
import br.com.caelum.fj91.rh.domain.validacao.reajuste.ValidadorReajuste;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.ReajusteRepository;
import br.com.caelum.fj91.rh.services.FuncionarioService;
import br.com.caelum.fj91.rh.util.jsf.MensagensJSF;

@Named
@RequestScoped
public class ReajusteBean {
	
	private static final String PAGINA_REAJUSTE = "reajustes";
	private static final String REDIRECT_PAGINA_FUNCIONARIOS = "funcionarios?faces-redirect=true";
	
	private FuncionarioService funcionarioService;
	private ReajusteRepository reajusteRepository;
	private Instance<ValidadorReajuste> validadoresReajuste;
	private MensagensJSF msg;
	
	private Funcionario funcionario;
	private Long idFuncionario;
	private List<Reajuste> reajustes;
	private Reajuste reajuste = new Reajuste();

	@Inject
	public ReajusteBean(FuncionarioService funcionarioService, ReajusteRepository reajusteRepository, Instance<ValidadorReajuste> validadoresReajuste, MensagensJSF msg) {
		this.funcionarioService = funcionarioService;
		this.reajusteRepository = reajusteRepository;
		this.validadoresReajuste = validadoresReajuste;
		this.msg = msg;
	}
	
	//CDI
	protected ReajusteBean() {
	}

	public String listar(Funcionario funcionario) {
		this.funcionario = funcionario;
		this.idFuncionario = funcionario.getId();
		this.reajustes = reajusteRepository.findAllByFuncionario(funcionario);
		return PAGINA_REAJUSTE;
	}

	@Transactional
	public String reajustar() {
		Funcionario selecionado = funcionarioService.buscarPorId(idFuncionario);
		reajuste.setFuncionario(selecionado);

		try {
			validadoresReajuste.forEach(v -> v.valida(reajuste));
			reajusteRepository.save(reajuste);
			msg.adicionaSucesso("Reajuste cadastrado!");
			return REDIRECT_PAGINA_FUNCIONARIOS;
		} catch (BusinessException e) {
			msg.adicionaErro(e.getMessage());
			return listar(selecionado);
		}
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public List<Reajuste> getReajustes() {
		return reajustes;
	}
	public Reajuste getReajuste() {
		return reajuste;
	}
	public Long getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

}
