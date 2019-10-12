package com.comp2100.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewTask extends RecyclerView.Adapter<RecyclerViewTask.MyviewHolder> {

    private Context mContent;
    private List<collection> mData;
    private GridLayoutManager layoutManger;


    public RecyclerViewTask(Context mContent, List<collection> mData){
        this.mContent = mContent;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContent);
        view = mInflater.inflate(R.layout.task_cardview,parent,false);


        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        holder.tv_collection_title.setText(mData.get(position).getTitle());
        holder.im_collection.setImageResource(mData.get(position).getImageView());



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setLayoutManger(GridLayoutManager layoutManger) {
        this.layoutManger = layoutManger;
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{

        TextView tv_collection_title;
        ImageView im_collection;



        public MyviewHolder(View view){
            super(view);

            tv_collection_title = (TextView) view.findViewById(R.id.collection_title);
            im_collection = (ImageView) view.findViewById(R.id.collection_img_id);


        }
    }
}
