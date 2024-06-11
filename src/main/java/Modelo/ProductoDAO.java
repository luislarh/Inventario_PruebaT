package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void agregarProducto(Producto producto, int idUsuario) {
        conexion con = null;
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            con = new conexion();
            cn = con.conectar();

            // Insertar en productos
            String sql = "INSERT INTO productos (nombre_producto, descripcion, precio, cantidad, estatus) VALUES (?, ?, ?, ?, ?)";
            ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, producto.getNombre_producto());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, 0); // Inicia en 0
            ps.setInt(5, 1); // Inicia en 1 (activo)
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int idProducto = 0;
            if (generatedKeys.next()) {
                idProducto = generatedKeys.getInt(1);
            }

            // Insertar en movimientos
            String sqlInsert = "INSERT INTO movimientos (id_usuario, id_producto, tipo_movimiento, cantidad, fecha_hora) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP())";
            ps = cn.prepareStatement(sqlInsert);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idProducto);
            ps.setString(3, "Entrada");
            ps.setInt(4, producto.getCantidad());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
    }

    public void actualizarCantidad(int idProducto, int cantidad) throws Exception {
        conexion con = null;
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            con = new conexion();
            cn = con.conectar();
            String sql = "UPDATE productos SET cantidad = ? WHERE id_producto = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar la cantidad del producto", e);
        } finally {
            try {
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
    }

    public int obtenerCantidadActual(int idProducto) throws Exception {
        conexion con = null;
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int cantidadActual = 0;

        try {
            con = new conexion();
            cn = con.conectar();
            String sql = "SELECT cantidad FROM productos WHERE id_producto = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();

            if (rs.next()) {
                cantidadActual = rs.getInt("cantidad");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al obtener la cantidad actual del producto", e);
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
        return cantidadActual;
    }

    public void cambiarEstatusProducto(int idProducto, int estatus) throws Exception {
        conexion con = null;
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            con = new conexion();
            cn = con.conectar();
            String sql = "UPDATE productos SET estatus = ? WHERE id_producto = ?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, estatus);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al cambiar el estatus del producto", e);
        } finally {
            try {
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
    }

    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        conexion con = null;
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = new conexion();
            cn = con.conectar();
            String sql = "SELECT * FROM productos";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre_producto(rs.getString("nombre_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setEstatus(rs.getInt("estatus"));
                productos.add(producto);
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

        return productos;
    }

    public List<Producto> listarProductosActivos() {
        List<Producto> productos = new ArrayList<>();
        Connection cn = null;
        conexion con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = new conexion();
            cn = con.conectar();
            String sql = "SELECT * FROM productos WHERE estatus = 1";  // 1 activo
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre_producto(rs.getString("nombre_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setEstatus(rs.getInt("estatus"));
                productos.add(producto);
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
        return productos;
    }

    public void sacarProducto(int idProducto, int cantidadASacar, int idUsuario) throws Exception {
    conexion con = null;
    Connection cn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = new conexion();
        cn = con.conectar();
        cn.setAutoCommit(false);  // Iniciar la transacción

        // Verificar existencia del usuario
        String sqlCheckUsuario = "SELECT COUNT(*) FROM usuarios WHERE id_usuario = ?";
        ps = cn.prepareStatement(sqlCheckUsuario);
        ps.setInt(1, idUsuario);
        rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1) == 0) {
            throw new Exception("El usuario no existe.");
        }
        rs.close();
        ps.close();

        // Verificar cantidad actual y estatus del producto
        String sqlCheckProducto = "SELECT cantidad, estatus FROM productos WHERE id_producto = ?";
        ps = cn.prepareStatement(sqlCheckProducto);
        ps.setInt(1, idProducto);
        rs = ps.executeQuery();
        int cantidadActual = 0;
        int estatus = 0;
        if (rs.next()) {
            cantidadActual = rs.getInt("cantidad");
            estatus = rs.getInt("estatus");
        } else {
            throw new Exception("El producto no existe.");
        }
        rs.close();
        ps.close();

        if (estatus != 1) {
            throw new Exception("El producto no está activo.");
        }

        if (cantidadASacar > cantidadActual) {
            throw new Exception("La cantidad a sacar supera la cantidad en inventario.");
        }

        // Actualizar cantidad del producto
        String sqlUpdate = "UPDATE productos SET cantidad = cantidad - ? WHERE id_producto = ? AND cantidad >= ?";
        ps = cn.prepareStatement(sqlUpdate);
        ps.setInt(1, cantidadASacar);
        ps.setInt(2, idProducto);
        ps.setInt(3, cantidadASacar);
        int rowsAffected = ps.executeUpdate();
        ps.close();

        if (rowsAffected == 0) {
            throw new Exception("La cantidad a sacar supera la cantidad en inventario.");
        }

        // Insertar movimiento
        String sqlInsert = "INSERT INTO movimientos (id_usuario, id_producto, tipo_movimiento, cantidad, fecha_hora) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP())";
        ps = cn.prepareStatement(sqlInsert);
        ps.setInt(1, idUsuario);
        ps.setInt(2, idProducto);
        ps.setString(3, "Salida");
        ps.setInt(4, cantidadASacar);
        ps.executeUpdate();

        // Commit de la transacción
        cn.commit();
    } catch (SQLException e) {
        if (cn != null) {
            try {
                cn.rollback();  // Revertir la transacción en caso de error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        throw new Exception("Error al sacar producto del inventario: " + e.getMessage(), e);
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
}


}
