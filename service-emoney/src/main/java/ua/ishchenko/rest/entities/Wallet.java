package ua.ishchenko.rest.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wallet entity
 * 
 * @author jaros
 */
@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name = "wallets", catalog = "emoney_store_db")
public class Wallet implements Serializable{
	private static final long serialVersionUID = -8042686692101387623L;
	
	/** id of the wallet */
	@Id
	@GeneratedValue
	@Column(name = "wallet_id")
	private Long id;
	
	/** balance of the wallet*/
	@Column(name = "balance")
	private Long balance;

	public Wallet() {

	}

	public void withdraw(long amount) {
		balance -= amount;
	}

	public void deposit(long amount) {
		balance += amount;
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

}
