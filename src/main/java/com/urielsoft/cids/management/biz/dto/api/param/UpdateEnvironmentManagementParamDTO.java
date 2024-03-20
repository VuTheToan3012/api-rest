package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Update Environment Management Param DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-12-22
 */

public class UpdateEnvironmentManagementParamDTO {
    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * envPropVal
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 200)
    private String envPropVal;

    /**
     * note
     */
    @Getter
    @Setter
    @Size(max = 1000)
    private String note;
}
