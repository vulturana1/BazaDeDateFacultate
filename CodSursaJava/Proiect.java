
import java.sql.*;

public class Proiect {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception ex) {
			System.err.println("An Exception occured during JDBC Driver loading." + 
					" Details are provided belo w:");
			ex.printStackTrace(System.err);
		}
		Connection connection = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		;
		try { 
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Proiect?useLegacyDatetimeCode=false&serverTimezone=UTC","root","@parola123");
			Logare login=new Logare(connection);
			
		}
		catch(SQLException sqlex) {
			System.err.println("An SQL Exception occured. Details below:");
			sqlex.printStackTrace(System.err);
		} 
	}
}
