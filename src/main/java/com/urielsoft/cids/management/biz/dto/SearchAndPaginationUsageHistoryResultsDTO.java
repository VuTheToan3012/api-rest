package com.urielsoft.cids.management.biz.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Search And Pagination Usage History Results DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-17
 */
public class SearchAndPaginationUsageHistoryResultsDTO {
    /**
     * View Cids Notify Manager DTOS
     */
    @Getter
    @Setter
    private List<ViewUsageHistoryDTO> viewUsageHistoryDTOS;

    /**
     * Count Filter Data
     */
    @Getter
    @Setter
    private Integer countFilterData;
}