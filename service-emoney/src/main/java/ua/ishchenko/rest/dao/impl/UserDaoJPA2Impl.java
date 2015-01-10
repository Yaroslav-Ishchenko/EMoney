package ua.ishchenko.rest.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ua.ishchenko.rest.dao.UserDao;
import ua.ishchenko.rest.entities.User;

public class UserDaoJPA2Impl implements UserDao {

	@PersistenceContext(unitName = "emoneyRestPersistence")
	private EntityManager entityManager;

	@Override
	public List<User> getUsers() {

		String qlString = "SELECT p FROM User p";
		TypedQuery<User> query = entityManager
				.createQuery(qlString, User.class);

		return query.getResultList();
	}

	@Override
	public void deleteUsers() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE users");
		query.executeUpdate();
	}

	@Override
	public User getUserById(Long id) {
		try {
			String qlString = "SELECT p FROM User p WHERE p.id = ?1";
			TypedQuery<User> query = entityManager.createQuery(qlString,
					User.class);
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long deleteUserById(Long id) {
		User wallet = entityManager.find(User.class, id);
		entityManager.remove(wallet);

		return 1L;
	}

	@Override
	public Long createUser(User user) {

		entityManager.persist(user);
		entityManager.flush();// force insert to receive the id of the wallet

		return user.getId();
	}

	@Override
	public int updateUser(User user) {
		entityManager.merge(user);

		return 1;
	}

}
