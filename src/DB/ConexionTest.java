package DB;

import java.sql.SQLException;

public class ConexionTest {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException 
    {
        ConexionDB.MySQL8();
        System.out.println("Conexion... OK!");
    }

}
