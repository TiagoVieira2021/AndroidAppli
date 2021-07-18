package lu.letsfruit.eshop.service;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import lu.letsfruit.eshop.Model.Category;
import lu.letsfruit.eshop.R;
import lu.letsfruit.eshop.utils.RequestManager;

public final class CategoryService {

    private List<Category> categories;

    private static CategoryService instance = null;

    private CategoryService() {
    }

    public static CategoryService getInstance() {

        if (instance == null) {
            instance = new CategoryService();
        }

        return instance;
    }


    public interface CategoriesListener {
        void onSuccessCategories(List<Category> categories);
    }

    public void getMenuCategories(Context context, CategoriesListener listener) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                context.getResources().getString(R.string.url_spring) + "category/main-menu",
                response -> {
                    categories = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            categories.add(new Category(response, i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listener.onSuccessCategories(categories);
                },
                error -> {
                    Toast.makeText(context, "Erreur téléchargement categories", Toast.LENGTH_LONG).show();
                }
        );
        RequestManager.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public List<Category> getCategories() {
        return categories;
    }
}
