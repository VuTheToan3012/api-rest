package com.urielsoft.cids.management.biz.dto.socket;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Cids Monitor Operation Modify Monitor Request Data DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-10-26
 */
public class CidsMonitorOperationModifyMonitorRequestDataDTO {
    @Getter
    @Setter
    @NotNull
    private String specifyType;

    /**
     * locationId
     */
    @Getter
    @Setter
    @NotNull
    private Integer specifyId;
}
