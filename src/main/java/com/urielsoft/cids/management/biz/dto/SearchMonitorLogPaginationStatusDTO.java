package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Search Monitor Log Pagination Status DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */

public class SearchMonitorLogPaginationStatusDTO {
    /**
     * searChableoCCRRNCDt
     */
    @Getter
    @Setter
    boolean searchableOCCRRNCDt = false;

    /**
     * orderableoCCRRNCDt
     */
    @Getter
    @Setter
    boolean orderableOCCRRNCDt = false;

    /**
     * searhoCCRRNCDtValue
     */
    @Getter
    @Setter
    String searchOCCRRNCDtValue;

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
     * searChableLogLvl
     */
    @Getter
    @Setter
    boolean searChableLogLvl = false;

    /**
     * orderableLogLvl
     */
    @Getter
    @Setter
    boolean orderableLogLvl = false;

    /**
     * searchLogLvlValue
     */
    @Getter
    @Setter
    String searchLogLvlValue;

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
    String[] searchLocTySeqNoValue;

    /**
     * searChableMonNm
     */
    @Getter
    @Setter
    boolean searChableMonNm = false;

    /**
     * searchSysTyNmValue
     */
    @Getter
    @Setter
    String searchSysTyNmValue;

    /**
     * searChableSysTyNm
     */
    @Getter
    @Setter
    boolean searChableSysTyNm = false;

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
     * sortBy
     */
    @Getter
    @Setter
    String sortBy;

    /**
     * orderableSeqNo
     */
    @Getter
    @Setter
    boolean orderableSeqNo = false;

    /**
     * orderableLogId
     */
    @Getter
    @Setter
    boolean orderableLogId = false;

    /**
     * orderableLogData
     */
    @Getter
    @Setter
    boolean orderableLogData = false;

    /**
     * orderableMonSeqNo
     */
    @Getter
    @Setter
    boolean orderableMonSeqNo = false;

    /**
     * orderableSysTyNm
     */
    @Getter
    @Setter
    boolean orderableSysTyNm = false;

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
            case "MonNm":
                return orderableMonNm;
            case "sysTyNm":
                return orderableSysTyNm;
            case "logId":
                return orderableLogId;
            case "logLvl":
                return orderableLogLvl;
            case "logData":
                return orderableLogData;
            case "monSeqNo":
                return orderableMonSeqNo;
            case "oCCRRNCDt":
                return orderableOCCRRNCDt;
            default:
                return false;
        }
    }

}
