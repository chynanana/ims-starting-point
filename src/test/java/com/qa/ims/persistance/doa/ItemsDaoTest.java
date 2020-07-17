package com.qa.ims.persistance.doa;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
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
import com.qa.ims.persistence.dao.CustomerDaoMysql;
import com.qa.ims.persistence.dao.ItemsDao;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Items;
import com.qa.ims.services.CustomerServices;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemsDaoTest {
	
	@Mock
	private CustomerServices customerServices;
	
	@Spy
	@InjectMocks
	private CustomerController customerController;

	public static final Logger LOGGER = Logger.getLogger(Ims.class);
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
		ItemsDao itemsDao = new ItemsDao(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		String product = "Toy Sword";
		Float price = 12.99f;
		Items item = new Items(1L, product, price);
		String product2= "Sweeny Todd Figure";
		Float price2 = 42.40f;
		Items item2 = new Items(2L, product2, price2);
		String product3 = "Sideshow Bob Figure";
		Float price3 = 29.99f;
		Items item3 = new Items(3L, product3, price3);
			
		LOGGER.debug(itemsDao.create(item));
			
		assertEquals(item, itemsDao.create(item));
		assertEquals(item2, itemsDao.create(item2));
		assertEquals(item3, itemsDao.create(item3));
		}
	@Test
	public void cReadAllTest() {
		ItemsDao itemsDao = new ItemsDao(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		List<Items> item = new ArrayList<>();
		item.add(new Items(1L, "Toy Sword",12.99f));
		item.add(new Items(2L, "Sweeny Todd Figure", 42.40f));
		item.add(new Items(3L, "Sideshow Bob Figure", 29.99f));

		assertEquals(item, itemsDao.readAll());
	}
	@Test
	public void dReadLatestTest() {
		ItemsDao itemsDao = new ItemsDao(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Items item = new Items(3L, "Sideshow Bob Figure", 29.99f);
		assertEquals(item, itemsDao.readLatest());
	}

	@Test
	public void eReadCustomerTest() {
		ItemsDao itemsDao = new ItemsDao(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Items item = new Items(2L, "Sweeny Todd Figure", 42.40f);
		assertEquals(item, itemsDao.readItem(2L));
	}
	@Test
	public void fUpdateTest() {
		ItemsDao itemsDao = new ItemsDao(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Long id = 1L;
		String product = "Submarine";
		Float price = 99.00f;
		Items item = new Items((id), product, price);
		assertEquals(item, itemsDao.update(item));
	}
	@Test
	public void gDeleteTest() {
		ItemsDao itemsDao = new ItemsDao(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		String id = "3";
		itemsDao.delete(Long.parseLong(id));
		List<Items> item = new ArrayList<>();
		item.add(new Items(3L, "Sideshow Bob Figure", 29.99f));
		assertEquals(item, itemsDao.readAll());
	}
	@AfterClass
	public static void cleanDB() {

		try (Connection connection = DriverManager.getConnection(jdbcurl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("drop table Items");
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}
}


