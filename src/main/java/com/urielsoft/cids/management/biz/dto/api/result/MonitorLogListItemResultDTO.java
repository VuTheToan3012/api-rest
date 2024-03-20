package com.urielsoft.cids.management.biz.dto.api.result;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * View Monitor Log DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */

public class MonitorLogListItemResultDTO {

    /**
     * SeqNo
     */
    @NotNull
    @Getter
    @Setter
    private Integer seqNo;

    /**
     * System Type Name
     */
    @NotNull
    @Getter
    @Setter
    @Size(max = 20)
    private String sysTyNm;

    /**
     * Log Id
     */
    @NotNull
    @Getter
    @Setter
    @Size(max = 50)
    private String logId;

    /**
     * Log Level
     */
    @Getter
    @Setter
    @Size(max = 10)
    private String logLvl;

    /**
     * LogData
     */
    @Getter
    @Setter
    private String logData;

    /**
     * Monitor SeqNo
     */
    @Getter
    @Setter
    private Integer monSeqNo;

    /**
     * OCCRRNC Date
     */
    @Getter
    @Setter
    private String oCCRRNCDt;

    /**
     * wkTyNm ( Task Type Name ( IMPORT / EXPORT ) )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 20)
    private String wkTyNm;

    /**
     * locNm ( Location Information Name )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 255)
    private String locNm;

    /**
     * monNm ( Monitor Name )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 255)
    private String monNm;

    /**
     * Count Filter Data
     */
    @Getter
    @Setter
    private Integer countFilterData;
}
