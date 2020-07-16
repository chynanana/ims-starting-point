package com.qa.ims.services;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.dao.Dao;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Items;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class ItemServicesTest {
	
	@Mock
	private Dao<Items> itemsDao;
	
	@InjectMocks
	private ItemServices itemServices;
	
	@Test
	public void itemServicesCreate() {
		Items item = new Items("Test tube", (float) 4.50);
		itemServices.create(item);
		Mockito.verify(itemsDao, Mockito.times(1)).create(item);
	}
	
	@Test 
	public void itemServicesRead() {
		itemServices.readAll();
		Mockito.verify(itemsDao, Mockito.times(1)).readAll();
	}
	
	@Test
	public void itemServicesUpdate() {
		Items item = new Items("Test Tube", (float) 5.50);
		itemServices.update(item);
		Mockito.verify(itemsDao, Mockito.times(1)).update(item);
	}
	
	@Test 
	public void itemServicesDelete() {
		itemServices.delete(1L);
		Mockito.verify(itemsDao, Mockito.times(1)).delete(1L);
	}
	

}
