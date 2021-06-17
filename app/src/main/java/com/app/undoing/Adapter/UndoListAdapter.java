package com.app.undoing.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.app.undoing.Content.DoingListItem;
import com.app.undoing.Content.UnDoListItem;
import com.app.undoing.MainActivity;
import com.app.undoing.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

public class UndoListAdapter extends BaseAdapter {

    private LinkedList<UnDoListItem> itemData;
    private Context context;

    public UndoListAdapter(Context context) {
        this.itemData = new LinkedList<UnDoListItem>();
        this.context=context;
    }

    public UndoListAdapter(LinkedList<UnDoListItem> itemData,Context context){
        this.itemData=itemData;
        this.context=context;
    }

    public void addItemData(UnDoListItem listItem) {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局
        convertView = LayoutInflater.from(context).inflate(R.layout.undo_list_item,parent,false);

        //判断是billActivity内的
        if (parent.getId()==R.id.bill_list) {
            //设置日期部分
            System.out.println("当前item的position是"+position);
            if ((position == 0)||(itemData.get(position).getUndo_date()!=itemData.get(position-1).getUndo_date())) {
                LinearLayout out_undo_list_item=(LinearLayout)convertView.findViewById(R.id.out_undo_list_item);
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日-EEEE");
                String dateOutput = sdf.format(itemData.get(position).getUndo_date());
                TextView dateView=new TextView(context);
                dateView.setTextAppearance(R.style.bill_date_text);
                dateView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) dateView.getLayoutParams();
                lp.setMargins(20, 20, 10, 20);
                dateView.setLayoutParams(lp);
                dateView.setText(dateOutput);
                out_undo_list_item.addView(dateView,0);
            }
        }

        //获取引用布局资源
        LinearLayout outImg=(LinearLayout) convertView.findViewById(R.id.out_item_img);
        ImageView img = (ImageView) convertView.findViewById(R.id.item_img);
        TextView content = (TextView) convertView.findViewById(R.id.item_content);
        TextView cost = (TextView) convertView.findViewById(R.id.item_cost);
        //设置内容
        outImg.setBackgroundColor(context.getResources().getColor(itemData.get(position).getImg_background()));
        img.setBackgroundResource(itemData.get(position).getUndo_image());
        content.setText(itemData.get(position).getUndo_content());
        cost.setText(String.format("%.2f",itemData.get(position).getUndo_cost()));
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
