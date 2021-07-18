package lu.letsfruit.eshop.Model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable, ShopAdapterItem {
    private int id;
    private String name;
    private String imageName;
    private boolean showHome;
    private List<Category> subCategories = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    public Category() {
    }

    public Category(JSONArray response, int i) throws JSONException {
        id = response.getJSONObject(i).getInt("id");
        name = response.getJSONObject(i).getString("name");
        imageName = response.getJSONObject(i).getString("imagePath");
        showHome = response.getJSONObject(i).getBoolean("showHome");

        JSONArray jsonArray = response.getJSONObject(i).getJSONArray("subCategories");

        for(int j = 0 ; j < jsonArray.length() ; j ++) {
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            subCategories.add(new Category(jsonObject));
        }

        JSONArray jsonArrayProducts = response.getJSONObject(i).getJSONArray("products");

        for(int j = 0 ; j < jsonArrayProducts.length() ; j ++) {
            JSONObject jsonObject = jsonArrayProducts.getJSONObject(j);
            products.add(new Product(jsonObject));
        }
    }

    public Category(JSONObject response) throws JSONException {
        id = response.getInt("id");
        name = response.getString("name");
        imageName = response.getString("imagePath");
        showHome = response.getBoolean("showHome");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isShowHome() {
        return showHome;
    }

    public void setShowHome(boolean showHome) {
        this.showHome = showHome;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
