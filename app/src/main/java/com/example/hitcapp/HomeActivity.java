package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;
import com.android.volley.RequestQueue;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private ListView mobileListView;
    private SearchView searchView;
    private MobileAdapter mobileAdapter;
    private CategoryGridAdapter categoryGridAdapter;
    private List<MobileItem> mobileList;
    private List<MobileItem> originalList;
    private List<CategoryItem> categoryList;
    private RecyclerView categoryRecyclerView;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://fakestoreapi.com/products";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getData();
        // Ánh xạ view
        searchView = findViewById(R.id.search_bar);
        mobileListView = findViewById(R.id.mobile_list);
        categoryRecyclerView = findViewById(R.id.category_grid);
        Button btnViewProducts = findViewById(R.id.btn_view_products);
        Button btnCart = findViewById(R.id.btn_cart);
        Button btnAccount = findViewById(R.id.btn_account); // ✅ thêm nút tài khoản

        // Danh sách sản phẩm với giá tiền
        originalList = new ArrayList<>();
        originalList.add(new MobileItem("Samsung Galaxy S23", "Android", R.drawable.ss, "Ra mắt 2023", "Android", "23.000.000₫"));
        originalList.add(new MobileItem("iPhone 15 Pro", "iOS", R.drawable.ip, "Chip A17 Pro", "iOS", "33.000.000₫"));
        originalList.add(new MobileItem("Lumia 950", "Windows Phone", R.drawable.lumi, "Ngừng phát triển", "Windows Phone", "5.000.000₫"));
        originalList.add(new MobileItem("Bold 9900", "Blackberry OS", R.drawable.black, "Bàn phím vật lý", "Blackberry", "2.000.000₫"));
        originalList.add(new MobileItem("Palm Pre", "WebOS", R.drawable.pam, "Giao diện đa nhiệm", "WebOS", "1.500.000₫"));
        originalList.add(new MobileItem("Meizu MX4", "Ubuntu", R.drawable.meizu, "Nguồn mở", "Ubuntu", "4.500.000₫"));
        originalList.add(new MobileItem("Laptop HP", "Windows7", R.drawable.hp, "Hệ điều hành PC", "Windows", "12.000.000₫"));
        originalList.add(new MobileItem("MacBook Pro", "Mac OS X", R.drawable.mac, "Giao diện đẹp", "Mac OS", "28.000.000₫"));

        // Adapter cho sản phẩm
        mobileList = new ArrayList<>(originalList);
        mobileAdapter = new MobileAdapter(this, mobileList);
        mobileListView.setAdapter(mobileAdapter);

        // Tạo danh sách danh mục
        categoryList = new ArrayList<>();
        categoryList.add(new CategoryItem("Android", R.drawable.ic_android));
        categoryList.add(new CategoryItem("iOS", R.drawable.ic_ios));
        categoryList.add(new CategoryItem("Windows Phone", R.drawable.ic_windows));
        categoryList.add(new CategoryItem("Blackberry", R.drawable.ic_blackberry));
        categoryList.add(new CategoryItem("WebOS", R.drawable.ic_webos));
        categoryList.add(new CategoryItem("Ubuntu", R.drawable.ic_ubuntu));
        categoryList.add(new CategoryItem("Windows", R.drawable.ic_windows));
        categoryList.add(new CategoryItem("Mac OS", R.drawable.ic_mac));

        // Thiết lập RecyclerView hiển thị dạng lưới
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        categoryGridAdapter = new CategoryGridAdapter(this, categoryList, selectedCategory -> {
            filterByCategory(selectedCategory.getTitle());
            Toast.makeText(this, "Lọc theo: " + selectedCategory.getTitle(), Toast.LENGTH_SHORT).show();
        });

        categoryRecyclerView.setAdapter(categoryGridAdapter);

        // Tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }
            @Override public boolean onQueryTextChange(String newText) {
                filterList(newText.trim());
                return true;
            }
        });

        // Xem chi tiết sản phẩm
        mobileListView.setOnItemClickListener((parent, view, position, id) -> {
            MobileItem item = mobileList.get(position);
            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        });

        // Nút xem tất cả sản phẩm
        btnViewProducts.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProductListActivity.class);
            startActivity(intent);
        });

        // Nút giỏ hàng
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // ✅ Nút tài khoản
        btnAccount.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
            startActivity(intent);
        });
    }

    private void filterList(String query) {
        mobileList.clear();
        if (query.isEmpty()) {
            mobileList.addAll(originalList);
        } else {
            for (MobileItem item : originalList) {
                if (item.getName().toLowerCase().contains(query.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                        item.getCategory().toLowerCase().contains(query.toLowerCase())) {
                    mobileList.add(item);
                }
            }
        }
        mobileAdapter.notifyDataSetChanged();
    }

    private void filterByCategory(String category) {
        mobileList.clear();
        for (MobileItem item : originalList) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                mobileList.add(item);
            }
        }
        mobileAdapter.notifyDataSetChanged();
    }
    private void getData() {
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        // String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }


}
