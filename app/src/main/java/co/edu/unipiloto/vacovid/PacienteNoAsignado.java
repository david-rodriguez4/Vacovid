package co.edu.unipiloto.vacovid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class PacienteNoAsignado extends AppCompatActivity implements View.OnClickListener {
    EditText fechaInscripcion, horaInscripcion;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacienteno);
        final TextView mensaje = (TextView) findViewById(R.id.textView);
        Intent i = this.getIntent();
        String nombre = i.getStringExtra("nombre");
        String rol = i.getStringExtra("rol");
        String documento = i.getStringExtra("documento");
        String fecha =  i.getStringExtra("fecha");
        mensaje.setText("¡Bienvenido " + nombre + "!");
        fechaInscripcion = (EditText) findViewById(R.id.etFechaInscripcion);
        horaInscripcion = (EditText) findViewById(R.id.etHoraInscripcion);
        fechaInscripcion.setOnClickListener(this);
        horaInscripcion.setOnClickListener(this);
        Button btnFI = (Button) findViewById(R.id.btn_fechaInscripcion);
        btnFI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = fechaInscripcion.getText().toString();
                String hn = horaInscripcion.getText().toString();
                if (fn.equals("") || hn.equals("")) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(PacienteNoAsignado.this);
                    alerta.setMessage("Debe ingresar una fecha y hora").setNegativeButton("Reintentar", null).create().show();
                } else {
                    Response.Listener<String> respuesta = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonRespuesta = new JSONObject(response);
                                boolean ok = jsonRespuesta.getBoolean("success");
                                if (ok == true) {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(PacienteNoAsignado.this);
                                    alerta.setMessage("Se inscribió correctamente").setNegativeButton("Aceptar", null).create().show();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(PacienteNoAsignado.this);
                                    alerta.setMessage("Fallo en la inscripción").setNegativeButton("Reintentar", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };

                    InscripcionRequest r = new InscripcionRequest(fn, hn, documento, respuesta);
                    RequestQueue cola = Volley.newRequestQueue(PacienteNoAsignado.this);
                    cola.add(r);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == fechaInscripcion) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            fechaInscripcion.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == horaInscripcion) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            horaInscripcion.setText(hourOfDay + ":" + minute);
                        }

                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}