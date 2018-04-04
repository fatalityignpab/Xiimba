package ipn.proyectoipod.com.xiimba.Utilidad;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.regex.Pattern;

import ipn.proyectoipod.com.xiimba.BasedeDatos.OracleBD;

public class Validaciones {

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9\\._-]+@[a-zA-Z0-9-]{2,}[.][a-zA-Z]{2,4}$";
    public static final String REGEX_EMAIL2 = "^[a-zA-Z0-9\\._-]{0,}([.]?[a-zA-Z0-9\\._-]{1,})[@](gmail.com|hotmail.com|yahoo.com.mx|outlook.com)$"; //live
    public static final String REGEX_CAPITALIZAR_NOMBRE = "^[a-zA-Z0-9\\._-]+@[a-zA-Z0-9-]{2,}[.][a-zA-Z]{2,4}$"; //bUSCAR EXCEPCION COMO DE LA ROSA, DE LA O, ETC.


    public Validaciones() {
    }

    private String construirREGEXBOLETAUniversidad() {
        String REGEX_BOLETA = "^("; //"^(2017|2016|2015)(60)[0-9]$"
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        for (int i = 2007; i < anio + 1; i++) {
            REGEX_BOLETA += String.valueOf(i);
            REGEX_BOLETA += "|";
        }
        REGEX_BOLETA = REGEX_BOLETA.substring(0, REGEX_BOLETA.length() - 1);
        REGEX_BOLETA += ")[0-9]+$";
        return REGEX_BOLETA;
    }

    public boolean validarCorreo(String correo) {
        Pattern patron = Pattern.compile(REGEX_EMAIL2);
        if (patron.matcher(correo).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarBoletaEstructura(String boleta) {
        String verifica = construirREGEXBOLETAUniversidad();
        Pattern pat = Pattern.compile(verifica);
        if (pat.matcher(boleta).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarBoletaTamanio(String boleta) {
        if (boleta.length() == 10) {
            return true;
        } else {
            return false;
        }
    }

    public boolean boletaExistente(String boleta) {
        try {
            OracleBD oracle = new OracleBD("ADMINMINIFB", "oracle123");
            oracle.CrearConexionUsuario();
            Connection con = oracle.getConexion();
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT boleta " +
                    "FROM adminminifb.alumnos " +
                    "ORDER BY boleta ASC");
            while (rs.next()) {
                if (boleta.equals(String.valueOf(rs.getString("boleta")))) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL");
        } catch (Exception ex) {
            System.out.println("Error de conexión");
        }
        return true;
    }

    public boolean correoExistente(String correo) {
        try {
            OracleBD oracle = new OracleBD("ADMINMINIFB", "oracle123");
            oracle.CrearConexionUsuario();
            Connection con = oracle.getConexion();
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT correo " +
                    "FROM adminminifb.alumnos " +
                    "ORDER BY correo ASC");
            while (rs.next()) {
                if (correo.equals(String.valueOf(rs.getString("correo")))) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL");
        } catch (Exception ex) {
            System.out.println("Error de conexión");
        }
        return true;
    }

    public boolean validarNombreTamanio(String nombre){
        if(nombre.length() > 3){
            return true;
        } else {
            return false;
        }
    }
}
