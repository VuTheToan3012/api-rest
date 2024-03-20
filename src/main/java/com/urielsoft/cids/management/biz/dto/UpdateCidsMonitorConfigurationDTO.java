package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * update Cids Monitor Configuration dto
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-05
 */
public class UpdateCidsMonitorConfigurationDTO {
    /**
     * seqNo
     */
    @Setter
    @NotNull
    @Getter
    private Integer seqNo;



    /**
     * locNm ( Location Information Name )
     */
    @Getter
    @NotNull
    @Setter
    @Size(max = 255)
    private String locNm;

    /**
     * wkTyNm ( Task Type Name ( IMPORT / EXPORT ) )
     */
    @Getter
    @NotNull
    @Setter
    @Size(max = 20)
    private String wkTyNm;

    /**
     * wkTySeqNo ( Export/Import type management table SEQNO )
     */
    @Getter
    @NotNull
    @Setter
    private Integer wkTySeqNo;

    /**
     * locTySeqNo ( Location type information management table SEQNO )
     */
    @Getter
    @NotNull
    @Setter
    private Integer locTySeqNo;

    /**
     * tmplatSeqNo ( Template management table SEQNO )
     */
    @NotNull
    @Getter
    @Setter
    private Integer tmplatSeqNo;

    /**
     * usage ( use YN )
     */
    @NotNull
    @Getter
    private Integer usage;

    /**
     * Check usage with 0 or 1
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
     * usage ( modify date )
     */
    @Getter
    @NotNull
    @Setter
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
}
