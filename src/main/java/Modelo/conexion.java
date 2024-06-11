
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author luis
 */
public class conexion {
    private final String baseDatos = "inventario";
    private final String servidor = "jdbc:mysql://localhost:3310/" + baseDatos;
    private final String usuario = "root";
    private final String clave = "";
    
    
    public Connection conectar(){
        Connection cn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(servidor, usuario, clave);
        } catch (Exception e) {
            System.out.println("Error al conectar" + e.getMessage());
        }
        return cn;
    }
    
}
