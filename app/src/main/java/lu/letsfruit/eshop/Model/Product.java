package lu.letsfruit.eshop.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Product implements Serializable, ShopAdapterItem {
    private int id;
    private String name;
    private String description;
    private String imagePath;
    private double price;

    public Product(JSONObject response) throws JSONException {
        id = response.getInt("id");
        name = response.getString("name");
        description = response.getString("description");
        imagePath = response.getString("imagePath");
        price = response.getDouble("price");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
