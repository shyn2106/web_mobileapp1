package com.example.hitcapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MobileAdapter extends BaseAdapter {

    private Context context;
    private List<MobileItem> itemList;
    private LayoutInflater inflater;

    public MobileAdapter(Context context, List<MobileItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView nameText, descText, extraText, categoryText, priceText; // ✅ Thêm priceText
        Button btnAddToCart, btnDetail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        MobileItem item = itemList.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_mobile, parent, false);
            holder = new ViewHolder();

            holder.imageView = convertView.findViewById(R.id.image_view);
            holder.nameText = convertView.findViewById(R.id.name_text);
            holder.descText = convertView.findViewById(R.id.desc_text);
            holder.extraText = convertView.findViewById(R.id.extra_text);
            holder.categoryText = convertView.findViewById(R.id.category_text);
            holder.priceText = convertView.findViewById(R.id.price_text); // ✅ Gán priceText
            holder.btnAddToCart = convertView.findViewById(R.id.add_to_cart_button);
            holder.btnDetail = convertView.findViewById(R.id.btn_detail);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Gán dữ liệu
        holder.imageView.setImageResource(item.getImageResId());
        holder.nameText.setText(item.getName());
        holder.descText.setText(item.getDescription());
        holder.extraText.setText(item.getExtraInfo());
        holder.categoryText.setText("Danh mục: " + item.getCategory());
        holder.priceText.setText("Giá: " + item.getGiaTien()); // ✅ Hiển thị giá

        // Nút thêm vào giỏ
        holder.btnAddToCart.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(item);
            item.setInCart(true);
        });

        // Nút xem chi tiết
        holder.btnDetail.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("item", item);
            context.startActivity(intent);
        });

        return convertView;
    }
}
