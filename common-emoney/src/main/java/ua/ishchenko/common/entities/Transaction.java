package ua.ishchenko.common.entities;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Transaction entity
 * 
 * @author jaros
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(User.class)
public class Transaction implements Serializable {
	private static final long serialVersionUID = -8039623196077737053L;

	/** id of the Transaction */
	private Long id;

	private Long userid;

	private String username;

	private Long amount;

	private Date dateTime;

	private int status;

	private int operationType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getDatetime() {
		return dateTime;
	}

	public void setDatetime(Date datetime) {
		this.dateTime = datetime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setOperationType(int value) {
		this.operationType = value;
	}

	public int getOperationType() {
		return this.operationType;
	}

}
