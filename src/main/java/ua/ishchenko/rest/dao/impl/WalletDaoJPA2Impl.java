package ua.ishchenko.rest.dao.impl;

/**
 * 
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ua.ishchenko.rest.dao.WalletDao;
import ua.ishchenko.rest.entities.DWallet;

public class WalletDaoJPA2Impl implements WalletDao {

	@PersistenceContext(unitName = "emoneyRestPersistence")
	private EntityManager entityManager;

	@Override
	public List<DWallet> getDWallets() {

		String qlString = "SELECT p FROM DWallet p";
		TypedQuery<DWallet> query = entityManager.createQuery(qlString,
				DWallet.class);

		return query.getResultList();
	}

	@Override
	public void deleteDWallets() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE wallets");
		query.executeUpdate();
	}

	@Override
	public DWallet getDWalletById(Long id) {

		try {
			String qlString = "SELECT p FROM DWallet p WHERE p.id = ?1";
			TypedQuery<DWallet> query = entityManager.createQuery(qlString,
					DWallet.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long deleteDWalletById(Long id) {

		DWallet dWallet = entityManager.find(DWallet.class, id);
		entityManager.remove(dWallet);
		
		return 1L;
	}

	@Override
	public Long createDWallet(DWallet dWallet) {

		entityManager.persist(dWallet);
		entityManager.flush();// force insert to receive the id of the dWallet

		return dWallet.getId();
	}

	@Override
	public int updateDWallet(DWallet dWallet) {

		entityManager.merge(dWallet);

		return 1;
	}

}
