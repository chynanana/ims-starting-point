package com.qa.ims.utils;

import java.util.Scanner;

import com.qa.ims.persistence.domain.Customer;

public class Utils {

	public static final String MYSQL_URL = "localhost:3306";
	public static final Scanner SCANNER = new Scanner(System.in);

	private Utils() {

	}

	public static String getInput() {
		return SCANNER.nextLine();
	}
	public static Float getFloat() {
		return SCANNER.nextFloat();
	}
	public static int getInt() {
		return SCANNER.nextInt();
	}
	public static Long getLong() {
		return SCANNER.nextLong();
	}
	//public static Customer getDate() {
		//return SCANNER.nextDouble();
	
	
}
