package ipn.proyectoipod.com.xiimba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtUsuario, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarComponentes();
    }

    private void iniciarComponentes(){
        txtUsuario = (EditText)findViewById(R.id.Inicio_txtUsuario);
        txtPass = (EditText)findViewById(R.id.Inicio_txtPass);
    }
}
