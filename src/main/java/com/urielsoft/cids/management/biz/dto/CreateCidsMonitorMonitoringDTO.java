package com.urielsoft.cids.management.biz.dto;

import com.urielsoft.cids.management.biz.common.enums.MonitorStatus;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for create Cids monitor monitoring
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */
public class CreateCidsMonitorMonitoringDTO {

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
    private MonitorStatus sts;

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
