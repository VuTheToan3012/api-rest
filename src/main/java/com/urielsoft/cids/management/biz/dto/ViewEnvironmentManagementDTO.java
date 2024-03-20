package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * View Environment Management DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-12-22
 */

public class ViewEnvironmentManagementDTO {
    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * Env Category
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 10)
    private String envCtgry;

    /**
     * envPropNm
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 100)
    private String envPropNm;

    /**
     * envPropVal
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 200)
    private String envPropVal;

    /**
     * envPropValTy
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 10)
    private String envPropValTy;

    /**
     * note
     */
    @Getter
    @Setter
    @Size(max = 1000)
    private String note;

    /**
     * wkTySeqNo
     */
    @Getter
    @Setter
    private Integer wkTySeqNo;

    /**
     * locTySeqNo
     */
    @Getter
    @Setter
    private Integer locTySeqNo;

    /**
     * mdfyDt
     */
    @Getter
    @Setter
    @NotNull
    private String mdfyDt;

    /**
     * mdfyUserSeqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer mdfyUserSeqNo;

    /**
     * mdfyUserName
     */
    @Getter
    @Setter
    @NotNull
    private String userNm;
}
