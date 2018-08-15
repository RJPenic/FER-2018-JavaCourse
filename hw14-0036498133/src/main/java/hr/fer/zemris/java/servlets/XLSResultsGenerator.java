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

import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

@WebServlet(name = "voting-XLS", urlPatterns = { "/servleti/glasanje-xls" })
/**
 * Servlet that constructs xls document from information extracted from voting
 * results database. Constructed xls will consist of three columns one of which is
 * name of the entry, one is entrys ID and last one represents entry voting score.
 * 
 * @author Rafael Josip Penić
 *
 */
public class XLSResultsGenerator extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException | NullPointerException ex) {
			resp.sendError(400, "Bad Request");
			return;
		}

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet();

		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Opcija");
		rowhead.createCell(1).setCellValue("Broj glasova");

		int i = 1;
		try {
			List<PollOption> list = DAOProvider.getDao().getPollOptionsFromPoll(pollID);
			for(PollOption opt : list) {
				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue(opt.getOptionTitle());
				row.createCell(1).setCellValue(opt.getVotesCount());
				i++;
			}
		} catch(DAOException ex) {
			resp.sendError(500, "Internal Server Error");
			hwb.close();
			return;
		}

		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");

		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());

		hwb.write(bos);
		bos.close();
		hwb.close();
	}
}
