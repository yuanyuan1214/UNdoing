package com.app.undoing.model;

import com.app.undoing.Database.DBManager;
import com.app.undoing.R;

public class VideoItem {
    public static int getVideoResource() {
        //TODO: 添加视频资源(依据)
        int number=(DBManager.getTotalSaveCount()) % 5;
        switch (number){
            case 0: return R.raw.tree1;
            case 1: return R.raw.tree2;
            case 2: return R.raw.tree3;
            case 3: return R.raw.tree4;
            case 4: return R.raw.tree5;
            default: return R.raw.tree1;
        }
    }
}
