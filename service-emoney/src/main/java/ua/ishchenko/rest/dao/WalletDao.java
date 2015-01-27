package ua.ishchenko.rest.dao;

import java.util.List;
import ua.ishchenko.rest.entities.Wallet;

public interface WalletDao{
	public List<Wallet> getWallets();
	
	/** 
	 * removes all Wallets 
	 * 
	 */
	public void deleteWallets();
	/**
	 * Returns a Wallet given its id
	 * 
	 * @param id
	 * @return Wallet
	 */
	public Wallet getWalletById(Long id);

	public Long deleteWalletById(Long id);

	public Long createWallet(Wallet wallet);

	public int updateWallet(Wallet wallet);

}