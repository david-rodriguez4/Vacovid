package co.edu.unipiloto.vacovid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Paciente extends AppCompatActivity {
    static EditText fechaInscripcion;

    public static void setFechaInscripcion(int year, int month, int day) {
        fechaInscripcion.setText(year + "-" + month + "-" + day);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        final TextView mensaje = (TextView) findViewById(R.id.textView);
        Intent i = this.getIntent();
        String nombre = i.getStringExtra("nombre");
        String rol = i.getStringExtra("rol");
        mensaje.setText("¡Bienvenido paciente!");
        fechaInscripcion = (EditText) findViewById(R.id.etFechaInscripcion);
        fechaInscripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        Button btnFI = (Button) findViewById(R.id.btn_fechaInscripcion);
        btnFI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = fechaInscripcion.getText().toString();
                if (fn.equals("")) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Paciente.this);
                    alerta.setMessage("Debe ingresar una fecha").setNegativeButton("Reintentar", null).create().show();
                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Paciente.this);
                    alerta.setMessage("Se inscribió correctamente").setNegativeButton("Aceptar", null).create().show();
                    /*
                    Response.Listener<String> respuesta = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonRespuesta = new JSONObject(response);
                                boolean ok = jsonRespuesta.getBoolean("success");
                                if (ok == true) {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Paciente.this);
                                    alerta.setMessage("Se inscribió correctamente").setNegativeButton("Reintentar", null).create().show();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Paciente.this);
                                    alerta.setMessage("Fallo en la inscripción").setNegativeButton("Reintentar", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };

                    RegistroRequest r = new RegistroRequest(id, id_paciente, id_personal, fecha_inscripcion, respuesta);
                    RequestQueue cola = Volley.newRequestQueue(Paciente.this);
                    cola.add(r);
                    */
                }
            }
        });
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}