package ua.ishchenko.rest.factories.impl;

import ua.ishchenko.common.entities.Wallet;
import ua.ishchenko.rest.entities.DWallet;
import ua.ishchenko.rest.factories.DWalletFactory;

/**
 * Operates with convertation between DWallet and Wallet entities
 * 
 * @author Jaros
 *
 */
public class DWalletFactoryImpl implements DWalletFactory {

	@Override
	public Wallet convertToWallet(DWallet dWallet) {
		Wallet wallet = new Wallet();
		wallet.setId(dWallet.getId());
		wallet.setBalance(dWallet.getBalance());
		return wallet;
	}

	@Override
	public DWallet convertToDWallet(Wallet wallet) {
		DWallet dWallet = new DWallet();
		dWallet.setId(wallet.getId());
		dWallet.setBalance(wallet.getBalance());
		return dWallet;
	}
}
