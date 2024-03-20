package com.urielsoft.cids.management.biz.service.impl;

import com.urielsoft.cids.management.biz.common.enums.CidsLogLevelEnum;
import com.urielsoft.cids.management.biz.common.enums.CidsLogMethod;
import com.urielsoft.cids.management.biz.dto.SearchAndPaginationUsageHistoryResultsDTO;
import com.urielsoft.cids.management.biz.dto.SearchUsageHistoryPaginationStatusDTO;
import com.urielsoft.cids.management.biz.dto.ViewUsageHistoryDTO;
import com.urielsoft.cids.management.biz.dto.search.Columns;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.dto.search.Order;
import com.urielsoft.cids.management.biz.dto.search.Search;
import com.urielsoft.cids.management.biz.mapper.UsageHistoryMapper;
import com.urielsoft.cids.management.biz.service.CidsLogService;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import com.urielsoft.cids.management.common.security.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Usage History Services Impl
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsageHistoryServiceImpl implements UsageHistoryService {
    /**
     * Usage History Mapper
     */
    private final UsageHistoryMapper usageHistoryMapper;

    /**
     * Cids Log Service
     */
    private final CidsLogService cidsLogService;

    /**
     * Get All List Monitor
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<ViewUsageHistoryDTO> listAllUsageHistoryData() throws Exception {
        return this.usageHistoryMapper.selectUsageHistoryDataAllList();
    }

    /**
     * Get Usage History By Seq
     *
     * @param usageHistorySeq
     * @return
     * @throws Exception
     */
    @Override
    public ViewUsageHistoryDTO getUsageHistoryDataBySeq(int usageHistorySeq) throws Exception {
        ViewUsageHistoryDTO viewUsageHistoryDTO = this.usageHistoryMapper.selectUsageHistoryDataBySeq(usageHistorySeq);
        if (viewUsageHistoryDTO == null) {
            throw new BizException(ExceptionCode.BIZ_DATA_NOT_FOUND);
        }
        return viewUsageHistoryDTO;
    }

    /**
     * Get Usage History Data List Pagination And Search
     *
     * @param dataTableRequest
     * @return
     */
    @Override
    public SearchAndPaginationUsageHistoryResultsDTO getUsageHistoryDataListPaginationAndSearch(DataTableRequest dataTableRequest) throws DataAccessException {

        int start = dataTableRequest.getStart();
        int length = dataTableRequest.getLength();

        SearchUsageHistoryPaginationStatusDTO searchUsageHistoryPaginationStatusDTO = new SearchUsageHistoryPaginationStatusDTO();
        // GET column data
        List<Columns> columns = dataTableRequest.getColumns();
        for (Columns column : columns) {
            String columnName = column.getData();
            Search search = column.getSearch();

            if ("locTySeqNo".equals(columnName)) {
                searchUsageHistoryPaginationStatusDTO.setSearChableLocTySeqNo(column.isSearchable());
                searchUsageHistoryPaginationStatusDTO.setOrderableLocTySeqNo(column.isOrderable());
                if (searchUsageHistoryPaginationStatusDTO.isSearChableLocTySeqNo() == true) {
                    if (search.getValue() != null && search.getValue() != "") {
                        searchUsageHistoryPaginationStatusDTO.setSearchLocTySeqNoValue(search.getValue());
                    }
                }
            } else if ("monNm".equals(columnName)) {
                searchUsageHistoryPaginationStatusDTO.setSearChableMonNm(column.isSearchable());
                searchUsageHistoryPaginationStatusDTO.setOrderableMonNm(column.isOrderable());
                if (searchUsageHistoryPaginationStatusDTO.isSearChableMonNm() == true) {
                    if (search.getValue() != null && search.getValue() != "") {
                        searchUsageHistoryPaginationStatusDTO.setSearchMonNmValue(search.getValue());
                    }
                }
            } else if ("wkTySeqNo".equals(columnName)) {
                searchUsageHistoryPaginationStatusDTO.setSearChableWkTySeqNo(column.isSearchable());
                searchUsageHistoryPaginationStatusDTO.setOrderableWkTySeqNo(column.isOrderable());
                if (searchUsageHistoryPaginationStatusDTO.isSearChableWkTySeqNo() == true) {
                    if (search.getValue() != null && search.getValue() != "") {
                        searchUsageHistoryPaginationStatusDTO.setSearchWkTySeqNoValue(search.getValue());
                    }
                }
            } else if ("regDt".equals(columnName)) {
                searchUsageHistoryPaginationStatusDTO.setSearChableRegDt(column.isSearchable());
                searchUsageHistoryPaginationStatusDTO.setOrderableRegDt(column.isOrderable());
                if (searchUsageHistoryPaginationStatusDTO.isSearChableRegDt() == true) {
                    if (search.getValue() != null && search.getValue() != "") {
                        LocalDate regDate = LocalDate.parse(search.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        searchUsageHistoryPaginationStatusDTO.setSearchRegDtValue(String.valueOf(regDate));
                    }
                }
            }
        }

        // GET Order Data
        List<Order> orders = dataTableRequest.getOrder();
        for (Order order : orders
        ) {
            String columnName = order.getColumn();
            if (searchUsageHistoryPaginationStatusDTO.isOrderByColumn(columnName)) {
                searchUsageHistoryPaginationStatusDTO.setDir(order.getDir());
                searchUsageHistoryPaginationStatusDTO.setSortBy(order.getColumn());
            }

        }

        SearchAndPaginationUsageHistoryResultsDTO dblist = new SearchAndPaginationUsageHistoryResultsDTO();

        dblist.setViewUsageHistoryDTOS(this.usageHistoryMapper.selectUsageHistoryDataListPaginationAndSearch(start, length, searchUsageHistoryPaginationStatusDTO));

        if (dblist.getViewUsageHistoryDTOS() == null || dblist.getViewUsageHistoryDTOS().size() == 0) {
            dblist.setCountFilterData(0);
        } else {
            dblist.setCountFilterData(dblist.getViewUsageHistoryDTOS().get(0).getCountFilterData());
        }

        return dblist;
    }

    /**
     * Get User Login ID
     *
     * @return
     */
    @Override
    public String getUserLoginID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        return loginUserDetails.getUserId();
    }

    /**
     * Generate Log Data
     *
     * @param cidsLogMethod
     * @param manipulation
     * @return
     */
    @Override
    public String generateLogData(CidsLogMethod cidsLogMethod, String manipulation) {
        String userId = getUserLoginID();

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedCurrentTime = currentTime.format(formatter);

        String action = "[" + formattedCurrentTime + "]" + " - " + cidsLogMethod.getKey() + manipulation + " With UserId : " + userId;

        return action;
    }

    /**
     * Create Protocol Log
     *
     * @param logId
     * @param protocolName
     * @param monitorId
     * @param result
     * @param requestData
     * @param responseData
     */
    @Override
    public void createProtocolLogWithMonitorId(String logId, String protocolName, Integer monitorId, String result, String requestData, String responseData, int occrrncUserSeqNo) {
        this.cidsLogService.registerProtocolLog(logId, protocolName, monitorId, result, requestData, responseData, occrrncUserSeqNo);
    }

    /**
     * Create Protocol Log No Minitor Id
     *
     * @param logId
     * @param protocolName
     * @param result
     * @param requestData
     * @param responseData
     */
    @Override
    public void createProtocolLog(String logId, String protocolName, String result, String requestData, String responseData, int occrrncUserSeqNo) throws Exception {

        this.cidsLogService.registerProtocolLog(logId, protocolName, result, requestData, responseData, occrrncUserSeqNo);
    }

    /**
     * Create Operation Log
     *
     * @param cidsLogLevel
     * @param cidsLogMethod
     * @param manipulation
     */
    public void createOperationLog(String logId, CidsLogLevelEnum cidsLogLevel, CidsLogMethod cidsLogMethod, String manipulation) {
        String action = generateLogData(cidsLogMethod, manipulation);
        this.cidsLogService.registerOperationLog(logId, cidsLogLevel, action);
    }

    /**
     * Create Operation Log With MonitorId
     *
     * @param logId
     * @param cidsLogLevel
     * @param monitorId
     * @param cidsLogMethod
     * @param manipulation
     */
    public void createOperationLogWithMonitorId(String logId, CidsLogLevelEnum cidsLogLevel, Integer monitorId, CidsLogMethod cidsLogMethod, String manipulation) {
        String action = generateLogData(cidsLogMethod, manipulation);
        this.cidsLogService.registerOperationLog(logId, cidsLogLevel, action, monitorId);
    }

}