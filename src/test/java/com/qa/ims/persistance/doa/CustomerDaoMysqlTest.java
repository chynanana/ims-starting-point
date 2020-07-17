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
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.services.CustomerServices;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerDaoMysqlTest {
	/**
	 * The thing I want to fake functionality for
	 */
	@Mock
	private CustomerServices customerServices;

	/**
	 * Spy is used because i want to mock some methods inside the item I'm testing
	 * InjectMocks uses dependency injection to insert the mock into the customer
	 * controller
	 */
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
			//statement.executeUpdate("Drop database ims_test");

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
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		String name = "Alexander Hamilton";
		String address = "51 Federal St";
		String postcode = "WL85 D76";
		Customer customer = new Customer(1L, name, address, postcode);
		String name2 = "Sweeny Todd";
		String address2 = "Barber Dr";
		String postcode2 = "SW56 5NK";
		Customer customer2 = new Customer(2L, name2, address2, postcode2);
		String name3 = "Sideshow Bob";
		String address3 = "23 Perry Ln";
		String postcode3 = "SW56 5NK";
		Customer customer3 = new Customer(3L, name3, address3, postcode3);
		
		LOGGER.debug(customerDaoMysql.create(customer));
		
		assertEquals(customer, customerDaoMysql.create(customer));
		assertEquals(customer2, customerDaoMysql.create(customer2));
		assertEquals(customer3, customerDaoMysql.create(customer3));
	}

	@Test
	public void cReadAllTest() {
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1L, "Alexander Hamilton","51 Federal St","WL85 D76"));
		customers.add(new Customer(2L, "Sweeny Todd", "Barber Dr","SW56 5NK"));
		customers.add(new Customer(3L, "Sideshow Bob", "23 Perry Ln","SW56 5NK" ));

		assertEquals(customers, customerDaoMysql.readAll());
	}

	@Test
	public void dReadLatestTest() {
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Customer customer = new Customer(3L);
		assertEquals(customer, customerDaoMysql.readLatest());
	}

	@Test
	public void eReadCustomerTest() {
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Customer customer = new Customer(2L, "Sweeny Todd", "Barber Dr","SW56 5NK");
		assertEquals(customer, customerDaoMysql.readCustomer(2L));
	}

//
//	/**
//	 * 
//	 */
	@Test
	public void fUpdateTest() {
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		Long id = 1L;
		String name = "Alexander Hamilton";
		String address = "51 Federal St";
		String postcode = "WL85 D76";
		Customer customer = new Customer((id), name, address, postcode);
		assertEquals(customer, customerDaoMysql.update(customer));
	}

//	/**
//	 * makes sure that after you delete, the entry is no longer in the database.
//	 */
	@Test
	public void gDeleteTest() {
		CustomerDaoMysql customerDaoMysql = new CustomerDaoMysql(
				"jdbc:mysql://localhost:3306/ims_test?serverTimezone=UTC", "root", "root");
		String id = "3";
		customerDaoMysql.delete(Long.parseLong(id));
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(3L, "Sideshow Bob", "23 Perry Ln","SW56 5NK"));
		assertEquals(customers, customerDaoMysql.readAll());
	}

	@AfterClass
	public static void cleanDB() {

		try (Connection connection = DriverManager.getConnection(jdbcurl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("drop table customers");
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

}

