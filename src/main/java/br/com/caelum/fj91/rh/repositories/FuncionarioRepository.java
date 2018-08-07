package br.com.caelum.fj91.rh.repositories;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.domain.Funcionario;

@Named
@RequestScoped
public class FuncionarioRepository {
	
	private EntityManager em;

	@Inject
	public FuncionarioRepository(EntityManager em) {
		this.em = em;
	}
	
	//CDI
	protected FuncionarioRepository() {
	}

	public boolean existsByCargo(Cargo cargo) {
		try {
			String jpql = "SELECT f FROM Funcionario f WHERE f.cargo = :cargo";
			em.createQuery(jpql, Funcionario.class).setParameter("cargo", cargo).getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	public void save(Funcionario novo) {
		em.joinTransaction();
		em.persist(novo);
	}

	public Funcionario getOne(Long id) {
		return em.find(Funcionario.class, id);
	}

	public List<Funcionario> findAll() {
		return em.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();
	}

	public void deleteById(Long id) {
		em.joinTransaction();
		em.remove(this.getOne(id));
	}

}
