package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Describes current context when sending a response to the client
 * 
 * @author Rafael Josip Penić
 *
 */
public class RequestContext {

	/**
	 * Output stream used for communication with the client
	 */
	private OutputStream outputStream;

	/**
	 * Charset used when writing
	 */
	private Charset charset;

	/**
	 * String used for constructing a charset
	 */
	private String encoding = "UTF-8";

	/**
	 * Status code of the response
	 */
	private int statusCode = 200;

	/**
	 * Status text of the response
	 */
	private String statusText = "OK";

	/**
	 * Type of the response(e.g. "text/plain" for "normal" text)
	 */
	private String mimeType = "text/html";

	/**
	 * Lenght of the content to be written
	 */
	private Long contentLength = null;

	/**
	 * Contexts dispatcher
	 */
	private IDispatcher dispatcher;

	/**
	 * Parameters of the context
	 */
	private Map<String, String> parameters;

	/**
	 * Temporary parameters of the context
	 */
	private Map<String, String> temporaryParameters;

	/**
	 * Persistent parameters of the context
	 */
	private Map<String, String> persistentParameters;

	/**
	 * Cookies of the context
	 */
	private List<RCCookie> outputCookies;

	/**
	 * Boolean flag that signalizes if the header has been generated or not
	 */
	private boolean headerGenerated = false;

	/**
	 * Represents a cookie which contains small bit of information
	 * 
	 * @author Rafael Josip Penić
	 *
	 */
	public static class RCCookie {

		/**
		 * Cookie name
		 */
		private String name;

		/**
		 * Cookie value
		 */
		private String value;

		/**
		 * Domain of the cookie
		 */
		private String domain;

		/**
		 * Cookie path
		 */
		private String path;

		/**
		 * Maximal duration of the cookie
		 */
		private Integer maxAge;

		/**
		 * Constructor for RCCookie objects
		 * 
		 * @param name
		 *            cookie name
		 * @param value
		 *            cookie value
		 * @param maxAge
		 *            duration of the cookie
		 * @param domain
		 *            cookie domain
		 * @param path
		 *            cookie path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {

			Objects.requireNonNull(name, "Given name string is null.");
			Objects.requireNonNull(value, "Given value string reference is null.");

			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
	}

	/**
	 * Constructor for RequestContext objects
	 * 
	 * @param outputStream
	 *            context output stream with makes communication with client
	 *            possible
	 * @param parameters
	 *            contexts parameters
	 * @param persistentParameters
	 *            persistent parameters of the context
	 * @param outputCookies
	 *            cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {

		Objects.requireNonNull(outputStream, "Given output stream reference is null.");

		this.outputStream = outputStream;
		this.parameters = parameters;
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
	}

	/**
	 * Constructor for RequestContext objects
	 * 
	 * @param outputStream
	 *            context output stream with makes communication with client
	 *            possible
	 * @param parameters
	 *            contexts parameters
	 * @param persistentParameters
	 *            persistent parameters of the context
	 * @param outputCookies
	 *            cookies
	 * @param temporaryParameters
	 *            temporary parameters
	 * @param dispatcher
	 *            contexts dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {

		this(outputStream, parameters, persistentParameters, outputCookies);

		this.dispatcher = dispatcher;
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Gets value stored in the parameters map under the given name
	 * 
	 * @param name
	 *            key of the searched entry
	 * @return extracted value
	 */
	public String getParameter(String name) {
		if (parameters == null)
			return null;
		return parameters.get(name);
	}

