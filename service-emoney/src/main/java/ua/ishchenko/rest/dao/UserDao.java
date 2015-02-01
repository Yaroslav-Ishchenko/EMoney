package ua.ishchenko.rest.dao;

import java.util.List;

import ua.ishchenko.rest.beans.DUser;

public interface UserDao{
	public List<DUser> getDUsers();
	
	/** 
	 * removes all Users 
	 * 
	 */
	public void deleteDUsers();
	/**
	 * Returns a User given its id
	 * 
	 * @param id
	 * @return User
	 */
	public DUser getDUserById(Long id);

	public Long deleteDUserById(Long id);

	public Long createDUser(DUser dUser);

	public int updateDUser(DUser dUser);

}