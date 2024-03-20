package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for View User
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */

public class ViewUserManagementDTO {
    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * userNm ( username )
     */
    @Getter
    @Setter
    @Size(max = 50)
    private String userNm;

    /**
     * userId
     */
    @Getter
    @Setter
    @Size(max = 50)
    private String userId;

    /**
     * userPw
     */
    @Getter
    @Setter
    private String userPw;

    /**
     * authSeqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer authSeqNo;

    /**
     * regDt ( Registration date )
     */
    @Getter
    @Setter
    @NotNull
    private String regDt;

    /**
     * mdfyDt ( Modify Date )
     */
    @Getter
    @Setter
    @NotNull
    private String mdfyDt;

    /**
     * authTyNm
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 50)
    private String authTyNm;

    /**
     * mdfyUserSeqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer mdfyUserSeqNo;
}
