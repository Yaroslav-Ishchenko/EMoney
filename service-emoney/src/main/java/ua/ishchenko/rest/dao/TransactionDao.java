package ua.ishchenko.rest.dao;

import java.util.Date;
import java.util.List;

import ua.ishchenko.rest.beans.DTransaction;


public interface TransactionDao {
public List<DTransaction> getDTransactions();
	
	/** 
	 * removes all DTransactions
	 * 
	 */
	public void deleteTransactions();
	/**
	 * Returns a DTransaction given its id
	 * 
	 * @param id
	 * @return DTransaction
	 */
	public DTransaction getDTransactionById(Long id);

	public Long deleteDTransactionById(Long id);

	public Long createDTransaction(DTransaction dTransaction);

	public int updateDTransaction(DTransaction dTransaction);

	public List<DTransaction> getDTransactionsByTimeRangeCriteria(Long userid, Date date,
			Date date2, Integer order);
}
