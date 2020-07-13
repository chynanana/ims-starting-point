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
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		Long order_id = resultSet.getLong("Order_ID");
		Customer customer = this.custDao.readCustomer(resultSet.getLong("Customer_ID"));
		Date placed = dateFmt.parse(resultSet.getString("Placed"));
		
		return new Order(order_id, customer, placed);
	}
	
	@Override
	public List<Order> readAll() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Orders");) {
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
		//stub no need to update order
		return null;
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
