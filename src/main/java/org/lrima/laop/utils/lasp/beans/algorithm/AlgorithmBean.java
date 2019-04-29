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
	private String jar_file_location;

	/**
	 * @return The url to download the jar file of the algorithm
	 */
	public String getJar_file_location() {
		return jar_file_location;
	}

	/**
	 * Set the url of the jar file
	 * @param jar_file_location the url of the jar file
	 */
	public void setJar_file_location(String jar_file_location) {
		this.jar_file_location = jar_file_location;
	}

	/**
	 * @return The number of likes on the algorithm
	 */
	public int getNb_likes() {
		return nb_likes;
	}

	/**
	 * @param nb_likes the number of likes on the algorithm
	 */
	public void setNb_likes(int nb_likes) {
		this.nb_likes = nb_likes;
	}

	/**
	 * @return the id of the algorithm in the database of LASP
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the id of the algorithm
	 * @param id the id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title of the algorithm
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the algorithm
	 * @param title the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description of the algorithm
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description of the algorithm
	 * @param description the description of the algorithm
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the user that made the algorithm
	 */
	public UserBean getUser() {
		return user;
	}

	/**
	 * Set the user that made the algorithm
	 * @param user the user
	 */
	public void setUser(UserBean user) {
		this.user = user;
	}

	/**
	 * @return the creation date of the algorithm
	 */
	public String getCreated_at() {
		return created_at;
	}

	/**
	 * Set the creation date of the algorithm
	 * @param created_at the creation date
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	/**
	 * @return The number of comments on the algorithm
	 */
	public int getNb_comments() {
		return nb_comments;
	}

	/**
	 * Set the number of comments on the algorithm
	 * @param nb_comments the number of algorithm
	 */
	public void setNb_comments(int nb_comments) {
		this.nb_comments = nb_comments;
	}
	
	
}
