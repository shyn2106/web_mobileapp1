package com.example.hitcapp;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<MobileItem> cartItems;

    public CartAdapter(Context context, List<MobileItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MobileItem item = cartItems.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mobile, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.image_view);
        TextView nameText = convertView.findViewById(R.id.name_text);
        TextView descText = convertView.findViewById(R.id.desc_text);

        imageView.setImageResource(item.getImageResId());
        nameText.setText(item.getName());
        descText.setText(item.getDescription());

        return convertView;
    }
}
