package com.app.undoing.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.undoing.Content.DoingListItem;
import com.app.undoing.R;

import java.util.LinkedList;

public class DoingListAdapter extends BaseAdapter {

    private LinkedList<DoingListItem> itemData;
    //context是什么好像是
    private Context context;

    public DoingListAdapter(Context context) {
        this.itemData = new LinkedList<DoingListItem>();
        this.context=context;
    }

    public DoingListAdapter(LinkedList<DoingListItem> itemData,Context context){
        this.itemData=itemData;
        this.context=context;
    }

    public void addItemData(DoingListItem listItem) {
        itemData.add(listItem);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局
        convertView = LayoutInflater.from(context).inflate(R.layout.doing_list_item,parent,false);

        //获取引用布局资源
        LinearLayout outImg=(LinearLayout) convertView.findViewById(R.id.out_item_img);
        ImageView img = (ImageView) convertView.findViewById(R.id.item_img);
        TextView content = (TextView) convertView.findViewById(R.id.item_content);
        TextView cost = (TextView) convertView.findViewById(R.id.item_cost);
        //设置内容
        outImg.setBackgroundColor(context.getResources().getColor(itemData.get(position).getImg_background()));
        img.setBackgroundResource(itemData.get(position).getDoing_image());
        content.setText(itemData.get(position).getDoing_content());
        cost.setText(String.format("%.2f",itemData.get(position).getDoing_cost()));
        //设置间隔背景
        LinearLayout outCost= (LinearLayout) convertView.findViewById(R.id.out_item_cost);
        LinearLayout outContent= (LinearLayout) convertView.findViewById(R.id.out_item_content);
        if (position%2==0)
        {
            outCost.setBackgroundColor(context.getResources().getColor(R.color.white));
            outContent.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        else {
            outCost.setBackgroundColor(context.getResources().getColor(R.color.list_gray));
            outContent.setBackgroundColor(context.getResources().getColor(R.color.list_gray));
        }

        return convertView;
    }
}

