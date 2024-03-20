package com.urielsoft.cids.management.biz.dto.api.param;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Create Operation Log Param DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-10-17
 */

public class CreateOperationLogParamDTO {
    /**
     * logId
     */
    @Getter
    @Setter
    @Size(max = 50)
    @NotNull
    private String logId;

    /**
     * Cids Log Level Enum
     */
    @Getter
    @Setter
    @NotNull
    private CidsLogLevelEnum cidsLogLevelEnum;

    /**
     * Cids Log Method
     */
    @Getter
    @Setter
    @NotNull
    private CidsLogMethod cidsLogMethod;

    /**
     * Manipulation
     */
    @Getter
    @Setter
    @NotNull
    private String manipulation;

}
