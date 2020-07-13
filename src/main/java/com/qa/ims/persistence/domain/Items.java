package com.qa.ims.persistence.domain;

public class Items {
	
	private Long Item_ID;
	private String Product;
	private Float Price;
	
	public Items(Long Item_ID, String Product, Float Price) {
		this.Item_ID = Item_ID;
		this.Product = Product;
		this.Price = Price;
	}
	
	public Items(String Product, Float Price) {
		this.Product = Product;
		this.Price = Price;
	}

	public Long getItem_ID() {
		return Item_ID;
	}

	/* ID will not be changed so not setter needed as this will be assigned or removed never changed 
	 * public void setItem_ID(Long item_ID) { Item_ID = item_ID; }
	 */

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public Float getPrice() {
		return Price;
	}

	public void setPrice(Float price) {
		Price = price;
	}

	public String toString() {
		return "Item_ID: " + Item_ID + " Product: " + Product + " Price: " + Price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Item_ID == null) ? 0 : Item_ID.hashCode());
		result = prime * result + ((Price == null) ? 0 : Price.hashCode());
		result = prime * result + ((Product == null) ? 0 : Product.hashCode());
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
		Items other = (Items) obj;
		if (Item_ID == null) {
			if (other.Item_ID != null)
				return false;
		} else if (!Item_ID.equals(other.Item_ID))
			return false;
		if (Price == null) {
			if (other.Price != null)
				return false;
		} else if (!Price.equals(other.Price))
			return false;
		if (Product == null) {
			if (other.Product != null)
				return false;
		} else if (!Product.equals(other.Product))
			return false;
		return true;
	}
	
	

}
