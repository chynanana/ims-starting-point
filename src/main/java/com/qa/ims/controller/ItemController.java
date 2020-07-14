package com.qa.ims.controller;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Items;
import com.qa.ims.services.CrudServices;
import com.qa.ims.services.ItemServices;
import com.qa.ims.utils.Utils;

public class ItemController implements CrudController<Items> {
public static final Logger LOGGER = Logger.getLogger(ItemController.class);
	
	private CrudServices<Items> itemService;
	
	public ItemController(CrudServices<Items> itemService) {
		this.itemService = itemService;
	}
	

	@Override
	public List<Items> readAll() {
		List<Items> items = itemService.readAll();
		for(Items x: items) {
			LOGGER.info(x.toString());
		}
		return items;
	}
	@Override
	public Items create() {
		LOGGER.info("Please enter the Name of the item ");
		String product = Utils.getInput();
		LOGGER.info("Please enter the price of that item");
		float price = Utils.getFloat();
		Items item = itemService.create(new Items(product, price));
		LOGGER.info("Iten added to catalog");
		return item;
	}
	
	@Override
	public Items update() {
		LOGGER.info("Please enter the id of the item that you would like to update");
		Long id = Utils.getLong();
		
		// clear buffer
		Utils.getInput();
		
		LOGGER.info("Please enter the product name");
		String product = Utils.getInput();
		LOGGER.info("Please enter the price of the product");
		float price = Utils.getFloat();
		Items item = itemService.update(new Items(id, product, price));
		LOGGER.info("Item has been Updated");
		return item;
	}

	@Override
	public void delete() {
		LOGGER.info("Please enter the id of the item that you would like to remove");
		Long id = Utils.getLong();
		itemService.delete(id);
	}
	}
	
	


