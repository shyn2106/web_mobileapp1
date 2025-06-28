package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private ListView listView;
    private MobileAdapter adapter;
    private List<MobileItem> allProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listView = findViewById(R.id.product_list_view);
        allProducts = new ArrayList<>();

        // ✅ Danh sách tất cả sản phẩm (có thể lấy từ HomeActivity hoặc lớp dùng chung)
        allProducts.add(new MobileItem("Samsung S23", "Android", R.drawable.ss, "Ra mắt 2023", "Điện thoại cao cấp", "23,000,000₫"));
        allProducts.add(new MobileItem("iPhone 15 Pro", "iOS", R.drawable.ip, "Chip A17", "Điện thoại cao cấp", "30,000,000₫"));
        allProducts.add(new MobileItem("Lumia 950", "Windows Phone", R.drawable.lumi, "Ngừng phát triển", "Điện thoại cổ điển", "5,000,000₫"));
        allProducts.add(new MobileItem("HP Laptop", "Windows 7", R.drawable.hp, "Dùng cho máy tính", "Hệ điều hành máy tính", "12,000,000₫"));
        allProducts.add(new MobileItem("Google Pixel 8", "Android", R.drawable.lumi, "Ra mắt 2023", "Điện thoại cao cấp", "22,000,000₫"));
        allProducts.add(new MobileItem("iPhone SE (2022)", "iOS", R.drawable.ip, "Giá phải chăng", "Điện thoại phổ thông", "11,000,000₫"));
        allProducts.add(new MobileItem("BlackBerry Key2", "Android", R.drawable.black, "Bàn phím vật lý", "Điện thoại cổ điển", "6,500,000₫"));
        allProducts.add(new MobileItem("Surface Pro 9", "Windows 11", R.drawable.lumi, "Thiết bị lai", "Hệ điều hành máy tính", "28,000,000₫"));
        allProducts.add(new MobileItem("MacBook Air M2", "macOS", R.drawable.mac, "Chip Apple M2", "Hệ điều hành máy tính", "27,000,000₫"));


        // ✅ Nhận dữ liệu lọc từ danh mục nếu có
        String categoryFilter = getIntent().getStringExtra("category_filter");

        List<MobileItem> filteredList = new ArrayList<>();

        if (categoryFilter != null) {
            for (MobileItem item : allProducts) {
                if (item.getCategory().equalsIgnoreCase(categoryFilter)) {
                    filteredList.add(item);
                }
            }
        } else {
            // Nếu không có lọc, hiển thị tất cả
            filteredList.addAll(allProducts);
        }

        adapter = new MobileAdapter(this, filteredList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            MobileItem item = filteredList.get(position);
            Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
            intent.putExtra("item", item); // nên thống nhất key là "item"
            startActivity(intent);
        });
    }
}
