package lu.letsfruit.eshop.service;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lu.letsfruit.eshop.Model.Cart;
import lu.letsfruit.eshop.Model.Category;
import lu.letsfruit.eshop.Model.Product;
import lu.letsfruit.eshop.R;
import lu.letsfruit.eshop.utils.JsonArrayRequestWithToken;
import lu.letsfruit.eshop.utils.JsonObjectRequestWithToken;
import lu.letsfruit.eshop.utils.RequestManager;
import lu.letsfruit.eshop.utils.StringRequestWithToken;

public final class CartService {

    private List<Cart> cartList;

    private static CartService instance = null;

    private CartService() {
    }

    public static CartService getInstance() {

        if (instance == null) {
            instance = new CartService();
        }

        return instance;
    }

    public interface AddToCartListener {
        void onSuccess(Product product, double quantity);
    }

    public void addToCart(Context context, Product p, AddToCartListener listener) {


        JsonObjectRequestWithToken jsonArrayRequest = new JsonObjectRequestWithToken(
                context,
                Request.Method.POST,
                context.getResources().getString(R.string.url_spring) + "cart/save",
                null,
                response -> {
                    try {
                        Cart cart = new Cart(response);
                        listener.onSuccess(p, cart.getQuantity());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(context, "Erreur ajout au panier", Toast.LENGTH_SHORT).show();
                }
        ){
            @Override
            public byte[] getBody() {
                // TODO dynamic quantity
                JSONObject jsonCart = new JSONObject();
                try {
                    jsonCart.put("productId", p.getId());
                    jsonCart.put("quantity", 1);
                    return jsonCart.toString().getBytes(StandardCharsets.UTF_8);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        RequestManager.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public interface CartListener {
        void onSuccess(List<Cart> cartList);
    }

    public void getCart(Context context, CartListener listener) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequestWithToken(
                context,
                context.getResources().getString(R.string.url_spring) + "cart",
                response -> {
                    cartList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            cartList.add(new Cart(response, i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listener.onSuccess(cartList);
                },
                error -> {
                    Toast.makeText(context, "Erreur téléchargement du panier", Toast.LENGTH_LONG).show();
                }
        );
        RequestManager.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }


    public void delete(Context context, Cart cart) {

        StringRequestWithToken requestWithToken = new StringRequestWithToken(
                context,
                Request.Method.POST,
                context.getResources().getString(R.string.url_spring) + "cart/delete",
                token -> {

                    Toast.makeText(context, "Supprimé", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(context, "Erreur lors de la suppression", Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            public byte[] getBody() {
                // TODO dynamic quantity
                JSONObject jsonCart = null;
                try {
                    jsonCart = cart.toJson();
                    return jsonCart.toString().getBytes(StandardCharsets.UTF_8);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        RequestManager.getInstance(context).addToRequestQueue(requestWithToken);
    }

}
