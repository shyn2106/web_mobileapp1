package com.example.hitcapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CheckoutAdapter checkoutAdapter;
    private RadioGroup paymentGroup;
    private ImageView qrImage;
    private Button btnPlaceOrder;
    private TextView totalAmountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        // Áp dụng insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_checkout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        paymentGroup = findViewById(R.id.group_payment_method);
        qrImage = findViewById(R.id.img_qr_code);
        btnPlaceOrder = findViewById(R.id.btn_place_order);
        totalAmountText = findViewById(R.id.tv_total_amount);
        cartRecyclerView = findViewById(R.id.rv_cart_items);

        // Nhận dữ liệu từ Intent
        int total = getIntent().getIntExtra("total_price", 0);
        ArrayList<MobileItem> cartItems = (ArrayList<MobileItem>) getIntent().getSerializableExtra("cart_items");

        // Hiển thị danh sách sản phẩm
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkoutAdapter = new CheckoutAdapter(this, cartItems);
        cartRecyclerView.setAdapter(checkoutAdapter);

        // Hiển thị tổng tiền định dạng VN
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        totalAmountText.setText("💰 Tổng cộng: " + formatter.format(total) + "₫");

        // Hiện/ẩn mã QR
        paymentGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_qr_payment) {
                qrImage.setVisibility(View.VISIBLE);
            } else {
                qrImage.setVisibility(View.GONE);
            }
        });

        // Đặt hàng
        btnPlaceOrder.setOnClickListener(v -> {
            int selectedId = paymentGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedButton = findViewById(selectedId);
            String method = selectedButton.getText().toString();

            Toast.makeText(this, "Đặt hàng thành công bằng: " + method, Toast.LENGTH_LONG).show();

            // Xóa giỏ hàng sau khi đặt
            CartManager.getInstance().clearCart();
            finish(); // Có thể quay lại trang chủ
        });
    }
}
