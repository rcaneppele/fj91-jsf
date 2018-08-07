package br.com.caelum.fj91.rh.repositories;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.caelum.fj91.rh.domain.Funcionario;
import br.com.caelum.fj91.rh.domain.Reajuste;

@Named
@RequestScoped
public class ReajusteRepository {
	
	private EntityManager em;

	@Inject
	public ReajusteRepository(EntityManager em) {
		this.em = em;
	}
	
	//CDI
	protected ReajusteRepository() {
	}

	public List<Reajuste> findAllByFuncionario(Funcionario funcionario) {
		return em.createQuery("SELECT r FROM Reajuste r WHERE r.funcionario = :funcionario", Reajuste.class)
				.setParameter("funcionario", funcionario)
				.getResultList();
	}

	public Reajuste findTopByFuncionarioOrderByIdDesc(Funcionario funcionario) {
		try {
			return em.createQuery("SELECT r FROM Reajuste r WHERE r.funcionario = :funcionario ORDER BY r.data DESC", Reajuste.class)
				.setParameter("funcionario", funcionario)
				.setMaxResults(1)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void save(Reajuste reajuste) {
		em.joinTransaction();
		em.persist(reajuste);
	}

}
