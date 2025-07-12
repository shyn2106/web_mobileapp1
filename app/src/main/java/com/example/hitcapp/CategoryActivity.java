package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    private String[] categories = {
            "Điện thoại Cao cấp",
            "Điện thoại Cổ điển",
            "Hệ điều hành máy tính",
            "Điện thoại Đặc biệt"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ListView listView = findViewById(R.id.category_list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                categories
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = categories[position];
            Intent intent = new Intent(CategoryActivity.this, ProductListActivity.class);
            intent.putExtra("category_filter", selectedCategory);
            startActivity(intent);
        });
    }
}
