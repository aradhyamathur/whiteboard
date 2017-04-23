package com.aradhya.krishna.whiteboard.base.UI.ListAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Models.NewsModel;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on 4/2/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsModel> newsModelArrayList;
    private static VClickListener myClickListener;
    public NewsAdapter(List<NewsModel>news){
        newsModelArrayList = news;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView newTitleTextView;
        private TextView newsDateTextView;
        private TextView newsInfoTextView;

        public ViewHolder(View view) {
            super(view);
            newTitleTextView = (TextView) view.findViewById(R.id.news_title_textView8);
            newsDateTextView = (TextView) view.findViewById(R.id.news_date_textView8);
            newsInfoTextView = (TextView) view.findViewById(R.id.news_infotextView9);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
                myClickListener.onItemClick(getAdapterPosition(),view);
        }
    }

    public void setOnItemClickListener(VClickListener clickListener){
        this.myClickListener = clickListener;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_view_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        //main rendering goes here
        NewsModel news = newsModelArrayList.get(position);
        holder.newTitleTextView.setText(news.getTitle());
        holder.newsDateTextView.setText(news.getDate());
        holder.newsInfoTextView.setText(news.getInfo());
    }

    @Override
    public int getItemCount() {
        return newsModelArrayList.size();
    }
}
