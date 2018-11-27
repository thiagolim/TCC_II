package escola.musica.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import escola.musica.dao.CursoDAO;
import escola.musica.modelo.Curso;
import escola.musica.modelo.TipoCurso;
import escola.musica.servico.CursoServico;
import escola.musica.servico.RelatorioServico;
import escola.musica.util.Mensagem;

@Controller("cursoBean")

@Scope("view")

public class CursoBean implements Serializable {

	private static final long serialVersionUID = -862660658464075437L;
	private Curso curso;
	private List<TipoCurso> tipos;
	private List<Curso> cursos = new ArrayList<Curso>();
	private RelatorioServico relatorioServico = new RelatorioServico();
	private List<Curso> cursosAccordion = new ArrayList<Curso>();
	private CursoDAO cursoDAO = new CursoDAO();
	private Curso cursoExclusao;
	private List<Curso> cursosFiltrados;

	@Autowired
	private CursoServico cursoServico;

	public void iniciarBean() {

		cursos = cursoServico.listarTodos();

		cursosAccordion = cursoServico.listarCursosAccordion();

		tipos = Arrays.asList(TipoCurso.values());

	}

	public void novoCurso() {

		curso = new Curso();

	}

	public void salvar() throws InterruptedException {

		cursoServico.salvar(curso);

		cursos = cursoServico.listarTodos();

		curso = null;

		Mensagem.mensagemInformacao("Curso salvo com sucesso");

	}

	public void editar(Curso curso) {

		this.curso = curso;

	}

	public void prepararExclusao(Curso curso) {

		this.cursoExclusao = curso;

	}

	public void excluir(Curso cursoExclusao) {

		cursoServico.excluir(cursoExclusao);

		Mensagem.mensagemInformacao("Curso excluido com sucesso!");

		cursos = new CursoDAO().listarTodos();

		cursosFiltrados = null;

	}

	public void voltar() {

		curso = null;

	}

	
	public String getDataAtual() {

		return new SimpleDateFormat("dd/MM/yyyy")

				.format(new Date());

	}

	public Curso getCurso() {

		return curso;

	}

	public void setCurso(Curso curso) {

		this.curso = curso;

	}

	public List<TipoCurso> getTipos() {

		return tipos;

	}

	public void setTipos(List<TipoCurso> tipos) {

		this.tipos = tipos;

	}

	public List<Curso> getCursos() {

		return cursos;

	}

	public void setCursos(List<Curso> cursos) {

		this.cursos = cursos;

	}

	public List<Curso> getCursosAccordion() {

		return cursosAccordion;

	}

	public void setCursosAccordion(List<Curso> cursosAccordion) {

		this.cursosAccordion = cursosAccordion;

	}

	public Curso getCursoExclusao() {

		return cursoExclusao;

	}

	public void setCursoExclusao(Curso cursoExclusao) {

		this.cursoExclusao = cursoExclusao;

	}

	public List<Curso> getCursosFiltrados() {

		return cursosFiltrados;

	}

	public void setCursosFiltrados(List<Curso> cursosFiltrados) {

		this.cursosFiltrados = cursosFiltrados;

	}
	
	public void download() {

		try {

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();

			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();

			List<Curso> cursos = cursoDAO.listarTodos();
			List dados = new ArrayList();
			dados.add(cursos);

			String fileUrl = relatorioServico.gerarRelatorio(dados,
					new HashMap(), "rel_cursos", "rel_cursos", servletContext);

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