package com.example.asus.organization2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.organization2.Bean.Info_PeronalMessage;
import com.example.asus.organization2.Bean.Info_taskMessage;
import com.example.asus.organization2.R;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by ASUS on 2019/3/5.
 */

public class Adapter_taskList extends BaseAdapter {
    ArrayList<Info_taskMessage> info_taskMessages = new ArrayList<>();
    Context context;

    public Adapter_taskList(){

    }

    public Adapter_taskList(ArrayList info_taskMessages, Context context){
        this.info_taskMessages = (ArrayList)info_taskMessages.clone();
        this.context = context;
        notifyDataSetChanged();
    }

    public void setNewsList(ArrayList<Info_taskMessage> list) {
        if (list != null) {
            info_taskMessages = (ArrayList<Info_taskMessage>) list.clone();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return info_taskMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return info_taskMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        View view_task_isread;
        TextView Tv_task_isread;
        TextView Tv_viewTask_title;
        TextView Tv_viewTask_time;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Info_taskMessage taskMessage = info_taskMessages.get(position);
        Adapter_taskList.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.view_task, parent, false);
            holder = new Adapter_taskList.ViewHolder();
            holder.view_task_isread = convertView.findViewById(R.id.view_task_isread);
            holder.Tv_task_isread = convertView.findViewById(R.id.Tv_task_isread);
            holder.Tv_viewTask_title = convertView.findViewById(R.id.Tv_viewTask_title);
            holder.Tv_viewTask_time = convertView.findViewById(R.id.Tv_viewTask_time);
            convertView.setTag(holder);
        } else {
            holder = (Adapter_taskList.ViewHolder) convertView.getTag();
        }
        if(taskMessage.getType() == 1){ //为社团任务
            if(taskMessage.getIsread() == 0){
                holder.Tv_task_isread.setText("未阅读");
            }else{
                holder.view_task_isread.setVisibility(View.GONE);
            }
        }else{
            holder.view_task_isread.setVisibility(View.GONE);
        }
        holder.Tv_viewTask_title.setText(taskMessage.getTitle());
        holder.Tv_viewTask_time.setText(taskMessage.getStartTime() + "->" + taskMessage.getEndTime());
        return convertView;
    }
}
