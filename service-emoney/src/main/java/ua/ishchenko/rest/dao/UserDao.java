package ua.ishchenko.rest.dao;

import java.util.List;
import ua.ishchenko.rest.entities.User;

public interface UserDao{
	public List<User> getUsers();
	
	/** 
	 * removes all Users 
	 * 
	 */
	public void deleteUsers();
	/**
	 * Returns a User given its id
	 * 
	 * @param id
	 * @return User
	 */
	public User getUserById(Long id);

	public Long deleteUserById(Long id);

	public Long createUser(User User);

	public int updateUser(User User);

}