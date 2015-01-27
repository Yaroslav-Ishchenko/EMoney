package ua.ishchenko.rest.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ua.ishchenko.rest.dao.UserDao;
import ua.ishchenko.rest.entities.DUser;

public class UserDaoJPA2Impl implements UserDao {

	@PersistenceContext(unitName = "emoneyRestPersistence")
	private EntityManager entityManager;

	@Override
	public List<DUser> getDUsers() {

		String qlString = "SELECT p FROM DUser p";
		TypedQuery<DUser> query = entityManager
				.createQuery(qlString, DUser.class);

		return query.getResultList();
	}

	@Override
	public void deleteDUsers() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE users");
		query.executeUpdate();
	}

	@Override
	public DUser getDUserById(Long id) {
		try {
			String qlString = "SELECT p FROM DUser p WHERE p.id = ?1";
			TypedQuery<DUser> query = entityManager.createQuery(qlString,
					DUser.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long deleteDUserById(Long id) {
		DUser wallet = entityManager.find(DUser.class, id);
		entityManager.remove(wallet);

		return 1L;
	}

	@Override
	public Long createDUser(DUser user) {

		entityManager.persist(user);
		entityManager.flush();// force insert to receive the id of the user

		return user.getId();
	}

	@Override
	public int updateDUser(DUser user) {
		entityManager.merge(user);

		return 1;
	}

}
