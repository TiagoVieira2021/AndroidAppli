package lu.letsfruit.eshop.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

import lu.letsfruit.eshop.R;
import lu.letsfruit.eshop.service.LoginService;

public class LoginActivity extends AppCompatActivity {

    private final LoginService loginService = LoginService.getInstance();

    private EditText email;
    private EditText password;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initEvents();
        }

    private void initEvents() {
        submit.setOnClickListener(
                (View view) -> loginService.login(
                        this,
                        email.getText().toString(),
                        password.getText().toString(),
                        () -> {
                            startActivity(new Intent(LoginActivity.this, ShopActivity.class));
                        }
                ));
    }

    private void initViews() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.login);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            return null;
        }
    }
}