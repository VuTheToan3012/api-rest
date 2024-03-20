package com.urielsoft.cids.management.biz.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Search And Pagination Error History Results DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-17
 */
public class SearchAndPaginationErrorHistoryResultsDTO {
    /**
     * View Error History DTO
     */
    @Getter
    @Setter
    private List<ViewErrorHistoryDTO> viewErrorHistoryDTOS;

    /**
     * Count Filter Data
     */
    @Getter
    @Setter
    private Integer countFilterData;
}