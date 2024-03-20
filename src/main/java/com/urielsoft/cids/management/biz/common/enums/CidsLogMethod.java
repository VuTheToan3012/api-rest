package com.urielsoft.cids.management.biz.common.enums;

/**
 * Enum For Usage Log
 * Chinh_hd BKS
 * @version 1.0
 * @since 12/09/2023
 */

public enum CidsLogMethod {
    GETALl("Get All ", 1),
    GETDetail("Get Detail ", 2),
    POST("Create New ", 3),
    PUT("Edit ", 4),
    DELETE("Delete ", 5),
    CheckDuplication("CheckDuplication ", 6),
    Count("Count ", 7);


    /**
     * key
     */
    private final String key;

    /**
     * value
     */
    private final Integer value;

    /**
     * Cids Log Method
     * @param key
     * @param value
     */
    CidsLogMethod(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get Key
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * Get Value
     * @return
     */
    public Integer getValue() {
        return value;
    }
}
