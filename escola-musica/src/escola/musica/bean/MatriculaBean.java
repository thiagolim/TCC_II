package escola.musica.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import escola.musica.dao.AlunoDAO;
import escola.musica.dao.MatriculaDAO;
import escola.musica.modelo.Aluno;
import escola.musica.modelo.Curso;
import escola.musica.modelo.Matricula;
import escola.musica.servico.AlunoServico;
import escola.musica.servico.CursoServico;
import escola.musica.servico.MatriculaServico;
import escola.musica.servico.RelatorioServico;
import escola.musica.util.Mensagem;

@Controller("matriculaBean")
@Scope("view")

public class MatriculaBean implements Serializable {
	private static final long serialVersionUID = -6018503417460500546L;
	private Matricula matricula;
	private List<Matricula> matriculas;
	private List<Aluno> alunos;
	private List<Curso> cursos;
	private RelatorioServico relatorioServico = new RelatorioServico();

	private MatriculaDAO matriculaDAO = new MatriculaDAO();
	 

	@Autowired
	private MatriculaServico matriculaServico;

	@Autowired
	private AlunoServico alunoServico;

	@Autowired
	private CursoServico cursoServico;

	private Matricula matriculaExclusao;

	private List<Matricula> matriculasFiltrados;

	public void iniciarBean() {
		atualizarMatriculas();
		alunos = alunoServico.listarTodos();
		cursos = cursoServico.listarTodos();

	}

	public void salvar() {
		if (matricula.getNumero().length() < 3) {
			Mensagem.mensagemErro("O campo matricula deve ter no minimo 3 caracteres");
			return;
		}

		matriculaServico.salvar(matricula);
		atualizarMatriculas();
		matricula = null;
		Mensagem.mensagemInformacao("Matricula cadastrada com sucesso!");

	}

	public void novaMatricula() {
		matricula = new Matricula();

	}

	public void editar(Matricula matricula) {
		this.matricula = matricula;

	}

	public String getDataAtual() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());

	}

	public void prepararExclusao(Matricula matricula) {
		this.matriculaExclusao = matricula;

	}

	public void excluir(Matricula matriculaExclusao) {
		matriculaServico.excluir(matriculaExclusao);
		Mensagem.mensagemInformacao("Matricula excluida com sucesso!");
		alunos = new AlunoDAO().listarTodos();
		matriculasFiltrados = null;

	}

	public void cancelar() {
		matricula = null;

	}

	private void atualizarMatriculas() {
		matriculas = matriculaServico.listarTodas();

	}

	public Matricula getMatricula() {
		return matricula;

	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;

	}

	public List<Matricula> getMatriculas() {
		return matriculas;

	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;

	}

	public List<Aluno> getAlunos() {
		return alunos;

	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;

	}

	public List<Curso> getCursos() {
		return cursos;

	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;

	}

	public void setMatriculaExclusao(Matricula matriculaExclusao) {
		this.matriculaExclusao = matriculaExclusao;

	}

	public List<Matricula> getMatriculasFiltrados() {
		return matriculasFiltrados;

	}

	public void setMatriculasFiltrados(List<Matricula> matriuculasFiltrados) {
		this.matriculasFiltrados = matriculasFiltrados;

	}

	public void download() {

		try {

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();

			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();

			List<Matricula> matriculas = matriculaDAO.listarTodos();
			List dados = new ArrayList();
			dados.add(matriculas);

			String fileUrl = relatorioServico.gerarRelatorio(dados,
					new HashMap(), "rel_matriculas", "rel_matriculas", servletContext);

			File downloadFile = new File(fileUrl);
			FileInputStream inputStream = new FileInputStream(downloadFile);

			String mimeType = servletContext.getMimeType(fileUrl);
			if (mimeType == null) {

				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			OutputStream outputStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(buffer)) != -1) {

				outputStream.write(buffer, 0, bytesRead);

			}

			inputStream.close();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	
}