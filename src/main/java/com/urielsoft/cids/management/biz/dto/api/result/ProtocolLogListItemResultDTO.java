package com.urielsoft.cids.management.biz.dto.api.result;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for View Protocol Log List Item Result DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-28
 */

public class ProtocolLogListItemResultDTO {
    /**
     * seqNo ( Sequence Number )
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * TransactionId
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 50)
    private String tranId;

    /**
     * protNm
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 30)
    private String protNm;

    /**
     * monSeqNo
     */
    @Getter
    @Setter
    private Integer monSeqNo;

    /**
     * rslt
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 10)
    private String rslt;

    /**
     * reqData
     */
    @Getter
    @Setter
    private String reqData;

    /**
     * repnData
     */
    @Getter
    @Setter
    private String repnData;

    /**
     * oCCRRNCUserSeqNo
     */
    @Getter
    @Setter
    private String oCCRRNCUserSeqNo;

    /**
     * oCCRRNCDt
     */
    @Getter
    @Setter
    private String oCCRRNCDt;

}
