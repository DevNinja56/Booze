package com.my.booze.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.my.booze.R;
import com.my.booze.activities.OrderDetailsActivity;
import com.my.booze.activities.OrderDetailsActivity2;
import com.my.booze.models.OrderDataModel;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    List<OrderDataModel> list;
    Context context;
    public OrdersAdapter(List<OrderDataModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    private OrdersAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OrdersAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new OrdersAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(list.get(position).getOrder_Status().equals("Placed")) {
            holder.order_Status.setText("Placed");
            holder.order_Picture.setImageResource(R.drawable.ic_checked);
            holder.text_EstimateTime.setText("Your Order is Placed");
            holder.order_EstimateTime.setVisibility(View.GONE);
        } else if(list.get(position).getOrder_Status().equals("OnWay")) {
            holder.order_Status.setText("On Way");
            holder.order_Picture.setImageResource(R.drawable.ic_delivery_boy);
            holder.order_EstimateTime.setText(list.get(position).getOrder_EstimateDeliveryTime());
        }
        int totalCost = list.get(position).getOrder_DeliveryCost()+list.get(position).getOrder_SubTotal();
        holder.order_TotalCost.setText("$"+totalCost);
        holder.order_DateTime.setText(list.get(position).getOrder_PlacingTime());
        holder.order_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity2.class);
                intent.putExtra("ORDER_ID", ""+list.get(position).getOrder_Id());
                intent.putExtra("ORDER_STATUS", ""+list.get(position).getOrder_Status());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView order_Picture;
        public TextView order_Status, text_EstimateTime, order_EstimateTime, order_TotalCost, order_DateTime;
        public RelativeLayout order_Item;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_Item =itemView.findViewById(R.id.order_Item);
            order_Picture =itemView.findViewById(R.id.order_Picture);
            order_Status =itemView.findViewById(R.id.order_Status);
            text_EstimateTime =itemView.findViewById(R.id.text_EstimateTime);
            order_EstimateTime =itemView.findViewById(R.id.order_EstimateTime);
            order_TotalCost =itemView.findViewById(R.id.order_TotalCost);
            order_DateTime =itemView.findViewById(R.id.order_DateTime);
        }
    }

}
