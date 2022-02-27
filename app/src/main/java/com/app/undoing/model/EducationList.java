package com.app.undoing.model;

import com.app.undoing.R;

import java.util.ArrayList;
import java.util.List;

public class EducationList {
    private static EducationList instance=null;
    public List<EducationItem> list=new ArrayList<>();

    private List<String> title_list=List.of("3D打印模型的前生后世","谁是巧克力届的奥斯卡","一件羊毛外套的一生","养乐多的一生","远渡重洋的沙拉");
    private  List<Integer> image_list=List.of(R.drawable.threedprint,R.drawable.chocolate,R.drawable.clothes,R.drawable.foodyuan,R.drawable.foodgong);
    private  List<String> pdf_list=List.of("threeDprint.pdf","chocolate.pdf","clothes.pdf","foodYuan.pdf","foodGong.pdf");

    public EducationList(){
        for(int i=0;i<title_list.size();++i){
            list.add(new EducationItem(title_list.get(i), image_list.get(i),pdf_list.get(i)));
        }
    }

    public static EducationList getInstance(){
        if(instance==null)  instance = new EducationList();
        return instance;
    }
}
