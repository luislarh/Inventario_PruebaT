
package Modelo;

/**
 *
 * @author luis
 */
public class usuario {
    private int id_usuario;
    private String nombre;
    private String email;
    private String password;
    private roles  id_rol;
    private int estatus;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public roles getId_rol() {
        return id_rol;
    }

    public void setId_rol(roles id_rol) {
        this.id_rol = id_rol;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
    
    
}
