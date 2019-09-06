package com.group.marriage.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 申述类型
 */
public enum AppealType {
    忘记密码("1"),
    封号申述("2");

    private AppealType(String value){
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getEnumName(String value){
        if(StringUtils.isEmpty(value)) return null;
        AppealType[] values = AppealType.values();
        for(int i=0;i<values.length;i++){
            AppealType enumobj = values[i];
            if(value.equals(enumobj.getValue())){
                return enumobj.name();
            }
        }
        return null;
    }
}
