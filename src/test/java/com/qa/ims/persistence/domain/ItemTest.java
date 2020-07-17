package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {
	
	private Items item;
	private Items item2;
	
	@Before
	public void setUp() {
		item = new Items(1L, "Coco Nuts", (float) 9.99);
		item2 = new Items(1L, "Soni Headfones", (float) 99.99);
	}
	
	@Test
	public void settersTest() {
		assertNotNull(item.getProduct());
		assertNotNull(item.getPrice());
		
		item.setProduct(null);
		assertNull(item.getProduct());
		item.setPrice(null);
		assertNull(item.getPrice());
	}
	@Test
	public void equalsWithNull() {
		assertFalse(item.equals(null));
	}
	
	@Test
	public void equalsWithDifferentObject() {
		assertFalse(item.equals(new Object()));
	}
	
	@Test
	public void createCustomerWithId() {
		assertEquals("Coco Nuts", item.getProduct());
		assertFalse(9.99f != item.getPrice());
		
	}
	@Test
	public void checkEquality() {
		assertTrue(item.equals(item));
	}
	@Test
	public void checkEqualityBetweenDifferentObjects() {
		assertTrue(item.equals(item));
	}
	@Test
	public void itemNameNullButOtherNameNotNull() {
		item.setProduct(null);
		assertFalse(item.equals(item2));
	}
	@Test
	public void ItemNamesNotEqual() {
		item2.setProduct("Coo King");
		assertFalse(item.equals(item2));
	}
	@Test
	public void checkEqualityBetweenDifferentObjectsNullName() {
		item.setProduct(null);
		item2.setProduct(null);
		assertTrue(item.equals(item2));
	}
	@Test
	public void nullId() {
		//item.setItem_ID((null));
		assertFalse(item.getItem_ID() != 1);
	}
	
	
}
	

  