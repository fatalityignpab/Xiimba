package ipn.proyectoipod.com.xiimba;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtUsuario, txtPass;
    Button btnIniciar, btnRegistro;
    SharedPreferences usuario, pass;

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

    }

}
