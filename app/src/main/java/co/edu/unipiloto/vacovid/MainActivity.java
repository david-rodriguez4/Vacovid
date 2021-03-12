package co.edu.unipiloto.vacovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView mensaje = (TextView) findViewById(R.id.textView);
        Intent i = this.getIntent();
        String nombre = i.getStringExtra("nombre");
        mensaje.setText("¡Bienvenido " + nombre + "!");
    }
}