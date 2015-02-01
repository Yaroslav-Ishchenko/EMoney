package ua.ishchenko.rest.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ua.ishchenko.rest.exceptions.NegativeBalanceException;


/**
 *  Wallet Data base entity
 * 
 * @author jaros
 */
@Entity
@Table(name = "wallets" , schema = "HR")
public class DWallet implements Serializable{
	private static final long serialVersionUID = -8042686692101387623L;
	
	/** id of the wallet */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="wallet_sequence") 
	@SequenceGenerator(name="wallet_sequence", sequenceName = "wallets_sequence")
	@Column(name = "wallet_id")
	private Long id;
	
	/** balance of the wallet*/
	@Column(name = "balance")
	private Long balance;

	public DWallet() {
		balance=0L;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
	return "Wallet [number=" + id + ", balance=" + balance + "]";
	}

}
