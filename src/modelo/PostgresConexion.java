package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConexion {
    private Connection conexion;

    public Connection conectar() {
        String host = "localhost";
        String puerto = "5432";
        String nombreBD = "inmueble";
        String usuario = "postgres";
        String pass = "";
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://" + host + ":" + puerto + "/" + nombreBD;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, pass);
            System.out.println("Conectado a la base de datos PostgreSQL");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexión a PostgreSQL: " + e.getMessage());
        }
        return conexion;
    }

    public void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión PostgreSQL cerrada");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
