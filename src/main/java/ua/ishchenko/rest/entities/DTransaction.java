package ua.ishchenko.rest.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.transaction.annotation.Transactional;

import ua.ishchenko.common.helper.CustomJsonDateDeserializer;
import ua.ishchenko.common.helper.CustomJsonDateSerializer;

/**
 * Transaction Data base entity
 * 
 * @author jaros
 */
@Entity
@Transactional
@Table(name = "transactions", schema = "HR")
public class DTransaction implements Serializable {
	private static final long serialVersionUID = -8039623196077737053L;

	/** id of the Transaction */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
	@SequenceGenerator(name = "transaction_sequence", sequenceName = "transactions_sequence")
	@Column(name = "id")
	private Long id;

	@Column(name = "userid")
	private Long userid;

	@Column(name = "user_name")
	private String username;

	@Column(name = "amount")
	private Long amount;

	@Column(name = "created_date")
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date dateTime;

	@Column(name = "status")
	private int status;

	@Column(name = "operation_type")
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
