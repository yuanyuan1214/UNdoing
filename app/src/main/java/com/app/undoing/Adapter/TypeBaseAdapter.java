package com.app.undoing.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.app.undoing.R;
import com.app.undoing.model.TypeItem;

import java.util.List;

public class TypeBaseAdapter extends BaseAdapter {
    Context context;
    List<TypeItem> mDatas;
    public int selectPos = 0;  //选中位置
    private int type=0;
    public TypeBaseAdapter(Context context, List<TypeItem> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    public void setType(int type){
        this.type=type;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    // 此适配器不考虑复用问题，因为所有的item都显示在界面上，不会因为滑动就消失，所有没有剩余的convertView，所以不用复写
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv,parent,false);
        //查找布局当中的控件
        ImageView iv = convertView.findViewById(R.id.item_recordfrag_iv);
        TextView tv = convertView.findViewById(R.id.item_recordfrag_tv);
        FrameLayout frameLayout=convertView.findViewById(R.id.item_recordfrag_back);
        //获取指定位置的数据源
        if(type==0){
            ColorStateList colorStateList1 = context.getResources().getColorStateList(R.color.book_image_background);
            ColorStateList colorStateList2 = context.getResources().getColorStateList(R.color.book_image_color);
            frameLayout.setBackgroundTintList(colorStateList1);
            iv.setImageTintList(colorStateList2);
        }
        else {
            ColorStateList colorStateList1 = context.getResources().getColorStateList(R.color.grass_image_background);
            ColorStateList colorStateList2 = context.getResources().getColorStateList(R.color.grass_image_color);
            frameLayout.setBackgroundTintList(colorStateList1);
            iv.setImageTintList(colorStateList2);
        }
        TypeItem typeItem = mDatas.get(position);
        tv.setText(typeItem.getTypename());
        iv.setImageResource(typeItem.getImageId());
//        判断当前位置是否为选中位置，如果是选中位置，就设置为带颜色的图片，否则为灰色图片
        if (selectPos == position) {
            frameLayout.setSelected(true);
            iv.setSelected(true);
        }else{
            frameLayout.setSelected(false);
            iv.setSelected(false);
        }
        return convertView;
    }
}
