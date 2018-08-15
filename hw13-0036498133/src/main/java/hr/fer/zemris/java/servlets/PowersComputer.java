package hr.fer.zemris.java.servlets;

import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet that constructs an xls which number of pages is determined by
 * parameter n. On each page it writes power value of each value between a and b
 * parameters. For example, on seconds page it will write square of each integer
 * number between a and b.
 * 
 * @author Rafael Josip Penić
 *
 */
public class PowersComputer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sa = req.getParameter("a");
		String sb = req.getParameter("b");
		String sn = req.getParameter("n");

		int a = 0;
		int b = 0;
		int n = 0;
		try {
			a = Integer.parseInt(sa);
			b = Integer.parseInt(sb);
			n = Integer.parseInt(sn);

			if (Math.abs(a) > 100 || Math.abs(b) > 100 || (n < 1 || n > 5))
				throw new NumberFormatException();
		} catch (NumberFormatException ex) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}

		if (b < a) {
			// if b is smaller than a, swap their values
			int temp = a;
			a = b;
			b = temp;
		}

		HSSFWorkbook hwb = new HSSFWorkbook();
		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("Page " + i);

			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Number");
			rowhead.createCell(1).setCellValue("Power value");

			for (int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow(j - a + 1);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}

		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");

		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		hwb.write(bos);
		bos.close();
		hwb.close();
	}
}
