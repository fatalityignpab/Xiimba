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
                        //irInicio();
                        testInicio();
                    }
                }
        );

        btnRegistro.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        irRegistro();
                    }
                }
        );
    }

    private void testInicio(){
        String usuario = txtUsuario.getText().toString().trim();
        String pass = txtPass.getText().toString().trim();
        if (txtUsuario.getText().toString().trim().isEmpty()){
            txtUsuario.setError("Campo Vacio");
            txtUsuario.requestFocus();
        } else if (txtPass.getText().toString().trim().isEmpty()) {
            txtPass.setError("Campo Vacio");
            txtPass.requestFocus();
        } else {
            //if (txtUsuario.getText().toString().trim() == "root" && txtPass.getText().toString().trim() == "root"){
                Intent menu = new Intent(MainActivity.this ,MenuXiimba.class);
                this.startActivity(menu);
                //finish();
            //} else {
            //    txtUsuario.setText("");
            //    txtPass.setText("");
            //}
        }
    }

    private void irRegistro(){
        Intent menu = new Intent(MainActivity.this ,Registro.class);
        this.startActivity(menu);
        //finish();
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
                //finish();
            } else {
                txtUsuario.setText("");
                txtPass.setText("");
            }
        }
    }

    private boolean iniciarSesion(){
        String nombrecompleto = "";
        String correo = "";
        String lugar = "";
        String usuario = txtUsuario.getText().toString().trim();
        String pass = txtPass.getText().toString().trim();
        try {
            OracleBD oracle = new OracleBD(usuario, pass);
            oracle.CrearConexionUsuario();
            Connection con = oracle.getConexion();
            Statement stmt = con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT\n" +
                    "    xi.nombre || ' ' ||\n" +
                    "    xi.appaterno || ' ' ||\n" +
                    "    xi.apmaterno AS NombreCompleto,\n" +
                    "    xi.correo,\n" +
                    "    xi.cvelugar\n" +
                    "FROM\n" +
                    "    xiimba_admin.turista xi\n" +
                    "WHERE\n" +
                    "    XI.CVETURISTA = "+txtUsuario.getText().toString().trim());
            while ( rs.next() ) {
                nombrecompleto = rs.getString("NOMBRECOMPLETO");
                correo = rs.getString("CORREO");
                lugar = rs.getString("CVELUGAR");
            }
            registrarUsuarioActivo(nombrecompleto, correo, lugar);
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

    public void registrarUsuarioActivo(String nombrecompleto, String correo, String lugar){
        SharedPreferences activo = getSharedPreferences("UsuarioActivo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editreg = activo.edit();
        editreg.putString("Nombre", nombrecompleto);
        editreg.putString("Correo", correo);
        editreg.putString("Lugar", lugar); // Ahi se tiene que hacer una consulta antes del usuario (SELECT correo FROM alumno WHERE usuario = '')
        editreg.putString("Pass", txtPass.getText().toString().trim()); //ALTAMENTE PELIGROSO
        editreg.commit();
    }

}
