package com.example.asus.organization2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus.organization2.R;
import com.example.asus.organization2.Bean.Info_PeronalMessage;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by ASUS on 2019/2/9.
 */

public class Adapter_FriendsList extends BaseAdapter {

    ArrayList<Info_PeronalMessage> info_messagesArray = new ArrayList<>();
    Context context;

    public Adapter_FriendsList(){

    }

    public Adapter_FriendsList(ArrayList info_messagesArray, Context context){
        this.info_messagesArray = (ArrayList)info_messagesArray.clone();
        this.context = context;
        notifyDataSetChanged();
    }

    public void setNewsList(ArrayList<Info_PeronalMessage> list) {
        if (list != null) {
            info_messagesArray = (ArrayList<Info_PeronalMessage>) list.clone();
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
        SmartImageView Iv_friendsfind;
        TextView Tv_friendsfind_name;
        TextView Tv_friendsfind_phoneNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Info_PeronalMessage attentionlist = info_messagesArray.get(position);
        Adapter_FriendsList.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.view_friendsfind, parent, false);
            holder = new Adapter_FriendsList.ViewHolder();
            holder.Iv_friendsfind = convertView.findViewById(R.id.Iv_friendsfind);
            holder.Tv_friendsfind_name = convertView.findViewById(R.id.Tv_friendsfind_name);
            holder.Tv_friendsfind_phoneNumber = convertView.findViewById(R.id.Tv_friendsfind_phoneNumber);
            convertView.setTag(holder);
        } else {
            holder = (Adapter_FriendsList.ViewHolder) convertView.getTag();
        }
        holder.Iv_friendsfind.setImageUrl(attentionlist.getHeadPicture(), R.drawable.ic_launcher_background, R.drawable.ic_launcher_background);
        holder.Tv_friendsfind_name.setText(attentionlist.getName());
        holder.Tv_friendsfind_phoneNumber.setText(attentionlist.getPhoneNumber());
        return convertView;
    }
}
