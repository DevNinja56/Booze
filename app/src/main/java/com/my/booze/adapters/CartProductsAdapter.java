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
import com.my.booze.models.CategoryModel;

import java.util.List;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.ViewHolder> {

    List<CategoryModel> list;
    Context context;

    private CartProductsAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CartProductsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public CartProductsAdapter(List<CategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public CartProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_small_item, parent, false);
        return new CartProductsAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartProductsAdapter.ViewHolder holder, int position) {
        CategoryModel model = list.get(position);
        holder.icon_Category.setImageResource(model.getCategoryImage());
        holder.text_Category.setText(model.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon_Category;
        public TextView text_Category;
        RelativeLayout item_Category;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon_Category = itemView.findViewById(R.id.icon_Category);
            text_Category = itemView.findViewById(R.id.text_Category);
            item_Category = (RelativeLayout) itemView.findViewById(R.id.item_Category);
            item_Category.setOnClickListener(new View.OnClickListener() {
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
