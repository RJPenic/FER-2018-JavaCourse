package hr.fer.zemris.java.rest;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import hr.fer.zemris.java.servlets.UtilLoader;

@Path("/tagj")
/**
 * Class used as a part of REST API. It provides methods that supply scripts
 * with currently available tags.
 * 
 * @author Rafael Josip Penić
 *
 */
public class FootballPlayerTagsJSON {
	
	@Context
	private ServletContext context;
	
	@GET
	@Produces("application/json")
	/**
	 * Gets all currently available tags
	 * 
	 * @return response containing set with all tags
	 */
	public Response getTags() {
		Set<String> tagSet;

		try {
			tagSet = UtilLoader.getTags(context.getRealPath("WEB-INF/opisnik.txt"));
		} catch (RuntimeException | IOException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		JSONObject result = new JSONObject();

		result.put("tagSet", tagSet);

		return Response.status(Status.OK).entity(result.toString()).build();
	}
}
