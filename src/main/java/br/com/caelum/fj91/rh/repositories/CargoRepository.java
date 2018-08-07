package br.com.caelum.fj91.rh.repositories;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.caelum.fj91.rh.domain.Cargo;

@Named
@RequestScoped
public class CargoRepository {

	private EntityManager em;
	
	//CDI
	protected CargoRepository() {
	}

	@Inject
	public CargoRepository(EntityManager em) {
		this.em = em;
	}

	public boolean existsByNome(String nome) {
		try {
			String jpql = "SELECT c FROM Cargo c WHERE c.nome = :nome";
			em.createQuery(jpql, Cargo.class).setParameter("nome", nome).getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	public Cargo getOne(Long id) {
		return em.find(Cargo.class, id);
	}

	public List<Cargo> findAll() {
		return em.createQuery("SELECT c FROM Cargo c", Cargo.class).getResultList();
	}

	public void save(Cargo cargo) {
		em.joinTransaction();
		em.persist(cargo);
	}

	public void delete(Cargo cargo) {
		em.joinTransaction();
		em.remove(cargo);
	}

}
