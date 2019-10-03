package com.comp2100.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TaskViewHolder> {
    List<Task> tasks;

    RVAdapter(List<Task> tasks){
        this.tasks = tasks;
    }
    public  static class TaskViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView taskTitle;
        TextView tasklocation;
        TextView taskdate;
        public TaskViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.CardView);
            taskTitle = itemView.findViewById(R.id.TextTitle);
            tasklocation = itemView.findViewById(R.id.PlaceText);
            taskdate = itemView.findViewById(R.id.DueText);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_base_swipe_list, viewGroup, false);
        TaskViewHolder Tvh = new TaskViewHolder(v);
        return Tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.taskTitle.setText(tasks.get(i).Title);
        taskViewHolder.tasklocation.setText(tasks.get(i).location);
        taskViewHolder.taskdate.setText(tasks.get(i).date);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
