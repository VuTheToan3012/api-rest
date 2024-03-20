package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * DTO for update CidsNotifyManager
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-24
 */

public class UpdateCidsNotifyManagerDTO {
    /**
     * seqNo
     */
    @Getter
    @NotNull
    @Setter
    private Integer seqNo;

    /**
     * wkTyNm ( Task Type Name ( IMPORT / EXPORT ) )
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 20)
    private String WkTyNm;

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
     * notiEng ( Notice (English) )
     */
    @Getter
    @Setter
    private String notiEng;

    /**
     * notiEng ( Notice (Bengali) )
     */
    @Getter
    @Setter
    private String notiBen;

    /**
     * msgFwdYn ( Message Forward Y/N )
     */
    @Getter
    @Setter
    @NotNull
    private Integer msgFwdYn;

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
