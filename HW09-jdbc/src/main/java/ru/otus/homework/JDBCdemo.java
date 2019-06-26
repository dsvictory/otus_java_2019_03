package ru.otus.homework;

import java.sql.*;

public class JDBCdemo {

	private static final String URL = "jdbc:h2:mem:";
	
	private final Connection connection;
	
	public static void main(String[] args) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		
		JDBCdemo demo = new JDBCdemo();
		
		System.out.println("USERS TEST!!!");
		
		JDBCTemplate<User> templateUser = new JDBCTemplateExample<User>(demo.getConnection());
	
		demo.createUserTable();
		
		User user1 = new User(1, "First", 100);
		User user2 = new User(2, "Second", 200);
		User user3 = new User(3, "Third", 300);
		
		templateUser.create(user1);
		templateUser.create(user2);
		templateUser.create(user3);
		
		User selectedUser1 = templateUser.load(1, User.class);
		User selectedUser2 = templateUser.load(2, User.class);
		User selectedUser3 = templateUser.load(3, User.class);
		
		System.out.println(selectedUser1);
		System.out.println(selectedUser2);
		System.out.println(selectedUser3);
		
		user1.setName("CHANGED!!!");
		user2.setAge(9000);
		user3.setName("Yeah!");
		user3.setAge(805);
		
		templateUser.update(user1);
		templateUser.update(user2);
		templateUser.update(user3);
		
		User selectedChangedUser1 = templateUser.load(1, User.class);
		User selectedChangedUser2 = templateUser.load(2, User.class);
		User selectedChangedUser3 = templateUser.load(3, User.class);
		
		System.out.println(selectedChangedUser1);
		System.out.println(selectedChangedUser2);
		System.out.println(selectedChangedUser3);
		
		
		System.out.println();
		System.out.println("ACCOUNTS TEST!!!");
		
		JDBCTemplate<Account> templateAccount = new JDBCTemplateExample<Account>(demo.getConnection());
		
		demo.createAccountTable();
		
		Account account1 = new Account(1, "First", 100);
		Account account2 = new Account(2, "Second", 200);
		Account account3 = new Account(3, "Third", 300);
		
		templateAccount.create(account1);
		templateAccount.create(account2);
		templateAccount.create(account3);
		
		Account selectedAccount1 = templateAccount.load(1, Account.class);
		Account selectedAccount2 = templateAccount.load(2, Account.class);
		Account selectedAccount3 = templateAccount.load(3, Account.class);
		
		System.out.println(selectedAccount1);
		System.out.println(selectedAccount2);
		System.out.println(selectedAccount3);
		
		account1.setType("CHANGED!!!");
		account2.setRest(9000);
		account3.setType("Yeah!");
		account3.setRest(805);
		
		templateAccount.update(account1);
		templateAccount.update(account2);
		templateAccount.update(account3);
		
		System.out.println(account1);
		System.out.println(account2);
		System.out.println(account3);
		
	}
	
	public JDBCdemo() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        this.connection.setAutoCommit(false);
    }
	
	private void createUserTable() throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(
        		"create table User("
        		+ "id bigint(20) NOT NULL auto_increment,"
        		+ "name varchar(255),"
        		+ "age int(3))")) {
            pst.executeUpdate();
        }
    }
	
	private void createAccountTable() throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(
        		"create table Account("
        		+ "no bigint(20) NOT NULL auto_increment,"
        		+ "type varchar(255),"
        		+ "rest number)")) {
            pst.executeUpdate();
        }
    }

	public Connection getConnection() {
		return connection;
	}
	
}