	/**
	 * Gets names of the parameters and stores them in a set
	 * 
	 * @return set containing names of the parameters
	 */
	public Set<String> getParameterNames() {
		if (parameters == null)
			return null;
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Gets value stored in the persistent parameters map under the given name
	 * 
	 * @param name
	 *            key of the searched entry
	 * @return extracted value
	 */
	public String getPersistentParameter(String name) {
		if (persistentParameters == null)
			return null;
		return persistentParameters.get(name);
	}

	/**
	 * Gets names of the persistent parameters and stores them in a set and then
	 * returns that set
	 * 
	 * @return set containing names of the parameters
	 */
	public Set<String> getPersistentParameterNames() {
		if (persistentParameters == null)
			return null;
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Adds given pair into persistent parameters map
	 * 
	 * @param name
	 *            key of the pair
	 * @param value
	 *            value of the pair
	 */
	public void setPersistentParameter(String name, String value) {
		if (persistentParameters == null) {
			persistentParameters = new HashMap<String, String>();
		}

		persistentParameters.put(name, value);
	}

	/**
	 * Removes entry with the given name from the persistent parameter map
	 * 
	 * @param name
	 *            key of the entry that will be removed
	 */
	public void removePersistentParameter(String name) {
		if (persistentParameters != null) {
			persistentParameters.remove(name);
		}
	}

	/**
	 * Gets value stored in the temporary parameters map under the given name
	 * 
	 * @param name
	 *            key of the searched entry
	 * @return extracted value
	 */
	public String getTemporaryParameter(String name) {
		if (temporaryParameters == null)
			return null;
		return temporaryParameters.get(name);
	}

	/**
	 * Gets names of the temporary parameters and stores them in a set and then
	 * returns that set
	 * 
	 * @return set containing names of the parameters
	 */
	public Set<String> getTemporaryParameterNames() {
		if (temporaryParameters == null)
			return null;
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Adds given pair into temporary parameters map
	 * 
	 * @param name
	 *            key of the pair
	 * @param value
	 *            value of the pair
	 */
	public void setTemporaryParameter(String name, String value) {
		if (temporaryParameters == null) {
			temporaryParameters = new HashMap<String, String>();
		}

		temporaryParameters.put(name, value);
	}

	/**
	 * Removes entry with the given name from the temporary parameter map
	 * 
	 * @param name
	 *            key of the entry that will be removed
	 */
	public void removeTemporaryParameter(String name) {
		if (temporaryParameters != null) {
			temporaryParameters.remove(name);
		}
	}

	/**
	 * Writes given byte array to the contexts output stream
	 * 
	 * @param data
	 *            data that will be written
	 * @return reference on this request context
	 * @throws IOException
	 *             in case of an error when writing
	 */
	public RequestContext write(byte[] data) throws IOException {
		write(data, 0, data.length);

		return this;
	}

	/**
	 * Writes given text to the contexts output stream
	 * 
	 * @param text
	 *            text that will be written
	 * @return reference on this context
	 * @throws IOException
	 *             in case of an error while writing
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		byte[] data = text.getBytes(charset);

		outputStream.write(data, 0, data.length);
		outputStream.flush();

		return this;
	}

	/**
	 * Writes parts of the given byte array to the output stream. Which parts will
	 * be written depends on the offset and the length.
	 * 
	 * @param data
	 *            data that will be written
	 * @param offset
	 *            offset in the array
	 * @param len
	 *            length to be written from offset further
	 * @return reference on this context
	 * @throws IOException
	 *             in case of an error while writing
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(data, offset, len);
		outputStream.flush();

		return this;
	}

	/**
	 * Generates header of the response
	 * 
	 * @throws IOException
	 *             in case of an error while writing
	 */
	private void generateHeader() throws IOException {

		charset = Charset.forName(encoding);

		StringBuilder sb = new StringBuilder();

		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append(
				"Content-Type: " + mimeType + (mimeType.startsWith("text/") ? ("; charset=" + encoding) : "") + "\r\n");

		if (contentLength != null) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}

		if (outputCookies != null || !outputCookies.isEmpty()) {
			for (RCCookie cookie : outputCookies) {
				sb.append("Set-Cookie: ");
				sb.append(cookie.name + "=" + "\"" + cookie.value + "\"");

				if (cookie.domain != null) {
					sb.append("; Domain=" + cookie.domain);
				}

				if (cookie.path != null) {
					sb.append("; Path=" + cookie.path);
				}

				if (cookie.maxAge != null) {
					sb.append("; Max-Age=" + cookie.maxAge);
				}

				sb.append("; HttpOnly");
				sb.append("\r\n");
			}
		}

		sb.append("\r\n");

		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		outputStream.flush();

		headerGenerated = true;
	}

	/**
	 * Adds given cookie to internal cookie list
	 * 
	 * @param cookie
	 *            cookie that will be added
	 */
	public void addRCCookie(RCCookie cookie) {
		if (headerGenerated)
			throw new RuntimeException("Header has been already generated.");

		Objects.requireNonNull(cookie, "Given RCCookie reference is null.");

		if (outputCookies == null) {
			outputCookies = new ArrayList<>();
		}

		outputCookies.add(cookie);
	}

	/**
	 * Setter for encoding
	 * 
	 * @param encoding
	 *            new value of encoding property
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated)
			throw new RuntimeException("Header has been already generated.");

		Objects.requireNonNull(encoding, "Given encoding string reference is null.");
		this.encoding = encoding;
	}

	/**
	 * Setter for status code of the context
	 * 
	 * @param statusCode
	 *            new status code
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated)
			throw new RuntimeException("Header has been already generated.");

		this.statusCode = statusCode;
	}

	/**
	 * Setter for status text/message of the contexxt
	 * 
	 * @param statusText
	 *            new status message
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated)
			throw new RuntimeException("Header has been already generated.");

		Objects.requireNonNull(statusText, "Given status text reference is null.");
		this.statusText = statusText;
	}

	/**
	 * Setter for mime type of the context
	 * 
	 * @param mimeType
	 *            new mime type
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated)
			throw new RuntimeException("Header has been already generated.");

		Objects.requireNonNull(mimeType, "Given mime type string reference is null.");
		this.mimeType = mimeType;
	}

	/**
	 * Setter for contexts content length
	 * 
	 * @param contentLength
	 *            new value of the content length property
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated)
			throw new RuntimeException("Header has been already generated.");

		Objects.requireNonNull(contentLength, "Given long reference is null.");
		this.contentLength = contentLength;
	}

	/**
	 * Getter for contexts dispatcher
	 * 
	 * @return contexts dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
}
