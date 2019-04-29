package org.lrima.laop.utils.lasp.beans.algorithm;

import java.util.Collection;

/**
 * Bean that represents a response from the API end point about algorithms
 * @author Clement Bisaillon
 */
public class AlgorithmResponseBean {
	private int current_page;
	private Collection<AlgorithmBean> data;
	private String first_page_url;
	private String next_page_url;
	private String path;
	private int per_page;
	private String prev_page_url;
	private int to;
	private int total;


	/**
	 * @return the current page of algorithms to show
	 */
	public int getCurrent_page() {
		return current_page;
	}

	/**
	 * Set the current page
	 * @param current_page the current page
	 */
	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	/**
	 * Get the array of algorithms
	 * @return the array of algorithms
	 */
	public Collection<AlgorithmBean> getData() {
		return data;
	}

	/**
	 * Set the array of algorithms
	 * @param data the array of algorithms
	 */
	public void setData(Collection<AlgorithmBean> data) {
		this.data = data;
	}

	/**
	 * @return the url of the first page
	 */
	public String getFirst_page_url() {
		return first_page_url;
	}

	/**
	 * Set the url of the first page
	 * @param first_page_url the url of the first page
	 */
	public void setFirst_page_url(String first_page_url) {
		this.first_page_url = first_page_url;
	}

	/**
	 * @return the url of the next page
	 */
	public String getNext_page_url() {
		return next_page_url;
	}

	/**
	 * Set the url of the next page
	 * @param next_page_url the url of the next page
	 */
	public void setNext_page_url(String next_page_url) {
		this.next_page_url = next_page_url;
	}

	/**
	 * @return the path of the algorithm
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Set the path of the algorithm
	 * @param path the path of the algorithm
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the number of algorithm to show per page
	 */
	public int getPer_page() {
		return per_page;
	}

	/**
	 * Set the number of algorithms to show per pages
	 * @param per_page the number of algorithms to show per pages
	 */
	public void setPer_page(int per_page) {
		this.per_page = per_page;
	}

	/**
	 * @return the url of the previous page
	 */
	public String getPrev_page_url() {
		return prev_page_url;
	}

	/**
	 * set the url of the previous page
	 * @param prev_page_url the url of the previous page
	 */
	public void setPrev_page_url(String prev_page_url) {
		this.prev_page_url = prev_page_url;
	}

	/**
	 * @return the id of the last algorithm shown on this page
	 */
	public int getTo() {
		return to;
	}

	/**
	 * Set the id of the last algorithm shown on this page
	 * @param to the id
	 */
	public void setTo(int to) {
		this.to = to;
	}

	/**
	 * @return the total number of algorithms
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * Set the total number of algorithms
	 * @param total the total number of algorithms
	 */
	public void setTotal(int total) {
		this.total = total;
	}
}
