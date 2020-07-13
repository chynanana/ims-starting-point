package com.qa.ims.persistence.domain;
import java.util.ArrayList;
import java.util.Date;

public class Order {

	/*
	 * java.util.Date placed = new java.util.Date(); java.text.SimpleDateFormat date
	 * = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String currentTime =
	 * date.format(placed);
	 */
	private ArrayList<BasketItem> items = new ArrayList<>();
	private Long order_id;
	private Customer customer;
	private Date placed;
	
	public Order(Long order_id, Customer customer, Date placed) {
		this.order_id = order_id;
		this.customer = customer;
		this.placed = placed;
	}
	public Order(Customer customer, Date placed) {
		this.customer = customer;
		this.placed = placed;
		
		// getters and setters
	}
	public Long getOrder_id() {
		return order_id;
	}

	/* no setter needed for order id this cannot be changed 
	 * public void setOrder_id(Long order_id) { this.order_id = order_id; }
	 */
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Date getPlaced() {
		return placed;
	}
	public void setPlaced(Date placed) {
		this.placed = placed;
	}
	
	public void addItem(BasketItem bitem) { 
		// add new line in basket (item with quantity)
		items.add(bitem); 
	}
	
	public void removeItem(BasketItem bitem) {
		items.remove(bitem);
	}
	
	public float total() {
		float runningTotal = 0.0;
		
		for(int i = 0; i < items.size(); i++) {
			runningTotal += items.get(i).calcPrice();
		}
		
		return runningTotal;
	}
	 
	/* 
	 * public void remove public void total
	 */
	
	public String toString() {
		return "Order_ID: " + order_id + " Customer_ID: " + customer + " Placed: " + placed;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((order_id == null) ? 0 : order_id.hashCode());
		result = prime * result + ((placed == null) ? 0 : placed.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (order_id == null) {
			if (other.order_id != null)
				return false;
		} else if (!order_id.equals(other.order_id))
			return false;
		if (placed == null) {
			if (other.placed != null)
				return false;
		} else if (!placed.equals(other.placed))
			return false;
		return true;
	}
	
	
	
}
