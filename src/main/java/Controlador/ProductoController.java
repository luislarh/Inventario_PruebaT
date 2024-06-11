package Controlador;

import Modelo.Producto;
import Modelo.ProductoDAO;
import Modelo.usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ProductoController", urlPatterns = {"/ProductoController"})
public class ProductoController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String accion = request.getParameter("accion");
        ProductoDAO productoDAO = new ProductoDAO();
         HttpSession session = request.getSession();
        usuario usuario = (session != null) ? (usuario) session.getAttribute("usuario") : null;
        usuario almacenista = (session != null) ? (usuario) session.getAttribute("almacenista") : null;
       
        int idUsuario = 0;
        if(usuario != null){
            idUsuario = usuario.getId_usuario();
        }else if(almacenista != null){
            idUsuario = almacenista.getId_usuario();
        }

        try {
            if (accion != null) {
                switch (accion) {
                    case "agregar":
                        agregarProducto(request, response, productoDAO, idUsuario);
                        break;
                    case "listar":
                        listarProductos(request, response, productoDAO);
                        break;
                    case "mostrarFormularioAgregar":
                        mostrarFormularioAgregar(request, response);
                        break;
                    case "cambiarEstatus":
                        cambiarEstatusProducto(request, response, productoDAO);
                        break;
                    case "actualizarCantidad":
                        actualizarCantidadProducto(request, response, productoDAO);
                        break;
                    case "sacar":
                        sacarProducto(request, response, productoDAO, idUsuario);
                        break;
                    case "listarActivos":
                        listarProductosActivos(request, response, productoDAO);
                        break;
                    default:
                        response.sendRedirect("index.jsp");
                }
            } else {
                response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarFormularioAgregar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/vistas/agregarProducto.jsp").forward(request, response);
    }

    private void agregarProducto(HttpServletRequest request, HttpServletResponse response, ProductoDAO productoDAO, int idUsuario) throws Exception {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precioParam = request.getParameter("precio");
        double precio = 0.0;
        if (precioParam != null && !precioParam.isEmpty()) {
            precio = Double.parseDouble(precioParam);
        };

        Producto producto = new Producto();
        producto.setNombre_producto(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCantidad(0); // inicia 0
        producto.setEstatus(1); // inicia 1 (activo)

        productoDAO.agregarProducto(producto, idUsuario);
        response.sendRedirect("ProductoController?accion=listar");
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response, ProductoDAO productoDAO) throws Exception {
        List<Producto> productos = productoDAO.listarProductos();
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/vistas/listarProductos.jsp").forward(request, response);
    }

    private void cambiarEstatusProducto(HttpServletRequest request, HttpServletResponse response, ProductoDAO productoDAO) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int estatus = Integer.parseInt(request.getParameter("estatus"));
        try {
            productoDAO.cambiarEstatusProducto(id, estatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("ProductoController?accion=listar");
    }

    private void actualizarCantidadProducto(HttpServletRequest request, HttpServletResponse response, ProductoDAO productoDAO) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int nuevaCantidad = Integer.parseInt(request.getParameter("cantidad"));

        try {
            int cantidadActual = productoDAO.obtenerCantidadActual(id);

            if (nuevaCantidad >= cantidadActual) {
                productoDAO.actualizarCantidad(id, nuevaCantidad);
                request.setAttribute("mensaje", "Cantidad actualizada con éxito.");
            } else {
                request.setAttribute("error", "No se puede disminuir la cantidad del inventario.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar la cantidad: " + e.getMessage());
        } finally {

            request.getRequestDispatcher("ProductoController?accion=listar").forward(request, response);
        }
    }

    private void sacarProducto(HttpServletRequest request, HttpServletResponse response, ProductoDAO productoDAO, int idUsuario) throws ServletException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        try {
            int cantidadActual = productoDAO.obtenerCantidadActual(idProducto);

            if (cantidad <= 0) {
                request.setAttribute("error", "La cantidad debe ser mayor que cero.");
            } else if (cantidad > cantidadActual) {
                request.setAttribute("error", "No se puede sacar una cantidad mayor de la disponible en el inventario.");
            } else {
                productoDAO.sacarProducto(idProducto, cantidad, idUsuario);
                request.setAttribute("mensaje", "Producto sacado con éxito.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Parámetros inválidos.");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("ProductoController?accion=listarActivos").forward(request, response);
    }



    private void listarProductosActivos(HttpServletRequest request, HttpServletResponse response, ProductoDAO productoDAO) throws Exception {
        List<Producto> productosActivos = productoDAO.listarProductosActivos();
        request.setAttribute("productos", productosActivos);
        request.getRequestDispatcher("/vistas/listarActivos.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "ProductoController";
    }// </editor-fold>

}
