package com.urielsoft.cids.management.biz.dto.api.result;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * System Log Detail Result DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */

public class SystemLogDetailResultDTO {

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
     * Count Filter Data
     */
    @Getter
    @Setter
    private Integer countFilterData;
}
