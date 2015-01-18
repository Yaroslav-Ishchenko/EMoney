package ua.ishchenko.rest.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
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
import org.springframework.transaction.annotation.Transactional;

import ua.ishchenko.rest.dao.TransactionDao;
import ua.ishchenko.rest.entities.Transaction;

@Component
@Path("/transactions")
@Transactional()
public class TransactionRestService {
	/**
	 * 
	 * Service class that handles REST requests related to operations on
	 * transactions
	 * O
	 * @author jaros
	 * 
	 */
	@Autowired
	private TransactionDao transactionDao;

	/************************************ READ ************************************/
	/**
	 * Returns a resource (transaction) from the database
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

		Transaction transactionById = transactionDao.getTransactionById(Long
				.valueOf(id));

		if (transactionById != null) {
			return Response.status(Response.Status.OK).entity(transactionById)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS").build();
		} else {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("The transaction with the id " + id
							+ " does not exist").build();
		}
	}

	/************************************ READ ************************************/
	/**
	 * Returns all resources (transactions) from the database
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Transaction> getTransactions() throws JsonGenerationException,
			JsonMappingException, IOException {

		List<Transaction> transactions = transactionDao.getTransactions();

		return transactions;
	}

	@GET
	@Path("/ordered")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Transaction> getTransactionsByDateWithOrder(
			@QueryParam("userid") Long userid,
			@QueryParam("startDate") Long startDateNumeric,
			@QueryParam("endDate") Long endDateNumeric,
			@QueryParam("order") Integer order) throws JsonGenerationException,
			JsonMappingException, IOException {

		Date startDate=null;
		Date endDate=null;
		if (startDateNumeric != null) {
			startDate = new Date(startDateNumeric);
		}
		if (endDateNumeric != null) {
			startDate = new Date(startDateNumeric);
		} 
		
		List<Transaction> transactions = transactionDao
				.getTransactionsByTimeRangeCriteria(userid,startDate, endDate, order);

		return transactions;
	}
	// view transaction per user(or for all users)
	// for period (from/to)
	// sort by time,
	// sort by transaction amount,
	// sort by and its status
}
