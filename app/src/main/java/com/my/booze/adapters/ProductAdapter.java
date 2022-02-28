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
import com.my.booze.models.ProductDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    String type;
    List<ProductDataModel> list;
    Context context;
    public ProductAdapter(String type, List<ProductDataModel> list, Context context) {
        this.type = type;
        this.list = list;
        this.context = context;
    }
    private ProductAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(ProductAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(type.equals("vertical")){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item2,parent,false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        }
        return new ProductAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        ProductDataModel model = list.get(position);
//        holder.product_Image.setImageResource(model.getProduct_Picture());
        Picasso.get().load(model.getProduct_Picture()).into(holder.product_Image);
        holder.text_ProductName.setText(model.getProduct_Name());
//        holder.text_CompanyName.setText(model.get());
        holder.text_Price.setText("$"+model.getProduct_Price());
        holder.item_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, ""+list.get(position).getProduct_Id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("PRODUCT_ID", ""+list.get(position).getProduct_Id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView product_Image;
        public TextView text_ProductName, text_CompanyName, text_Price;
        RelativeLayout item_Product;
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_Image =itemView.findViewById(R.id.product_Image);
            text_ProductName =itemView.findViewById(R.id.text_ProductName);
            text_CompanyName =itemView.findViewById(R.id.text_CompanyName);
            text_Price =itemView.findViewById(R.id.text_Price);
            item_Product = itemView.findViewById(R.id.item_Product);
            item_Product.setOnClickListener(new View.OnClickListener() {
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
