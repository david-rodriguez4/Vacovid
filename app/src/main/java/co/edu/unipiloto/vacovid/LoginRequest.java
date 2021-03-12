package co.edu.unipiloto.vacovid;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    private static final String ruta = "https://davidfrodriguezc.000webhostapp.com/login.php";
    private Map<String, String> parametros;

    public LoginRequest(String documento, String clave, Response.Listener<String> listener) {
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("documento", documento + "");
        parametros.put("clave", clave + "");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
