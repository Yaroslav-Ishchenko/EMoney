package ua.ishchenko.common.wsbeans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;


/**
 * User entity
 * 
 * @author jaros
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = { "id", "name","createdDate", "wallet" })
@XmlRootElement(name = "user")
public class User implements Serializable {

	/** id of the user */
	@XmlElement(name = "id")
	@Json(name = "id")
	@JsonProperty("id")
	private Long id;

	/** name of the user */
	@XmlElement(name = "name")
	@Json(name = "name")
	@JsonProperty("name")
	private String name;

	/** creation date */
	@XmlElement(name = "createdDate")
	@Json(name = "createdDate")
	@JsonProperty("createdDate")
	private Long createdDate;
	
	
	@XmlElement(name = "wallet")
	@Json(name = "wallet")
	@JsonProperty("wallet")
	private Wallet wallet;

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public User() {
		this(null);
	}

	public User(String name) {
		this.name = name;
		this.createdDate = new Date().getTime();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return new Date(createdDate);
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}