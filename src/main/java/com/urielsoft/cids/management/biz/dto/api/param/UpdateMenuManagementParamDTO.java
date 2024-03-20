package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;


/**
 * Update Menu Param
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */

public class UpdateMenuManagementParamDTO {
    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * visibleYesNo
     */
    @Getter
    @Setter
    private Integer vwYn;

    /**
     * menuNmBen
     */
    @Getter
    @Setter
    private String menuNmBen;

    /**
     * menuNmKor
     */
    @Getter
    @Setter
    private String menuNmKor;

}
