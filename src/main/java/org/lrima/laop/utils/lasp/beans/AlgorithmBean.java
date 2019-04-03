package org.lrima.laop.utils.lasp.beans;

/**
 * Bean that represents an algorithm from LISP
 * @author Clement Bisaillon
 */
public class AlgorithmBean {
	private int id;
	private String title;
	private String description;
	private UserBean user;
	private String created_at;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	
}
