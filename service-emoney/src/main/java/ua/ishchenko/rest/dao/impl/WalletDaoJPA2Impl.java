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
import ua.ishchenko.rest.entities.User;
import ua.ishchenko.rest.entities.Wallet;

public class WalletDaoJPA2Impl implements WalletDao {

	@PersistenceContext(unitName = "emoneyRestPersistence")
	private EntityManager entityManager;

	@Override
	public List<Wallet> getWallets() {

		String qlString = "SELECT p FROM Wallet p";
		TypedQuery<Wallet> query = entityManager.createQuery(qlString,
				Wallet.class);

		return query.getResultList();
	}

	@Override
	public void deleteWallets() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE wallets");
		query.executeUpdate();
	}

	@Override
	public Wallet getWalletById(Long id) {

		try {
			String qlString = "SELECT p FROM Wallet p WHERE p.id = ?1";
			TypedQuery<Wallet> query = entityManager.createQuery(qlString,
					Wallet.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long deleteWalletById(Long id) {

		Wallet wallet = entityManager.find(Wallet.class, id);
		entityManager.remove(wallet);
		
		return 1L;
	}

	@Override
	public Long createWallet(Wallet wallet) {

		entityManager.persist(wallet);
		entityManager.flush();// force insert to receive the id of the wallet

		return wallet.getId();
	}

	@Override
	public int updateWallet(Wallet wallet) {

		entityManager.merge(wallet);

		return 1;
	}

}
