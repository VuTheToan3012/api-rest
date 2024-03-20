package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Error History Pagination Search
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */

public class SearchErrorHistoryPaginationStatusDTO {
    /**
     * searChableSeqNo
     */
    @Getter
    @Setter
    boolean searChableSeqNo = false;

    /**
     * orderableSeqNo
     */
    @Getter
    @Setter
    boolean orderableSeqNo = false;

    /**
     * searhSeqNoValue
     */
    @Getter
    @Setter
    String searhSeqNoValue;

    /**
     * searChableWkTySeqNo
     */
    @Getter
    @Setter
    boolean searChableWkTySeqNo = false;

    /**
     * orderableWkTySeqNo
     */
    @Getter
    @Setter
    boolean orderableWkTySeqNo = false;

    /**
     * searchWkTySeqNoValue
     */
    @Getter
    @Setter
    String searchWkTySeqNoValue;

    /**
     * searChableLocTySeqNo
     */
    @Getter
    @Setter
    boolean searChableLocTySeqNo = false;

    /**
     * orderableLocTySeqNo
     */
    @Getter
    @Setter
    boolean orderableLocTySeqNo = false;

    /**
     * searchLocTySeqNoValue
     */
    @Getter
    @Setter
    String searchLocTySeqNoValue;

    /**
     * searChableMonNm
     */
    @Getter
    @Setter
    boolean searChableMonNm = false;

    /**
     * orderableMonNm
     */
    @Getter
    @Setter
    boolean orderableMonNm = false;

    /**
     * searchMonNmValue
     */
    @Getter
    @Setter
    String searchMonNmValue;

    /**
     * searChableLogData
     */
    @Getter
    @Setter
    boolean searChableLogData = false;

    /**
     * orderableLogData
     */
    @Getter
    @Setter
    boolean orderableLogData = false;

    /**
     * searchLogDataValue
     */
    @Getter
    @Setter
    String searchLogDataValue;

    /**
     * searChableRegDt
     */
    @Getter
    @Setter
    boolean searChableRegDt = false;

    /**
     * orderableRegDt
     */
    @Getter
    @Setter
    boolean orderableRegDt = false;

    /**
     * searchRegDtValue
     */
    @Getter
    @Setter
    String searchRegDtValue;

    /**
     * sortBy
     */
    @Getter
    @Setter
    String sortBy;

    /**
     * dir
     */
    @Getter
    @Setter
    String dir;

    /**
     * @param columnName
     * @return
     */
    public boolean isOrderByColumn(String columnName) {
        switch (columnName) {
            case "seqNo":
                return orderableSeqNo;
            case "wkTySeqNo":
                return orderableWkTySeqNo;
            case "locTySeqNo":
                return orderableLocTySeqNo;
            case "monNm":
                return orderableMonNm;
            case "logData":
                return orderableLogData;
            case "regDt":
                return orderableRegDt;
            default:
                return false;
        }
    }

}
