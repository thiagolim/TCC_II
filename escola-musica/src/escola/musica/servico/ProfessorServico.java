package escola.musica.servico;

import java.util.List;

import escola.musica.modelo.Professor;

public interface ProfessorServico {

	public void salvar(Professor professor);

	public List<Professor> listarTodos();
	public void excluir(Professor professor);

}