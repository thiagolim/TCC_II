package escola.musica.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import escola.musica.dao.AlunoDAO;
import escola.musica.dao.GenericDAO;
import escola.musica.modelo.Aluno;
import escola.musica.modelo.Pessoa;
import escola.musica.servico.AlunoServico;
import escola.musica.servico.RelatorioServico;
import escola.musica.util.Mensagem;

@Controller("alunoBean")

@Scope("session")

public class AlunoBean implements Serializable {

	private static final long serialVersionUID = -1025252140353914359L;

	private Aluno aluno;
	
	private RelatorioServico relatorioServico = new RelatorioServico();

	private AlunoDAO alunoDAO = new AlunoDAO();

	private List<Aluno> alunos;

	@Autowired

	private AlunoServico alunoServico;
	
	private Aluno alunoExclusao;

	private List<Aluno> alunosFiltrados;
	

	public void iniciarBean() {

		alunos = alunoServico.listarTodos();

	}

	public void novoAluno() {

		aluno = new Aluno();

	}

	
	
	public void salvar() {

		alunoServico.salvar(aluno);

		Mensagem.mensagemInformacao("Aluno cadastrado com sucesso");

		alunos = alunoServico.listarTodos();

		aluno = null;

	}

	public void editar(Aluno aluno) {

		this.aluno = aluno;

	}
	
	public void prepararExclusao(Aluno aluno) {

		this.alunoExclusao = aluno;

	}

	public void excluir(Aluno alunoExclusao) {

		alunoServico.excluir(alunoExclusao);

		Mensagem.mensagemInformacao("Aluno excluido com sucesso!");

		alunos = new AlunoDAO().listarTodos();

		alunosFiltrados = null;
		
	}
	
	
	

	public void voltar() {

		this.aluno = null;

	}

	public String getDataAtual() {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.YEAR, -6);

		return new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

	}

	

	public Aluno getAluno() {

		return aluno;

	}

	public void setAluno(Aluno aluno) {

		this.aluno = aluno;

	}

	public List<Aluno> getAlunos() {

		return alunos;

	}

	public void setAlunos(List<Aluno> alunos) {

		this.alunos = alunos;

	}
	
	public void setAlunoExclusao(Aluno alunoExclusao) {

		this.alunoExclusao = alunoExclusao;

	}

	public List<Aluno> getAlunosFiltrados() {

		return alunosFiltrados;

	}

	public void setAlunosFiltrados(List<Aluno> alunosFiltrados) {

		this.alunosFiltrados = alunosFiltrados;

	}
	

	public void download() {

		try {

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();

			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();

			List<Aluno> alunos = alunoDAO.listarTodos();
			List dados = new ArrayList();
			dados.add(alunos);

			String fileUrl = relatorioServico.gerarRelatorio(dados,
					new HashMap(), "rel_alunos", "rel_alunos", servletContext);

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