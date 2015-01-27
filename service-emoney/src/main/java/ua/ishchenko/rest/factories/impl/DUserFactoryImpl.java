package ua.ishchenko.rest.factories.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.ishchenko.common.entities.User;
import ua.ishchenko.common.entities.Wallet;
import ua.ishchenko.rest.entities.DUser;
import ua.ishchenko.rest.entities.DWallet;
import ua.ishchenko.rest.factories.DUserFactory;
import ua.ishchenko.rest.factories.DWalletFactory;

/**
 * Operates with convertation between DUser and User entities
 * 
 * @author Jaros
 *
 */
public class DUserFactoryImpl implements DUserFactory {
	@Autowired
	private DWalletFactory dWalletFactory;

	@Override
	public User convertToUser(DUser dUser) {
		User user = new User();
		user.setId(dUser.getId());
		user.setName(dUser.getName());
		Wallet wallet = dWalletFactory.convertToWallet(dUser.getDWallet());
		user.setWallet(wallet);
		user.setCreatedDate(dUser.getCreatedDate());
		return user;

	}

	@Override
	public DUser convertToDUser(User user) {
		DUser dUser = new DUser();
		dUser.setId(user.getId());
		dUser.setName(user.getName());
		DWallet wallet = dWalletFactory.convertToDWallet(user.getWallet());
		dUser.setDWallet(wallet);
		dUser.setCreatedDate(user.getCreatedDate());
		return dUser;

	}

	@Override
	public List<User> convertToListUser(List<DUser> dUsers) {
		List<User> list = new ArrayList<>();
		for (DUser dUser : dUsers) {
			list.add(convertToUser(dUser));
		}
		return list;
	}
}
