package ua.ishchenko.rest.factories.impl;

import java.util.ArrayList;
import java.util.List;

import ua.ishchenko.common.wsbeans.Transaction;
import ua.ishchenko.rest.beans.DTransaction;
import ua.ishchenko.rest.factories.DTransacactionFactory;

public class DTransactionFactoryImpl implements DTransacactionFactory {

	@Override
	public Transaction convertToTransaction(DTransaction dTransaction) {
		Transaction trns = new Transaction();
		trns.setId(dTransaction.getId());
		trns.setUserid(dTransaction.getUserid());
		trns.setUsername(dTransaction.getUsername());
		trns.setStatus(dTransaction.getStatus());
		trns.setOperationType(dTransaction.getOperationType());
		trns.setDatetime(dTransaction.getDatetime());
		trns.setAmount(dTransaction.getAmount());
		return trns;
	}

	@Override
	public DTransaction convertToDTransaction(Transaction trns) {
		DTransaction dTransaction = new DTransaction();
		dTransaction.setId(trns.getId());
		dTransaction.setUserid(trns.getUserid());
		dTransaction.setUsername(trns.getUsername());
		dTransaction.setStatus(trns.getStatus());
		dTransaction.setOperationType(trns.getOperationType());
		dTransaction.setDatetime(trns.getDatetime());
		dTransaction.setAmount(trns.getAmount());
		return dTransaction;
	}

	@Override
	public List<Transaction> convertToTransactionList(
			List<DTransaction> dTransactions) {
		if (dTransactions == null)
			return null;
		
		List<Transaction> list = new ArrayList<>();
		for (DTransaction dTransaction : dTransactions) {
			list.add(convertToTransaction(dTransaction));
		}
		return list;
	}
}
