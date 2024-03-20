package com.urielsoft.cids.management.biz.dto.socket;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
/**
 * Request Socket Data
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-08-08
 */
public class CidsMonitorOperationTmplatRequestDataDTO {
    @Getter
    @Setter
    @NotNull
    private String specifyType;

    /**
     * locationId
     */
    @Getter
    @Setter
    @NotNull
    private Integer specifyId;
}
