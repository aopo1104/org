package com.example.asus.organization2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Bean.Info_organizationMessage;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by ASUS on 2019/2/11.
 */

public class Adapter_organizationList extends BaseAdapter{

    ArrayList<Info_organizationMessage> info_messagesArray = new ArrayList<>();
    Context context;

    public Adapter_organizationList(){

    }

    public Adapter_organizationList(ArrayList info_messagesArray, Context context){
        this.info_messagesArray = (ArrayList)info_messagesArray.clone();
        this.context = context;
        notifyDataSetChanged();
    }

    public void setNewsList(ArrayList<Info_organizationMessage> list) {
        if (list != null) {
            info_messagesArray = (ArrayList<Info_organizationMessage>) list.clone();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return info_messagesArray.size();
    }

    @Override
    public Object getItem(int position) {
        return info_messagesArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        SmartImageView Iv_organizationItem_headPicture;
        TextView Tv_organizationItem_name;
        TextView Tv_organizationItem_type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Info_organizationMessage orgList = info_messagesArray.get(position);
        Adapter_organizationList.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.view_organizationitem, parent, false);
            holder = new Adapter_organizationList.ViewHolder();
            holder.Iv_organizationItem_headPicture = convertView.findViewById(R.id.Iv_organizationItem_headPicture);
            holder.Tv_organizationItem_name = convertView.findViewById(R.id.Tv_organizationItem_name);
            holder.Tv_organizationItem_type = convertView.findViewById(R.id.Tv_organizationItem_type);
            convertView.setTag(holder);
        } else {
            holder = (Adapter_organizationList.ViewHolder) convertView.getTag();
        }
        holder.Iv_organizationItem_headPicture.setImageUrl(orgList.getHeadPicture(), R.drawable.ic_launcher_background, R.drawable.ic_launcher_background);
        holder.Tv_organizationItem_name.setText(orgList.getName());
        holder.Tv_organizationItem_type.setText(orgList.getType()==1 ? "社团" : "组织");
        return convertView;
    }
}
