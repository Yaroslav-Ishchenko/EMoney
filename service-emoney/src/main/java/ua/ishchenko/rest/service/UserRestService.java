package ua.ishchenko.rest.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.ishchenko.rest.dao.UserDao;
import ua.ishchenko.rest.entities.User;

/**
 * 
 * Service class that handles REST requests
 * 
 * @author jaros
 * 
 */
@Component
@Path("/users")
@Transactional()
public class UserRestService {

	@Autowired
	private UserDao userDao;

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
	@Transactional
	public Response createUser(User User) {
		userDao.createUser(User);

		return Response.status(201)
				.entity("A new User/resource has been created").build();
	}

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
	@Transactional
	public Response createUsers(List<User> users) {
		for (User user : users) {
			userDao.createUser(user);
		}

		return Response.status(204).build();
	}
	
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findById(@PathParam("id") Long id)
			throws JsonGenerationException, JsonMappingException, IOException {

		User userById = userDao.getUserById(Long.valueOf(id));

		if (userById != null) {
			return Response.status(200).entity(userById)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS").build();
		} else {
			return Response.status(404)
					.entity("The user with the id " + id + " does not exist")
					.build();
		}
	}

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
	public List<User> getUsers() throws JsonGenerationException,
			JsonMappingException, IOException {

		List<User> users = userDao.getUsers();

		return users;
	}

	

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
	@Transactional
	public Response updateUserById(User user) {
		String message;
		int status;
		if (user.getId() != null) {
			userDao.updateUser(user);
			status = 200; // OK
			message = "User has been updated";
		} else if (userCanBeCreated(user)) {
			userDao.createUser(user);
			status = 201; // Created
			message = "The user you provided has been added to the database";
		} else {
			status = 406; // Not acceptable
			message = "The information you provided is not sufficient to perform either an UPDATE or "
					+ " an INSERTION of the new user resource <br/>"
					+ " If you want to UPDATE please make sure you provide an existent <strong>id</strong> <br/>"
					+ " If you want to insert a new user please provide at least a <strong>title</strong> and the <strong>feed</strong> for the user resource";
		}

		return Response.status(status).entity(message).build();
	}

	private boolean userCanBeCreated(User user) {
		return user.getName() != null;
	}

	/************************************ DELETE ************************************/
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response deleteUserById(@PathParam("id") Long id) {
		if (userDao.deleteUserById(id) == 1) {
			return Response.status(204).build();
		} else {
			return Response
					.status(404)
					.entity("User with the id " + id
							+ " is not present in the database").build();
		}
	}

	@DELETE
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response deleteUsers() {
		userDao.deleteUsers();
		return Response.status(200)
				.entity("All users have been successfully removed").build();
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public Response debit(Long userid, Long debitAmount) {
		User userById = userDao.getUserById(userid);
		if (userById != null) {
			userById.getWallet().withdraw(debitAmount);
			userDao.updateUser(userById);
			return Response.status(200)
					.entity(debitAmount + "debited to "+userById.getName()).build();
		}
		else
		{
			return Response
					.status(404)
					.entity("User with the id " + userid
							+ " is not present in the database").build();
		}
	}
	
	@Path("/credit")
	@POST
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public Response credit(@QueryParam("userid")Long userid,@QueryParam("amount") Long creditAmount) {
		User userById = userDao.getUserById(userid);
		if (userById != null) {
			userById.getWallet().deposit(creditAmount);
			userDao.updateUser(userById);
			return Response.status(200)
					.entity(creditAmount + "credited to "+userById.getName()).build();
		}
		else
		{
			return Response
					.status(404)
					.entity("User with the id " + userid
							+ " is not present in the database").build();
		}
	}

	public void setuserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
