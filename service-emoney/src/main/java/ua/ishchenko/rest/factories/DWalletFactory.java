package ua.ishchenko.rest.factories;

import ua.ishchenko.common.entities.Wallet;
import ua.ishchenko.rest.entities.DWallet;

/**
 * Operates with convertation between DWallet and Wallet entities
 * 
 * @author Jaros
 *
 */
public interface DWalletFactory {

	public Wallet convertToWallet(DWallet dWallet);

	public DWallet convertToDWallet(Wallet wallet);
}
