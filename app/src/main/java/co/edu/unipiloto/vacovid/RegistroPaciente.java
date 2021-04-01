package co.edu.unipiloto.vacovid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RegistroPaciente extends AppCompatActivity implements View.OnClickListener {

    EditText txtFN;
    private int mYear, mMonth, mDay;
    private int checkGenero = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);
        EditText txtnombre = (EditText) findViewById(R.id.etenombre);
        EditText txtcorreo = (EditText) findViewById(R.id.etecorreo);
        EditText txtclave = (EditText) findViewById(R.id.etecontraseña);
        EditText txtnumero = (EditText) findViewById(R.id.etenumero);
        EditText txtdocumento = (EditText) findViewById(R.id.etedocumento);
        txtFN = (EditText) findViewById(R.id.etFechaNacimiento);
        Spinner ocupacion = (Spinner) findViewById(R.id.ocupacion);
        TextView login = (TextView) findViewById(R.id.txtsignin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(RegistroPaciente.this, Login.class);
                RegistroPaciente.this.startActivity(login);
                RegistroPaciente.this.finish();
            }
        });
        Button btnRegistro = (Button) findViewById(R.id.btn_register);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtnombre.getText().toString();
                String correo = txtcorreo.getText().toString();
                String clave = txtclave.getText().toString();
                String numero = txtnumero.getText().toString();
                String documento = txtdocumento.getText().toString();
                String fechaNacimiento = txtFN.getText().toString();
                String ocupacion = ocupacion.getText().toString();
                if (nombre.equals("") || correo.equals("") || clave.equals("") || numero.equals("") || documento.equals("") || fechaNacimiento.equals("") || ocupacion.equals("")) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(RegistroPaciente.this);
                    alerta.setMessage("Debe llenar todos los campos").setNegativeButton("Reintentar", null).create().show();
                } else {

                    Response.Listener<String> respuesta = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonRespuesta = new JSONObject(response);
                                boolean ok = jsonRespuesta.getBoolean("success");
                                if (ok == true) {
                                    Intent i = new Intent(RegistroPaciente.this, Login.class);
                                    RegistroPaciente.this.startActivity(i);
                                    RegistroPaciente.this.finish();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RegistroPaciente.this);
                                    alerta.setMessage("Fallo en el registro").setNegativeButton("Reintentar", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };

                    RegistroRequest r = new RegistroRequest(nombre, correo, clave, numero, documento, checkGenero, fechaNacimiento, ocupacionrespuesta);
                    RequestQueue cola = Volley.newRequestQueue(RegistroPaciente.this);
                    cola.add(r);
                }
            }
        });
        getSupportActionBar().hide();
    }

    public void onRadioButtonClicked (View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbtgeneroH:
                if (checked) {
                    checkGenero = 1;
                }
                break;
            case R.id.rbtgeneroM:
                if (checked) {
                    checkGenero = 0;
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == txtFN) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtFN.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}