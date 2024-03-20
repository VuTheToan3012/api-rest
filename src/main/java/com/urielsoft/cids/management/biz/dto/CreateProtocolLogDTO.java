package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Create Protocol Log DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-10-17
 */

public class CreateProtocolLogDTO {
    /**
     * logId
     */
    @Getter
    @Setter
    @Size(max = 50)
    @NotNull
    private String logId;

    /**
     * Protocol Name
     */
    @Getter
    @Setter
    @NotNull
    private String protocolName;

    /**
     * result
     */
    @Getter
    @Setter
    @NotNull
    private String result;

    /**
     * Request Data
     */
    @Getter
    @Setter
    @NotNull
    private String requestData;

    /**
     * Response Data
     */
    @Getter
    @Setter
    @NotNull
    private String responseData;

    /**
     * occrrnc User SeqNo
     */
    @Getter
    @Setter
    @NotNull
    private int occrrncUserSeqNo;

}
