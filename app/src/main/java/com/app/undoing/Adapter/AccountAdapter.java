package com.app.undoing.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.undoing.BookKeep;
import com.app.undoing.Database.AccountBean;
import com.app.undoing.Database.DBManager;
import com.app.undoing.PdfDocument;
import com.app.undoing.R;
import com.app.undoing.model.AccountItem;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.BaseViewHolder>{
    private Context context;
    private List<AccountItem> list=new ArrayList<>();
    private int adapterType;
    private boolean dark_background=false;

    public static final int VIEW_TYPE_DATE=0;
    public static final int TYPE_POSITIVE=1;
    public static final int TYPE_NEGATIVE=2;
    public static final int TYPE_GREED=3;

    public AccountAdapter(Context context){
        this.context=context;
    }

    public void getData(int dataType) {
        this.adapterType=dataType;
        dark_background=false;
        list=new ArrayList<>();
        List<AccountBean> dataList=new ArrayList<>();
        switch (dataType){
            case TYPE_POSITIVE : { dataList= DBManager.getPositivetb(); break;}
            case TYPE_NEGATIVE: { dataList=DBManager.getNegativetb(); break; }
            case TYPE_GREED: { dataList=DBManager.getGreedtb(); break; }
            default: return;
        }
        if(dataList.size()==0) {notifyDataSetChanged();return;}
        int year=dataList.get(0).getYear();
        int month=dataList.get(0).getMonth();
        int day=dataList.get(0).getDay();
        String date=year+"年"+month+"月"+day+"日";
        list.add(new AccountItem(true,date));
        list.add(new AccountItem(false,dataList.get(0)));
        for(int i=1;i<dataList.size();++i){
            int new_year=dataList.get(i).getYear();
            int new_month=dataList.get(i).getMonth();
            int new_day=dataList.get(i).getDay();
            if (new_year!=year || new_month != month || new_day !=day){
                year=new_year;
                month=new_month;
                day=new_day;
                date=year+"年"+month+"月"+day+"日";
                list.add(new AccountItem(true,date));
            }
            list.add(new AccountItem(false,dataList.get(i)));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_DATE:{
                View itemView= LayoutInflater.from(context).inflate(R.layout.list_date_item,parent,false);
                return new DateViewHolder(itemView);
            }
            case TYPE_NEGATIVE:
            case TYPE_POSITIVE: {
                View itemView= LayoutInflater.from(context).inflate(R.layout.book_list_item,parent,false);
                return new AccountViewHolder(itemView);
            }
            case TYPE_GREED: {
                View itemView= LayoutInflater.from(context).inflate(R.layout.grass_list_item,parent,false);
                return new GreedViewHolder(itemView);
            }
            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setData(position);
        holder.setListener(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).isDate) return VIEW_TYPE_DATE;
        return adapterType;
    }

    public class AccountViewHolder extends BaseViewHolder{

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {
            if(dark_background)  {itemView.setBackgroundColor(context.getResources().getColor(R.color.list_gray));dark_background=false;}
            else {itemView.setBackgroundColor(context.getResources().getColor(R.color.white));dark_background=true;}
            FrameLayout frameLayout=itemView.findViewById(R.id.out_item_img);
            frameLayout.setBackgroundColor(context.getResources().getColor(list.get(position).getMoneyColor(adapterType)));
            ImageView imageView=itemView.findViewById(R.id.item_img);
            imageView.setImageResource(list.get(position).getTypeImage());
            TextView type=itemView.findViewById(R.id.item_type);
            type.setText(list.get(position).account.getTypename());
            ImageView degree=itemView.findViewById(R.id.item_degree);
            degree.setImageResource(list.get(position).getCarbonDegree());
            TextView money=itemView.findViewById(R.id.item_money);
            String value="";
            if(adapterType == TYPE_POSITIVE){
                value="-¥"+list.get(position).account.getItemmoney();
            }
            else if(adapterType == TYPE_NEGATIVE){
                value="+¥"+list.get(position).account.getItemmoney();
                money.setTextColor(context.getResources().getColor(R.color.green));
            }
            money.setText(value);
        }

        @Override
        public void setListener(int position) {
            itemView.setOnClickListener(view -> {showPopupWindow(position);});
        }

        @Override
        public void createPopUpView() {
            popUpView=LayoutInflater.from(context).inflate(R.layout.popup_account, null);
        }
    }

    public class DateViewHolder extends BaseViewHolder{

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {
            TextView textView=itemView.findViewById(R.id.date_text);
            textView.setText(list.get(position).dateText);
        }

    }

    public class GreedViewHolder extends BaseViewHolder{

        public GreedViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {
            if(dark_background)  {itemView.setBackgroundColor(context.getResources().getColor(R.color.list_gray));dark_background=false;}
            else dark_background=true;
            FrameLayout frameLayout=itemView.findViewById(R.id.out_item_img);
            frameLayout.setBackgroundColor(context.getResources().getColor(list.get(position).getMoneyColor(adapterType)));
            ImageView imageView=itemView.findViewById(R.id.item_img);
            imageView.setImageResource(list.get(position).getTypeImage());
            TextView type=itemView.findViewById(R.id.item_type);
            type.setText(list.get(position).account.getTypename());
            ImageView degree=itemView.findViewById(R.id.item_degree);
            degree.setImageResource(list.get(position).getCarbonDegree());
        }

        @Override
        public void setListener(int position) {
            itemView.findViewById(R.id.grass_more).setOnClickListener(view -> {showPopupWindow(position);});
            itemView.findViewById(R.id.grass_minus).setOnClickListener(view -> {
                showPopupWindow(position);
                popUpView.findViewById(R.id.grass_tip).setVisibility(View.VISIBLE);
                DBManager.deleteItemFromGreedtbById(list.get(position).account.getId());
                DBManager.insertItemToNegativetb(list.get(position).account);
                deleteItem(position);
            });
            itemView.findViewById(R.id.grass_add).setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.putExtra("accountBean",list.get(position).account);
                intent.setClass(context, BookKeep.class);
                context.startActivity(intent);
            });
        }

        @Override
        public void createPopUpView() {
            popUpView=LayoutInflater.from(context).inflate(R.layout.popup_grass, null);
        }

        @Override
        public void setPopUpView(int position) {
            super.setPopUpView(position);
        }

        public void deleteItem(int position) {
            //判断该项是否是当前日期下唯一一项，若是则同日期一起删去
            if(list.get(position-1).isDate){
                if(list.size()<=position+1||list.get(position+1).isDate){
                    list.remove(position-1);
                    list.remove(position-1);
                }
                else {
                    list.remove(position);
                }
            }
            else{
                list.remove(position);
            }
            notifyDataSetChanged();
        }

    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder{
        public View popUpView;
        public PopupWindow popupWindow;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public abstract void setData(int position);
        public void setListener(int position) { }
        public void createPopUpView() {}
        public void setPopUpView(int position) {
            //set attributes
            ImageView imageView=popUpView.findViewById(R.id.account_type_image);
            imageView.setImageResource(list.get(position).getTypeImage());
            TextView type=popUpView.findViewById(R.id.account_type_text);
            type.setText(list.get(position).account.getTypename());
            TextView description=popUpView.findViewById(R.id.account_description);
            description.setText(list.get(position).account.getItemname());
            TextView isnew=popUpView.findViewById(R.id.account_isnew);
            String txt="";
            switch (list.get(position).account.getIsNew()){
                case 0: {
                    txt="全新";
                    break;
                }
                case 1: {
                    txt="二手";
                    break;
                }
                default: {
                    txt="";
                }
            }
            isnew.setText(txt);
            TextView selfcarbon=popUpView.findViewById(R.id.account_carbon_self);
            selfcarbon.setText(String.format("%s kgCO2eq", list.get(position).account.getSelfCarbon()));
            TextView wrapcarbon=popUpView.findViewById(R.id.account_carbon_wrap);
            wrapcarbon.setText(String.format("%s kgCO2eq", list.get(position).account.getWrapCarbon()));
            TextView money=popUpView.findViewById(R.id.account_money);
            money.setText(String.format("¥ %s", list.get(position).account.getItemmoney()));
            TextView introduce=popUpView.findViewById(R.id.account_introduce);
            String introTxt=list.get(position).getIntroduction();
            if(introTxt==null) introduce.setVisibility(View.GONE);
            else introduce.setText(introTxt);
            ImageView close=popUpView.findViewById(R.id.account_close);
            close.setOnClickListener(mview -> {popupWindow.dismiss();});
            //
        }
        protected void showPopupWindow(int position) {
            createPopUpView();
            popupWindow = new PopupWindow(popUpView,
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
            setPopUpView(position);
            popupWindow.setTouchable(true);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                    // 这里如果返回true的话，touch事件将被拦截
                    // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                }
            });
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
            // 我觉得这里是API的一个bug
            popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
            Activity activity=(Activity) context;
            //产生背景变暗效果
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 0.4f;
            activity.getWindow().setAttributes(lp);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                //在dismiss中恢复透明度
                public void onDismiss() {
                    WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                    lp.alpha = 1f;
                    activity.getWindow().setAttributes(lp);
                }
            });
            popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
    }
}
