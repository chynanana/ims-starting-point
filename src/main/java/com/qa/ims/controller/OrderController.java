package com.qa.ims.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Items;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.services.CrudServices;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = Logger.getLogger(OrderController.class);

	private CrudServices<Order> orderService;

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
		}
		return order;
	}
	@Override
	public Order create() {
		LOGGER.info("Please enter Your Customer ID");
		Long id = Utils.getLong();
		LOGGER.info("Please enter the Catalog number of the item that you would like to order");
		String item = getInput();
		LOGGER.info("Please enter the customer's postcode");
		int quant = Utils.getInt();
		Order order = orderService.create(new Order(id, item, quant));
		LOGGER.info("Your order has been placed");
		return order;
	}

	@Override
	public Order update() {
		LOGGER.info("Please enter the id of the item that you would like to update");
		Long id = Utils.getLong();
		LOGGER.info("Please enter the product name");
		String product = Utils.getInput();
		LOGGER.info("Please enter the price of the product");
		float price = Utils.getFloat();
		Items order = orderService.update(new Items(id, product, price));
		LOGGER.info("Item has been Updated");
		return order;
	}

	@Override
	public void delete() {

	}

}
