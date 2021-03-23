package co.edu.unipiloto.vacovid;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InscripcionRequest extends StringRequest {
    private static final String ruta = "https://davidfrodriguezc.000webhostapp.com/inscripcion.php";
    private Map<String, String> parametros;

    public InscripcionRequest(String fecha, String hora, String id_paciente, Response.Listener<String> listener) {
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("fecha", fecha + "");
        parametros.put("hora", hora + "");
        parametros.put("id_paciente", id_paciente + "");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
