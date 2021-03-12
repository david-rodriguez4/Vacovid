package co.edu.unipiloto.vacovid;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroRequest extends StringRequest {
    private static final String ruta = "https://davidfrodriguezc.000webhostapp.com/registro.php";
    private Map<String, String> parametros;

    public RegistroRequest(String nombre, String correo, String clave, String numero, String documento, Response.Listener<String> listener) {
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("nombre", nombre + "");
        parametros.put("correo", correo + "");
        parametros.put("clave", clave + "");
        parametros.put("numero", numero + "");
        parametros.put("documento", documento + "");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
