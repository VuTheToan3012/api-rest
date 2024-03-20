package com.urielsoft.cids.management.biz.dto;

import com.urielsoft.cids.management.biz.common.enums.Protocol;
import com.urielsoft.cids.management.biz.dto.socket.CidsMonitorOperationMsgRequestDataDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Create Socket DTO
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-08-08
 */
public class CreateCidsMonitorOperationMsgRequestSocketDTO {
    /**
     * protocol
     */
    @Getter
    @Setter
    @NotNull
    private Protocol protocol;

    /**
     * transactionId
     */
    @Getter
    @Setter
    @NotNull
    private String transactionId;

    /**
     * requestData
     */
    @Getter
    @Setter
    @NotNull
    private CidsMonitorOperationMsgRequestDataDTO requestData;
}
