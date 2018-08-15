package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Properties;
import java.util.Random;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class representing a simple server with basic server functionalities
 * 
 * @author Rafael Josip Penić
 *
 */
public class SmartHttpServer {

	/**
	 * Servers domain name
	 */
	private String domainName;

	/**
	 * Servers port
	 */
	private int port;

	/**
	 * Determines how many worker threads there will be
	 */
	private int workerThreads;

	/**
	 * timeout of sessions on the server
	 */
	private int sessionTimeout;

	/**
	 * Map containing mimes(how certain file will be interpreted)
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/**
	 * "main" thread which starts servers work
	 */
	private ServerThread serverThread;

	/**
	 * Thread pool used for synchronization
	 */
	private ExecutorService threadPool;

	/**
	 * document root which the server can access. If server tries to access anything
	 * "above" that, access will be denied
	 */
	private Path documentRoot;

	/**
	 * Map storing workers that will be loaded before server starts with the "work"
	 */
	private Map<String, IWebWorker> workersMap;

	/**
	 * Boolean flag which signalizes if the server thread should be stopped or not
	 */
	private boolean stopped = false;

	/**
	 * Class representing a single session map entry
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	private static class SessionMapEntry {

		/**
		 * Session identification key
		 */
		String sid;

		/**
		 * Host of the session
		 */
		String host;

		/**
		 * Time after which this session is not valid
		 */
		long validUntil;

