package com.my.booze.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.my.booze.R;
import com.my.booze.models.CartProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.ViewHolder> {
    List<CartProductModel> list;
    Context context;

    public OrderProductsAdapter(List<CartProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private OrderProductsAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OrderProductsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public OrderProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_item, parent, false);
        return new OrderProductsAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderProductsAdapter.ViewHolder holder, int position) {
        CartProductModel model = list.get(position);
        Picasso.get().load(model.getproduct_Picture()).into(holder.product_Picture);
        holder.product_Name.setText(model.getProduct_Name());
        holder.product_Price.setText("$" + model.getProduct_Price());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView product_Picture;
        public TextView product_Name, product_Price;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_Picture = itemView.findViewById(R.id.product_Picture);
            product_Name = itemView.findViewById(R.id.product_Name);
            product_Price = itemView.findViewById(R.id.product_Price);
        }
    }
}

