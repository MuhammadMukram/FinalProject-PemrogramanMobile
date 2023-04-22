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

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   //SEGMENT1
    Context context;
    List<HomepageModel.News> news;
    int image_left = 1;
    int image_top = 2;


    public NewsAdapter(Context context, List<HomepageModel.News> news) {
        this.context = context;
        this.news = news;
    }
    @Override
    public int getItemViewType(int position){
        // load 2 different layouts (Multiple layout in recycleview)
        if((position+1)%8 == 0 || position == 0){
            //Loading the first item & every 8 items the big layout
            return image_top;
        }else{
            return image_left;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == image_left){
            View v = LayoutInflater.from(context).inflate(R.layout.item_news,parent, false);
            return new ViewHolder(v);
        }else{
            View v = LayoutInflater.from(context).inflate(R.layout.item_news_image,parent, false);
            return new ViewHolder(v);
        }
    }

    //Custom View Holder
    //SEGMENT 1
    public class ViewHolder extends RecyclerView.ViewHolder{
        View holder;
        ImageView newsImage;
        TextView newsTitle, newsDesc, newsDate, newsSource, newsView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            holder = itemView;
            newsImage = holder.findViewById(R.id.news_image);
            newsTitle = holder.findViewById(R.id.news_title);
            newsDesc = holder.findViewById(R.id.news_desc);
            newsSource = holder.findViewById(R.id.news_source);
            newsView = holder.findViewById(R.id.news_view);
            newsDate = holder.findViewById(R.id.news_date);
        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //Fix error and prevent white spaces
        HomepageModel.News singleNews = news.get(holder.getAdapterPosition());
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.newsTitle.setText(removeHtml(singleNews.getTitle()));
        viewHolder.newsDesc.setText(removeHtml(singleNews.getPostContent()).trim());
        if(singleNews.getSource() != null){
            viewHolder.newsSource.setText(singleNews.getSource());
        }

        //Fix Error and prevent white spaces
        if(singleNews.getImage().length() <= 1){
            viewHolder.newsImage.setVisibility(View.GONE);
        }else{
            viewHolder.newsImage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(singleNews.getImage())
                    .into(viewHolder.newsImage);
        }

    }

    //SEGMENT 1
    @Override
    public int getItemCount() {
        return news.size();
    }


    public static String removeHtml(String html){
        html = html.replaceAll("<(.*?)\\>"," ");
        html = html.replaceAll("<(.*?)\\\n"," ");
        html = html.replaceFirst("(.*?)\\>", " ");
        html = html.replaceAll("&nbsp;"," ");
        html = html.replaceAll("&amp;"," ");
        return html;
    }
}
