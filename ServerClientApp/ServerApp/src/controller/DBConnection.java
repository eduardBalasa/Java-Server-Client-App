package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.CarsList;
import model.Car;

public class DBConnection {
	public Connection dbConnection = null;

	public DBConnection() {
		try {
			Class.forName("org.mariadb.jdbc.Driver").getConstructor().newInstance();
			dbConnection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cars_db", "root", "parola");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError" + ex.getErrorCode());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public synchronized void update(Car car) {
		if (dbConnection == null) {
			return;
		}
		PreparedStatement stmt = null;
		try {
			String sql = "UPDATE cars SET producer = ? , model = ? , price = ? , year = ? WHERE id = '" + car.getId() + "'";
			stmt = dbConnection.prepareStatement(sql);
			stmt.setString(1, car.getProducer());
			stmt.setString(2, car.getModel());
			stmt.setInt(3, car.getPrice());
			stmt.setInt(4, car.getYear());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
			}
		}
	}

	public synchronized void insert(Car car) {
		if (dbConnection == null) {
			return;
		}
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO cars VALUES (? ,? , ? , ? , ?)";
			stmt = dbConnection.prepareStatement(sql);
			stmt.setInt(1, car.getId());
			stmt.setString(2, car.getProducer());
			stmt.setString(3, car.getModel());
			stmt.setInt(4, car.getPrice());
			stmt.setInt(5, car.getYear());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
			}
		}
	}

	public synchronized void delete(Car car) {
		if (dbConnection == null) {
			return;
		}

		Statement stmt = null;
		try {
			stmt = dbConnection.createStatement();
			String sql = "DELETE FROM cars WHERE id  = '" + car.getId() + "'";
			stmt.executeUpdate(sql);
			String sql1 = "DELETE FROM cars WHERE producer  = '" + car.getProducer() + "'";
			stmt.executeUpdate(sql1);
			String sql2 = "DELETE FROM cars WHERE model  = '" + car.getModel() + "'";
			stmt.executeUpdate(sql2);
			String sql3 = "DELETE FROM car WHERE price = '" + car.getPrice() + "'";
			stmt.executeUpdate(sql3);
			String sql4 = "DELETE FROM cars WHERE year = '" + car.getYear() + "'";
			stmt.executeUpdate(sql4);
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	public synchronized CarsList retrieve() throws SQLException {

		Statement stmt = dbConnection.createStatement();
		String sql = "SELECT id, producer, model, price, year FROM cars";
		ResultSet rs = stmt.executeQuery(sql);
		Car car;
		CarsList list = new CarsList();

		while (rs.next()) {

			int id = rs.getInt("id");
			String producer = rs.getString("producer");
			String model = rs.getString("model");
			int price = rs.getInt("price");
			int year = rs.getInt("year");

			car = new Car(id, producer, model, price, year);
			list.Add(car);

		}
		rs.close();
		stmt.close();
		return list;
	}

	public synchronized void clear() {
		if (dbConnection == null)
			return;
		Statement stmt = null;
		try {
			stmt = dbConnection.createStatement();
			String sql2 = "SELECT * FROM cars";
			ResultSet rs = stmt.executeQuery(sql2);
			if (rs.isBeforeFirst()) {
				String sql = "DELETE FROM cars";
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
			}
		}
	}

}
