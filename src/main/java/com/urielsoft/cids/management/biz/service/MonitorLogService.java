package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.dto.SearchAndPaginationMonitorLogResultsDTO;
import com.urielsoft.cids.management.biz.dto.ViewMonitorLogDTO;
import com.urielsoft.cids.management.biz.dto.api.param.ReadMonitorLogByDateParamDTO;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Monitor Log Service
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-09-27
 */
public interface MonitorLogService {
    /**
     * Count Total Record
     *
     * @return
     * @throws Exception
     */
    public List<ViewMonitorLogDTO> listAllMonitorLogData() throws Exception;

    /**
     * Get Error History Data By Seq
     *
     * @param monitorLogSeq
     * @return
     * @throws Exception
     */
    ViewMonitorLogDTO getMonitorLogDataBySeq(int monitorLogSeq) throws Exception;

    /**
     * Get Monitor Log Data By Date
     *
     * @param readMonitorLogByDateParamDTO
     * @return
     * @throws DataAccessException
     */
    public List<ViewMonitorLogDTO> getMonitorLogDataByDate(ReadMonitorLogByDateParamDTO readMonitorLogByDateParamDTO) throws DataAccessException;

    /**
     * Get Monitor Log Data List Pagination And Search
     *
     * @param dataTableRequest
     * @return
     */
    SearchAndPaginationMonitorLogResultsDTO getMonitorLogDataListPaginationAndSearch(DataTableRequest dataTableRequest);
}
