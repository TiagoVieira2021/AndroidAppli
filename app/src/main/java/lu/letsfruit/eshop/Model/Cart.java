package lu.letsfruit.eshop.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Cart {
    private int customerId;
    private int productId;
    private double quantity;
    private Product product;

    public Cart(JSONObject response) throws JSONException {
        customerId = response.getInt("customerId");
        productId = response.getInt("productId");
        quantity = response.getDouble("quantity");
    }

    public Cart(int customerId, int productId, float quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Cart(JSONArray response, int i) throws JSONException {
        customerId = response.getJSONObject(i).getInt("customerId");
        productId = response.getJSONObject(i).getInt("productId");
        quantity = response.getJSONObject(i).getDouble("quantity");
        product = new Product(response.getJSONObject(i).getJSONObject("product"));
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("customerId", this.getCustomerId());
        jsonObject.put("productId", this.getProductId());
        jsonObject.put("quantity", this.getQuantity());

        return jsonObject;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", product=" + product +
                '}';
    }
}
