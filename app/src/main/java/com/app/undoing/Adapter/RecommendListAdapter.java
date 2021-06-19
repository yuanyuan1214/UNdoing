package com.app.undoing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.app.undoing.Content.DoingListItem;
import com.app.undoing.Content.RecommendListItem;
import com.app.undoing.MainActivity;
import com.app.undoing.R;
import com.app.undoing.RecommendList;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class RecommendListAdapter extends BaseAdapter {
    private LinkedList<RecommendListItem> itemData;
    private Context context;
    public RecommendListAdapter(LinkedList<RecommendListItem> itemData,Context context){
        this.itemData=itemData;
        this.context=context;
        System.out.println("recommend: "+context);
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
        return itemData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局
        convertView = LayoutInflater.from(context).inflate(R.layout.recommend_list_item,parent,false);

        //判断是recommend内的
        if (parent.getId()==R.id.recommend_list) {
            //设置日期部分
            if ((position == 0)||(itemData.get(position).getRecommend_date()!=itemData.get(position-1).getRecommend_date())) {
                LinearLayout out_recommend_list_item=(LinearLayout)convertView.findViewById(R.id.out_recommend_list_item);
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日-EEEE");
                String dateOutput = sdf.format(itemData.get(position).getRecommend_date());
                TextView dateView=new TextView(context);
                dateView.setTextAppearance(R.style.bill_date_text);
                dateView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dateView.getLayoutParams();
                lp.setMargins(20, 20, 10, 20);
                dateView.setLayoutParams(lp);
                dateView.setText(dateOutput);
                out_recommend_list_item.addView(dateView,0);
            }
        }
        //获取引用布局资源
        LinearLayout outItemImg=(LinearLayout)convertView.findViewById(R.id.out_item_img);
        ImageView img = (ImageView) convertView.findViewById(R.id.item_img);
        TextView content = (TextView) convertView.findViewById(R.id.item_content);
        TextView detail=(TextView) convertView.findViewById(R.id.item_content_detail);
        TextView save = (TextView) convertView.findViewById(R.id.item_save);
        TextView save_count_0=convertView.findViewById(R.id.save_count_0);
        TextView save_count_1=convertView.findViewById(R.id.save_count_1);
        TextView save_count_2=convertView.findViewById(R.id.save_count_2);
        TextView save_count_3=convertView.findViewById(R.id.save_count_3);
        TextView save_count_4=convertView.findViewById(R.id.save_count_4);
        Button add_btn=(Button) convertView.findViewById(R.id.add);
        Button minus_btn=(Button) convertView.findViewById(R.id.minus);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemData.remove(getItem(position));
                notifyDataSetChanged();
            }
        });
        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemData.remove(getItem((position)));
                notifyDataSetChanged();
            }
        });

        //设置内容
        outItemImg.setBackgroundColor(context.getResources().getColor(itemData.get(position).getImg_background()));
        img.setBackgroundResource(itemData.get(position).getRecommend_image());
        content.setText(itemData.get(position).getRecommend_content());
        detail.setText(itemData.get(position).getRecommend_detail());
        save.setText(String.format("%.2f",itemData.get(position).getRecommend_save()));
        List<Integer> count_list=itemData.get(position).getRecommend_save_count();
        save_count_0.setText(count_list.get(0).toString());
        save_count_1.setText(count_list.get(1).toString());
        save_count_2.setText(count_list.get(2).toString());
        save_count_3.setText(count_list.get(3).toString());
        save_count_4.setText(count_list.get(4).toString());

        //设置间隔背景
        LinearLayout outItem= (LinearLayout) convertView.findViewById(R.id.out_item_content);
        LinearLayout recommendContent=(LinearLayout) convertView.findViewById(R.id.recommend_content);
        if (position%2==0)
        {
            outItem.setBackgroundColor(context.getResources().getColor(R.color.white));
            recommendContent.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        else {
            outItem.setBackgroundColor(context.getResources().getColor(R.color.list_gray));
            recommendContent.setBackgroundColor(context.getResources().getColor(R.color.list_gray));
        }

        return convertView;
    }
}
