package escola.musica.dao;

import java.util.List;

import javax.persistence.EntityManager;

import escola.musica.modelo.Matricula;

public class MatriculaDAO {

	public void salvar(Matricula matricula) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();

		entityManager.merge(matricula);

		entityManager.getTransaction().commit();

		entityManager.close();

	}

	@SuppressWarnings("unchecked")
	public List<Matricula> listarTodos() {

		EntityManager entityManager = JPAUtil.getEntityManager();

		return entityManager.createQuery("from Matricula").getResultList();

	}

	public void excluir(Matricula matricula) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();

		matricula = entityManager.merge(matricula);

		entityManager.remove(matricula);

		entityManager.getTransaction().commit();

		entityManager.close();

	}

}
