package co.edu.unipiloto.vacovid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        EditText txtnombre = (EditText) findViewById(R.id.etenombre);
        EditText txtcorreo = (EditText) findViewById(R.id.etecorreo);
        EditText txtclave = (EditText) findViewById(R.id.etecontraseña);
        EditText txtnumero = (EditText) findViewById(R.id.etenumero);
        EditText txtdocumento = (EditText) findViewById(R.id.etedocumento);
        Spinner sRol = (Spinner) findViewById(R.id.roles);
        TextView login = (TextView) findViewById(R.id.txtsignin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Registro.this, Login.class);
                Registro.this.startActivity(login);
                Registro.this.finish();
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
                String roles = String.valueOf(sRol.getSelectedItem());
                int rol = 3;

                if (roles.equals("Paciente")) {
                    rol = 0;
                } else if (roles.equals("Personal de salud")) {
                    rol = 1;
                }

                if (nombre.equals("") || correo.equals("") || clave.equals("") || numero.equals("") || documento.equals("") || rol == 3) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                    alerta.setMessage("Debe llenar todos los campos").setNegativeButton("Reintentar", null).create().show();
                } else {

                    Response.Listener<String> respuesta = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonRespuesta = new JSONObject(response);
                                boolean ok = jsonRespuesta.getBoolean("success");
                                if (ok == true) {
                                    Intent i = new Intent(Registro.this, Login.class);
                                    Registro.this.startActivity(i);
                                    Registro.this.finish();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                                    alerta.setMessage("Fallo en el registro").setNegativeButton("Reintentar", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };

                    RegistroRequest r = new RegistroRequest(nombre, correo, clave, numero, documento, rol, respuesta);
                    RequestQueue cola = Volley.newRequestQueue(Registro.this);
                    cola.add(r);
                }
            }
        });
        getSupportActionBar().hide();
    }
}