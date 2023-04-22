package com.example.finalproject_pemrogramanmobile.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject_pemrogramanmobile.Activity.model.HomepageModel;
import com.example.finalproject_pemrogramanmobile.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    List<HomepageModel.CategoryBotton> categoryBottons;

    public CategoryAdapter(List<HomepageModel.CategoryBotton> categoryBottons) {
        this.categoryBottons = categoryBottons;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        HomepageModel.CategoryBotton categoryBotton = categoryBottons.get(position);
        holder.text_category.setText(categoryBotton.getName());
        Glide.with(holder.itemView.getContext())
                .load(categoryBotton.getImage()).into(holder.category_image);

    }

    @Override
    public int getItemCount() {
        return categoryBottons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView category_image;
        TextView text_category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            text_category = itemView.findViewById(R.id.text_category);
        }
    }
}
