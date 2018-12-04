package gui.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	public static Connection getConexao() throws SQLException {
		Connection connection = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost/dbsodontosystem";
		String login = "root";
		String senha = "s0d0nt0.2015";
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, senha);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	
	
}
