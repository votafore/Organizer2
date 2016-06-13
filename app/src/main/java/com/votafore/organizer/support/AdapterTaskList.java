package com.votafore.organizer.support;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.votafore.organizer.R;
import com.votafore.organizer.model.Task;
import com.votafore.organizer.model.TaskManager;


public class AdapterTaskList extends RecyclerView.Adapter<AdapterTaskList.ItemHolder>{

    public interface IListener{
        void onClick(int position, int id);
    }

    IListener mListener;

    private TaskManager mManager;

    public AdapterTaskList(TaskManager manager){
        this.mManager = manager;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        Task curTask = mManager.getTaskList().get(position);

        holder.mTitleView.setText(curTask.getTitle());
        holder.mTimeView.setText(DateFormat.format("HH:mm", curTask.getTime()));
    }

    @Override
    public int getItemCount() {
        return mManager.getTaskList().size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTitleView;
        public TextView mTimeView;

        public ItemHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mTitleView = (TextView)itemView.findViewById(R.id.template_task_list_item_title);
            mTimeView = (TextView)itemView.findViewById(R.id.template_task_list_item_time);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getAdapterPosition(), mManager.getTaskList().get(getAdapterPosition()).getId());
        }
    }

    public void setListener(IListener mListener) {
        this.mListener = mListener;
    }
}
