package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
/**
 *  Cids Monitor Configuration param dto
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-05
 */
public class UpdateCidsMonitorConfigurationParamDTO {
    /**
     * seqNo
     */
    @Setter
    @NotNull
    @Getter
    private Integer seqNo;

    /**
     * tmplatSeqNo ( Template management table SEQNO )
     */
    @Getter
    @Setter
    @NotNull
    private Integer tmplatSeqNo;
}
