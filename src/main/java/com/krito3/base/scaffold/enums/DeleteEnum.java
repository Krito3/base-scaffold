package com.krito3.base.scaffold.enums;

/**
 * @author kno.ci
 * @description: 全局使用，deleted 是否删除字段 枚举
 * @date 2022/3/10 下午3:11
 */
public enum DeleteEnum {
    NOT_DELETE(0, "正常"), DELETED(1, "已删除");

    DeleteEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getName(int value) {
        for (DeleteEnum obj : DeleteEnum.values()) {
            if (obj.getValue() == value) {
                return obj.getName();
            }
        }
        return "";
    }
}
