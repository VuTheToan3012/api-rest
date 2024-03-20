package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Update Environment Management DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-12-22
 */

public class UpdateEnvironmentManagementDTO {
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
     * note
     */
    @Getter
    @Setter
    @Size(max = 1000)
    private String note;

    /**
     * mdfyUserSeqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer mdfyUserSeqNo;
}
