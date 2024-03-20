package com.urielsoft.cids.management.biz.service;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.SearchAndPaginationUsageHistoryResultsDTO;
import com.urielsoft.cids.management.biz.dto.ViewUsageHistoryDTO;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Usage History Services.
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-26
 */
public interface UsageHistoryService {
    /**
     * View Usage History Data
     *
     * @return
     * @throws Exception
     */
    public List<ViewUsageHistoryDTO> listAllUsageHistoryData() throws Exception;

    /**
     * Get Usage History Data By Seq
     *
     * @param usageHistorySeq
     * @return
     * @throws Exception
     */
    ViewUsageHistoryDTO getUsageHistoryDataBySeq(int usageHistorySeq) throws Exception;

    /**
     * Get Usage History Data List Pagination And Search
     * @param dataTableRequest
     * @return
     * @throws DataAccessException
     */
    public SearchAndPaginationUsageHistoryResultsDTO getUsageHistoryDataListPaginationAndSearch(DataTableRequest dataTableRequest) throws DataAccessException;

    /**
     * Get User Login ID
     * @return
     */
    public String getUserLoginID();

    /**
     * Generate Log Data
     * @param cidsLogMethod
     * @param manipulation
     * @return
     */
    public String generateLogData(CidsLogMethod cidsLogMethod , String manipulation);

    /**
     *  Create Protocol Log
     * @param logId
     * @param protocolName
     * @param monitorId
     * @param result
     * @param requestData
     * @param responseData
     * @param occrrncUserSeqNo
     */
    public void createProtocolLogWithMonitorId(String logId, String protocolName, Integer monitorId, String result, String requestData, String responseData, int occrrncUserSeqNo);

    /**
     * Create Protocol Log No Monitor Id
     * @param logId
     * @param protocolName
     * @param result
     * @param requestData
     * @param responseData
     * @param occrrncUserSeqNo
     * @throws Exception
     */
    public void createProtocolLog(String logId, String protocolName, String result, String requestData, String responseData, int occrrncUserSeqNo) throws Exception;

    /**
     * Create Operation Log
     * @param logId
     * @param cidsLogLevel
     * @param cidsLogMethod
     * @param manipulation
     */
    public void createOperationLog(String logId, CidsLogLevelEnum cidsLogLevel , CidsLogMethod cidsLogMethod , String manipulation);

    /**
     * Create Operation Log With Monitor Id
     * @param logId
     * @param cidsLogLevel
     * @param monitorId
     * @param cidsLogMethod
     * @param manipulation
     */
    public void createOperationLogWithMonitorId(String logId, CidsLogLevelEnum cidsLogLevel, Integer monitorId , CidsLogMethod cidsLogMethod , String manipulation);
}

