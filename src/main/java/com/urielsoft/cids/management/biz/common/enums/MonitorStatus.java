package com.urielsoft.cids.management.biz.common.enums;

/**
 * CIDS Monitor Status Enum
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-04
 */

public enum MonitorStatus {
    ONLINE("ONLINE", 1), OFFLINE("OFFLINE", 2);

    private final String key;
    private final Integer value;

    MonitorStatus(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Integer getValue() {
        return value;
    }
}
