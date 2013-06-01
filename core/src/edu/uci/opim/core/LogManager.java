package edu.uci.opim.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.uci.opim.core.exception.ExceptionToLog;

/**
 * 
 * Manager incharge of logging events to the database
 * 
 * @author bram
 * 
 */
public class LogManager {

	private Statement statement = null;
	private static SimpleDateFormat sqld = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	LogManager() {
		// TODO Auto-generated constructor stub

		// Connect to DB from config file

		// Check if the tables exist if not create the tables

		// setup handles to the tables

		Connection con = null;

		String url = "jdbc:mysql://localhost";
		String user = "testuser";
		String password = "test623";

		try {

			con = DriverManager.getConnection(url, user, password);

			statement = con.createStatement();
			statement.execute("USE testdb");

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(ExceptionToLog.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (statement != null) {
					statement.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(ExceptionToLog.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}

	public void logEvent(ExceptionToLog exp) {
		// TODO Auto-generated constructor stub
		// Log events to the DB

		try {
			statement
					.executeUpdate("INSERT INTO eventlog(`Time`, `Message`, `Info`, `Priority`)"
							+ "VALUES('"
							+ sqld.format(exp.time)
							+ "', '"
							+ exp.message
							+ "','"
							+ exp.obj.toString()
							+ "','"
							+ exp.pr.toString() + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
