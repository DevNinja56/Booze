package com.my.booze.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.my.booze.R;
import com.my.booze.activities.ProductDetailsActivity;
import com.my.booze.models.CartProductModel;
import com.my.booze.models.CartProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<CartProductModel> list;
    Context context;

    public CartAdapter(List<CartProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private CartAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CartAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartProductModel model = list.get(position);
        Picasso.get().load(model.getproduct_Picture()).into(holder.product_Picture);
        holder.product_Name.setText(model.getProduct_Name());
        holder.product_Quantity.setText(model.getProduct_Quantity());
        holder.product_Price.setText("$" + model.getProduct_Price());
        holder.cart_ProductsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, ""+list.get(position).getProduct_Id(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, ProductDetailsActivity.class);
//                intent.putExtra("PRODUCT_ID", ""+list.get(position).getProduct_Id());
//                context.startActivity(intent);
            }
        });
//        holder.plus_Sign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.product_Quantity.setText(Integer.parseInt(holder.product_Quantity.getText().toString())+1);
//                addToCart();
//            }
//        });
//        holder.minus_Sign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.product_Quantity.setText(Integer.parseInt(holder.product_Quantity.getText().toString())+1);
//                removeFromCart();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView product_Picture, minus_Sign, plus_Sign;
        public TextView product_Name, product_Price, product_Quantity;
        RelativeLayout cart_ProductsItem;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            minus_Sign = itemView.findViewById(R.id.minus_Sign);
            plus_Sign = itemView.findViewById(R.id.plus_Sign);
            product_Picture = itemView.findViewById(R.id.product_Picture);
            product_Name = itemView.findViewById(R.id.product_Name);
            product_Price = itemView.findViewById(R.id.product_Price);
            product_Quantity = itemView.findViewById(R.id.product_Quantity);
            cart_ProductsItem = itemView.findViewById(R.id.cart_ProductsItem);
            cart_ProductsItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        Log.v("Enter OnClick", "Enter Click");
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
