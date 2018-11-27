package escola.musica.servico;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import escola.musica.dao.AlunoDAO;
import escola.musica.dao.MatriculaDAO;
import escola.musica.modelo.Aluno;
import escola.musica.modelo.Matricula;


@ManagedBean(name="servletDownloadMatricula")
public class ServletDownloadMatricula{

	private RelatorioServico relatorioServico = new RelatorioServico();

	private MatriculaDAO matriculaDAO = new MatriculaDAO();
	

	public void download() {

		try {
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.
					getCurrentInstance().getExternalContext()
					.getResponse();
			
			ServletContext context = (ServletContext) FacesContext
				    .getCurrentInstance().getExternalContext().getContext();


			List<Matricula> matriculas = matriculaDAO.listarTodos();
			
			String fileUrl = relatorioServico.gerarRelatorio(matriculas, new HashMap(), "rel_matriculas", "rel_matriculas", context);

			File downloadFile = new File(fileUrl);
			FileInputStream inputStream = new FileInputStream(downloadFile);

			String mimeType = context.getMimeType(fileUrl);
			if (mimeType == null) {

				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			OutputStream outputStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(buffer)) != -1) {

				outputStream.write(buffer, 0, bytesRead);

			}

			response.setContentType("application/octet-stream");
			response.getOutputStream().flush();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
