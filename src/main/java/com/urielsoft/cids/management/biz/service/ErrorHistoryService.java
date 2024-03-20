package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.SearchAndPaginationErrorHistoryResultsDTO;
import com.urielsoft.cids.management.biz.dto.ViewErrorHistoryDTO;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;

import java.util.List;

/**
 * Error History Service
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */
public interface ErrorHistoryService {
    /**
     * View Error History Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewErrorHistoryDTO> listAllErrorHistoryData() throws Exception;

    /**
     * Get Error History Data By Seq
     *
     * @param errorHistorySeq
     * @return
     * @throws Exception
     */
    ViewErrorHistoryDTO getErrorHistoryDataBySeq(int errorHistorySeq) throws Exception;

    /**
     * Get Error History Data List Pagination And Search
     *
     * @param dataTableRequest
     * @return
     */
    SearchAndPaginationErrorHistoryResultsDTO getErrorHistoryDataListPaginationAndSearch(DataTableRequest dataTableRequest);
}
