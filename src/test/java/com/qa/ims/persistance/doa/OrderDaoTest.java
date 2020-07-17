package com.qa.ims.persistance.doa;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.Ims;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.ItemsDao;
import com.qa.ims.persistence.dao.OrderDao;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Items;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.services.CustomerServices;
import com.qa.ims.services.OrderServices;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderDaoTest {
	@Mock
	private OrderServices orderServices;
	
	@Spy
	@InjectMocks
	private OrderController orderController;

	public static final Logger LOGGER = Logger.getLogger(Ims.class);

	private static final Float Date = null;
	static String jdbcurl = "jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC";
	static String username = "root";
	static String password = "root";
	
	@BeforeClass
	public static void setup() {
		try {
			Connection connection = DriverManager.getConnection(jdbcurl, username, password);
			Statement statement = connection.createStatement();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
}
}
	@BeforeClass
	public static void aInit() {
		Ims ims = new Ims();
	
		ims.init("jdbc:mysql://localhost:3306/?serverTimezone=UTC", "root", "root",
			"src/test/resources/sql-schema.sql");
	}
	@Test
	public void CreateTest() {
		OrderDao orderDao = new OrderDao(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Long id = 1L;
		Date Date =null;
		Date placed = Date;
		Order order = new Order(id, placed);
		Long id2 = 2L;
		Date placed2 = Date;
		Order order2 = new Order(id2, placed2);
		Long id3 = 3L;
		Date placed3 = Date;
		Order order3 = new Order(id3, placed3);
			
		LOGGER.debug(orderDao.create(order));
			
		assertEquals(order, orderDao.create(order));
		assertEquals(order2, orderDao.create(order2));
		assertEquals(order3, orderDao.create(order3));
		}
	@Test
	public void cReadAllTest() {
		OrderDao orderDao = new OrderDao(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		List<Order> order = new ArrayList<>();
		Date Date = null;
		Customer Customer = null;
		order.add(new Order(1L, Customer,Date ));
		order.add(new Order(2L, Customer,Date));
		order.add(new Order(3L, Customer,Date));

		assertEquals(order, orderDao.readAll());
	}
	@Test
	public void dReadLatestTest() {
		OrderDao orderDao = new OrderDao(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Date Date= null;
		Customer Customer= null;
		Order order = new Order(3L, Customer,Date);
		assertEquals(order, orderDao.readLatest());
	}

	@Test
	public void eReadCustomerTest() {
		OrderDao orderDao = new OrderDao(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		String Customer;
		Order order = new Order(2L, Customer,Date);
		assertEquals(order, orderDao.readOrder(2L));
	}

}
