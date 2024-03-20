package com.urielsoft.cids.management.biz.dto;


import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * DTO for update Menu
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */


public class UpdateMenuManagementDTO {

    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * menuNmEng
     */
    @Getter
    @Setter
    private String menuNmEng;

    /**
     * visibleYesNo
     */
    @Getter
    @Setter
    private Integer vwYn;

    /**
     * mdfDt
     */
    @Getter
    @Setter
    @NotNull
    private String mdfyDt;

    /**
     * modifyUserSeqNo
     */
    @Getter
    @Setter
    private Integer mdfyUserSeqNo;

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
