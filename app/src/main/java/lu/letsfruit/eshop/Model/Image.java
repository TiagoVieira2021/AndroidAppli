package lu.letsfruit.eshop.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Image {
    private int id;
    private String base64;
    private String path;

    public Image(String base64) {
        this.base64 = base64;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("base64", this.base64);

        return jsonObject;
    }
}
