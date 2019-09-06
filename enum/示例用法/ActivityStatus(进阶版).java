package com.ems.demo.enums;

import org.springframework.util.StringUtils;

public enum ActivityStatus {
    报名中("报名中","1"),
    截止报名("截止报名","2");

    private ActivityStatus(String explain,String index){
        this.explain = explain;
        this.index = index;
    }

    private String explain;//详细说明
    private String index;   //数字代码

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }


    public static String getEnumName(String index){
        if(StringUtils.isEmpty(index)) return null;
        ActivityStatus[] values = ActivityStatus.values();
        for(int i=0;i<values.length;i++){
            ActivityStatus enumobj = values[i];
            if(index.equals(enumobj.getIndex())){
                return enumobj.getExplain();
            }
        }
        return null;
    }

    public static String getEnumNum(String explain){
        if(StringUtils.isEmpty(explain)) return null;
        ActivityStatus[] values = ActivityStatus.values();
        for(int i=0;i<values.length;i++){
            ActivityStatus enumobj = values[i];
            if(explain.equals(enumobj.getExplain())){
                return enumobj.getIndex();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(getEnumNum("报名中"));
        System.out.println(ActivityStatus.截止报名.getIndex());
        System.out.println(getEnumName("1"));
    }
}
