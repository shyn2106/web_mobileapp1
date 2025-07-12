//package com.example.hitcapp;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class LoginActivity extends AppCompatActivity {
//
//    EditText usernameEditText, passwordEditText;
//    Button loginButton;
//    TextView registerText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        usernameEditText = findViewById(R.id.username);
//        passwordEditText = findViewById(R.id.password);
//        loginButton = findViewById(R.id.login_button);
//        registerText = findViewById(R.id.register_text);
//
//        loginButton.setOnClickListener(view -> {
//            String enteredUsername = usernameEditText.getText().toString();
//            String enteredPassword = passwordEditText.getText().toString();
//
//            SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
//            String savedUsername = preferences.getString("username", "");
//            String savedPassword = preferences.getString("password", "");
//
//            if (enteredUsername.equals(savedUsername) && enteredPassword.equals(savedPassword)) {
//                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                finish();
//            } else {
//                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        registerText.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
//            startActivity(intent);
//        });
//    }
//}
//
package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton;
    TextView registerText;

    private static final String LOGIN_URL = "https://fakestoreapi.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerText = findViewById(R.id.register_text);

        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                return;
            }

            performLogin(username, password);
        });

        registerText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });
    }

    private void performLogin(String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, LOGIN_URL, jsonBody,
                response -> {
                    try {
                        String token = response.getString("token");
                        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                        // Lưu token nếu cần
                        // SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
                        // prefs.edit().putString("token", token).apply();

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(this, "Lỗi phản hồi!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }
}
