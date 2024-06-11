
package Modelo;


public class comprobar {
    public static void main(String[] args) {
        conexion  c =  new conexion();
        if(c.conectar() != null){
            System.out.println("Conexion Exitosa");
        }else{
            System.out.println("Conexion Fallida");
        }
    }
}
