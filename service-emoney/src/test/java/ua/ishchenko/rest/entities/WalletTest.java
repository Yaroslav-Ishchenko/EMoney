package ua.ishchenko.rest.entities;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WalletTest {
	
	Wallet wallet;
	
	@Before
	public void testWitdraw()
	{
		wallet = new Wallet();
	}
	@Test
	public void testDepositAction()
	{
		wallet.setBalance(1000L);
		wallet.deposit(200L);
		assertEquals((Long)1200L, wallet.getBalance());
	}
	
	@Test
	public void testWithdrawalAction()
	{
		wallet.setBalance(1000L);
		wallet.withdraw(200L);
		assertEquals((Long)800L, wallet.getBalance());
	}
}
