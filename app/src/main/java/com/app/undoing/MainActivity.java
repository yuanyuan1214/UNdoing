package com.app.undoing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.app.undoing.Adapter.DoingListAdapter;
import com.app.undoing.Content.DoingListItem;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinkedList<DoingListItem> initList;
    //好像是用于处理ListView的动态添加的
    private DoingListAdapter listAdapter;
    private ListView doing_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doing_list = (ListView) findViewById(R.id.doingList);
        initList =new LinkedList<DoingListItem>();
        initList.add(new DoingListItem("买水果",-21.00,R.drawable.dashicons_fruit));
        listAdapter = new DoingListAdapter(initList,MainActivity.this);
        listAdapter.addItemData(new DoingListItem("五角场看电影",-48.00,R.drawable.dashicons_editor_video));
        listAdapter.addItemData(new DoingListItem("请同学吃饭",-121.00,R.drawable.dashicons_food));
        listAdapter.addItemData(new DoingListItem("网购洗发水",-78.00,R.drawable.dashicons_cart));
        listAdapter.addItemData(new DoingListItem("预订回家机票",-830.00,R.drawable.dashicons_plane));
        listAdapter.addItemData(new DoingListItem("买狗粮",-120.00,R.drawable.dashicons_pets));
        doing_list.setAdapter(listAdapter);
        listAdapter.addItemData(new DoingListItem("请同学吃饭",-121.00,R.drawable.dashicons_food));
        listAdapter.addItemData(new DoingListItem("网购洗发水",-78.00,R.drawable.dashicons_cart));
        listAdapter.addItemData(new DoingListItem("预订回家机票",-830.00,R.drawable.dashicons_plane));
        listAdapter.addItemData(new DoingListItem("买狗粮",-120.00,R.drawable.dashicons_pets));
        fixListViewHeight(doing_list);
    }

    public void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
            View listViewItem = listAdapter.getView(index , null, listView);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}