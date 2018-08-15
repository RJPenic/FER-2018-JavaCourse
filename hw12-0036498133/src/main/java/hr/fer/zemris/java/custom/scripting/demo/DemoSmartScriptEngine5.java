package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Fifth demo class that presents how smart script engine works
 * 
 * @author Rafael Josip Penić
 *
 */
public class DemoSmartScriptEngine5 {

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		String documentBody;
		try {
			Path tempPath = Paths.get("webroot/scripts/fibonaccih.smscr");
			documentBody = new String(Files.readAllBytes(tempPath), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			System.out.println("Error while reading from fibonaccih.smscr");
			return;
		}

		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}
}
