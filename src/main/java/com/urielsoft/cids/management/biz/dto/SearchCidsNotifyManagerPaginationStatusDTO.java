package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Cids Notify Manager Pagination Search
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-24
 */
public class SearchCidsNotifyManagerPaginationStatusDTO {
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
    String searhSeqNoValue ;

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
    String searchWkTySeqNoValue ;

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
    String[] searchLocTySeqNoValue ;

    /**
     * searChableNotiEng
     */
    @Getter
    @Setter
    boolean searChableNotiEng = false;

    /**
     * orderableNotiEng
     */
    @Getter
    @Setter
    boolean orderableNotiEng = false;

    /**
     * searchNotiEng
     */
    @Getter
    @Setter
    String searchNotiEngValue;

    /**
     * searChableNotiBen
     */
    @Getter
    @Setter
    boolean searChableNotiBen= false;

    /**
     * orderableNotiBen
     */
    @Getter
    @Setter
    boolean orderableNotiBen = false;

    /**
     * searchNotiBenValue
     */
    @Getter
    @Setter
    String searchNotiBenValue ;

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
    String searchRegDtValue ;

    /**
     * searChableMdfyUserSeqNo
     */
    @Getter
    @Setter
    boolean searChableMdfyUserSeqNo = false;

    /**
     * orderableMdfyUserSeqNo
     */
    @Getter
    @Setter
    boolean orderableMdfyUserSeqNo = false;

    /**
     * searchMdfyUserSeqNoValue
     */
    @Getter
    @Setter
    String searchMdfyUserSeqNoValue ;

    /**
     * searChableMdfyDt
     */
    @Getter
    @Setter
    boolean searChableMdfyDt = false;

    /**
     * orderableMdfyDt
     */
    @Getter
    @Setter
    boolean orderableMdfyDt = false;

    /**
     * searchMdfyDtValue
     */
    @Getter
    @Setter
    String searchMdfyDtValue ;

    /**
     * sortBy
     */
    @Getter
    @Setter
    String sortBy ;

    /**
     * dir
     */
    @Getter
    @Setter
    String dir ;

    /**
     *
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
            case "notiEng":
                return orderableNotiEng;
            case "notiBen":
                return orderableNotiBen;
            case "regDt":
                return orderableRegDt;
            case "mdfyUserSeqNO":
                return orderableMdfyUserSeqNo;
            case "MdfyDt":
                return orderableMdfyDt;


            default:
                return false;
        }
    }

}
