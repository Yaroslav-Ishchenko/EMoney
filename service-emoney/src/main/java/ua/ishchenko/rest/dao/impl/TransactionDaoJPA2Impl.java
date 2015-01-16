package ua.ishchenko.rest.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ua.ishchenko.rest.dao.TransactionDao;
import ua.ishchenko.rest.entities.Transaction;

public class TransactionDaoJPA2Impl implements TransactionDao {
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
		throw new UnsupportedOperationException(
				"The scoope of transaction can't be deleted");

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
		throw new UnsupportedOperationException(
				"Any transaction can't be deleted");
	}

	@Override
	public Long createTransaction(Transaction transaction) {
		entityManager.persist(transaction);
		entityManager.flush();// force insert to receive the id of the
								// transaction

		return transaction.getId();
	}

	@Override
	public int updateTransaction(Transaction transaction) {
		throw new UnsupportedOperationException(
				"Transactions are not updateable");
	}
/**
 * The start and end dates can be placed in any order(but not recommended), the search always starts from less date and goes till bigger
 * @param startDate the start date to look from for searching period, can be null if so than the oldest records will be chosen till the endPeriod if specified 
 * @param endDate the end date till which the searching will look
 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> getTransactionsByTimeRangeCriteria(Date startDate,
			Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		if(calendar.before(endDate))
		{
			Date tmp= startDate;
			startDate = endDate;
			endDate = tmp;
		}
		List<Transaction> list=null;

		String queryStr = null;
		if (startDate != null && endDate != null) {
			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();
			calStart.setTime(startDate);
			calEnd.setTime(endDate);
			queryStr = "Select trans from Transaction trans where day(trans.dateTime) BETWEEN :start_date and :end_date "
					+ "and month(trans.dateTime) BETWEEN :start_month and :end_month "
					+ "and year(trans.dateTime) BETWEEN :start_year and :end_year";
			Query query = entityManager.createQuery(queryStr);
			query.setParameter("start_date", calStart.get(Calendar.DATE));
			query.setParameter("start_month", calStart.get(Calendar.MONTH) + 1);
			query.setParameter("start_year", calStart.get(Calendar.YEAR));
			query.setParameter("end_date", calEnd.get(Calendar.DATE));
			query.setParameter("end_month", calEnd.get(Calendar.MONTH) + 1);
			query.setParameter("end_year", calEnd.get(Calendar.YEAR));
			list = query.getResultList();
		} else {
			Date queryDate = null;
			String sign = null;
			if (endDate != null) {
				queryDate = endDate;
				sign = "<=";

			} else if (startDate != null) {
				queryDate=startDate;
				sign = ">=";
			} else {
				queryDate = new Date();
				sign = "=";
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			queryStr = "Select trans from Transaction trans where day(trans.dateTime) "
					+ sign + " :date"
					+ " and month(trans.dateTime) " + sign
					+ " :month and year(trans.dateTime) " + sign + " :year";
			Query query = entityManager.createQuery(queryStr);
			query.setParameter("date", cal.get(Calendar.DATE));
			query.setParameter("month", cal.get(Calendar.MONTH) + 1);
			query.setParameter("year", cal.get(Calendar.YEAR));
			list = query.getResultList();

		}

		return list;
	}
}
