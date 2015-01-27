package ua.ishchenko.rest.entities;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

	final static Long BALANCE = 1000L;
	final static String name = "Jaros";
	User user;

	@Before
	public void setUp() {
		user = new User(name);
	}

	@Test
	public void testUserName() {
		assertEquals(name, user.getName());
	}

	@Test
	public void testBalance() {
		Wallet wallet = new Wallet();
		wallet.setBalance(BALANCE);
		user.setWallet(wallet);
		assertEquals(BALANCE, user.getWallet().getBalance());
	}
}
