package ua.ishchenko.rest.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ua.ishchenko.rest.beans.DTransaction;
import ua.ishchenko.rest.dao.TransactionDao;
import ua.ishchenko.common.enumerations.ETransactionsOrderBY;

public class TransactionDaoJPA2Impl implements TransactionDao {
	@PersistenceContext(unitName = "emoneyRestPersistence")
	private EntityManager entityManager;

	@Override
	public List<DTransaction> getDTransactions() {
		String qlString = "SELECT p FROM DTransaction p";
		TypedQuery<DTransaction> query = entityManager.createQuery(qlString,
				DTransaction.class);

		return query.getResultList();
	}

	@Override
	public void deleteTransactions() {
		throw new UnsupportedOperationException(
				"The scoope of transaction can't be deleted");

	}

	@Override
	public DTransaction getDTransactionById(Long id) {
		try {
			String qlString = "SELECT p FROM DTransaction p WHERE p.id = ?1";
			TypedQuery<DTransaction> query = entityManager.createQuery(qlString,
					DTransaction.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long deleteDTransactionById(Long id) {
		throw new UnsupportedOperationException(
				"Any transaction can't be deleted");
	}

	@Override
	public Long createDTransaction(DTransaction transaction) {
		entityManager.persist(transaction);
		entityManager.flush();// force insert to receive the id of the
								// transaction

		return transaction.getId();
	}

	@Override
	public int updateDTransaction(DTransaction transaction) {
		throw new UnsupportedOperationException(
				"Transactions are not updateable");
	}

	/**
	 * The start AND end dates can be placed in any order(but not recommended),
	 * the search always starts FROM less date AND goes till bigger.
	 * 
	 * <b>Both can be null</b> if so than the response will have all the records
	 * for the current day in server's timezone
	 * 
	 * @param startDate
	 *            the start date to look FROM for searching period, can be null
	 *            if so than the oldest records will be chosen till the
	 *            endPeriod if specified
	 * @param endDate
	 *            the end date till which the searching will look
	 */
	@SuppressWarnings({ "unchecked"})
	@Override
	public List<DTransaction> getDTransactionsByTimeRangeCriteria(Long userid,
			Date startDate, Date endDate, Integer order) {
		if (startDate == null && endDate == null) {// if both null means that
													// intention is to get data
													// for current day
			startDate = new Date();
			endDate = new Date();
		}

		/*
		 * Trying to figure out which one from the dates is starting and which
		 * is ending
		 */
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		if (calendar.before(endDate)) {
			Date tmp = startDate;
			startDate = endDate;
			endDate = tmp;
		}

		List<DTransaction> list = null;// the list that is going to store the
										// search results

		ETransactionsOrderBY eOrder = ETransactionsOrderBY
				.getETransactionsOrderBY(order);// field to be ordered by AND
												// asc desc rules
		String orderBy = "";
		if (eOrder != null) {
			orderBy = " ORDER BY" + eOrder.toString();
		}
		String userSelQuery = "";
		if (userid != null) {
			userSelQuery = " trans.userid = '" + userid + "' AND ";
		}
		String queryStr = null;

		if (startDate != null && endDate != null) {// if both left and right
													// limitations exist
			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();
			calStart.setTime(startDate);
			calEnd.setTime(endDate);
			queryStr = "SELECT trans FROM DTransaction trans WHERE "+ userSelQuery +" day(trans.dateTime) BETWEEN :start_date AND :end_date "
					+ "AND month(trans.dateTime) BETWEEN :start_month AND :end_month "
					+ "AND year(trans.dateTime) BETWEEN :start_year AND :end_year"
					+ orderBy;
			Query query = entityManager.createQuery(queryStr);
			query.setParameter("start_date", calStart.get(Calendar.DATE));
			query.setParameter("start_month", calStart.get(Calendar.MONTH) + 1);
			query.setParameter("start_year", calStart.get(Calendar.YEAR));
			query.setParameter("end_date", calEnd.get(Calendar.DATE));
			query.setParameter("end_month", calEnd.get(Calendar.MONTH) + 1);
			query.setParameter("end_year", calEnd.get(Calendar.YEAR));
			list = query.getResultList();
		} else {// if limitation presents for single or any limits aren't
				// specified
			Date queryDate = null;
			String sign = null;
			if (endDate != null) {// if ending date limit is specified
				queryDate = endDate;
				sign = "<=";// select all that less or equal from the date

			} else if (startDate != null) {// if starting date limit is
											// specified
				queryDate = startDate;
				sign = ">=";// select all that bigger or equal from the date
			} else {// if no limit is specified
				queryDate = new Date();
				sign = "=";// select all that equal to the date
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(queryDate);
			queryStr = "SELECT trans FROM DTransaction trans WHERE "+ userSelQuery
					+ " day(trans.dateTime) "
					+ sign
					+ " :date"
					+ " AND month(trans.dateTime) "
					+ sign
					+ " :month AND year(trans.dateTime) "
					+ sign
					+ " :year"
					+ orderBy;
			Query query = entityManager.createQuery(queryStr);
			query.setParameter("date", cal.get(Calendar.DATE));
			query.setParameter("month", cal.get(Calendar.MONTH) + 1);
			query.setParameter("year", cal.get(Calendar.YEAR));
			list = query.getResultList();

		}

		return list;
	}
}
