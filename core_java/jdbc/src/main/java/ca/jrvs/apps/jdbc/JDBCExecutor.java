package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sun.util.resources.cldr.so.CurrencyNames_so;

public class JDBCExecutor {

  public static void main(String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport",
        "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      //Creating DATA
//      Customer customer = new Customer();
//      customer.setFirstName("George");
//      customer.setLastName("Washington");
//      customer.setEmail("georgewashington@wh.gov");
//      customer.setPhone("(555) 555-6543");
//      customer.setAddress("1234 Main St");
//      customer.setCity("Mount Vernon");
//      customer.setState("VA");
//      customer.setZipcode("22121");
//      customerDAO.create(customer);

      //Insert DATA
//      Customer customer = customerDAO.findByID(1000);
//      System.out.println(customer.getFirstName() + " " + customer.getLastName());

      //Update Data
//      Customer customer = customerDAO.findByID(10000);
//      System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getEmail());
//      customer.setEmail("gwashington@wh.gov");
//      customer = customerDAO.update(customer);
//      System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getEmail());

      //DELETE data
      Customer customer = new Customer();
      customer.setFirstName("John");
      customer.setLastName("Adams");
      customer.setEmail("jadams@wh.gov");
      customer.setPhone("(555) 555-9845");
      customer.setAddress("1234 Main st");
      customer.setCity("Arlington");
      customer.setState("VA");
      customer.setZipcode("01234");

      Customer dbCustomer = customerDAO.create(customer);
      System.out.println(dbCustomer);
      dbCustomer = customerDAO.findByID(dbCustomer.getId());
      System.out.println(dbCustomer);
      dbCustomer.setEmail("john.adams@wh.gov");
      dbCustomer = customerDAO.update(dbCustomer);
      System.out.println(dbCustomer);
      customerDAO.delete(dbCustomer.getId());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
