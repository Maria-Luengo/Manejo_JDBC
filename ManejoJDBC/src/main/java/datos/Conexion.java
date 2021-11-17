package datos;

import com.mysql.cj.xdevapi.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class Conexion {

    //esta clae siempre la tendré 
    /*definimos 3 propiedade
    privadas estáticas y finales
    URL
    USUARIO
    CONTRASEÑA
     */
    
    //Daatos de conexión y los PARÁMETROS PARA LA CONEXIÓN a partir del ?
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test_java?"
            + "useSSL=false&useTimezone=true&serverTimezone=UTC&"
            + "allowPublicKeyRetrieval=true";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";//OJO!! 

    //METODO PARA ESTABLECER LA CONEXION
    public static Connection getConnection() throws SQLException {
        //devuelve una conexion
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    //METODO PARA CERRAR LA CONEXION
    public static void close(ResultSet rs) throws SQLException {
        rs.close();
    }

    public static void close(Statement stmt) throws SQLException {
        stmt.close();
    }

    public static void close(PreparedStatement stmt) throws SQLException {
        stmt.close();
    }

    public static void close(Connection conn) throws SQLException {
        conn.close();
    }
}
