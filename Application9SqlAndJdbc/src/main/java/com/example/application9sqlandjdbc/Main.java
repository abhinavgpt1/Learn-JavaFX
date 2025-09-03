package com.example.application9sqlandjdbc;

import java.sql.*;

public class Main {
	final static DBConnectionCredentials mysqlConnection = new MySQLCredentials(
			"jdbc:mysql://localhost/learnjavafx", "root", "");
	final static DBConnectionCredentials pgConnection = new PostgresCredentials(
			"jdbc:postgresql://localhost:5432/database_ag", "postgres", "password");
	// use trustServerCertificate=true for local and not for production.
	final static DBConnectionCredentials sqlServerConnection = new SQLServerCredentials(
			"jdbc:sqlserver://localhost;encrypt=true;trustServerCertificate=true;databaseName=database_ag;", "root", "password");

	public static void main(String[] args) {
		Connection con = null;
		try {
			// Class.forName("com.mysql.cj.jdbc.Driver"); // no need to load drivers
			// No need to load class `com.mysql.jdbc.Driver' because
			// 1. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
			// 2. also, the new driver class is `com.mysql.cj.jdbc.Driver' :')
			DBConnectionCredentials creds = pgConnection;
			con = DriverManager.getConnection(creds.getConnection(), creds.getUsername(), creds.getPassword());
			try {
				PreparedStatement pst=con.prepareStatement("select * from trainees");
				ResultSet table = pst.executeQuery();
				while(table.next())
				{
					System.out.print(table.getFloat("per") + ", ");
					System.out.print(table.getString("sname") + ", ");
					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