		/**
		 * Map containing permanent parameters
		 */
		Map<String, String> map;
	}

	/**
	 * Map containing all sessions currently on the server
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

	/**
	 * Used for generating session IDs
	 */
	private Random sessionRandom = new Random();

	@SuppressWarnings("deprecation")
	/**
	 * Constructs a server using a given configuration file
	 * 
	 * @param configFileName
	 *            name of the configuration file with which the server will be
	 *            constructed
	 */
	public SmartHttpServer(String configFileName) {
		Properties prop = new Properties();
		Properties prop1 = new Properties();

		try {
			prop.load(Files.newInputStream(Paths.get("config/" + configFileName)));
		} catch (IOException e) {
			throw new RuntimeException("Error while reading from " + configFileName);
		}

		domainName = prop.getProperty("server.domainName");
		port = Integer.parseInt(prop.getProperty("server.port"));
		workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));
		documentRoot = Paths.get(prop.getProperty("server.documentRoot"));

		try {
			prop1.load(Files.newInputStream(Paths.get(prop.getProperty("server.mimeConfig"))));
		} catch (IOException e) {
			throw new RuntimeException("Error while reading from " + prop.getProperty("server.mimeConfig"));
		}

		prop1.forEach((k, v) -> mimeTypes.put(k.toString(), v.toString()));

		serverThread = new ServerThread();

		try {
			prop1.clear();
			prop1.load(Files.newInputStream(Paths.get(prop.getProperty("server.workers"))));
		} catch (IOException e) {
			throw new RuntimeException("Error while reading from " + prop.getProperty("server.workers"));
		}

		workersMap = new HashMap<>();

		prop1.forEach((k, v) -> {
			String path = k.toString();
			String fqcn = v.toString();
			Class<?> referenceToClass = null;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Object newObject = null;
			try {
				newObject = referenceToClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			IWebWorker iww = (IWebWorker) newObject;
			workersMap.put(path, iww);
		});

	}

	/**
	 * Starts the server. Starts the server thread and initializes thread pool
	 */
	protected synchronized void start() {
		if (!serverThread.isAlive()) {
			serverThread.start();
		}

		threadPool = Executors.newFixedThreadPool(workerThreads);
	}

	/**
	 * Stops the server
	 * 
	 * @throws IOException
	 *             in case of an error while closing
	 */
	protected synchronized void stop() {
		stopped = true;
		threadPool.shutdown();
	}

	/**
	 * Class representing basic server thread which initializes socket and accepts
	 * client requests
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	protected class ServerThread extends Thread {

		@Override
		public void run() {

			try {
				ServerSocket serverSocket = new ServerSocket(port);

				Thread t = new Thread(() -> {
					while (true) {
						try {
							Thread.sleep(300000);
						} catch (InterruptedException e) {
							throw new RuntimeException("Sleeping thread has been interrupted.");
						}

						Map<String, SessionMapEntry> tempMap = new HashMap<>(sessions);
						tempMap.forEach((k, v) -> {
							long time = System.currentTimeMillis() / 1000;

							if (time >= v.validUntil) {
								sessions.remove(k);
							}
						});
					}
				});

				t.setDaemon(true);
				t.start();

				serverSocket.setSoTimeout(5000);// timeout 5 seconds
				while (true) {
					Socket client = null;
					try {
						client = serverSocket.accept();
					} catch(SocketTimeoutException ex) {
						//ignore
					}
					
					if (stopped)
						break;

					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
				
				serverSocket.close();

			} catch (IOException ex) {
				throw new RuntimeException("Error while working with sockets.");
			}

		}
	}

	/**
	 * Workers that process client requests. They are created by server thread.
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/**
		 * Socket used for producing input and output stream which will later be used to
		 * receive and respond
		 */
		private Socket csocket;

		/**
		 * Input stream with which the request is received
		 */
		private PushbackInputStream istream;

		/**
		 * Output stream that makes responding to client possible
		 */
		private OutputStream ostream;

		/**
		 * Version of the HTTPS
		 */
		private String version;

		/**
		 * Method extracted from the request
		 */
		private String method;

		/**
		 * Host of the client
		 */
		private String host;

		/**
		 * Parameters
		 */
		private Map<String, String> params = new HashMap<String, String>();

		/**
		 * Temporary parameters
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();

		/**
		 * Permanent parameters
		 */
		private Map<String, String> permParams = new HashMap<String, String>();

		/**
		 * Workers cookies containing bits of information
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		/**
		 * Sessions ID
		 */
		private String SID;

		/**
		 * Clients context
		 */
		private RequestContext context = null;

		/**
		 * Path extracted from the given request
		 */
		private Path urlPath;

		/**
		 * Boolean value which determines if the certain worker has done its job
		 */
		private boolean workerProcessed = false;

		/**
		 * Boolean flag that signals if the certain method has produced an error
		 */
		private boolean errorProduced = false;

		/**
		 * String that stores package of the server workers
		 */
		private static final String WORKER_PACKAGE = "hr.fer.zemris.java.webserver.workers";

		/**
		 * Constructor for client worker which initializes the socket
		 * 
		 * @param csocket
		 *            value of the socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				serve();
			} catch (Exception e) {
				throw new RuntimeException("Error while processing request.");
			}
		}

		/**
		 * Gets extension of the given path
		 * 
		 * @param requestedPath
		 *            paths extension(or empty string if the file has no extension or if
		 *            path is a directory)
		 * @return paths extension
		 */
		private String getExtension(Path requestedPath) {
			String pathString = requestedPath.toString();
			int index = pathString.lastIndexOf(".");

			if (index > 0) {
				return pathString.substring(index + 1);
			}

			return "";
		}

		/**
		 * Parses parameters in the given string and puts them into parameter map
		 * 
		 * @param paramString
		 *            unparsed string containing parameters
		 * @throws IOException
		 *             in case of an error when sending error
		 */
		private void parseParameters(String paramString) throws IOException {
			Objects.requireNonNull(paramString, "Given string reference is null");

			if (paramString.isEmpty()) {
				sendError(400, "Bad Request");
				errorProduced = true;
				return;
			}

			String[] pairs = paramString.trim().split("&");

			for (String pair : pairs) {
				String[] pairArr = pair.split("=");

				if (pairArr.length < 2) {
					params.put(pairArr[0], "");
				} else if (pairArr.length == 2) {
					params.put(pairArr[0], pairArr[1]);
				} else {
					sendError(400, "Bad Request");
					errorProduced = true;
					return;
				}
			}
		}

		/**
		 * Extracts the host value from the request and sets it as the host of this
		 * client
		 * 
		 * @param request
		 *            header of the request
		 */
		private void getHost(List<String> request) {
			boolean hostFound = false;

			for (String line : request) {
				String lineTrimmed = line.trim();
				if (lineTrimmed.startsWith("Host:")) {
					hostFound = true;
					String result = lineTrimmed.substring(("Host:").length()).trim();

					if (result.matches(".*:[0-9]+")) {
						host = result.substring(0, result.lastIndexOf(":"));
					} else {
						host = result;
					}

					break;
				}
			}

			if (!hostFound) {
				host = domainName;
			}
		}

		/**
		 * Reads request and returns requests header
		 * 
		 * @return list containing lines of the header
		 * @throws IOException
		 *             in case of an error while reading
		 */
		private List<String> readRequest() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = istream.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}

			return extractHeaders(new String(bos.toByteArray(), StandardCharsets.ISO_8859_1));
		}

		/**
		 * Extracts header from the request
		 * 
		 * @param requestHeader
		 *            string of the request header
		 * @return list containing lines of the header
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;

			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}

			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}

			return headers;
		}

		/**
		 * Sends error response on the output
		 * 
		 * @param statusCode
		 *            code of the error
		 * @param statusText
		 *            message of the error
		 * @throws IOException
		 *             in case of an error while "sending" the error
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: smart HTTP Server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.ISO_8859_1));

			ostream.flush();
		}

		/**
		 * Method that serves the reqest
		 * 
		 * @throws Exception
		 *             in case of an error
		 */
		private void serve() throws Exception {
			istream = new PushbackInputStream(csocket.getInputStream());
			ostream = csocket.getOutputStream();

			List<String> request;
			request = readRequest();

			if (request.isEmpty()) {
				sendError(400, "Bad request");
				ostream.close();
				istream.close();
				csocket.close();
				return;
			}

			String firstLine = request.get(0);

			String[] splittedArr = firstLine.trim().split("\\s+");

			if (splittedArr.length != 3) {
				sendError(400, "Bad Request");
				ostream.close();
				istream.close();
				csocket.close();
				return;
			}

			method = splittedArr[0];
			String requestedPath = splittedArr[1];
			version = splittedArr[2];

			if (!method.equals("GET") || (!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1"))) {
				sendError(400, "Bad request");
				ostream.close();
				istream.close();
				csocket.close();
				return;
			}

			getHost(request);

			String path;
			String paramString;

			String[] tempStringArray = requestedPath.toString().split("\\?");

			if (tempStringArray.length > 2) {
				sendError(400, "Bad request");
				ostream.close();
				istream.close();
				csocket.close();
				return;
			}

			path = tempStringArray[0];

			checkSession(request);

			if (tempStringArray.length == 2) {
				paramString = tempStringArray[1];

				parseParameters(paramString);

				if (errorProduced) {

					ostream.close();
					istream.close();
					csocket.close();
					return;
				}
			}

			urlPath = documentRoot.resolve(path.substring(1));

			if (!(urlPath.startsWith(documentRoot))) {
				sendError(403, "Forbidden");
				ostream.close();
				istream.close();
				csocket.close();
				return;
			}

			internalDispatchRequest(path, true);

			if (errorProduced || workerProcessed) {
				istream.close();
				ostream.close();
				csocket.close();
				return;
			}

			if (!(urlPath.toFile().exists() && urlPath.toFile().canRead() && urlPath.toFile().isFile())) {
				sendError(404, "Not Found");
				ostream.close();
				istream.close();
				csocket.close();
				return;
			}

			if (getExtension(urlPath).equals("smscr")) {
				executeSmartScript(urlPath);

				permParams.forEach((k, v) -> {
					sessions.get(SID).map.put(k, v);
				});

			} else {
				context.setContentLength(urlPath.toFile().length());

				InputStream is = Files.newInputStream(urlPath);
				context.write(is.readAllBytes());
				is.close();
			}

			ostream.close();
			istream.close();
			csocket.close();
		}

		/**
		 * Method that goes through the header, find cookies there and if needed creates
		 * new SID or prolongs validity of the session
		 * 
		 * @param request
		 *            list of header lines
		 */
		private void checkSession(List<String> request) {
			String sidCandidate = null;

			for (String line : request) {
				if (!line.trim().startsWith("Cookie:"))
					continue;

				String cookies = line.trim().substring(("Cookie:").length());
				String[] cookiePairsArrays = cookies.split(";");

				for (String pair : cookiePairsArrays) {
					String[] keyAndValue = pair.split("=");
					if (keyAndValue.length != 2) {
						throw new RuntimeException("String cannot be separated as map pair.");
					}

					if (keyAndValue[0].trim().equals("sid")) {
						sidCandidate = keyAndValue[1].trim().substring(1, keyAndValue[1].trim().length() - 1);
					}
				}
			}

			if (sidCandidate == null) {
				SID = generateSID();
				storeSID(SID);
			} else {
				SessionMapEntry entry = sessions.get(sidCandidate);

				if (entry == null) {
					SID = generateSID();
					storeSID(SID);
					return;
				}

				long currentTime = System.currentTimeMillis() / 1000;
				if (!entry.host.equals(host) || currentTime > entry.validUntil) {
					if (currentTime > entry.validUntil) {
						sessions.remove(sidCandidate);
					}

					SID = generateSID();
					storeSID(SID);
				} else {
					entry.validUntil = currentTime + sessionTimeout;
					permParams = entry.map;
					SID = sidCandidate;
				}
			}
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		@SuppressWarnings("deprecation")
		/**
		 * Method with similar function as dispatchRequest method but alongside the path
		 * it is given the boolean value which determines if the method has been called
		 * directly or not.
		 * 
		 * @param urlPath
		 *            path that will be analyzed
		 * @param directCall
		 *            true if the method has been called directly and false otherwise
		 * @throws Exception
		 *             in case of an error
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);

				sessions.get(SID).map.forEach((k, v) -> {
					context.setPersistentParameter(k, v);
				});
			}

			if (urlPath.startsWith("/private/") || urlPath.equals("/private")) {

				if (directCall) {
					sendError(404, "Not Found");
					errorProduced = true;
					return;
				}
			}

			if (urlPath.startsWith("/ext/")) {
				Class<?> referenceToClass = null;
				try {
					referenceToClass = this.getClass().getClassLoader()
							.loadClass(WORKER_PACKAGE + "." + urlPath.substring(("/ext/").length()));
				} catch (ClassNotFoundException e) {
					sendError(400, "Bad Request");
					errorProduced = true;
					return;
				}

				Object newObject = null;
				try {
					newObject = referenceToClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}

				IWebWorker iww = (IWebWorker) newObject;
				iww.processRequest(context);
				workerProcessed = true;
			} else if (workersMap.containsKey(urlPath)) {
				workerProcessed = true;
				workersMap.get(urlPath).processRequest(context);
			} else {
				String ext = getExtension(Paths.get(urlPath));

				if (ext.equals("smscr")) {
					workerProcessed = false;
					this.urlPath = documentRoot.resolve(urlPath.substring(1));
				}

				String mimeType = mimeTypes.get(ext);

				if (mimeType != null) {
					context.setMimeType(mimeType);
				} else {
					context.setMimeType("application/octet-stream");
				}
			}
		}

		/**
		 * Executes smart script on the given path
		 * 
		 * @param urlPath
		 *            path of the script
		 * @throws IOException
		 *             in case of an error when executing the script
		 */
		private void executeSmartScript(Path urlPath) throws IOException {
			InputStream is = Files.newInputStream(urlPath);
			String documentBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			is.close();

			DocumentNode docNode = new SmartScriptParser(documentBody).getDocumentNode();

			SmartScriptEngine sce = new SmartScriptEngine(docNode, context);
			sce.execute();
		}

		/**
		 * Generates Session ID
		 * 
		 * @return generated session ID consisting of upper case letters
		 */
		private String generateSID() {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < 20; i++) {
				sb.append((char) ('A' + sessionRandom.nextInt('Z' - 'A')));
			}

			return sb.toString();
		}

		/**
		 * Stores given session ID in sessions map and cookies list
		 * 
		 * @param SID
		 *            Session ID to be stored
		 */
		private void storeSID(String SID) {
			SessionMapEntry entry = new SessionMapEntry();
			entry.sid = SID;
			entry.host = host;
			entry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			entry.map = new ConcurrentHashMap<>();

			sessions.put(entry.sid, entry);

			outputCookies.add(new RCCookie("sid", SID, null, host, "/"));
		}
	}
}
