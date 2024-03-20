package com.urielsoft.cids.management.biz.dto;


import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for View Menu
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */


public class ViewMenuManagementDTO {

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
    @Size(max = 10)
    private String tagId;

    /**
     * visibleYesNo
     */
    @Getter
    @Setter
    private Integer vwYn;

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

    /**
     * modifyUserSeqNo
     */
    @Getter
    @Setter
    private Integer mdfyUserSeqNo;
}