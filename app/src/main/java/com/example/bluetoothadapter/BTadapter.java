package com.example.bluetoothadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BTadapter extends ArrayAdapter<LIstItem> {
    private List<LIstItem> list;

    public BTadapter(@NonNull Context context, int resource, List<LIstItem> btList) {
        super(context, resource, btList);
        list = btList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, null, false);
            viewHolder.textView = convertView.findViewById(R.id.tvBTname);
            viewHolder.checkBox = convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).getName());
        viewHolder.checkBox.setChecked(false);
        return convertView;
    }

    class ViewHolder{
        TextView textView;
        CheckBox checkBox;
    }
}
