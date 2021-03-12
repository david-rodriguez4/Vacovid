package co.edu.unipiloto.vacovid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        TextView registro = (TextView) findViewById(R.id.txtsignup);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(Login.this, Registro.class);
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

                if (documento.equals("") || clave.equals("")) {
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
                                    Intent bienvenido = new Intent(Login.this, Principal.class);
                                    bienvenido.putExtra("nombre", nombre);
                                    Login.this.startActivity(bienvenido);
                                    Login.this.finish();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                    alerta.setMessage("El documento o la contraseña no coinciden").setNegativeButton("Reintentar", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };

                    LoginRequest r = new LoginRequest(documento, clave, respuesta);
                    RequestQueue cola = Volley.newRequestQueue(Login.this);
                    cola.add(r);
                }
            }
        });
        getSupportActionBar().hide();
    }
}
