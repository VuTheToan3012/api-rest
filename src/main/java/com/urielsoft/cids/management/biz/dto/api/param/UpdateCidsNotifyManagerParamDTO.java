package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
/**
 * DTO for Send Param to Update Cids Notify
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-24
 */

public class UpdateCidsNotifyManagerParamDTO {

    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;
    /**
     * wkTySeqNo ( Export/Import type management table SEQNO )
     */
    @Getter
    @Setter
    private Integer wkTySeqNo;

    /**
     * locTySeqNo ( Location type information management table SEQNO )
     */
    @Getter
    @Setter
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


}
