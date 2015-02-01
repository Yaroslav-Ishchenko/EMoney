package ua.ishchenko.rest.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.ishchenko.common.wsbeans.Transaction;
import ua.ishchenko.common.wsbeans.WSResultCode;
import ua.ishchenko.common.service.ITransactionRestService;
import ua.ishchenko.rest.beans.DTransaction;
import ua.ishchenko.rest.dao.TransactionDao;
import ua.ishchenko.rest.dao.UserDao;
import ua.ishchenko.rest.factories.DTransacactionFactory;

@Component
@Transactional
public class TransactionRestService implements ITransactionRestService {
	/**
	 * 
	 * Service class that handles REST requests related to operations on
	 * transactions O
	 * 
	 * @author jaros
	 * 
	 */
	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private DTransacactionFactory dTransacactionFactory;

	@Override
	@Transactional
	public Transaction findById(Long id) {
		WSResultCode result = new WSResultCode();

		DTransaction transactionById = transactionDao.getDTransactionById(Long
				.valueOf(id));

		if (transactionById != null) {
			result.setCode(Response.Status.OK.getStatusCode());
			result.setDescription(Response.Status.OK.name());
			result.setSummary("All users have been successfully removed");
			return dTransacactionFactory.convertToTransaction(transactionById);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public List<Transaction> getTransactions() {

		List<Transaction> transactions = dTransacactionFactory
				.convertToTransactionList(transactionDao.getDTransactions());
		return transactions;
	}

	@Override
	@Transactional
	public List<Transaction> getTransactionsByDateWithOrder(Long userid,
			Long startDateNumeric, Long endDateNumeric, Integer order) {

		Date startDate = null;
		Date endDate = null;
		if (startDateNumeric != null) {
			startDate = new Date(startDateNumeric);
		}
		if (endDateNumeric != null) {
			startDate = new Date(startDateNumeric);
		}

		List<DTransaction> dTransactions = transactionDao
				.getDTransactionsByTimeRangeCriteria(userid, startDate,
						endDate, order);

		return dTransacactionFactory.convertToTransactionList(dTransactions);
	}

	public void setdTransacactionFactory(
			DTransacactionFactory dTransacactionFactory) {
		this.dTransacactionFactory = dTransacactionFactory;
	}
}
