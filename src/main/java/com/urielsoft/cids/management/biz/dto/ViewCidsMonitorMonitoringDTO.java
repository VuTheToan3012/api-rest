package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for view Cids monitor monitoring
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
public class ViewCidsMonitorMonitoringDTO {

    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * monNm ( Monitor Name )
     */
    @Getter
    @NotNull
    @Setter
    @Size(max = 255)
    private String monNm;

    /**
     * locNm ( Location Information Name )
     */
    @Getter
    @NotNull
    @Setter
    @Size(max = 255)
    private String locNm;

    /**
     * wkTyNm ( Task Type Name ( IMPORT / EXPORT ) )
     */
    @Getter
    @NotNull
    @Setter
    @Size(max = 20)
    private String wkTyNm;

    /**
     * wkTySeqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer wkTySeqNo;

    /**
     * moSeqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer monSeqNo;

    /**
     * stsInfo
     */
    @Getter
    @Setter
    @Size(max = 50)
    private String sts;

    /**
     * stsInfoCode
     */
    @Getter
    @Setter
    private Integer stsCd;

    /**
     * regDt
     */
    @Getter
    @Setter
    @NotNull
    private String regDt;

    /**
     * mdfDt
     */
    @Getter
    @Setter
    @NotNull
    private String mdfyDt;
}
