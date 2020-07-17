package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.BasketItem;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Items;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;


public class OrderDao implements Dao<Order> {
	public static final Logger LOGGER = Logger.getLogger(OrderDao.class);
	
	private String jdbcConnectionUrl;
	private String username;
	private String password;
	CustomerDaoMysql custDao;
	
	public OrderDao(String username, String password) {
		this.jdbcConnectionUrl = "jdbc:mysql://" + Utils.MYSQL_URL + "/ims?serverTimezone=UTC";
		this.username = username;
		this.password = password;
		this.custDao = new CustomerDaoMysql(username, password);
	}
	
	public OrderDao(String jdbcConnectionUrl, String username, String password) {
		this.jdbcConnectionUrl = jdbcConnectionUrl;
		this.username = username;
		this.password = password;
	}
	
	Order OrderFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd"); 
		
		Long order_id = resultSet.getLong("Order_ID");
		Customer customer = this.custDao.readCustomer(resultSet.getLong("fk_Customer_ID"));
		Date placed = dateFmt.parse(resultSet.getString("Placed"));
		
		Order order = new Order(order_id, customer, placed);
		BasketFillOrder(order);
		return order;
	}
	
	public void EmptyBasketDB(Long order_id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("DELETE FROM basket WHERE fk_Order_ID = " + order_id);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}
	
	public void AddBasketItemToDB(Long order_id, BasketItem b_item) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("INSERT INTO Basket(fk_Order_ID, fk_Item_ID, Quantity) "
						+ "VALUES (" + order_id + ", " + b_item.getItemId() + ", " + b_item.getQuantity() + ");");
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}
	
	public float BasketTotal(Long order_id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();){
			ResultSet resultSet = statement.executeQuery("SELECT Basket.fk_Order_ID,SUM(Items.Price * Basket.Quantity) AS TotalPrice\r\n" + 
					"FROM Items \r\n" + 
					"INNER JOIN Basket ON Items.Item_ID=Basket.fk_Item_ID\r\n" + 
					"WHERE Basket.fk_Order_ID = " + 
					order_id +";" );
			return resultSet.getFloat("TotalPrice");
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		
		return 0;
	}

	public void BasketPrintList(Long order_id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();){
			ResultSet resultSet = statement.executeQuery("SELECT Basket.fk_Order_ID, Items.Product,Items.Price, Basket.Quantity, Items.Price * Basket.Quantity AS TotalPrice\r\n" + 
					"FROM Items \r\n" + 
					"INNER JOIN Basket ON Items.Item_ID=Basket.fk_Item_ID\r\n" + 
					"WHERE Basket.fk_Order_ID =" + 
					order_id + ";");
			
			while (resultSet.next()) { //finds next in result set and returns true if there is result to be read 
				LOGGER.debug("Item ID: " + resultSet.getInt("fk_Item_ID") + " Quantity: " + resultSet.getInt("Quantity") + " Price: " + resultSet.getFloat("Price"));
			}
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

	public void BasketFillOrder(Order order) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();){
			ResultSet resultSet = statement.executeQuery("SELECT Basket.fk_Order_ID, Basket.fk_Item_ID, Items.Product,Items.Price, Basket.Quantity, Items.Price * Basket.Quantity AS TotalPrice\r\n" + 
					"FROM Items \r\n" + 
					"INNER JOIN Basket ON Items.Item_ID=Basket.fk_Item_ID\r\n" + 
					"WHERE Basket.fk_Order_ID =" + 
					order.getOrder_id() + ";");
			
			while (resultSet.next()) { //finds next in result set and returns true if there is result to be read 
				BasketItem b_item = new BasketItem(resultSet.getInt("fk_Item_ID"), resultSet.getInt("Quantity"));
				order.addItem(b_item);
			}
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}
	
	
	
	@Override
	public List<Order> readAll() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT Orders.Order_ID, Customers.Name, Orders.Placed, Orders.fk_Customer_ID \r\n" + 
						"FROM Orders\r\n" + 
						"INNER JOIN Customers ON Orders.fk_Customer_ID=Customers.Customer_ID" );) 
		{
			ArrayList<Order> order = new ArrayList<>();
			while (resultSet.next()) {
				order.add(OrderFromResultSet(resultSet));
			}
			return order;
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		} catch (ParseException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	public Order readLatest() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Orders ORDER BY Order_ID DESC LIMIT 1");) {
			resultSet.next();
			return OrderFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public Order create(Order order) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("INSERT INTO Orders (fk_Customer_ID, Placed) VALUES ('" 
					+ order.getCustomer().getId() + "', "
					+ "Now())");
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}
		
	public Order readOrder(Long order_id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Orders WHERE Order_ID = " + order_id);) {
			resultSet.next();
			return OrderFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public Order update(Order order) {
		// Update the basket-list ... we need to delete all items from the basket
		// then add them back in if it changes
		LOGGER.debug("Emptying basket...");
		EmptyBasketDB(order.getOrder_id());
		
		// Add items back in
		for(BasketItem b_item : order.getOrderList()) {
			LOGGER.debug("Adding item ID " + b_item.getItemId() + " with quantity " + b_item.getQuantity());
			AddBasketItemToDB(order.getOrder_id(), b_item);
		}
		
		LOGGER.debug("The total for you order is now: " + BasketTotal(order.getOrder_id()));
		
		return order;
	}
	
	@Override
	public void delete(long order_id) {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("DELETE FROM Orders WHERE Order_ID = " + order_id);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

}
