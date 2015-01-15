package ua.ishchenko.rest.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.transaction.annotation.Transactional;

import ua.ishchenko.rest.enumerations.ETransactionStatus;
import ua.ishchenko.rest.service.helper.CustomJsonDateDeserializer;
import ua.ishchenko.rest.service.helper.CustomJsonDateSerializer;

/**
 * Transaction entity
 * 
 * @author jaros
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(User.class)
@Entity
@Transactional
@Table(name = "transactions", schema = "HR")
public class Transaction {
	private static final long serialVersionUID = -8039623196077737053L;
	
	/** id of the Transaction */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="transaction_sequence") 
	@SequenceGenerator(name="transaction_sequence", sequenceName = "transactions_sequence")
	@Column(name = "id")
	Long id;
	@Column(name = "userid")
	Long userid;
	@Column(name = "username")
	String username;
	@Column(name = "amount")
	Long amount;
	@Column(name = "created_date")
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)	
	Date datetime;
	@Column(name = "status")
	int status;
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
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	

}
