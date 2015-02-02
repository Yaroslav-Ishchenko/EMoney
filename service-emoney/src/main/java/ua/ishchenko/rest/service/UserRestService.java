package ua.ishchenko.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.servlet.http.HttpServletResponse;

import ua.ishchenko.common.wsbeans.WSResultCode;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.ishchenko.common.wsbeans.User;
import ua.ishchenko.common.enumerations.EOperationType;
import ua.ishchenko.common.enumerations.ETransactionStatus;
import ua.ishchenko.common.service.IUserRestService;
import ua.ishchenko.rest.beans.DTransaction;
import ua.ishchenko.rest.beans.DUser;
import ua.ishchenko.rest.dao.TransactionDao;
import ua.ishchenko.rest.dao.UserDao;
import ua.ishchenko.rest.exceptions.NegativeBalanceException;
import ua.ishchenko.rest.factories.DTransacactionFactory;
import ua.ishchenko.rest.factories.DUserFactory;
import ua.ishchenko.rest.factories.DWalletFactory;

/**
 * 
 * Service class that handles REST requests related to operations with users
 * described in IUserRestService
 * 
 * @author jaros
 * 
 */
@Component
@Transactional()
public class UserRestService implements IUserRestService {

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
	@Context
	private HttpServletResponse response;

	private static Response onOptionsRequest;
	static {
		onOptionsRequest = Response
				.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods",
						"POST, GET, UPDATE, OPTIONS")
				.header("Access-Control-Allow-Headers",
						"x-http-method-override")
						.build();
		//header("Content-Type", "application/json")
	}

	@Override
	@Transactional
	public WSResultCode createUser(User user) {
		WSResultCode result = new WSResultCode();
		DUser dUser = dUserFactory.convertToDUser(user);
		userDao.createDUser(dUser);

		result.setCode(Response.Status.CREATED.getStatusCode());
		result.setDescription(Response.Status.CREATED.name());
		result.setSummary("A new User/resource has been created");
		return result;
	}

	@Override
	@Transactional
	public WSResultCode createUsers(List<User> users) {
		WSResultCode result = new WSResultCode();
		try {
			for (User user : users) {
				DUser dUser = dUserFactory.convertToDUser(user);
				userDao.createDUser(dUser);
			}
			result.setCode(Response.Status.CREATED.getStatusCode());
			result.setDescription(Response.Status.CREATED.name());
			result.setSummary("List of users is created");
		} catch (Exception ex) {
			result.setCode(Response.Status.INTERNAL_SERVER_ERROR
					.getStatusCode());
			result.setDescription(Response.Status.INTERNAL_SERVER_ERROR.name());
			result.setSummary(ex.getMessage());
		}

		return result;
	}

	@Override
	public User findById(Long id) throws JsonGenerationException,
			JsonMappingException, IOException {

		DUser dUserById = userDao.getDUserById(Long.valueOf(id));

		if (dUserById != null) {
			User userById = dUserFactory.convertToUser(dUserById);
			return userById;
		} else
			return null;
	}

	@Override
	public List<User> getUsers() throws JsonGenerationException,
			JsonMappingException, IOException {
		// response.addHeader("Access-Control-Allow-Origin", "*");
		// response.addHeader("Access-Control-Allow-Methods",
		// "POST, GET, UPDATE, OPTIONS");
		// response.addHeader("Access-Control-Allow-Headers",
		// "x-http-method-override");
		List<DUser> users = userDao.getDUsers();

		return dUserFactory.convertToListUser(users);
	}

	@Override
	@Transactional
	public WSResultCode updateUserById(User user) {
		String message;
		Status status;
		WSResultCode result = new WSResultCode();
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

		result.setCode(status.getStatusCode());
		result.setDescription(status.name());
		result.setSummary(message);
		return null;
	}

	private boolean userCanBeCreated(DUser user) {
		return user.getName() != null;
	}

	@Override
	@Transactional
	public WSResultCode deleteUserById(Long id) {
		WSResultCode result = new WSResultCode();
		if (userDao.deleteDUserById(id) == 1) {
			result.setCode(Response.Status.CREATED.getStatusCode());
			result.setDescription(Response.Status.CREATED.name());
			result.setSummary("User with the id " + id + " is deleted");
		} else {
			result.setCode(Response.Status.NOT_FOUND.getStatusCode());
			result.setDescription(Response.Status.NOT_FOUND.name());
			result.setSummary("User with the id " + id
					+ " is not present in the database");
		}

		return result;
	}

	@Override
	@Transactional
	public WSResultCode deleteUsers() {
		userDao.deleteDUsers();
		WSResultCode result = new WSResultCode();
		result.setCode(Response.Status.OK.getStatusCode());
		result.setDescription(Response.Status.OK.name());
		result.setSummary("All users have been successfully removed");
		return result;
	}

	@Override
	@Transactional
	public WSResultCode withdraw(Long userid, Long withdrawAmount) {
		DUser userById = userDao.getDUserById(userid);
		WSResultCode result = new WSResultCode();
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

			result.setCode(Response.Status.OK.getStatusCode());
			result.setDescription(Response.Status.OK.name());
			result.setSummary(withdrawAmount + " withdrawn from "
					+ userById.getName());
		} else {
			result.setCode(Response.Status.NOT_FOUND.getStatusCode());
			result.setDescription(Response.Status.NOT_FOUND.name());
			result.setSummary("User with the id " + userid
					+ " is not present in the database");
		}
		return result;
	}

	@Override
	@Transactional
	public WSResultCode deposit(Long userid, Long depositAmount) {
		DUser userById = userDao.getDUserById(userid);
		WSResultCode result = new WSResultCode();
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
			result.setCode(Response.Status.OK.getStatusCode());
			result.setDescription(Response.Status.OK.name());
			result.setSummary(depositAmount + "deposited to "
					+ userById.getName());
		} else {
			result.setCode(Response.Status.NOT_FOUND.getStatusCode());
			result.setDescription(Response.Status.NOT_FOUND.name());
			result.setSummary("User with the id " + userid
					+ " is not present in the database");

		}
		return result;
	}

	@Override
	public Response usersCORSRequest() {
		return onOptionsRequest;

	}

	@Override
	public Response idCORSRequest() {
		return onOptionsRequest;

	}

	@Override
	public Response listCORSRequest() {
		return onOptionsRequest;

	}

	@Override
	public Response depositWithdrawCORSRequest() {
		return onOptionsRequest;

	}

	public DTransaction createTransaction(DUser userById, Long amount,
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
