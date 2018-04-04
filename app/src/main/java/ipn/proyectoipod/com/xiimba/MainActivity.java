package ipn.proyectoipod.com.xiimba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ipn.proyectoipod.com.xiimba.BasedeDatos.OracleBD;

public class MainActivity extends AppCompatActivity {

    EditText txtUsuario, txtPass;
    Button btnIniciar, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarComponentes();
        accionesbotones();
    }

    private void iniciarComponentes(){
        txtUsuario = (EditText)findViewById(R.id.Inicio_txtUsuario);
        txtPass = (EditText)findViewById(R.id.Inicio_txtPass);
        btnIniciar = (Button)findViewById(R.id.Inicio_btnIniciar);
        btnRegistro = (Button)findViewById(R.id.Inicio_btnRegistrar);
    }

    private void accionesbotones(){
        btnIniciar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        irInicio();
                    }
                }
        );
    }

    private void irInicio(){
        if (txtUsuario.getText().toString().trim().isEmpty()){
            txtUsuario.setError("Campo Vacio");
            txtUsuario.requestFocus();
        } else if (txtPass.getText().toString().trim().isEmpty()) {
            txtPass.setError("Campo Vacio");
            txtPass.requestFocus();
        } else {
            boolean verdad = iniciarSesion();
            if (verdad){
                Intent menu = new Intent(MainActivity.this ,MenuXiimba.class);
                this.startActivity(menu);
                finish();
            } else {
                txtUsuario.setText("");
                txtPass.setText("");
            }
        }
    }

    private boolean iniciarSesion(){
        String boleta = "";
        String nombre = "";
        String correo = "";
        try {
            OracleBD oracle = new OracleBD(txtUsuario.getText().toString().trim(), txtPass.getText().toString().trim());
            oracle.CrearConexionUsuario();
            Connection con = oracle.getConexion();
            Statement stmt = con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT ad.boleta, ad.nombre ||' '|| ad.appaterno ||' '|| ad.apmaterno AS NombreCompleto, ad.correo FROM adminminifb.alumnos ad WHERE ad.boleta = "+txtUsuario.getText().toString().trim());
            while ( rs.next() ) {
                boleta = rs.getString("boleta");
                nombre = rs.getString("NombreCompleto");
                correo = rs.getString("correo");
            }
            registrarUsuarioActivo(boleta, nombre, correo);
            oracle.CerrarConexion();
            return true;
        } catch (SQLException ex) {
            Toast.makeText(this, "Error interno SQL", Toast.LENGTH_SHORT).show();
            return false;
        } catch (Exception ex){
            Toast.makeText(this, "Usuario y Contraseña no validos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void registrarUsuarioActivo(String boleta, String nombre, String correo){
        SharedPreferences activo = getSharedPreferences("UsuarioActivo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editreg = activo.edit();
        editreg.putString("Boleta", boleta);
        editreg.putString("Nombre", nombre);
        editreg.putString("Correo", correo); // Ahi se tiene que hacer una consulta antes del usuario (SELECT correo FROM alumno WHERE usuario = '')
        editreg.putString("Pass", txtPass.getText().toString().trim()); //ALTAMENTE PELIGROSO
        editreg.commit();
    }

}
