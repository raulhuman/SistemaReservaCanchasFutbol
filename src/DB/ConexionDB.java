package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
	
	public static Connection MySQL8() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/db_centario?useSSL=false&allowPublicKeyRetrieval=true";
        
        
        return DriverManager.getConnection(url,"root","G4t02803.");
    }

}
