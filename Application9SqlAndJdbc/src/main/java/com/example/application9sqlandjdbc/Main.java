package com.example.application9sqlandjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * We learnt:
 * 1. How to connect to a database using JDBC.
 * 2. Different database connections, yet same code to connect.
 * 3. How to execute a query and get results.
 * 
 * PTR: We need maven dependencies for different databases.
 */
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

            // QQ- what's the point of Class.forName()
            // Ans- loads the class - https://stackoverflow.com/questions/15039265/what-exactly-does-this-do-class-fornamecom-mysql-jdbc-driver-newinstance

            // QQ- how to add drivers?
            // Ans- Maven dependency. Check pom.xml for this, it contains all 3 db's maven dependency.
            // eg. <dependency><groupId>com.mysql</groupId><artifactId>mysql-connector-j</artifactId><version>8.0.33</version></dependency>

            // QQ- PreparedStatement vs CallableStatement?
            // Ans- PreparedStatement is used to execute parameterized SQL queries, while CallableStatement is used to execute stored procedures in the database.
            // ref: https://www.geeksforgeeks.org/java/difference-between-preparedstatement-and-callablestatement/

            DBConnectionCredentials creds = pgConnection;
            con = DriverManager.getConnection(creds.getConnection(), creds.getUsername(), creds.getPassword());
            try {
                PreparedStatement pst = con.prepareStatement("select * from trainees");
                ResultSet table = pst.executeQuery();
                while (table.next()) {
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
/**
 * In Java's JDBC API, execute(), executeUpdate(), and executeQuery() are methods of the Statement interface used to execute SQL statements, each with a specific purpose and return type:
 * 
 * executeQuery(String sql):
 *  Purpose: Exclusively used for executing SELECT statements, which retrieve data from the database.
 *  Return Type: Returns a ResultSet object, which contains the data retrieved by the query. It will never return null, even if no records match the query.
 *  Usage: When you expect to get a set of results back from the database.
 * 
 * executeUpdate(String sql):
 *  Purpose: Used for executing Data Manipulation Language (DML) statements like INSERT, UPDATE, and DELETE, as well as Data Definition Language (DDL) statements like CREATE TABLE and DROP TABLE.
 *  Return Type: Returns an int representing the number of rows affected by the SQL statement (for DML operations) or 0 (for DDL operations or DML operations that affected zero rows).
 *  Usage: When you are modifying the database and need to know how many rows were changed.
 * 
 * execute(String sql):
 *  Purpose: A general-purpose method that can execute any type of SQL statement, including SELECT, INSERT, UPDATE, DELETE, and DDL statements. It is particularly useful when the type of SQL statement is not known at compile time or when executing stored procedures that might return a ResultSet or an update count.
 *  Return Type: Returns a boolean value:
 *      true if the first result is a ResultSet object (typically from a SELECT statement).
 *      false if the first result is an update count or if there are no results (e.g., from an INSERT, UPDATE, DELETE, or DDL statement).
 *  Usage: After calling execute(), you need to use getResultSet() to retrieve the ResultSet if true was returned, or getUpdateCount() to retrieve the update count if false was returned. This method is more flexible but requires additional logic to handle the different potential outcomes.
 */