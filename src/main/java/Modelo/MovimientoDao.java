
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDao {
    
    public List<movimientos> listarMovimientos() {
        List<movimientos> movimientos = new ArrayList<>();
        conexion con = null;
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = new conexion();
            cn = con.conectar();
            String sql = "SELECT * FROM movimientos";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                movimientos movimiento = new movimientos();
                movimiento.setId_movimiento(rs.getInt("id_movimiento"));
                movimiento.setId_usuario(rs.getInt("id_usuario"));
                movimiento.setId_producto(rs.getInt("id_producto"));
                movimiento.setTipo_movimiento(rs.getString("tipo_movimiento"));
                movimiento.setCantidad(rs.getInt("cantidad"));
                movimiento.setFecha_hora(rs.getTimestamp("fecha_hora"));
                movimientos.add(movimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return movimientos;
    }

}
