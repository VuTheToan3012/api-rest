package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for Send Param to Create Cids Monitor
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */

public class CreateCidsMonitorManagerParamDTO {

    /**
     * monNm ( Monitor Name )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 255)
    private String monNm;

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

    /**
     * usage ( use YN )
     */
    @Getter
    @NotNull
    private Integer usage;

    /**
     * Check usage with 0 or 1
     *
     * @param usage
     */
    public void setUsage(Integer usage) {

        if (usage != null && (usage == 0 || usage == 1)) {
            this.usage = usage;
        } else {
            throw new IllegalArgumentException("usage must be 0 or 1");
        }
    }

    /**
     * ip ( CIDS MONITOR IP )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 50)
    private String ip;

    /**
     * note
     */
    @Getter
    @Setter
    private String note;

}
