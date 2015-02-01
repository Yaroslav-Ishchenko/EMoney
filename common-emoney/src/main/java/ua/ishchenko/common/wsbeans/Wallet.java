package ua.ishchenko.common.wsbeans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * Wallet entity
 * 
 * @author jaros
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(Wallet.class)
public class Wallet implements Serializable{
	private static final long serialVersionUID = -8042686692101387623L;
	
	/** id of the wallet */
	private Long id;
	
	/** balance of the wallet*/
	private Long balance;

	public Wallet() {
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
