package com.qa.ims.services;

import java.util.List;

import com.qa.ims.controller.CrudController;
import com.qa.ims.persistence.dao.CustomerDaoMysql;
import com.qa.ims.persistence.dao.Dao;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.dao.OrderDao;

public class OrderServices implements CrudServices<Order> {
	
	public OrderServices(Dao<Order> orderDao) {
		this.orderDao = orderDao;
	}
	

	private Dao<Order> orderDao;
	
	public List<Order> readAll() {
		return orderDao.readAll();
	}
	
	public Order create(Order order) {
		return orderDao.create(order);
	}
	public Order update(Order order) {
		return orderDao.update(order);
	}
	public void delete(Long id) {
		orderDao.delete(id);
	}

	@Override
	public Order create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
}
