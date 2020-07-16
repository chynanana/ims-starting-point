package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.services.CustomerServices;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

	/**
	 * The thing I want to fake functionlity for
	 */
	@Mock
	private CustomerServices customerServices;

	/**
	 * Spy is used because i want to mock some methods inside the item I'm testing
	 * InjectMocks uses dependency injection to insert the mock into the customer
	 * controller
	 */
	@Spy // for the methods in customerController
	@InjectMocks // for any classes our customerController calls (in this case customerService)
	private CustomerController customerController;

	@Test
	public void readAllTest() {
		CustomerController customerController = new CustomerController(customerServices);
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer("Too Mato", "689 Street Rd", "KF65 9HG" ));
		customers.add(new Customer("Aire Conditioner", "Cool St", "HO2 6CO"));
		customers.add(new Customer("Inflatable Man", "Wave Terr", "PV3 3EE"));
		Mockito.when(customerServices.readAll()).thenReturn(customers);
		assertEquals(customers, customerController.readAll());
	}

	@Test
	public void createTest() {
		String name = "Elle Lady";
		String address = "1 Lady Ln";
		String postcode = "QT23 0LD";
		Mockito.doReturn(name, address, postcode).when(customerController).getInput();
		Customer customer = new Customer(name, address, postcode);
		Customer savedCustomer = new Customer(1L, "Elle Lady", "1 Lady Ln", "QT23 0LD");
		Mockito.when(customerServices.create(customer)).thenReturn(savedCustomer);
		assertEquals(savedCustomer, customerController.create());
	}

	/**
	 *
	 */
	@Test
	public void updateTest() {
		
		String Name = "Too Mato";
		String Address = "689 Street Rd";
		String Postcode = "KF65 9HG";
		Mockito.doReturn(Name, Address, Postcode).when(customerController).getInput();
		Customer customer = new Customer(Name, Address, Postcode);
		Mockito.when(customerServices.update(customer)).thenReturn(customer);
		assertEquals(customer, customerController.update());
	}

	/**
	 * Delete doesn't return anything, so we can just verify that it calls the
	 * delete method
	 */
	@Test
	public void deleteTest() {
		String id = "1";
		Mockito.doReturn(id).when(customerController).getInput();
		customerController.delete();
		Mockito.verify(customerServices, Mockito.times(1)).delete(1L);
	}

}
