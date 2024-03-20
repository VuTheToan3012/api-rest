package com.urielsoft.cids.management.biz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Search And Pagination Monitor Log Results DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */
public class SearchAndPaginationMonitorLogResultsDTO {
    /**
     * View Cids Notify Manager DTOS
     */
    @Getter
    @Setter
    private List<ViewMonitorLogDTO> viewMonitorLogDTOS;

    /**
     * Count Filter Data
     */
    @Getter
    @Setter
    private Integer countFilterData;
}