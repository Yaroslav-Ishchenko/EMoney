package ua.ishchenko.common.service;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import ua.ishchenko.common.entities.User;

@Path("/users")
public interface IUserRestService {

	/************************************ CREATE ************************************/

	/**
	 * Adds a new resource (user) from the given json format (at least title and
	 * feed elements are required at the DB level)
	 * 
	 * @param user
	 * @return
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	Response createUser(User user);

	/**
	 * A list of resources (here Users) provided in json format will be added to
	 * the database.
	 * 
	 * @param Users
	 * @return
	 */
	@POST
	@Path("/list")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	Response createUsers(List<User> users);

	/************************************ READ ************************************/
	/**
	 * Returns a resource (user) from the database
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@GET
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	Response findById(@PathParam("id") Long id) throws JsonGenerationException,
			JsonMappingException, IOException;

	/************************************ READ ************************************/
	/**
	 * Returns all resources (users) from the database
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	List<User> getUsers() throws JsonGenerationException, JsonMappingException,
			IOException;

	/************************************ UPDATE ************************************/
	/**
	 * Updates the attributes of the user received via JSON for the given @param
	 * id
	 * 
	 * If the user does not exist yet in the database (verified by
	 * <strong>id</strong>) then the application will try to create a new user
	 * resource in the db
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	Response updateUserById(User user);

	/************************************ DELETE ************************************/
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	Response deleteUserById(@PathParam("id") Long id);

	@DELETE
	@Produces({ MediaType.TEXT_HTML })
	Response deleteUsers();

	@Path("{userid}/withdraw")
	@POST
	public Response withdraw(@PathParam("userid") Long userid,
			@QueryParam("amount") Long withdrawAmount);

	@Path("{userid}/deposit")
	@POST
	Response deposit(@PathParam("userid") Long userid,
			@QueryParam("amount") Long depositAmount);

}
