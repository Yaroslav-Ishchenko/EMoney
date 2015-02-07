package ua.ishchenko.common.service;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import ua.ishchenko.common.wsbeans.Transaction;

@Path("/transactions")
public interface ITransactionRestService {

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
	Transaction findById(@PathParam("id") Long id) throws JsonGenerationException,
			JsonMappingException, IOException;

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
	List<Transaction> getTransactions() throws JsonGenerationException,
			JsonMappingException, IOException;

	@GET
	@Path("/ordered")
	@Produces({ MediaType.APPLICATION_JSON })
	List<Transaction> getTransactionsByDateWithOrder(
			@QueryParam("userid") Long userid,
			@QueryParam("startDate") Long startDateNumeric,
			@QueryParam("endDate") Long endDateNumeric,
			@QueryParam("order") Integer order) throws JsonGenerationException,
			JsonMappingException, IOException;

}
