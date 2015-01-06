package ua.ishchenko.database;

import java.io.Serializable;
import java.util.Date;

public class DBUser implements Serializable {
	 
	private int userId;
	private String username;
	private Date createdDate;
 
	public DBUser() {
	}
 
	public DBUser(int userId, String username, String createdBy,
			Date createdDate) {
		this.userId = userId;
		this.username = username;
		this.createdDate = createdDate;
	}
 
	public int getUserId() {
		return this.userId;
	}
 
	public void setUserId(int userId) {
		this.userId = userId;
	}
 
	public String getUsername() {
		return this.username;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}
 
	public Date getCreatedDate() {
		return this.createdDate;
	}
 
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
 
}