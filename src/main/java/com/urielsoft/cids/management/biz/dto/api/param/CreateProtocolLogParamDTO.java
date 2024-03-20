package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Create Protocol Log Param DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-10-17
 */

public class CreateProtocolLogParamDTO {
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

}
