package co.edu.unipiloto.vacovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        final TextView mensaje = (TextView) findViewById(R.id.bienvenido);
        Intent i = this.getIntent();
        String nombre = i.getStringExtra("nombre");
        mensaje.setText("¡Bienvenido " + nombre + "!");
    }
}