package com.example.hitcapp;

import android.content.Context;
import android.view.*;
import android.widget.*;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<CategoryItem> categoryList;
    private int selectedIndex = -1;

    public CategoryAdapter(Context context, List<CategoryItem> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public void setSelectedIndex(int index) {
        this.selectedIndex = index;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);

        TextView title = view.findViewById(R.id.category_title);
        ImageView icon = view.findViewById(R.id.category_icon);

        CategoryItem item = categoryList.get(position);
        title.setText(item.getTitle());
        icon.setImageResource(item.getImageRes());

        // Đổi màu nền khi chọn
        if (position == selectedIndex) {
            view.setBackgroundResource(R.drawable.category_bg);
        } else {
            view.setBackgroundResource(R.drawable.category_bg);
        }

        return view;
    }
}
