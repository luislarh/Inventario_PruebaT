
package Controlador;

import Modelo.DAOUsuario;
import Modelo.usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author luis
 */
@WebServlet(name= "srvUsuario", urlPatterns = {"/srvUsuario"})
public class srvUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String accion = request.getParameter("accion");
        try {
            if(accion != null){
                switch(accion){
                    case  "verificar":
                        verificar(request, response);
                        break;
                    case "cerrar":
                        cerrarsession(request, response);
                    default:
                        response.sendRedirect("identificar.jsp");
                }
                
            }else{
                response.sendRedirect("identificar.jsp");
            }
        } catch (Exception e) {
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/mensaje.jsp").forward(request, response); 
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
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
        return "Short description";
    }// </editor-fold>

    private void verificar(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession sesion;
        DAOUsuario dao;
        usuario usuario;
        usuario = this.obtenerUsuario(request);
        dao = new DAOUsuario();
        usuario = dao.identificar(usuario);
        
        if(usuario != null && usuario.getId_rol().getNombre_rol().equals("Administrador")){
            sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            request.setAttribute("mensaje", "Bienvenido al inventario");
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/index.jsp").forward(request, response); 
        }else if(usuario != null && usuario.getId_rol().getNombre_rol().equals("Almacenista")){
            sesion = request.getSession();
            sesion.setAttribute("almacenista", usuario);
            this.getServletConfig().getServletContext().getRequestDispatcher("/vistas/formAlmacenista.jsp").forward(request, response); 
        }else{
            request.setAttribute("mensaje", "No registrado, credenciales incorrectas");
            request.getRequestDispatcher("identificar.jsp").forward(request, response);
        }
        

    }

    private void cerrarsession(HttpServletRequest request, HttpServletResponse response) throws Exception{
       HttpSession sesion = request.getSession();
       sesion.setAttribute("usuario", null);
       sesion.invalidate();
       response.sendRedirect("identificar.jsp");
    }

    private usuario obtenerUsuario(HttpServletRequest request) {
        usuario u = new usuario();
        u.setEmail(request.getParameter("email"));
        u.setPassword(request.getParameter("password"));
        return u;
        
    }
}

