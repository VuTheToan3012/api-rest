package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for View Error History
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */

public class ViewErrorHistoryDTO {
    /**
     * seqNo ( Sequence Number )
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * wkTyNm ( Task Type Name ( IMPORT / EXPORT ) )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 20)
    private String wkTyNm;

    /**
     * wkTySeqNo ( Export/Import type management table SEQNO )
     */
    @Getter
    @Setter
    @NotNull
    private Integer wkTySeqNo;

    /**
     * locTySeqNo ( Location type information management table SEQNO )
     */
    @Getter
    @Setter
    @NotNull
    private Integer locTySeqNo;

    /**
     * monNm ( Monitor Name )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 255)
    private String monNm;

    /**
     * monSeqNo ( Monitor Information Registration Number )
     */
    @Getter
    @Setter
    @NotNull
    private Integer monSeqNo;

    /**
     * logData ( Control Log )
     */
    @Getter
    @Setter
    private String logData;

    /**
     * regDt ( Registration Date )
     */
    @Getter
    @Setter
    @NotNull
    private String regDt;

    /**
     * locNm ( Location Information Name )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 255)
    private String locNm;

    /**
     * Count Filter Data
     */
    @Getter
    @Setter
    private Integer countFilterData;
}
