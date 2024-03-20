package com.urielsoft.cids.management.biz.dto.api.result;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * List Item Cids Monitor Configuration
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-05
 */
public class CidsMonitorConfigurationListItemResultDTO {

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

    /**
     * Modify UserNm
     */
    @Getter
    @Setter
    private String userNm;

}
