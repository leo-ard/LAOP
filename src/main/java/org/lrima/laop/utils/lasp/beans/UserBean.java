package org.lrima.laop.utils.lasp.beans;

/**
 * Bean that represents a user from LISP that created an algorithm
 * @author Clement Bisaillon
 */
public class UserBean {
	private int id;
	private String name;


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
	 * @return the name of the algorithm
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the algorithm
	 * @param name the name of the algorithm
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
