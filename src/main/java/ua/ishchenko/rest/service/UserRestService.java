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
import org.springframework.transaction.annotation.Transactional;

import ua.ishchenko.common.entities.User;
import ua.ishchenko.common.enumerations.EOperationType;
import ua.ishchenko.common.enumerations.ETransactionStatus;
import ua.ishchenko.rest.dao.TransactionDao;
import ua.ishchenko.rest.dao.UserDao;
import ua.ishchenko.rest.entities.DTransaction;
import ua.ishchenko.rest.entities.DUser;
import ua.ishchenko.rest.exceptions.NegativeBalanceException;
import ua.ishchenko.rest.factories.DTransacactionFactory;
import ua.ishchenko.rest.factories.DUserFactory;
import ua.ishchenko.rest.factories.DWalletFactory;

/**
 * 
 * Service class that handles REST requests related to operations with users
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

	@Autowired
	private DWalletFactory dWalletFactory;

	@Autowired
	private DTransacactionFactory dTransacactionFactory;

	@Autowired
	private DUserFactory dUserFactory;

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
	public Response createUser(User user) {
		DUser dUser = dUserFactory.convertToDUser(user);
		userDao.createDUser(dUser);

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
			DUser dUser = dUserFactory.convertToDUser(user);
			userDao.createDUser(dUser);
		}

		return Response.status(204).build();
	}

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
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findById(@PathParam("id") Long id)
			throws JsonGenerationException, JsonMappingException, IOException {

		DUser dUserById = userDao.getDUserById(Long.valueOf(id));

		if (dUserById != null) {
			User userById = dUserFactory.convertToUser(dUserById);
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

		List<DUser> users = userDao.getDUsers();

		return dUserFactory.convertToListUser(users);
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
		DUser dUser = dUserFactory.convertToDUser(user);
		if (dUser.getId() != null) {
			userDao.updateDUser(dUser);
			status = Response.Status.OK; // OK
			message = "User has been updated";
		} else if (userCanBeCreated(dUser)) {
			userDao.createDUser(dUser);
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

	private boolean userCanBeCreated(DUser user) {
		return user.getName() != null;
	}

	/************************************ DELETE ************************************/
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response deleteUserById(@PathParam("id") Long id) {
		if (userDao.deleteDUserById(id) == 1) {
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
		userDao.deleteDUsers();
		return Response.status(Response.Status.OK)
				.entity("All users have been successfully removed").build();
	}

	@Path("{userid}/withdraw")
	@POST
	@Transactional
	public Response withdraw(@PathParam("userid") Long userid,
			@QueryParam("amount") Long withdrawAmount) {
		DUser userById = userDao.getDUserById(userid);

		if (userById != null) {
			ETransactionStatus status = null;

			try {
				userById.withdraw(withdrawAmount);
				userDao.updateDUser(userById);
				status = ETransactionStatus.Success;
			} catch (NegativeBalanceException ex) {
				status = ETransactionStatus.Failed;
			}
			DTransaction transaction = createTransaction(userById,
					withdrawAmount, status, EOperationType.Withdraw);
			transactionDao.createDTransaction(transaction);
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
		DUser userById = userDao.getDUserById(userid);
		if (userById != null) {
			ETransactionStatus status = null;
			try {
				userById.deposit(depositAmount);
				userDao.updateDUser(userById);
				status = ETransactionStatus.Success;
			} catch (NegativeBalanceException ex) {
				status = ETransactionStatus.Failed;
			}
			DTransaction transaction = createTransaction(userById,
					depositAmount, status, EOperationType.Withdraw);
			transactionDao.createDTransaction(transaction);
			return Response
					.status(Response.Status.OK)
					.entity(depositAmount + "deposited to "
							+ userById.getName()).build();
		} else {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("User with the id " + userid
							+ " is not present in the database").build();
		}
	}

	private DTransaction createTransaction(DUser userById, Long amount,
			ETransactionStatus status, EOperationType operationType) {
		DTransaction transaction = new DTransaction();
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

	public void setdWalletFactory(DWalletFactory dWalletFactory) {
		this.dWalletFactory = dWalletFactory;
	}

	public void setdTransacactionFactory(
			DTransacactionFactory dTransacactionFactory) {
		this.dTransacactionFactory = dTransacactionFactory;
	}

	public void setdUserFactory(DUserFactory dUserFactory) {
		this.dUserFactory = dUserFactory;
	}
}
