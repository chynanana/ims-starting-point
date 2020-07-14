package com.qa.ims.services;

import java.util.List;

import com.qa.ims.persistence.dao.Dao;
import com.qa.ims.persistence.dao.ItemsDao;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Items;

public class ItemServices implements CrudServices<Items> {
	
	public ItemServices(Dao<Items> itemsDao) {
		this.itemsDao = itemsDao;
	}
	

	private Dao<Items> itemsDao;
	
	
	public List<Items> readAll() {
		return itemsDao.readAll();
	}
	
	public Items create(Items item) {
		return itemsDao.create(item);
	}
	public Items update(Items item) {
		return itemsDao.update(item);
	}
	public void delete(Long id) {
		itemsDao.delete(id);
	}

}
	

