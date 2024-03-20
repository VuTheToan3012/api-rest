package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Update Advertise Mng Param DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-23
 */

public class UpdateAdvertiseManagementParamDTO {
    /**
     * stTm
     */
    @Getter
    @Setter
    @Size(max = 4)
    private String stTm;

    /**
     * endTm
     */
    @Getter
    @Setter
    @Size(max = 4)
    private String endTm;

    /**
     * usageYn
     */
    @Getter
    private Integer usageYn;
    public void setUsageYn(Integer usageYn) {

        if (usageYn != null && (usageYn == 0 || usageYn == 1)) {
            this.usageYn = usageYn;
        } else {
            throw new IllegalArgumentException("usage must be 0 or 1");
        }
    }
}
