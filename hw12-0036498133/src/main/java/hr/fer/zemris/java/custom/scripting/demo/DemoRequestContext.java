package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Demo class presenting how Request Context works
 * 
 * @author Rafael Josip penić
 *
 */
public class DemoRequestContext {

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 * @throws IOException
	 *             in case of an error while writing or reading from a certain
	 *             stream
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}

	/**
	 * Method presenting how request context works without cookies
	 * 
	 * @param filePath
	 *            path to the file in which the request context will write its
	 *            content
	 * @param encoding
	 *            encoding of request context
	 * @throws IOException
	 *             in case of an error while writing
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));

		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");

		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");

		os.close();
	}

	/**
	 * Method presenting how request context works with cookies
	 * 
	 * @param filePath
	 *            path to the file in which the request context will write its
	 *            content
	 * @param encoding
	 *            encoding of request context
	 * @throws IOException
	 *             in case of an error while writing
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));

		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());

		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");

		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));

		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}
