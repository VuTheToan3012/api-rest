package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for Send Param to Check Duplicate For MonCmsNm By Template and Location
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-12-01
 */

public class ReadCidsMonitorManagerMonCmsNmDuplicateParamDTO {
    /**
     * monCmsNm
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 255)
    private String monCmsNm;

    /**
     * wkTySeqNo ( Export/Import type management table SEQNO )
     */
    @Getter
    @Setter
    @NotNull
    private Integer wkTySeqNo;

    /**
     * locTySeqNo ( Location type information management table SEQNO )
     */
    @Getter
    @Setter
    @NotNull
    private Integer locTySeqNo;

}
