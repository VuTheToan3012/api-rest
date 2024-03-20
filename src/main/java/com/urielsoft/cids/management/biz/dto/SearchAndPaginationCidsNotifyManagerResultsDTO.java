package com.urielsoft.cids.management.biz.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Search And Pagination Cids Notify Manager Results DTO
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-17
 */
public class SearchAndPaginationCidsNotifyManagerResultsDTO {
    /**
     * View Cids Notify Manager DTOS
     */
    @Getter
    @Setter
    private List<ViewCidsNotifyManagerDTO> viewCidsNotifyManagerDTOS;

    /**
     * Count Filter Data
     */
    @Getter
    @Setter
    private Integer countFilterData;
}