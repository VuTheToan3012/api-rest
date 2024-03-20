package com.urielsoft.cids.management.biz.dto;

import com.urielsoft.cids.management.biz.common.enums.Protocol;
import com.urielsoft.cids.management.biz.dto.socket.CidsMonitorOperationModifyMonitorRequestDataDTO;
import com.urielsoft.cids.management.biz.dto.socket.CidsMonitorOperationTmplatRequestDataDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Create Cids Monitor Operation Modify Monitor Request Socket DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-10-26
 */
public class CreateCidsMonitorOperationModifyMonitorRequestSocketDTO {
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
    private CidsMonitorOperationModifyMonitorRequestDataDTO requestData;
}
