package ua.ishchenko.rest.factories;

import java.util.List;

import ua.ishchenko.common.entities.User;
import ua.ishchenko.rest.entities.DUser;

/**
 * Operates with convertation between DUser and User entities
 * @author Jaros
 *
 */
public interface DUserFactory {
	
	public User convertToUser(DUser dUser);

	public DUser convertToDUser(User user);

	public List<User> convertToListUser(List<DUser> users);
}
