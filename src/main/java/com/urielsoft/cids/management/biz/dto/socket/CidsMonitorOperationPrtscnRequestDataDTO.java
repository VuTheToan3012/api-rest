package com.urielsoft.cids.management.biz.dto.socket;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Cids Monitor Operation Prtscn Request Data DTO
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-08-08
 */
public class CidsMonitorOperationPrtscnRequestDataDTO {

    /**
     * monitorId
     */
    @Getter
    @Setter
    @NotNull
    private String monitorId;
}
