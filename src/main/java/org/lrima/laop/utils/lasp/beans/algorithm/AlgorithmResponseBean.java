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
	
	
	public int getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}
	public Collection<AlgorithmBean> getData() {
		return data;
	}
	public void setData(Collection<AlgorithmBean> data) {
		this.data = data;
	}
	public String getFirst_page_url() {
		return first_page_url;
	}
	public void setFirst_page_url(String first_page_url) {
		this.first_page_url = first_page_url;
	}
	public String getNext_page_url() {
		return next_page_url;
	}
	public void setNext_page_url(String next_page_url) {
		this.next_page_url = next_page_url;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getPer_page() {
		return per_page;
	}
	public void setPer_page(int per_page) {
		this.per_page = per_page;
	}
	public String getPrev_page_url() {
		return prev_page_url;
	}
	public void setPrev_page_url(String prev_page_url) {
		this.prev_page_url = prev_page_url;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
