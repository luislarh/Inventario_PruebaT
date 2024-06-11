
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class DAOUsuario extends conexion{
    
     public usuario identificar(usuario user) throws Exception {
        usuario usu = null;
        conexion con;
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        String sql = "SELECT u.id_usuario, r.nombre_rol FROM usuarios u "
                     + "INNER JOIN roles r ON u.id_rol = r.id_rol "
                     + "WHERE u.email = '"+ user.getEmail() + "' " 
                     + " AND u.password = '"+ user.getPassword() + "'";
         System.out.println("consulta: " + sql );
        con = new conexion();
        try {
            // conexión
            cn = con.conectar();
            
            // consulta
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            // Procesar 
            if (rs.next() == true){
                usu = new usuario();
                usu.setId_usuario(rs.getInt("id_usuario"));
                usu.setEmail(user.getEmail());
                usu.setId_rol(new roles());
                usu.getId_rol().setNombre_rol(rs.getString("nombre_rol"));
//              usu.setEstatus(1);
                
            }
            
        } catch (Exception e) {
            System.out.println("Error" +  e.getMessage());
        } finally {
            // Cerrar 
            if (rs != null && rs.isClosed() == false){
                rs.close();
            }
            rs = null;
            if(cn != null & cn.isClosed() == false){
                cn.close();
            }
            st = null;
            if(cn != null & cn.isClosed() == false){
                cn.close();
            }
            cn = null;
        }
        
        return usu;
    }
    
}
