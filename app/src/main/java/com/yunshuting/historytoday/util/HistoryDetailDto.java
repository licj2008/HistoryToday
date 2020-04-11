package com.yunshuting.historytoday.util;

//{
//        "status": 0,
//        "msg": "成功",
//        "data": {
//        "id": "4525",
//        "title": "黎巴嫩与以色列发生军事冲突",
//        "content": "1996年4月8日,黎巴嫩南部平民被以色列地雷炸伤，引发两国严重军事冲突。<br>    4月9日，黎巴嫩真主党游击队凌晨向以色列北部发射了两枚火箭，作为报复，但随即招致以军以黎南部的报复性炮击。<br>    11—12日，以色列对黎巴嫩南部、贝卡谷地以及贝鲁特南郊的黎真主党基地进行大规模空袭，这是1982年黎巴嫩战争以来以色列首次空袭贝鲁特，造成20人死亡，50多人受伤。黎真主党奋起还击，向以北部发射喀秋莎火箭，炸伤数人。以海军13日凌晨对贝鲁特成员发出总动员，要求奋起抵抗以色列军队的袭击。国际社会强烈呼吁冲突双方立即停火。<br>    18日，联合国安理会一致通过1052号决议，要求冲突各方停止敌对行动。26日，经过有关各方的外交努力，黎巴嫩和以色列正式达成停火协议。<br>   这次代号为“愤怒的葡萄”的作战行动造成近两百平民死亡，50多万黎巴嫩平民沦为难民。<br>",
//        "date": "1996-04-08",
//        "y": "1996",
//        "m": "4",
//        "d": "8",
//        "fav": "0",
//        "pre_id": "4524",
//        "pre_title": "世界上跨径最大的斜拉桥——上海杨浦大桥合龙",
//        "next_id": "4526",
//        "next_title": "日本首相细川宣布辞职",
//        "famous": {
//        "id": "6522",
//        "name": "奥维德",
//        "content": "爱情把我拽向这边，而理智却要把我拉向那边。"
//        }
//        }
//        }
public class HistoryDetailDto {
    public String msg;
    public int status;
    public DetailBean data;

    public class DetailBean {
        public String id;
        public String title;
        public String content;
        public String date;
        public String y;
        public String m;
        public String d;
        public String fav;
        public String pre_id;
        public String pre_title;
        public String next_id;
        public String next_title;
        public FamousBean famous;
    }
}

