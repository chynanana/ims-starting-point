import com.qa.ims.persistence.domain.Items;

public class BasketItem {
	
	private Items item;
	private int quantity;
	
	public BasketItem (Items item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	public Items getItem() {
		return item;
	}

	public void setItem(Items item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public float calcPrice() {
		return this.item.getPrice() * this.quantity;
	}
	
}
