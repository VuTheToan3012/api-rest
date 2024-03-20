package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for update Cids monitor monitoring
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
public class UpdateCidsMonitorMonitoringDTO {

    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * monSeqNo
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
     * mdfDt
     */
    @Getter
    @Setter
    @NotNull
    private String mdfyDt;
}
