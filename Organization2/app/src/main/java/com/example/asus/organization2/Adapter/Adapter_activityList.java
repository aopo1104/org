package com.example.asus.organization2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Bean.Info_ActivityMessage;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by ASUS on 2019/2/14.
 */

public class Adapter_activityList extends BaseAdapter{
    ArrayList<Info_ActivityMessage> infoActivityArray = new ArrayList<>();
    Context context;

    public Adapter_activityList(){

    }

    public Adapter_activityList(ArrayList activityInfoArray, Context context){
        this.infoActivityArray = (ArrayList)activityInfoArray.clone();
        this.context = context;
        notifyDataSetChanged();
    }

    public void setNewsList(ArrayList<Info_ActivityMessage> list) {
        if (list != null) {
            infoActivityArray = (ArrayList<Info_ActivityMessage>) list.clone();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return infoActivityArray.size();
    }

    @Override
    public Object getItem(int position) {
        return infoActivityArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        View view_activitylist_type;
        TextView Tv_activitylist_type;
        SmartImageView Siv_activityInfoImg;
        TextView Tv_activityInfoTitle;
        TextView Tv_activityInfoTime;
        TextView Tv_activityInfoContent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Info_ActivityMessage activity = infoActivityArray.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_activity_list, parent, false);
            holder = new ViewHolder();
            holder.view_activitylist_type = convertView.findViewById(R.id.view_activitylist_type);
            holder.Tv_activitylist_type = convertView.findViewById(R.id.Tv_activitylist_type);
            holder.Siv_activityInfoImg = convertView.findViewById(R.id.Siv_activityInfoImg);
            holder.Tv_activityInfoTitle = convertView.findViewById(R.id.Tv_activityInfoTitle);
            holder.Tv_activityInfoTime = convertView.findViewById(R.id.Tv_activityInfoTime);
            holder.Tv_activityInfoContent = convertView.findViewById(R.id.Tv_activityInfoContent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(activity.getType()==1){
            holder.view_activitylist_type.setBackgroundResource(R.drawable.triangle_shape_brown);
            holder.Tv_activitylist_type.setText("活动");
        }
        holder.Siv_activityInfoImg.setImageUrl(activity.getPicture(), R.drawable.ic_launcher_background, R.drawable.ic_launcher_background);
        holder.Tv_activityInfoTitle.setText(activity.getTitle());
        holder.Tv_activityInfoTime.setText(activity.getCreateTime());
        holder.Tv_activityInfoContent.setText(activity.getContent());
        return convertView;
    }
}
