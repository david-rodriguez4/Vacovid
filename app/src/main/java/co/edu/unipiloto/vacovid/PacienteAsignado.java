package co.edu.unipiloto.vacovid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class PacienteAsignado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientesi);
        final TextView mensaje = (TextView) findViewById(R.id.textView);
        final TextView msgcita1 = (TextView) findViewById(R.id.txtcita1);
        final TextView msgcita2 = (TextView) findViewById(R.id.txtcita2);
        Intent i = this.getIntent();
        String nombre = i.getStringExtra("nombre");
        String cita1 = i.getStringExtra("fechauno");
        String cita2 = i.getStringExtra("fechados");
        String hora = i.getStringExtra("hora");
        mensaje.setText("¡Bienvenido " + nombre + "!");
        msgcita1.setText("Primera cita: " + cita1 + "\nHora: " + hora);
        msgcita2.setText("Segunda cita: " + cita2 + "\nHora: " + hora);
    }
}