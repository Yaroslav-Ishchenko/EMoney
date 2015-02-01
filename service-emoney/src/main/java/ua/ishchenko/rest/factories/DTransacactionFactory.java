package ua.ishchenko.rest.factories;

import java.util.List;

import ua.ishchenko.common.wsbeans.Transaction;
import ua.ishchenko.rest.beans.DTransaction;

/**
 * Operates with convertation between DTransaction and Transaction entities
 * @author Jaros
 *
 */
public interface DTransacactionFactory {
	
	public Transaction convertToTransaction(DTransaction dTransaction);

	public DTransaction convertToDTransaction(Transaction trns);

	public List<Transaction> convertToTransactionList(
			List<DTransaction> dTransactions);
}
