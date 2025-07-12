<<<<<<< HEAD
//package com.example.hitcapp;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class RegistrationActivity extends AppCompatActivity {
//
//    EditText regUsername, regPassword;
//    Button registerButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//
//        regUsername = findViewById(R.id.reg_username);
//        regPassword = findViewById(R.id.reg_password);
//        registerButton = findViewById(R.id.register_button);
//
//        registerButton.setOnClickListener(v -> {
//            String enteredUsername = regUsername.getText().toString();
//            String enteredPassword = regPassword.getText().toString();
//
//            if (!enteredUsername.isEmpty() && !enteredPassword.isEmpty()) {
//                SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("username", enteredUsername);
//                editor.putString("password", enteredPassword);
//                editor.apply();
//
//                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
//                finish();
//            } else {
//                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
//
//
package com.example.hitcapp;

import android.content.Intent;

package com.example.hitcapp;

import android.content.Intent;
import android.content.SharedPreferences;
>>>>>>> d79caba1330101d49c3507fa99ad7709e4d09f2a
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class RegistrationActivity extends AppCompatActivity {

    EditText regUsername, regPassword;
    Button registerButton;


    // ✅ Đây là link mock thật bạn vừa tạo!
    private static final String REGISTER_URL = "https://6871fca076a5723aacd3446d.mockapi.io/api/v1/users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regUsername = findViewById(R.id.reg_username);
        regPassword = findViewById(R.id.reg_password);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {

            String username = regUsername.getText().toString().trim();
            String password = regPassword.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                registerWithAPI(username, password);

            String enteredUsername = regUsername.getText().toString();
            String enteredPassword = regPassword.getText().toString();

            if (!enteredUsername.isEmpty() && !enteredPassword.isEmpty()) {
                SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", enteredUsername);
                editor.putString("password", enteredPassword);
                editor.apply();

                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();

            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void registerWithAPI(String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi tạo JSON!", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                REGISTER_URL,
                jsonBody,
                response -> {
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    finish();
                },
                error -> {
                    Toast.makeText(this, "Lỗi khi đăng ký: " + error.toString(), Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(request);
    }
}

}



