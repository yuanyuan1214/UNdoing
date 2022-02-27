package com.app.undoing.model;

import com.app.undoing.Adapter.AccountAdapter;
import com.app.undoing.Database.AccountBean;
import com.app.undoing.R;

public class AccountItem {
    public boolean isDate;
    public AccountBean account;
    public String dateText="";

    public AccountItem(boolean isDate, AccountBean accountBean) {
        this.isDate=isDate;
        this.account=accountBean;
    }

    public AccountItem(boolean isDate, String dateText) {
        this.isDate=isDate;
        this.dateText=dateText;
    }

    public int getTypeImage() {
        switch (account.getTypename()){
            case "服饰": return R.drawable.ic_clothes;
            case "小物": return R.drawable.ic_tinythings;
            case "日化": return R.drawable.ic_daily;
            case "学习": return R.drawable.ic_study;
            case "数码": return R.drawable.ic_digit;
            case "餐饮": return R.drawable.ic_eat;
            case "零食": return R.drawable.ic_snack;
            case "交通": return R.drawable.ic_transport;
            case "旅行": return R.drawable.ic_travel;
            case "娱乐": return R.drawable.ic_entertain;
            case "医疗": return R.drawable.ic_medical;
            case "通讯": return R.drawable.ic_communication;
            case "校园": return R.drawable.ic_school;
            case "水电": return R.drawable.ic_water;
            case "洗衣": return R.drawable.ic_wash;
            case "其他": return R.drawable.ic_other;
            default: return R.drawable.ic_other;
        }
    }

    public int getMoneyColor(int type) {
        //根据钱数确定颜色
        float money=account.getItemmoney();
        if(type== AccountAdapter.TYPE_GREED){
            if(money<100) return R.color.grass_degree_1;
            else if(money<500) return R.color.grass_degree_2;
            else if(money<1000) return R.color.grass_degree_3;
            else return R.color.grass_degree_4;
        }
        else{
            if(money<100) return R.color.account_degree_1;
            else if(money<500) return R.color.account_degree_2;
            else if(money<1000) return R.color.account_degree_3;
            else return R.color.account_degree_4;
        }
    }

    public int getCarbonDegree() {
        // 根据碳排放量确定显示等级
        if(account.getTotalCarbon()==0.0) return R.drawable.ic_degree0;
        double value=Math.log(account.getTotalCarbon())+2.0;
        int degree=(int)Math.round(value);
        switch (degree){
            case 0: return R.drawable.ic_degree0;
            case 1: return R.drawable.ic_degree1;
            case 2: return R.drawable.ic_degree2;
            case 3: return R.drawable.ic_degree3;
            case 4: return R.drawable.ic_degree4;
            case 5: return R.drawable.ic_degree5;
            case 6: return R.drawable.ic_degree6;
            case 7: return R.drawable.ic_degree7;
            case 8: return R.drawable.ic_degree8;
            case 9: return R.drawable.ic_degree9;
            default: return R.drawable.ic_degree9;
        }
    }

    public String getIntroduction() {
        switch (account.getTypename()){
            case "服饰": return "时尚产业产生了10%的全球碳排放量（超过造船业等大型重工业）；牛仔裤几乎就是由水制成的！一条牛仔裤耗费3480升水（约为一个成年人五年的饮水量），产生约2500种化学物质（丙烯酸树脂、粘合剂、漂白粉，酚类化合物，偶碳化合物，次氯酸盐，钾金属、偶氮染料，高锰酸钾，铬、镉等的重金属原料，等等），严重水污染（Ⅳ类水），工人与高锰酸钾的频繁直接接触，工厂地当地人群癌症、胃病、皮肤病和其他疾病的高发病率。";
            case "小物": return "想一想，买回去后会好好使用它几次呢？如果不久便会把它忘在某处积灰的话，不如把它让给更有缘分的买主吧！";
            case "日化": return "化妆品的生产过程中易燃易爆、毒害、腐蚀、放射性等安全风险，污染性原料如口红中的苯胺、BHA 和BHT、洗衣粉中的三氯生、指甲油中的邻苯二甲酸二丁酯（DBP）、化学防晒剂中的羟苯甲酮、二苯酮-3，排放大量污水，塑料微珠类成分进入食物链循环......为了你的皮肤也为了地球，少用化学产品，不如试试自然成分的化妆品？";
            case "学习": return "看一看手里的手机、平板、笔记本电脑，已经“万事俱备”了......让无纸化学习成为可能，或者，充分使用每一张白纸吧！";
            case "数码": return "你真的需要更新手里的电子产品吗？这是商家的“计划报废”，还是时尚的“潮流前沿”......或许只需要简单的维修护理，它依然可以再为你服务几年！";
            case "餐饮": return "食物生产在全球生态排放中约占26%的大比重，而层层包装更是大大增加了环境成本......减少外卖、多多堂食，自带饭盒也是很棒的选择！";
            case "零食": return "全球瓶装水产业每年支出4000亿美元，想一想你每天拆掉的临时包装堆起来有多高......随身带水杯，多吃新鲜蔬菜水果，少吃袋装零食，既有利于健康、节约开支，又能减少包装！";
            case "交通": return "2021年中国汽车保有量超过3亿，燃油车造成的尾气污染、噪声污染与日俱增......不想再有雾霾天！选择步行、自行车、公共交通，顺便还能锻炼身体哦！";
            case "旅行":
            case "校园":
            case "通讯":
            case "医疗":
            case "娱乐":
            case "其他":
                return null;
            case "水电": return "火电仍是中国最主要电力来源，发电厂年燃煤量5000万吨、日耗水量每座10万吨，电力工业是中国最大的污染排放产业之一......选用节能产品、及时关灯、电脑关机、空调冬20度夏26度，最好减少任何电器的使用！\n" +
                    "中国是全球人均水资源最贫乏、也是用水最多的国家。地球可利用淡水仅占0.26%，预计2025年世界缺水人口将超25亿......减少使用洗衣机、洗碗机，都是节约用水的好方法！";
            case "洗衣": return "小件衣物尽量手洗，减少洗衣机的使用次数，与其使用机器烘干不如自然晾干......太阳和地球都会感谢你的勤劳！";
            default: return null;
        }
    }

}
