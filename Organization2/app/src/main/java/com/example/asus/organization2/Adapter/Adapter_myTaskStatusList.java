package com.example.asus.organization2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.organization2.Bean.Info_myTaskStatusMessage;
import com.example.asus.organization2.Bean.Info_taskMessage;
import com.example.asus.organization2.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 2019/3/8.
 */

public class Adapter_myTaskStatusList extends BaseAdapter {
    ArrayList<Info_myTaskStatusMessage> info_myTaskStatusMessages = new ArrayList<>();
    Context context;

    public Adapter_myTaskStatusList(){

    }

    public Adapter_myTaskStatusList(ArrayList info_myTaskStatusMessages, Context context){
        this.info_myTaskStatusMessages = (ArrayList)info_myTaskStatusMessages.clone();
        this.context = context;
        notifyDataSetChanged();
    }

    public void setNewsList(ArrayList<Info_myTaskStatusMessage> list) {
        if (list != null) {
            info_myTaskStatusMessages = (ArrayList<Info_myTaskStatusMessage>) list.clone();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return info_myTaskStatusMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return info_myTaskStatusMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView Iv_member_status;
        TextView Tv_member_name;
        TextView Tv_member_report;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Info_myTaskStatusMessage myTaskStatusMessage = info_myTaskStatusMessages.get(position);
        Adapter_myTaskStatusList.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.view_mypublishtask_member, parent, false);
            holder = new Adapter_myTaskStatusList.ViewHolder();
            holder.Iv_member_status = convertView.findViewById(R.id.Iv_member_status);
            holder.Tv_member_name = convertView.findViewById(R.id.Tv_member_name);
            holder.Tv_member_report = convertView.findViewById(R.id.Tv_member_report);
            convertView.setTag(holder);
        } else {
            holder = (Adapter_myTaskStatusList.ViewHolder) convertView.getTag();
        }
        if(myTaskStatusMessage.getIsread() == 1){
            holder.Iv_member_status.setImageResource(R.drawable.right);
        }else{
            holder.Iv_member_status.setImageResource(R.drawable.wrong);
        }
        holder.Tv_member_name.setText(myTaskStatusMessage.getName());
        holder.Tv_member_report.setText(myTaskStatusMessage.getReport());
        return convertView;
    }
}