package hr.fer.zemris.java.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.java.servlets.UtilLoader;
import hr.fer.zemris.java.servlets.UtilLoader.FootballPlayerEntry;

@Path("/picnames")
/**
 * Class that uses org.json library to send appropriate arguments to scripts. It
 * is responsible for providing image names and players with specific image
 * name.
 * 
 * @author Rafael Josip Penić
 *
 */
public class FootballPlayerPictureNamesJSON {

	@Context
	private ServletContext context;
	
	@Path("{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Gets image names associate with the given tag
	 * 
	 * @param tag
	 *            tag that will be used as a certain filter for image sources
	 * @return response that contains a collection with all image names that are
	 *         associated with the given tag
	 * @throws IOException
	 *             in case of an error while reading
	 */
	public Response getImageSources(@PathParam("tag") String tag) {
		List<String> imgSrcs;

		try {
			imgSrcs = UtilLoader.getPictureNames(tag, context.getRealPath("WEB-INF/opisnik.txt"));
		} catch (RuntimeException | IOException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		JSONObject result = new JSONObject();

		JSONArray srcsArr = new JSONArray();
		for (String t : imgSrcs) {
			srcsArr.put(t);
		}
		result.put("imgSrcs", srcsArr);

		return Response.status(Status.OK).entity(result.toString()).build();
	}

	@Path("/origin/{picname}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Gets player associate with the image named with the given string
	 * 
	 * @param picname
	 *            image name
	 * @return response containing tags and image title of the player with the given
	 *         image name
	 */
	public Response getPlayer(@PathParam("picname") String picname) {
		FootballPlayerEntry player;
		try {
			player = UtilLoader.getPlayerEntryWithPicName(picname, context.getRealPath("WEB-INF/opisnik.txt"));
		} catch (RuntimeException | IOException ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		JSONObject result = new JSONObject();
		result.put("tags", player.getTags());
		result.put("title", player.getTitle());

		return Response.status(Status.OK).entity(result.toString()).build();
	}
}
