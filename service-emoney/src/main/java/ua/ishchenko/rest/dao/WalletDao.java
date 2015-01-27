package ua.ishchenko.rest.dao;

import java.util.List;

import ua.ishchenko.rest.entities.DWallet;

public interface WalletDao{
	public List<DWallet> getDWallets();
	
	/** 
	 * removes all DWallets 
	 * 
	 */
	public void deleteDWallets();
	/**
	 * Returns a DWallet given its id
	 * 
	 * @param id
	 * @return DWallet
	 */
	public DWallet getDWalletById(Long id);

	public Long deleteDWalletById(Long id);

	public Long createDWallet(DWallet dWallet);

	public int updateDWallet(DWallet dWallet);

}