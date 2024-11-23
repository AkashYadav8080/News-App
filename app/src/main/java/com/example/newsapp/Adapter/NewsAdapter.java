package com.example.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Activity.SourceActivity;
import com.example.newsapp.Activity.WebViewActivity;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {


    Context context;
    List<NewsModel> newsModelList;

    private int lastPosition = -1;

    public NewsAdapter(Context context, List<NewsModel> newsModelList) {
        this.context = context;
        this.newsModelList = newsModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    NewsModel newsModel = newsModelList.get(position);

    holder.author.setText(newsModel.getAuthor());
    holder.description.setText(newsModel.getDescription());
//    holder.url.setText(newsModel.getUrl());
    holder.publishedAt.setText(newsModel.getPublishedAt());


    Picasso.get().load(newsModel.getUrlToImage()).into(holder.img);

        holder.newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(context, WebViewActivity.class);
                a.putExtra("url",""+newsModel.getUrl());
                context.startActivity(a);
            }
        });

        setAnimation(holder.itemView,position);

//        holder.newsSource.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent a = new Intent(context, SourceActivity.class);
//                context.startActivity(a);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView author,description,publishedAt;
        ImageView img;
        CardView newsCard;

//        LinearLayout newsSource;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.author);
            description = itemView.findViewById(R.id.description);
//            url = itemView.findViewById(R.id.url);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            img = itemView.findViewById(R.id.img);
            newsCard = itemView.findViewById(R.id.newsCard);
//            newsSource = itemView.findViewById(R.id.newsSource);

        }
    }

    private void setAnimation(View viewToAnimate,int position){

        if (position>lastPosition){
            Animation slideIn = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//            Animation slideIn = AnimationUtils.loadAnimation(context, R.anim.lay_anim);
            viewToAnimate.startAnimation(slideIn);

            lastPosition=position;
        }

    }

}
