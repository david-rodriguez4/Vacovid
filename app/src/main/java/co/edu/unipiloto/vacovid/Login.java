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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText txtdocumento = (EditText) findViewById(R.id.etedoc);
        EditText txtclave = (EditText) findViewById(R.id.etcontraseña);
        Spinner sRol = (Spinner) findViewById(R.id.roleslog);
        TextView registro = (TextView) findViewById(R.id.txtsignup);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(Login.this, TipoUsuario.class);
                Login.this.startActivity(registro);
                Login.this.finish();
            }
        });

        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String documento = txtdocumento.getText().toString();
                String clave = txtclave.getText().toString();
                String roles = String.valueOf(sRol.getSelectedItem());
                int rol = 3;

                if (roles.equals("Paciente")) {
                    rol = 0;
                } else if (roles.equals("Personal de salud")) {
                    rol = 1;
                }

                if (documento.equals("") || clave.equals("") || rol == 3) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                    alerta.setMessage("Debe llenar todos los campos").setNegativeButton("Reintentar", null).create().show();
                } else {

                    Response.Listener<String> respuesta = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonrespuesta = new JSONObject(response);
                                boolean ok = jsonrespuesta.getBoolean("success");
                                if (ok == true) {
                                    String nombre = jsonrespuesta.getString("nombre");
                                    String documento = jsonrespuesta.getString("documento");
                                    String rol = jsonrespuesta.getString("rol");
                                    String fecha = jsonrespuesta.getString("fecha");

                                    if (rol.equals("0")) {
                                        if (fecha.equals("si")) {
                                            String fecha1 = jsonrespuesta.getString("fechauno");
                                            String fecha2 = jsonrespuesta.getString("fechados");
                                            String hora = jsonrespuesta.getString("hora");
                                            Intent pacienteAsignado = new Intent(Login.this, PacienteAsignado.class);
                                            pacienteAsignado.putExtra("nombre", nombre);
                                            pacienteAsignado.putExtra("rol", rol);
                                            pacienteAsignado.putExtra("documento", documento);
                                            pacienteAsignado.putExtra("fechauno", fecha1);
                                            pacienteAsignado.putExtra("fechados", fecha2);
                                            pacienteAsignado.putExtra("hora", hora);
                                            Login.this.startActivity(pacienteAsignado);
                                            Login.this.finish();
                                        }
                                        else if (fecha.equals("no")) {
                                            Intent pacienteNoAsignado = new Intent(Login.this, PacienteNoAsignado.class);
                                            pacienteNoAsignado.putExtra("nombre", nombre);
                                            pacienteNoAsignado.putExtra("rol", rol);
                                            pacienteNoAsignado.putExtra("documento", documento);
                                            Login.this.startActivity(pacienteNoAsignado);
                                            Login.this.finish();
                                        }
                                    } else if (rol.equals("1")) {
                                        Intent personal = new Intent(Login.this, Personal.class);
                                        personal.putExtra("nombre", nombre);
                                        personal.putExtra("rol", rol);
                                        personal.putExtra("documento", documento);
                                        Login.this.startActivity(personal);
                                        Login.this.finish();
                                    }

                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                    alerta.setMessage("El documento o la contraseña no coinciden").setNegativeButton("Reintentar", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };

                    LoginRequest r = new LoginRequest(documento, clave, rol, respuesta);
                    RequestQueue cola = Volley.newRequestQueue(Login.this);
                    cola.add(r);
                }
            }
        });
        getSupportActionBar().hide();
    }
}
