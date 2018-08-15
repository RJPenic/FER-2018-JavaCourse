package hr.fer.zemris.java.servlets;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.util.UtilLoader;
import hr.fer.zemris.java.util.UtilLoader.BandResult;

@WebServlet(name = "voting-XLS", urlPatterns = { "/glasanje-xls" })
/**
 * Servlet that constructs xls document from information extracted from voting
 * results file. Constructed xls will consist of three columns one of which is
 * name of the band, one is bands ID and last one represents bands voting score.
 * 
 * @author Rafael Josip Penić
 *
 */
public class XLSResultsGenerator extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BandResult> bandResultsList = UtilLoader.mergeDefinitionAndResult(req, "/WEB-INF/glasanje-rezultati.txt",
				"/WEB-INF/glasanje-definicija.txt");

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet();

		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Bend");
		rowhead.createCell(1).setCellValue("ID");
		rowhead.createCell(2).setCellValue("Broj glasova");

		int i = 1;
		for (BandResult res : bandResultsList) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(res.getBand().getName());
			row.createCell(1).setCellValue(res.getBand().getId());
			row.createCell(2).setCellValue(res.getVoteCount().toString());
			i++;
		}

		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");

		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());

		hwb.write(bos);
		bos.close();
		hwb.close();
	}
}
