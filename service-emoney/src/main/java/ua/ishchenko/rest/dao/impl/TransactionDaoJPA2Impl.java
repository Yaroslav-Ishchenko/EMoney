package ua.ishchenko.rest.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ua.ishchenko.rest.dao.TransactionDao;
import ua.ishchenko.rest.entities.Transaction;


public class TransactionDaoJPA2Impl implements TransactionDao{
	@PersistenceContext(unitName = "emoneyRestPersistence")
	private EntityManager entityManager;
	
	@Override
	public List<Transaction> getTransactions() {
		String qlString = "SELECT p FROM Transaction p";
		TypedQuery<Transaction> query = entityManager.createQuery(qlString,
				Transaction.class);

		return query.getResultList();
	}

	@Override
	public void deleteTransactions() {
		throw new UnsupportedOperationException("The scoope of transaction can't be deleted");
		
	}

	@Override
	public Transaction getTransactionById(Long id) {
		try {
			String qlString = "SELECT p FROM Transaction p WHERE p.id = ?1";
			TypedQuery<Transaction> query = entityManager.createQuery(qlString,
					Transaction.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long deleteTransactionById(Long id) {
		throw new UnsupportedOperationException("Any transaction can't be deleted");
	}

	@Override
	public Long createTransaction(Transaction transaction) {
		entityManager.persist(transaction);
		entityManager.flush();// force insert to receive the id of the transaction

		return transaction.getId();
	}

	@Override
	public int updateTransaction(Transaction transaction) {
		throw new UnsupportedOperationException("Transactions are not updateable");
	}

}
