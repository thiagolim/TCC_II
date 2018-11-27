package escola.musica.dao;

import java.util.List;

import javax.persistence.EntityManager;

import escola.musica.modelo.Professor;

public class ProfessorDAO {

	public void salvar(Professor professor) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();

		entityManager.merge(professor);

		entityManager.getTransaction().commit();

		entityManager.close();

	}

	@SuppressWarnings("unchecked")
	public List<Professor> listarTodos() {

		EntityManager entityManager = JPAUtil.getEntityManager();

		return entityManager.createQuery("from Professor").getResultList();

	}

	public void excluir(Professor professor) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();

		professor = entityManager.merge(professor);

		entityManager.remove(professor);

		entityManager.getTransaction().commit();

		entityManager.close();

	}

}
