package escola.musica.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import escola.musica.dao.ProfessorDAO;
import escola.musica.modelo.Professor;
import escola.musica.servico.ProfessorServico;
import escola.musica.util.Mensagem;

@Controller("professorBean")

@Scope("session")

public class ProfessorBean implements Serializable {

	
	private static final long serialVersionUID = 3439871488626981861L;


	private Professor professor;
	private List<Professor> professores;

	@Autowired
	private ProfessorServico professorServico;
	
	private Professor professorExclusao;

	private List<Professor> professoresFiltrados;
	

	public void iniciarBean() {

		professores = professorServico.listarTodos();

	}

	public void novoProfessor() {

		professor = new Professor();

	}

	public void salvar() {

		professorServico.salvar(professor);

		Mensagem.mensagemInformacao("Professor cadastrado com sucesso");

		professores = professorServico.listarTodos();

		professor = null;

	}

	public void editar(Professor professor) {

		this.professor = professor;

	}
	
	public void prepararExclusao(Professor professor) {

		this.professorExclusao = professor;

	}

	public void excluir(Professor professorExclusao) {

		professorServico.excluir(professorExclusao);

		Mensagem.mensagemInformacao("Professor excluido com sucesso!");

		professores = new ProfessorDAO().listarTodos();

		professoresFiltrados = null;
		
	}
	
	
	

	public void voltar() {

		this.professor = null;

	}

	public String getDataAtual() {
		
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());

	}

	

	public Professor getProfessor() {

		return professor;

	}

	public void setProfessor(Professor professor) {

		this.professor = professor;

	}

	public List<Professor> getProfessores() {

		return professores;

	}

	public void setProfessores(List<Professor> professores) {

		this.professores = professores;

	}
	
	public void setProfessorExclusao(Professor professorExclusao) {

		this.professorExclusao = professorExclusao;

	}

	public List<Professor> getProfessoresFiltrados() {

		return professoresFiltrados;

	}

	public void setProfessoresFiltrados(List<Professor> professoresFiltrados) {

		this.professoresFiltrados = professoresFiltrados;

	}


}