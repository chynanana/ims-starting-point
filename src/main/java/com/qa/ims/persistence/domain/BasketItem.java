package com.qa.ims.persistence.domain;

public class BasketItem {
	
	private int item_id;
	private int quantity;
	
	public BasketItem (int item_id, int quantity) {
		this.item_id = item_id;
		this.quantity = quantity;
	}

	public int getItemId() {
		return item_id;
	}

	public void setItemId(int id) {
		this.item_id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
