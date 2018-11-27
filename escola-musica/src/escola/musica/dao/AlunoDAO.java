package escola.musica.dao;

import java.util.List;

import javax.persistence.EntityManager;

import escola.musica.modelo.Aluno;

public class AlunoDAO {

	public void salvar(Aluno aluno) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();

		entityManager.merge(aluno);

		entityManager.getTransaction().commit();

		entityManager.close();

	}

	@SuppressWarnings("unchecked")
	public List<Aluno> listarTodos() {

		EntityManager entityManager = JPAUtil.getEntityManager();

		return entityManager.createQuery("from Aluno").getResultList();

	}

	public void excluir(Aluno aluno) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();

		aluno = entityManager.merge(aluno);

		entityManager.remove(aluno);

		entityManager.getTransaction().commit();

		entityManager.close();

	}

}
