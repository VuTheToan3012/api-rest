package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * CREATE USER PARAM
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */

public class CreateUserManagementParamDTO {
    /**
     * userNm ( UserName )
     */
    @Getter
    @Setter
    @Size( max = 50)
    private String userNm;

    /**
     * userId
     */
    @Getter
    @Setter
    @Size( max = 50)
    private String userId;

    /**
     * userPw ( User Password )
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
}
