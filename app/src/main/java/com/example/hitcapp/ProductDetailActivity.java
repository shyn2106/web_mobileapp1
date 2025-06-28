package com.example.hitcapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView productImage;
    TextView productName, productCategory, productDescription, productPrice;
    Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ view
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productCategory = findViewById(R.id.product_category); // ✅ THÊM dòng này
        productDescription = findViewById(R.id.product_description);
        buyButton = findViewById(R.id.buy_button);

        // Nhận dữ liệu
        MobileItem item = (MobileItem) getIntent().getSerializableExtra("item");

        if (item == null) {
            Toast.makeText(this, "Không thể hiển thị sản phẩm!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Hiển thị thông tin
        productImage.setImageResource(item.getImageResId());
        productName.setText(item.getName());
        productPrice.setText("Giá: " + item.getGiaTien());
        productCategory.setText("Danh mục: " + item.getCategory());
        productDescription.setText("Mô tả: " + item.getDescription() +
                "\n\nThông tin thêm: " + item.getExtraInfo());

        buyButton.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(item);
            Toast.makeText(this, "Đã thêm vào giỏ: " + item.getName(), Toast.LENGTH_SHORT).show();
        });
    }
}
