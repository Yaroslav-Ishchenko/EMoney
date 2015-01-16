package ua.ishchenko.rest.service;

import java.io.IOException;
import java.util.Date;
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
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.ishchenko.rest.dao.TransactionDao;
import ua.ishchenko.rest.dao.UserDao;
import ua.ishchenko.rest.entities.Transaction;
import ua.ishchenko.rest.entities.User;
import ua.ishchenko.rest.entities.Wallet;
import ua.ishchenko.rest.enumerations.EOperationType;
import ua.ishchenko.rest.enumerations.ETransactionStatus;
import ua.ishchenko.rest.exceptions.NegativeBalanceException;

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

	@Autowired
	private TransactionDao transactionDao;

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

		return Response.status(Response.Status.CREATED)
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
			return Response.status(Response.Status.OK).entity(userById)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS").build();
		} else {
			return Response.status(Response.Status.NOT_FOUND)
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
		User user = new User("Jaros");
		user.setWallet(new Wallet());
		userDao.createUser(user);

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
		Status status;
		if (user.getId() != null) {
			userDao.updateUser(user);
			status = Response.Status.OK; // OK
			message = "User has been updated";
		} else if (userCanBeCreated(user)) {
			userDao.createUser(user);
			status = Response.Status.CREATED; // Created
			message = "The user you provided has been added to the database";
		} else {
			status = Response.Status.NOT_ACCEPTABLE; // Not acceptable
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
					.status(Response.Status.NOT_FOUND)
					.entity("User with the id " + id
							+ " is not present in the database").build();
		}
	}

	@DELETE
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response deleteUsers() {
		userDao.deleteUsers();
		return Response.status(Response.Status.OK)
				.entity("All users have been successfully removed").build();
	}

	@Path("{userid}/withdraw")
	@POST
	@Transactional
	public Response withdraw(@PathParam("userid") Long userid,
			@QueryParam("amount") Long withdrawAmount) {
		User userById = userDao.getUserById(userid);

		if (userById != null) {
			ETransactionStatus status = null;

			try {
				userById.getWallet().withdraw(withdrawAmount);
				userDao.updateUser(userById);
				status = ETransactionStatus.Success;
			} catch (NegativeBalanceException ex) {
				status = ETransactionStatus.Failed;
			}
			Transaction transaction = createTransaction(userById, withdrawAmount,
					status, EOperationType.Withdraw);
			transactionDao.createTransaction(transaction);
			return Response
					.status(Response.Status.OK)
					.entity(withdrawAmount + " withdrawn from "
							+ userById.getName()).build();
		} else {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("User with the id " + userid
							+ " is not present in the database").build();
		}

	}

	@Path("{userid}/deposit")
	@POST
	@Transactional
	public Response deposit(@PathParam("userid") Long userid,
			@QueryParam("amount") Long depositAmount) {
		User userById = userDao.getUserById(userid);
		if (userById != null) {
			ETransactionStatus status = null;
			try {
				userById.getWallet().deposit(depositAmount);
				userDao.updateUser(userById);
				status = ETransactionStatus.Success;
			} catch (NegativeBalanceException ex) {
				status = ETransactionStatus.Failed;
			}
			Transaction transaction = createTransaction(userById, depositAmount,
					status, EOperationType.Withdraw);
			transactionDao.createTransaction(transaction);
			return Response
					.status(Response.Status.OK)
					.entity(depositAmount + "deposited to " + userById.getName())
					.build();
		} else {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("User with the id " + userid
							+ " is not present in the database").build();
		}
	}
	private Transaction createTransaction(User userById, Long amount,
			ETransactionStatus status, EOperationType operationType) {
		Transaction transaction = new Transaction();
		transaction.setUsername(userById.getName());
		transaction.setUserid(userById.getId());
		transaction.setAmount(amount);
		transaction.setDatetime(new Date());
		transaction.setStatus(status.getValue());
		transaction.setOperationType(operationType.getValue());
		return transaction;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

}
