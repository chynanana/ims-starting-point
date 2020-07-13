package com.qa.ims.persistence.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Items;
import com.qa.ims.utils.Utils;



public class ItemsDao implements Dao<Items> {
	
	public static final Logger LOGGER = Logger.getLogger(ItemsDao.class);
	
	private String jdbcConnectionUrl;
	private String username;
	private String password;
	
	public ItemsDao(String username, String password) {
		this.jdbcConnectionUrl = "jdbc:mysql://" + Utils.MYSQL_URL + "/ims?serverTimezone=UTC";
		this.username = username;
		this.password = password;
	}
	
	public ItemsDao(String jdbcConnectionUrl, String username, String password) {
		this.jdbcConnectionUrl = jdbcConnectionUrl;
		this.username = username;
		this.password = password;
	}
	
	Items ItemsFromResultSet(ResultSet resultSet) throws SQLException {
		Long Item_ID = resultSet.getLong("Item_ID");
		String Product = resultSet.getString("Product");
		Float Price = resultSet.getFloat("Price");
		return new Items(Item_ID, Product, Price);
	}
	
	@Override
	public List<Items> readAll() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Items");) {
			ArrayList<Items> items = new ArrayList<>();
			while (resultSet.next()) {
				items.add(ItemsFromResultSet(resultSet));
			}
			return items;
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	public Items readLatest() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Items ORDER BY Item_ID DESC LIMIT 1");) {
			resultSet.next();
			return ItemsFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public Items create(Items item) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("INSERT INTO Items(Product, Price) VALUES ('" 
					+ item.getProduct() + "','" 
					+ item.getPrice() + "','");
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	public Items readItem(Long item_id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Items WHERE Item_ID = " + item_id);) {
			resultSet.next();
			return ItemsFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	public Items update(Items item) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("UPDATE Items SET Product ='" + item.getProduct() + "', Price ='"
					+ item.getPrice() + 
					"' WHERE Item_ID =" + item.getItem_ID());
			return readItem(item.getItem_ID());
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public void delete(long item_id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("DELETE FROM Items WHERE Item_ID = " + item_id);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}
	
	

}
