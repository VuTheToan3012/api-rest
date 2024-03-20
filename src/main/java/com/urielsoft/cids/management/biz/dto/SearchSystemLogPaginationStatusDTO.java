package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Search System Log Pagination Status DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */

public class SearchSystemLogPaginationStatusDTO {
    /**
     * searChableoCCRRNCDt
     */
    @Getter
    @Setter
    boolean searChableoCCRRNCDt = false;

    /**
     * orderableoCCRRNCDt
     */
    @Getter
    @Setter
    boolean orderableoCCRRNCDt = false;

    /**
     * searhoCCRRNCDtValue
     */
    @Getter
    @Setter
    String searchoCCRRNCDtValue;

    /**
     * searChableSysTyNm
     */
    @Getter
    @Setter
    boolean searChableSysTyNm = false;

    /**
     * orderableSysTyNm
     */
    @Getter
    @Setter
    boolean orderableSysTyNm = false;

    /**
     * searchSysTyNmValue
     */
    @Getter
    @Setter
    String searchSysTyNmValue;

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
     * dir
     */
    @Getter
    @Setter
    String dir;

    /**
     *
     * @param columnName
     * @return
     */
    public boolean isOrderByColumn(String columnName) {
        switch (columnName) {
            case "seqNo":
                return orderableSeqNo;
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
            case "oCCRRNCDt" :
                return orderableoCCRRNCDt;
            default:
                return false;
        }
    }

}
