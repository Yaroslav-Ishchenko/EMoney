package ua.ishchenko.rest.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.transaction.annotation.Transactional;

import ua.ishchenko.common.helper.CustomJsonDateDeserializer;
import ua.ishchenko.common.helper.CustomJsonDateSerializer;
import ua.ishchenko.rest.exceptions.NegativeBalanceException;

/**
 * User Data base entity
 * 
 * @author jaros
 */
@Entity
@Transactional
@Table(name = "users", schema = "HR")
public class DUser implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the user */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	@SequenceGenerator(name = "user_sequence", sequenceName = "users_sequence")
	@Column(name = "id")
	private Long id;

	/** name of the user */
	@Column(name = "user_name")
	private String name;

	/** creation date */
	@Column(name = "created_date")
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date createdDate;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "wallet_id", referencedColumnName = "wallet_id")
	private DWallet wallet;

	public DUser() {
		this(null);
	}

	public DUser(String name) {
		this.name = name;
		this.createdDate = new Date();
	}

	public DWallet getDWallet() {
		return wallet;
	}

	public void setDWallet(DWallet wallet) {
		this.wallet = wallet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void withdraw(Long amount) {
		if (amount < 0)
			throw new IllegalArgumentException(
					"The sum you are going to withdraw can not be negative number");
		if (amount > this.wallet.getBalance())
			throw new NegativeBalanceException();
		this.wallet.setBalance(this.wallet.getBalance() - amount);
	}

	public void deposit(Long creditAmount) {
		if (creditAmount < 0)
			throw new IllegalArgumentException(
					"The sum you are going to deposit can not be negative number");
		this.wallet.setBalance(this.wallet.getBalance() + creditAmount);
	}
}