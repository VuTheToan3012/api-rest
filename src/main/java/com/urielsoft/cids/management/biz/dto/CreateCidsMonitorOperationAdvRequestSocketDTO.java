package com.urielsoft.cids.management.biz.dto;

import com.urielsoft.cids.management.biz.common.enums.Protocol;
import com.urielsoft.cids.management.biz.dto.socket.CidsMonitorOperationADVDataDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Create Cids Monitor Operation Adv Request Socket DTO
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-08-18
 */
public class CreateCidsMonitorOperationAdvRequestSocketDTO {
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
     * Request Data
     */
    @Getter
    @Setter
    private CidsMonitorOperationADVDataDTO requestData;
}
