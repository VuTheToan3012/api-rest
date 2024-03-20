package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Read Monitor Log By Date Param DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-29
 */

public class ReadMonitorLogByDateParamDTO {
    /**
     * Start Date
     */
    @Getter
    @Setter
    private String startDate;

    /**
     * End Date
     */
    @Getter
    @Setter
    private String endDate;
}
