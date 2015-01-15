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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.transaction.annotation.Transactional;

import ua.ishchenko.rest.service.helper.CustomJsonDateDeserializer;
import ua.ishchenko.rest.service.helper.CustomJsonDateSerializer;

/**
 * User entity
 * 
 * @author jaros
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(User.class)
@Entity
@Transactional
@Table(name = "users", schema = "HR")
public class User implements Serializable {
	
	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the user */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_sequence") 
	@SequenceGenerator(name="user_sequence", sequenceName = "users_sequence")
	@Column(name = "id")
	private Long id;

	/** name of the user */
	@Column(name = "user_name")
	private String name;

	/** creation date */
	@Column(name="created_date")
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)	
	private Date createdDate;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "wallet_id", referencedColumnName = "wallet_id")
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

	public User( String name) {
		this.name = name;
		this.createdDate = new Date();
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
}