package com.qa.ims.controller;

import java.util.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.BasketItem;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Items;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.services.CrudServices;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = Logger.getLogger(OrderController.class);

	private CrudServices<Order> orderService;

	private Order order;

	public OrderController(CrudServices<Order> orderService) {
		this.orderService = orderService;
	}
	
	String getInput() {
		return Utils.getInput();
	}
	
	@Override
	public List<Order> readAll() {
		List<Order> order = orderService.readAll();
		
		for(Order x: order) {
			LOGGER.info(x.toString());
			LOGGER.info(x.getItemsAsString());
		}
		
		return order;
	}
	@Override
	public Order create() {
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd"); 
		LOGGER.info("Please enter Your Customer ID");
		Long id = Long.valueOf(getInput());
		Date placed = new Date();
		
		//Customer customer = Utils.getCustomer();
		//Customer placed = ("NOW()");
		//Customer customer = Utils.getInput();
		//Long order_id = resultSet.getLong("Order_ID");
		//Date placed = dateFmt.parse(resultSet.getString("Placed"));
		//Customer id = Utils."Order_ID";
		//String placed = ((ResultSet) dateFmt).getString("Placed");
		//Customer id = Customer.getId();
		this.order = orderService.create(new Order(id, placed));
		LOGGER.info("Your order has been created");
		
		LOGGER.info("Would you like to add items to this order? (Yes/No)");
		String resp = getInput();
		
		if(resp.toLowerCase().contentEquals("yes")) {
			update();
		}
		
		return this.order;
	}

	@Override
	public Order update() {
		int item_id;
		int quantity;
		BasketItem b_item;
		
		LOGGER.info("** Add items to order **");
		
		while(true) {
			LOGGER.info("Enter the ID of the item you want to add or type the number 0 to quit");
			item_id = Integer.valueOf(getInput());
			if(item_id == 0) {
				break;
			}
			
			LOGGER.info("How many items do you want?");
			quantity = Integer.valueOf(getInput());
			if(quantity <= 0) {
				LOGGER.info("That is not a valid quantity!");
				continue;
			}
			
			b_item = new BasketItem(item_id, quantity);
			this.order.addItem(b_item);
		}
		
		LOGGER.info("Starting to update basket");
		
		orderService.update(this.order);
		
		LOGGER.info("Done adding items");
		
		return this.order;
	}

	@Override
	public void delete() {

	}

}
