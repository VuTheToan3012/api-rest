package com.urielsoft.cids.management.biz.dto.socket;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class CidsMonitorOperationRestartRequestDataDTO {
    /**
     * monitorId
     */
    @Getter
    @Setter
    @NotNull
    private Integer monitorId;
}
