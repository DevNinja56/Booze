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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.my.booze.R;
import com.my.booze.activities.ProductsListActivity;
import com.my.booze.models.CategoryModel;
import com.my.booze.models.NestedCategoryModel;

import java.util.List;

public class NestedCategoryAdapter extends RecyclerView.Adapter<NestedCategoryAdapter.ViewHolder> {

    List<NestedCategoryModel> list;
    Context context;
    public NestedCategoryAdapter(List<NestedCategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    private NestedCategoryAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(NestedCategoryAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public NestedCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_category_item,parent,false);
        return new NestedCategoryAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NestedCategoryModel model = list.get(position);
        holder.icon_Category.setImageResource(model.getNestedCategoryImage());
        holder.text_Category.setText(model.getNestedCategoryName());
        holder.item_NestedCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductsListActivity.class);
                intent.putExtra("CATEGORY_NAME", list.get(position).getCategoryName());
                intent.putExtra("NESTED_CATEGORY_NAME", list.get(position).getNestedCategoryName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon_Category;
        public TextView text_Category;
        RelativeLayout item_NestedCategory;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon_Category =itemView.findViewById(R.id.icon_Category);
            text_Category =itemView.findViewById(R.id.text_Category);
            item_NestedCategory = itemView.findViewById(R.id.item_NestedCategory);
            item_NestedCategory.setOnClickListener(new View.OnClickListener() {
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
