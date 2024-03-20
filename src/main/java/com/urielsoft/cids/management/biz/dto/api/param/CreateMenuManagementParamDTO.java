package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;


import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Create Menu Param
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */

public class CreateMenuManagementParamDTO {

    /**
     * menuNmEng
     */
    @Getter
    @Setter
    private String menuNmEng;

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

    /**
     * tagId
     */
    @Getter
    @Setter
    @Size(max = 10, message = "{validation.name.size.too_long}")
    private String tagId;

    /**
     * visibleYesNo
     */
    @Getter
    @Setter
    private Integer vwYn;

    /**
     * modifyUserSeqNo
     */
    @Getter
    @Setter
    private Integer mdfyUserSeqNo;
}
