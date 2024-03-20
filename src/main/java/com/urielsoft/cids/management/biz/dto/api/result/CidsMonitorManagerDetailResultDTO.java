package com.urielsoft.cids.management.biz.dto.api.result;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Result for Cids Monitor by seqNo
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */

public class CidsMonitorManagerDetailResultDTO {
    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

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
     * locNm ( Location Information Name )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 255)
    private String locNm;

    /**
     * wkTyNm ( Task Type Name ( IMPORT / EXPORT ) )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 20)
    private String wkTyNm;

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
     * tmplatSeqNo ( Template management table SEQNO )
     */
    @Getter
    @Setter
    @NotNull
    private Integer tmplatSeqNo;

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

    /**
     * regDt ( registration date )
     */
    @Getter
    @Setter
    @NotNull
    private String regDt;

    /**
     * usage ( modify date )
     */
    @Getter
    @Setter
    @NotNull
    private String mdfyDt;

    /**
     * mdfyUserSeqNo ( Modify User by SeqNo )
     */
    @Getter
    @Setter
    @NotNull
    private Integer mdfyUserSeqNo;

    /**
     * tmplatNm ( Template Name )
     */
    @Getter
    @Setter
    private String tmplatNm;

    /**
     * Modify UserNm
     */
    @Getter
    @Setter
    private String userNm;
}
