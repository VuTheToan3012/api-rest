package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for Update Multilingual Management
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */
public class UpdateMultilingualManagementDTO {
    /**
     * seqNo
     */
    @NotNull
    @Getter
    @Setter
    private Integer seqNo;
    /**
     * Item Name(English)
     */
    @Getter
    @Setter
    private String itemNmEng;

    /**
     * Item Name(Bengali)
     */
    @Getter
    @Setter
    private String itemNmBen;

    /**
     * Item Name(Korean)
     */
    @Getter
    @Setter
    private String itemNmKor;

    /**
     * tagId
     */
    @Getter
    @Setter
    @Size(max = 10)
    private String tagId;
    /**
     * MdfyDt ( modify Date )
     */
    @Getter
    @Setter
    @NotNull
    private String MdfyDt;

    /**
     * regDt ( registration date )
     */
    @Getter
    @Setter
    @NotNull
    private String regDt;

    /**
     * mdfyUserSeqNO ( modify user)
     */
    @Getter
    @Setter
    @NotNull
    private Integer mdfyUserSeqNO;


}
