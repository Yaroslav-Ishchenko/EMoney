package ua.ishchenko.rest.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import ua.ishchenko.rest.exceptions.NegativeBalanceException;


@RunWith(BlockJUnit4ClassRunner.class)
public class UserTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	final static Long BALANCE = 1000L, DEPOSIT = 200L, WITHDRAW = 999L;
	final static String name = "Jaros";
	private DUser user;

	@Before
	public void setUp() {
		user = new DUser(name);
		user.setDWallet(new DWallet());
	}

	@Test
	public void testUserName() {
		assertEquals(name, user.getName());
	}

	@Test
	public void testBalance() {

		user.getDWallet().setBalance(BALANCE);
		assertEquals(BALANCE, user.getDWallet().getBalance());
	}

	@Test
	public void testDepositAction() {
		user.getDWallet().setBalance(BALANCE);
		user.deposit(DEPOSIT);
		assertEquals((Long) (BALANCE + DEPOSIT), user.getDWallet().getBalance());
	}

	@Test
	public void testWithdrawalAction() {
		user.getDWallet().setBalance(BALANCE);
		user.withdraw(WITHDRAW);
		assertEquals((Long) (BALANCE - WITHDRAW), user.getDWallet()
				.getBalance());
	}

	/**
	 * NegativeBalanceException should occur when user exceeded available amount
	 * of money
	 */
	@Test
	public void testWidrawSumBiggerThanBalance() {
		thrown.expect(NegativeBalanceException.class);
		user.getDWallet().setBalance(BALANCE);
		user.withdraw(BALANCE + 200L);
	}

	/**
	 * If user wants to make a deposit it must be positive number
	 */
	@Test
	public void testWithNegativeDeposit() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("The sum you are going to deposit can not be negative number");
		user.deposit(-200L);
	}

	/**
	 * Withdrawal amount should be negative number as user specify amount of
	 * money he wants to pay out from its account
	 */
	@Test
	public void testWithPositiveWithdrawal() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("The sum you are going to withdraw can not be negative number");
		user.withdraw(-200L);
	}

	// @Test(expected = java.lang.IllegalArgumentException.class)
	// public void testWithNegativeDeposit()
	// {
	// user.getDWallet().setBalance(BALANCE);
	// user.withdraw(BALANCE);
	// }
}
