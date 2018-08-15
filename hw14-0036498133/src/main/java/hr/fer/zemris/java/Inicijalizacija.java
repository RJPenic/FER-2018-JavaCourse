package hr.fer.zemris.java;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

@WebListener
/**
 * Context listener that sets database in initial state if needed and prepares
 * connection pool for more efficient usage of the application.
 * 
 * @author Rafael Josip Penić
 *
 */
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Initial pool size
	 */
	private static final int INITIAL_POOL_SIZE = 5;

	/**
	 * Minimal pool size
	 */
	private static final int MIN_POOL_SIZE = 5;

	/**
	 * Pool increment coefficient
	 */
	private static final int POOL_INCREMENT = 5;

	/**
	 * Maximum pool size
	 */
	private static final int MAX_POOL_SIZE = 20;

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties databaseSettings = new Properties();
		try {
			databaseSettings.load(Files
					.newInputStream(Paths.get(sce.getServletContext().getRealPath("WEB-INF/dbsettings.properties"))));
		} catch (IOException e) {
			throw new RuntimeException("Error while loading database settings.", e);
		}

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Error while initializing the pool.", e1);
		}

		try {
			setUpDatabase(cpds, databaseSettings);
		} catch (NullPointerException ex) {
			contextDestroyed(sce);
		}

		try {
			checkExistanceAndCreateTables(cpds);
			loadTablesIfEmpty(cpds, sce);
		} catch (SQLException e) {
			throw new RuntimeException("Error while creating tables.", e);
		} catch (IOException ex) {
			throw new RuntimeException("Error while loading tables.", ex);
		}

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method that checks if Polls and PollOptions tables are empty and fills them
	 * with needed entries if needed.
	 * 
	 * @param cpds
	 *            Connection pool used for preparing sql statements
	 * @throws IOException
	 *             in case of an error while reading from files while loading tables
	 * @throws SQLException
	 *             in case of an error while executing SQL commands
	 */
	private static void loadTablesIfEmpty(ComboPooledDataSource cpds, ServletContextEvent sce) throws IOException, SQLException {
		PreparedStatement ps = null;
		try (Connection con = cpds.getConnection()) {
			ps = con.prepareStatement("SELECT * FROM Polls");
			ResultSet rs = ps.executeQuery();

			try {
				if (rs != null && !rs.next()) {
					loadPoll("Glasanje za omiljeni bend",
							"Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!",
							"poll1-definitions.txt", cpds, sce);

					loadPoll("Glasanje za najljepšu sliku",
							"Od sljedećih slika, koja Vam je slika najljepša? Kliknite na link kako biste glasali!",
							"poll2-definitions.txt", cpds, sce);
				}
			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}
	}

	/**
	 * Method used for loading table entries from file to the PollOptions table and
	 * for loading given title-message pair into Polls table.
	 * 
	 * @param title
	 *            title of single Polls table entry
	 * @param message
	 *            message of Polls table entry
	 * @param pollOptionsFile
	 *            name of the file containing entries needed for initial loading of
	 *            PollOptions table
	 * @param cpds
	 *            Connection pool used for executing SQL queries
	 * @throws SQLException
	 *             in case of an error while executing SQL commands
	 * @throws IOException
	 *             in case of an error when reading from file
	 */
	private static void loadPoll(String title, String message, String pollOptionsFile, ComboPooledDataSource cpds, ServletContextEvent sce)
			throws SQLException, IOException {
		PreparedStatement pst = null;
		long id = 0;
		try (Connection con = cpds.getConnection()) {
			pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, title);
			pst.setString(2, message);

			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();

			try {
				if (rs != null && rs.next()) {
					id = rs.getLong(1);
				}
			} finally {
				rs.close();
			}
		} finally {
			pst.close();
		}

		loadPollOptions(pollOptionsFile, cpds, id, sce);
	}

	/**
	 * Loads poll options from given file to database
	 * 
	 * @param pollOptionsFile
	 *            name of the file from which the entries will be loaded
	 * @param cpds
	 *            connection pool used for controlling the database
	 * @param pollID
	 *            ID of the poll
	 * @throws IOException
	 *             in case of an error while reading from file
	 * @throws SQLException
	 *             in case of an error while
	 */
	private static void loadPollOptions(String pollOptionsFile, ComboPooledDataSource cpds, long pollID, ServletContextEvent sce)
			throws IOException, SQLException {
		List<String> pollOptionDef = Files.readAllLines(
				Paths.get(sce.getServletContext().getRealPath(
						"WEB-INF/" + pollOptionsFile)));

		PreparedStatement pst = null;
		try (Connection con = cpds.getConnection()) {
			for (String line : pollOptionDef) {
				String[] tempArray = line.trim().split("\t");

				if (line.trim().isEmpty() || tempArray.length < 2)
					continue;

				pst = con.prepareStatement(
						"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)");

				pst.setString(1, tempArray[0]);
				pst.setString(2, tempArray[1]);
				pst.setLong(3, pollID);
				pst.setLong(4, 0);// votes set to zero

				pst.executeUpdate();
			}
		} finally {
			pst.close();
		}
	}

	/**
	 * Checks if Polls and PollOptions tables exist and creates them if they don't
	 * 
	 * @param cpds
	 *            connection pool used for controlling the database
	 * @throws SQLException
	 *             in case of an error while executing SQL queries
	 */
	private static void checkExistanceAndCreateTables(ComboPooledDataSource cpds) throws SQLException {
		DatabaseMetaData meta = cpds.getConnection().getMetaData();
		ResultSet resPoll = meta.getTables(null, null, "POLLS", new String[] { "TABLE" });
		ResultSet resPollOpt = meta.getTables(null, null, "POLLOPTIONS", new String[] { "TABLE" });

		PreparedStatement ps = null;
		try (Connection con = cpds.getConnection()) {
			if (!resPoll.next()) {
				ps = con.prepareStatement(
						"CREATE TABLE Polls\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
								+ " title VARCHAR(150) NOT NULL,\r\n" + " message CLOB(2048) NOT NULL\r\n" + ")");
				ps.executeUpdate();
				ps.close();
			}

			if (!resPollOpt.next()) {
				ps = con.prepareStatement(
						"CREATE TABLE PollOptions\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
								+ " optionTitle VARCHAR(100) NOT NULL,\r\n" + " optionLink VARCHAR(150) NOT NULL,\r\n"
								+ " pollID BIGINT,\r\n" + " votesCount BIGINT,\r\n"
								+ " FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + ")");
				ps.executeUpdate();
				ps.close();
			}
		} finally {
			if(ps != null) {
				ps.close();
			}
		}
	}

	/**
	 * Sets up database in initial state using the given property object
	 * 
	 * @param cpds
	 *            connection pool
	 * @param databaseSettings
	 *            properties objects containing needed properties for setting up the
	 *            database
	 */
	private static void setUpDatabase(ComboPooledDataSource cpds, Properties databaseSettings) {
		String dbName = databaseSettings.getProperty("name");
		String host = databaseSettings.getProperty("host");
		String port = databaseSettings.getProperty("port");
		String user = databaseSettings.getProperty("user");
		String password = databaseSettings.getProperty("password");

		Objects.requireNonNull(dbName, "Database name is not present in properties file.");
		Objects.requireNonNull(host, "Host name is not present in properties file.");
		Objects.requireNonNull(port, "Port is not given in properties file.");
		Objects.requireNonNull(user, "User is not given in properties file.");
		Objects.requireNonNull(password, "Password is not given in properties file.");

		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName;

		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(user);
		cpds.setPassword(password);
		cpds.setInitialPoolSize(INITIAL_POOL_SIZE);
		cpds.setMinPoolSize(MIN_POOL_SIZE);
		cpds.setAcquireIncrement(POOL_INCREMENT);
		cpds.setMaxPoolSize(MAX_POOL_SIZE);
	}
}