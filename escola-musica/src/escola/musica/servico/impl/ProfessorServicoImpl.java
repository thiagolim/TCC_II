package escola.musica.servico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escola.musica.modelo.Professor;
import escola.musica.servico.ProfessorServico;

@Service("professorServico")

@Transactional

public class ProfessorServicoImpl implements ProfessorServico {

	@PersistenceContext

	private EntityManager entityManager;

	@Override

	public void salvar(Professor professor) {

		entityManager.merge(professor);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Professor> listarTodos() {

		return entityManager.createQuery("from Professor").getResultList();

	}

	@Override
	public void excluir(Professor professor) {
		professor = entityManager.merge(professor);
		entityManager.remove(professor);
		
	}

}