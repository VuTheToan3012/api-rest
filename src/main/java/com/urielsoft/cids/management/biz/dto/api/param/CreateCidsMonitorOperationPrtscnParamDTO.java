package com.urielsoft.cids.management.biz.dto.api.param;

import com.urielsoft.cids.management.biz.dto.socket.CidsMonitorOperationPrtscnRequestDataDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Create Cids Monitor Operation Prtscn Param DTO
 *
 * @author thangdt_bks (thangdt@gmail.com)
 * @version 1.0
 * @since 2023-08-08
 */
public class CreateCidsMonitorOperationPrtscnParamDTO {
    /**
     * requestData
     */
    @Getter
    @Setter
    @NotNull
    private CidsMonitorOperationPrtscnRequestDataDTO requestData;
}
