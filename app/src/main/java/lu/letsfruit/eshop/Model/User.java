package lu.letsfruit.eshop.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private int id;
    private String login;
    private String firstname;
    private String lastname;
    private Image image;
    private String password;
    private String confirmPassword;

    public User() {
    }

    public User(JSONObject jsonUser) throws JSONException {
        id = jsonUser.getInt("id");
        login = jsonUser.getString("login");
        firstname = jsonUser.getString("firstname");
        lastname = jsonUser.getString("lastname");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        if (this.getPassword().equals("")){
            this.setPassword(null);
        }

        jsonObject.put("id", this.getId());
        jsonObject.put("login", this.getLogin());
        jsonObject.put("loginPassword", this.getPassword());
        jsonObject.put("firstname", this.getFirstname());
        jsonObject.put("lastname", this.getLastname());

        if (getImage() != null)
            jsonObject.put("image", this.getImage().toJson());

        return jsonObject;
    }
}
