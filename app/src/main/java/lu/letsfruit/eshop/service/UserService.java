package lu.letsfruit.eshop.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import lu.letsfruit.eshop.Model.User;
import lu.letsfruit.eshop.R;
import lu.letsfruit.eshop.utils.JsonObjectRequestWithToken;
import lu.letsfruit.eshop.utils.RequestManager;
import lu.letsfruit.eshop.utils.StringRequestWithToken;

public final class UserService {

    private static UserService instance = null;

    private UserService() {
    }

    public static UserService getInstance() {

        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }


    public interface SuccessListener {
        void onSuccessListener(User user);
    }

    public void getConnectedUser(Context context, SuccessListener listener) {

        JsonObjectRequestWithToken request = new JsonObjectRequestWithToken(
                context,
                Request.Method.GET,
                context.getResources().getString(R.string.url_spring) + "customer/connected-customer",
                null,
                jsonUser -> {

                    try {
                        User user = new User(jsonUser);
                        listener.onSuccessListener(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> System.out.println("erreur")
        );

        RequestManager.getInstance(context).addToRequestQueue(request);
    }

    public void updateProfile(Context context, User user, SuccessListener listener){

        StringRequestWithToken requestWithToken = new StringRequestWithToken(
                context,
                Request.Method.PUT,
                context.getResources().getString(R.string.url_spring) + "customer/update",
                token -> {

                    SharedPreferences preferences =
                            context.getSharedPreferences("MesPreferences", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("token", token);
                    editor.apply();

                    listener.onSuccessListener(null);
                },
                error -> {
                    Toast.makeText(context, new String(error.networkResponse.data), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            public byte[] getBody() {
                JSONObject jsonUser = null;
                try {
                    jsonUser = user.toJson();
                    return jsonUser.toString().getBytes(StandardCharsets.UTF_8);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        RequestManager.getInstance(context).addToRequestQueue(requestWithToken);
    }
}
