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

import escola.musica.dao.ProfessorDAO;
import escola.musica.modelo.Professor;


@ManagedBean(name="servletDownloadProfessor")
public class ServletDownloadProfessor{

	private RelatorioServico relatorioServico = new RelatorioServico();

	private ProfessorDAO professorDAO = new ProfessorDAO();
	

	public void download() {

		try {
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.
					getCurrentInstance().getExternalContext()
					.getResponse();
			
			ServletContext context = (ServletContext) FacesContext
				    .getCurrentInstance().getExternalContext().getContext();


			List<Professor> professores = professorDAO.listarTodos();
			
			String fileUrl = relatorioServico.gerarRelatorio(professores, new HashMap(), "rel_professores", "rel_professores", context);

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
