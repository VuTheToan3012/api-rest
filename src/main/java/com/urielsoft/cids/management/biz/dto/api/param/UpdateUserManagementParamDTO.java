package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Update USer Param
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */

public class UpdateUserManagementParamDTO {
    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * userNm
     */
    @Getter
    @Setter
    @Size( max = 50)
    private String userNm;

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
     * User Id
     */
    @Getter
    @Setter
    @Size( max = 50)
    private String userId;
}
