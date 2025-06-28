package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private ListView cartListView;
    private TextView totalPriceText;
    private CartAdapter cartAdapter;
    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cart_list);
        totalPriceText = findViewById(R.id.total_price);
        Button btnCheckout = findViewById(R.id.btn_checkout);

        List<MobileItem> cartItems = CartManager.getInstance().getCartItems();

        cartAdapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(cartAdapter);

        for (MobileItem item : cartItems) {
            total += item.getGiaTienAsInt();
        }

        NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));
        totalPriceText.setText("Tổng tiền: " + format.format(total) + "₫");

        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("total_price", total);
            intent.putExtra("cart_items", new ArrayList<>(cartItems)); // ✅ THÊM DÒNG NÀY
            startActivity(intent);
        });
    }
}
