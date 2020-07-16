package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CustomerTest {
	
	private Customer customer;
	private Customer other;
	
	@Before
	public void setUp() {
		customer = new Customer(1L, "Harry Bose", "300 Sweet St","EZ33 9GF");
		other = new Customer(1L, "Harry Bose", "300 Sweet St","EZ33 9GF");
	}
	
	@Test
	public void settersTest() {
		assertNotNull(customer.getId());
		assertNotNull(customer.getName());
		assertNotNull(customer.getAddress());
		assertNotNull(customer.getPostcode());
		
		customer.setId(null);
		assertNull(customer.getId());
		customer.setName(null);
		assertNull(customer.getName());
		customer.setAddress(null);
		assertNull(customer.getAddress());
		customer.setAddress(null);
		assertNull(customer.getAddress());
		customer.setPostcode(null);
		assertNull(customer.getPostcode());
	}
	
	@Test
	public void equalsWithNull() {
		assertFalse(customer.equals(null));
	}
	
	@Test
	public void equalsWithDifferentObject() {
		assertFalse(customer.equals(new Object()));
	}
	
	@Test
	public void createCustomerWithId() {
		assertEquals(1L, customer.getId(), 0);
		assertEquals("Harry Bose", customer.getName());
		assertEquals("300 Sweet St",customer.getAddress());
		assertEquals("EZ33 9GF",customer.getPostcode());
	}
	
	@Test
	public void checkEquality() {
		assertTrue(customer.equals(customer));
	}
	
	@Test
	public void checkEqualityBetweenDifferentObjects() {
		assertTrue(customer.equals(other));
	}
	
	@Test
	public void customerNameNullButOtherNameNotNull() {
		customer.setName(null);
		assertFalse(customer.equals(other));
	}
	
	@Test
	public void customerNamesNotEqual() {
		other.setName("Coo King");
		assertFalse(customer.equals(other));
	}
	
	@Test
	public void checkEqualityBetweenDifferentObjectsNullName() {
		customer.setName(null);
		other.setName(null);
		assertTrue(customer.equals(other));
	}
	
	@Test
	public void nullId() {
		customer.setId(null);
		assertFalse(customer.equals(other));
	}
	
	@Test
	public void nullIdOnBoth() {
		customer.setId(null);
		other.setId(null);
		assertTrue(customer.equals(other));
	}
	
	@Test
	public void otherIdDifferent() {
		other.setId(2L);
		assertFalse(customer.equals(other));
	}
	
	@Test
	public void nullName() {
		customer.setName(null);
		assertFalse(customer.equals(other));
	}
	
	@Test
	public void nullSurnameOnBoth() {
		customer.setName(null);
		other.setName(null);
		assertTrue(customer.equals(other));
	}
	
	@Test
	public void otherNameDifferent() {
		other.setName("Oscar Pollow");
		assertFalse(customer.equals(other));
	}
	
	@Test
	public void constructorWithoutId() {
		Customer customer = new Customer("Bart Simpson", "8 Myshorts Ave", "BS23 8SB");
		assertNull(customer.getId());
		assertNotNull(customer.getName());
		assertNotNull(customer.getAddress());
		assertNotNull(customer.getPostcode());
	}
	
	@Test
	public void hashCodeTest() {
		assertEquals(customer.hashCode(), other.hashCode());
	}
	@Test
	public void hashCodeTestWithNull() {
		Customer customer = new Customer(0L, "", "", "");
		Customer other = new Customer(0L, "", "", "");
		assertEquals(customer.hashCode(), other.hashCode());
	}
	
	@Test
	public void toStringTest() {
		String toString = "id:1 name:Harry Bose address: 300 Sweet St postcode: EZ33 9GF";
		assertEquals(toString, customer.toString());
	}
}
