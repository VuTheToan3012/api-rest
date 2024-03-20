package com.urielsoft.cids.management.biz.dto.socket;

import com.urielsoft.cids.management.biz.common.enums.Protocol;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class CidsSocketMessageDTO {

    /**
     * Protocol
     */
    @Getter
    @Setter
    @NotNull
    private Protocol protocol;

    /**
     * MonitorId
     */
    @Getter
    @Setter
    private int monitorId;

    /**
     * TransactionId
     */
    @Getter
    @Setter
    private String transactionId;

    /**
     * requestData
     */
    @Getter
    @Setter
    private Object requestData;
}
