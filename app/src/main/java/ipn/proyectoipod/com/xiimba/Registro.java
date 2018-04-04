package ipn.proyectoipod.com.xiimba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {

    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        spin = (Spinner) findViewById(R.id.spin);

        //1) Crear los Datos
        List<String> items = new ArrayList<>();
        items.add("Guadalajara");
        items.add("Ciudad de Mexico");
        items.add("Guanajuato");

        //2)Crear el adaptador con los datos
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items); // Generica de Android

        //3)Referenciar el Adaptador con el control
        spin.setAdapter(adap);

        spin.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        ); // IMPORTANTE
    }
}
