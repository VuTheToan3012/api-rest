package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 *  Cids Monitor Monitoring Check LocAndTy ParamDTO
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-05
 */
public class CidsMonitorConfigurationFindLocAndTyParamDTO {

    /**
     * locTySeqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer locTySeqNo;

    /**
     * wkTySeqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer wkTySeqNo;
}
