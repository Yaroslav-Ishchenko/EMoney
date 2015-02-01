package ua.ishchenko.rest.factories;

import ua.ishchenko.common.wsbeans.Wallet;
import ua.ishchenko.rest.beans.DWallet;

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
