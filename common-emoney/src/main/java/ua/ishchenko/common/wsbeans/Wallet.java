package ua.ishchenko.common.wsbeans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;

/**
 * Wallet entity
 * 
 * @author jaros
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wallet")
@XmlRootElement(name = "wallet")
public class Wallet implements Serializable {

	/** id of the wallet */
    @XmlElement(name = "id")
    @Json(name = "id")
    @JsonProperty("id")
	private Long id;

	/** balance of the wallet */
    @XmlElement(name = "balance")
    @Json(name = "balance")
    @JsonProperty("balance")
	private Long balance;

	public Wallet() {
		balance = 0L;
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
