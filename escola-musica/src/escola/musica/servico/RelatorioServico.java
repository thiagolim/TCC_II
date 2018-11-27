package escola.musica.servico;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioServico implements Serializable {

	private static final long serialVersionUID = -827679311764577021L;
	private static final String FOLDER_RELATORIOS = "/escola/musica/relatorios";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private  String SERATOR = File.separator;
	private  String caminhoArquivoRelatorio = null;
	private JRExporter exporter = null;
	private String caminhoSubReport_Dir = "";
	private File arquivoGerado = null;
		
	public String gerarRelatorio(List<?>listaDataBeanColletion,
			HashMap parametrosRelatorio	, String nomeRelatorioJasper,
			String nomeRelatorioSaida, ServletContext servletContext) throws Exception{
		
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDataBeanColletion); 
		
		String caminhoRelatorio = servletContext.getRealPath(FOLDER_RELATORIOS);
		
		File file = new File(caminhoRelatorio + SERATOR + nomeRelatorioJasper + ".jasper");
		
		if(caminhoRelatorio == null
				
				|| (caminhoRelatorio !=null && caminhoRelatorio.isEmpty())
				|| !file.exists()) 	{
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SERATOR = "";
		}
		
		
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio + "musica.JPG");
		
		String caminhoArquivosJasper = caminhoRelatorio + SERATOR + nomeRelatorioJasper + ".jasper";
		
		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivosJasper);
		
		caminhoSubReport_Dir = caminhoRelatorio + SERATOR;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubReport_Dir);
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, jrbcds);
		
		exporter = new JRPdfExporter();
		caminhoArquivoRelatorio = caminhoRelatorio + SERATOR + nomeRelatorioSaida + ".pdf";
		
		arquivoGerado = new File(caminhoArquivoRelatorio);
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
		
		exporter.exportReport();
		
		arquivoGerado.deleteOnExit();
		
		return caminhoArquivoRelatorio; 
			
		
		
		
	}
	
	
	
	

	}
	


