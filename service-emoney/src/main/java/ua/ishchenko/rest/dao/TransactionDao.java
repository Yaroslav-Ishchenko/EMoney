package ua.ishchenko.rest.dao;

import java.util.Date;
import java.util.List;

import ua.ishchenko.rest.entities.Transaction;

public interface TransactionDao {
public List<Transaction> getTransactions();
	
	/** 
	 * removes all Transaction 
	 * 
	 */
	public void deleteTransactions();
	/**
	 * Returns a Transaction given its id
	 * 
	 * @param id
	 * @return Transaction
	 */
	public Transaction getTransactionById(Long id);

	public Long deleteTransactionById(Long id);

	public Long createTransaction(Transaction transaction);

	public int updateTransaction(Transaction transaction);

	public List<Transaction> getTransactionsByTimeRangeCriteria(Date date,
			Date date2, int order);
}
