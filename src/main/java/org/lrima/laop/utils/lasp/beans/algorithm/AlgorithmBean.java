package org.lrima.laop.utils.lasp.beans.algorithm;

import org.lrima.laop.utils.lasp.beans.UserBean;

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
	private int nb_likes;
	private int nb_comments;
	
	public int getNb_likes() {
		return nb_likes;
	}
	public void setNb_likes(int nb_likes) {
		this.nb_likes = nb_likes;
	}
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
	public int getNb_comments() {
		return nb_comments;
	}
	public void setNb_comments(int nb_comments) {
		this.nb_comments = nb_comments;
	}
	
	
}
