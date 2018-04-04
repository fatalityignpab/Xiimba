package ipn.proyectoipod.com.xiimba.BasedeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleBD {

    private String usuario, contrasenia;
    private Connection conexion = null; //Conexion de la Base de Datos
    private static String driver = "oracle.jdbc.OracleDriver"; //Nombre del driver
    private static String bd = "XE"; //Nombre de la Base de Datos
    private static String puerto = "1521"; //Nombre del puerto (1521 es de Oracle)
    private static String ip = MiIP.IP; //Nombre de la IP que da mi celular

    public OracleBD() {

    }

    public OracleBD(String usuario, String contrasenia) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void CrearConexionUsuario() throws SQLException {
        if (conexion == null || conexion.isClosed() == true) {
            try {
                Class.forName(driver);

                String url = "jdbc:oracle:thin:@" + ip + ":" + puerto + ":" + bd;
                conexion = DriverManager.getConnection(url, usuario, contrasenia);
            } catch (SQLException ex) {
                System.err.println("1.- OracleConexion.getComando: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.err.println("2.- OracleConexion.getComando: " + ex.getMessage());
            } catch (Exception ex){
                System.err.println("3.- OracleConexion.getComando: " + ex.getMessage());
            }
        }

    }

    public void CerrarConexion() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}
