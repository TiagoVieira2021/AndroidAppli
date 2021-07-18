package lu.letsfruit.eshop.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import lu.letsfruit.eshop.R;
import lu.letsfruit.eshop.utils.RequestManager;

public class LoginService {

    private static LoginService instance = null;

    private LoginService() {
    }

    public static LoginService getInstance() {

        if(instance == null) {
            instance = new LoginService();
        }

        return instance;
    }

    public interface SuccessLoginListener {
        void onSuccessLoginListener();
    }

    public void login(Context context, String pseudo, String password, SuccessLoginListener listener) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                context.getResources().getString(R.string.url_spring) + "authentication",
                token -> {

                    SharedPreferences preferences =
                            context.getSharedPreferences("MesPreferences", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("token", token);
                    editor.apply();

                    listener.onSuccessLoginListener();

                },
                error -> {
                    Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_LONG).show();
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json; charset=UTF-8");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonUser = new JSONObject();
                try {
                    jsonUser.put("login", pseudo);
                    jsonUser.put("loginPassword", password);
                    return jsonUser.toString().getBytes(StandardCharsets.UTF_8);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
        RequestManager.getInstance(context).addToRequestQueue(stringRequest);
    }

}
