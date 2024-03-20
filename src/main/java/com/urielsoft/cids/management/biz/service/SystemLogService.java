package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.SearchAndPaginationSystemLogResultsDTO;
import com.urielsoft.cids.management.biz.dto.ViewSystemLogDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadSystemLogByDateParamDTO;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;

import java.util.List;

/**
 * System Log Service
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */
public interface SystemLogService {
    /**
     * Count Total Record
     *
     * @return
     * @throws Exception
     */
    public List<ViewSystemLogDTO> listAllSystemLogData() throws Exception;

    /**
     * Get Error History Data By Seq
     *
     * @param systemLogSeq
     * @return
     * @throws Exception
     */
    ViewSystemLogDTO getSystemLogDataBySeq(int systemLogSeq) throws Exception;

    /**
     * Get System Log Data By Date
     *
     * @param readSystemLogByDateParamDTO
     * @return
     * @throws Exception
     */
    List<ViewSystemLogDTO> getSystemLogDataByDate(ReadSystemLogByDateParamDTO readSystemLogByDateParamDTO) throws Exception;

    /**
     * Get System Log Data List Pagination And Search
     *
     * @param dataTableRequest
     * @return
     */
    SearchAndPaginationSystemLogResultsDTO getSystemLogDataListPaginationAndSearch(DataTableRequest dataTableRequest);
}
