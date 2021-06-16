package com.app.undoing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.undoing.Content.DoingListItem;
import com.app.undoing.Content.RecommendListItem;
import com.app.undoing.R;

import java.util.LinkedList;
import java.util.List;

public class RecommendListAdapter extends BaseAdapter {
    private LinkedList<RecommendListItem> itemData;
    private Context context;
    public RecommendListAdapter(LinkedList<RecommendListItem> itemData,Context context){
        this.itemData=itemData;
        this.context=context;
    }
    public void addItemData(RecommendListItem listItem) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.recommend_list_item,parent,false);

        //获取引用布局资源
        ImageView img = (ImageView) convertView.findViewById(R.id.item_img);
        TextView content = (TextView) convertView.findViewById(R.id.item_content);
        TextView detail=(TextView) convertView.findViewById(R.id.item_content_detail);
        TextView save = (TextView) convertView.findViewById(R.id.item_save);
        TextView save_count_0=convertView.findViewById(R.id.save_count_0);
        TextView save_count_1=convertView.findViewById(R.id.save_count_1);
        TextView save_count_2=convertView.findViewById(R.id.save_count_2);
        TextView save_count_3=convertView.findViewById(R.id.save_count_3);
        TextView save_count_4=convertView.findViewById(R.id.save_count_4);

        //设置内容
        img.setBackgroundResource(itemData.get(position).getRecommend_image());
        content.setText(itemData.get(position).getRecommend_content());
        detail.setText(itemData.get(position).getRecommend_detail());
        save.setText(String.format("%.2f",itemData.get(position).getRecommend_save()));
        List<Integer> count_list=itemData.get(position).getRecommend_save_count();
        save_count_0.setText(count_list.get(0));
        save_count_1.setText(count_list.get(1));
        save_count_2.setText(count_list.get(2));
        save_count_3.setText(count_list.get(3));
        save_count_4.setText(count_list.get(4));

        //设置间隔背景
        if (position%2==0)
        {
            LinearLayout outCost= (LinearLayout) convertView.findViewById(R.id.out_item_cost);
            LinearLayout outContent= (LinearLayout) convertView.findViewById(R.id.out_item_content);
            outCost.setBackgroundColor(context.getResources().getColor(R.color.white));
            outContent.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        else {
            LinearLayout outCost= (LinearLayout) convertView.findViewById(R.id.out_item_cost);
            LinearLayout outContent= (LinearLayout) convertView.findViewById(R.id.out_item_content);
            outCost.setBackgroundColor(context.getResources().getColor(R.color.list_gray));
            outContent.setBackgroundColor(context.getResources().getColor(R.color.list_gray));
        }

        return convertView;
    }
}
